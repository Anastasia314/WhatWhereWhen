package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Checks user existing.
     *
     * @param login user login
     * @param password user password
     * @return Optional of User
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    Optional<User> authenticateUser(String login, String password) throws ServiceException;

    /**
     * Creates new User in database.
     *
     * @param user creating user
     * @param email user email
     * @param confirmPassword repetition of user password
     * @return true if the user is created
     * @throws ServiceException if input data is incorrect or passwords are different or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean registryUser(User user, String email, String confirmPassword) throws ServiceException;

    /**
     * Returns UserData by user ID.
     *
     * @param userId Id of user
     * @return Optional of UserData
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    Optional<UserData> findUserData(long userId) throws ServiceException;

    /**
     * Returns all existing users.
     *
     * @return list of Users
     * @throws ServiceException if {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    List<User> findAllUsers() throws ServiceException;

    /**
     * Changes user data.
     *
     * @param userData data for changing
     * @return true if data were changed
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean changeUserData(UserData userData) throws ServiceException;

    /**
     * Updates user image and returns true if the image was changed.
     *
     * @param user User
     * @param part user image
     * @return true if the image was changed
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean updateImage(User user, Part part) throws ServiceException;

    /**
     * Increases user rating on constant.
     *
     * @param userId long
     * @return updated user rating
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    long increaseRating(long userId) throws ServiceException;

    /**
     * Makes user rating equals 0.
     *
     * @param userId long
     * @return true if the rating was updated
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean removeRating(long userId) throws ServiceException;
}
