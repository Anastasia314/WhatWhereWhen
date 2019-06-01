package com.epam.whatwherewhen.service.impl;

import com.epam.whatwherewhen.dao.impl.UserDaoImpl;
import com.epam.whatwherewhen.dao.impl.UserDataDaoImpl;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.UserService;
import com.epam.whatwherewhen.util.MailSender;
import com.epam.whatwherewhen.util.PasswordEncryptor;
import com.epam.whatwherewhen.validation.RequestValidator;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Date: 12.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private UserServiceImpl() {
    }

    private static class SingletonHolder {
        private final static UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Optional<User> authenticateUser(String login, String password) throws ServiceException {
        if (!RequestValidator.getInstance().isValidLoginPassword(login, password)) {
            throw new ServiceException("Illegal login or password");
        }
        try {
            return UserDaoImpl.getInstance().findByLoginPassword(login, PasswordEncryptor.encryptPassword(password));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean registryUser(User user, String email, String confirmPassword) throws ServiceException {
        if (user == null || !RequestValidator.getInstance().isValidLoginPassword(user.getLogin(), user.getPassword())
                || !user.getPassword().equals(confirmPassword)
                || !RequestValidator.getInstance().isValidEmail(email)
                || loginAlreadyExist(user.getLogin())) {
            throw new ServiceException("Invalid input data");
        }
        MailSender mailSender = new MailSender(email, user.getLogin() + " " + user.getPassword());
        mailSender.start();
        user.setPassword(PasswordEncryptor.encryptPassword(user.getPassword()));
        try {
            return UserDaoImpl.getInstance().create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean loginAlreadyExist(String login) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findByLogin(login).isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<UserData> findUserData(long userId) throws ServiceException {
        try {
            return UserDataDaoImpl.getInstance().findEntityById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean changeUserData(UserData userData) throws ServiceException {
        if (!RequestValidator.getInstance().isValidUserData(userData)) {
            throw new ServiceException("Invalid input data");
        }
        try {
            UserDataDaoImpl userDataDao = UserDataDaoImpl.getInstance();
            Optional<UserData> oldUserData = userDataDao.findEntityById(userData.getUserId());
            if (oldUserData.isPresent()) {
                return userDataDao.update(userData);
            } else {
                return userDataDao.create(userData);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateImage(User user, Part part) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.updatePhoto(user.getUserId(), part.getInputStream());
            user.setPhoto(userDao.findUserPhoto(user.getUserId()));
        } catch (DaoException | IOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    public long increaseRating(long userId) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> userOptional = userDao.findEntityById(userId);
            if (!userOptional.isPresent()) {
                throw new ServiceException("User with userId " + userId + " doesn't exist.");
            }
            long updatedRating = userOptional.get().getRating() + 1;
            userDao.updateRating(userId, updatedRating);
            return updatedRating;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean removeRating(long userId) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().updateRating(userId, 0);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
