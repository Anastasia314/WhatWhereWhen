package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.dao.UserDao;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class UserDaoImpl implements BaseDao<User>, UserDao {
    private final static String SELECT_ALL_USERS
            = "SELECT UserId, Login, Password, Rating, isAdmin,IsActive, Photo FROM User";

    private final static String SELECT_USER_BY_ID = SELECT_ALL_USERS + " WHERE UserId=?";

    private final static String SELECT_USER_BY_LOGIN_PASSWORD = SELECT_ALL_USERS + " where Login=? AND Password=? " +
            "AND IsActive=true";

    private final static String SELECT_USER_BY_LOGIN = SELECT_ALL_USERS + " WHERE Login=?";

    private final static String SELECT_USER_PHOTO = "SELECT Photo FROM User WHERE UserId=?";

    private final static String DELETE_USER_BY_ID = "UPDATE User SET IsActive=false WHERE UserId=?";

    private final static String DELETE_USER_BY_LOGIN = "UPDATE User SET IsActive=false WHERE Login=? AND Password=?";

    private final static String INSERT_USER = "INSERT INTO User(Login,Password) VALUES(?,?)";

    private final static String UPDATE_USER
            = "UPDATE User SET Login=?, Password=?, IsAdmin=?, IsActive=?, Rating=? WHERE UserId=?";

    private final static String UPDATE_USER_PHOTO = "UPDATE User SET Photo=? WHERE UserId=?";

    private final static String UPDATE_RATING = "UPDATE User SET Rating=? WHERE UserId=?";

    private UserDaoImpl() {
    }

    private static class SingletonHolder {
        private final static UserDaoImpl INSTANCE = new UserDaoImpl();
    }

    public static UserDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_USERS)) {
                while (rs.next()) {
                    User user = getUserFromResultSet(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_USERS, e);
        }
        return users;
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        User user = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    user = getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_USER_BY_ID, e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByLoginPassword(String login, String password) throws DaoException {
        User user = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD)) {
            ps.setString(1, login);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    user = getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_USER_BY_LOGIN_PASSWORD, e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        User user = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    user = getUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_USER_BY_LOGIN, e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_ID)) {
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + DELETE_USER_BY_ID, e);
        }
        return result > 0;
    }

    @Override
    public boolean delete(String login, String password) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_LOGIN)) {
            ps.setString(1, login);
            ps.setString(2, password);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + DELETE_USER_BY_LOGIN, e);
        }
        return result > 0;
    }

    @Override
    public boolean create(User entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_USER, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setBoolean(3, entity.isAdmin());
            ps.setBoolean(4, entity.isActive());
            ps.setLong(5, entity.getRating());
            ps.setLong(6, entity.getUserId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_USER, e);
        }
        return result > 0;
    }

    @Override
    public boolean updateRating(long userId, long rating) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_RATING)) {
            ps.setLong(1, rating);
            ps.setLong(2, userId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_RATING, e);
        }
        return result > 0;
    }

    @Override
    public boolean updatePhoto(long UserId, InputStream photo) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PHOTO)) {
            ps.setLong(2, UserId);
            ps.setBlob(1, photo);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_USER_PHOTO, e);
        }
        return result > 0;
    }

    @Override
    public Blob findUserPhoto(long UserId) throws DaoException {
        Blob photo = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_PHOTO)) {
            ps.setLong(1, UserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    photo = rs.getBlob(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_USER_PHOTO, e);
        }
        return photo;
    }


    private User getUserFromResultSet(ResultSet rs) throws DaoException {
        User user = new User();
        try {
            user.setUserId(rs.getLong(1));
            user.setLogin(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setRating(rs.getLong(4));
            user.setAdmin(rs.getBoolean(5));
            user.setActive(rs.getBoolean(6));
            user.setPhoto(rs.getBlob(7));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }
}
