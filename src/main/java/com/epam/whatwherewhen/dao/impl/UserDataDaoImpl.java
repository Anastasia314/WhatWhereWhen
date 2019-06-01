package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;
import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.dao.UserDataDao;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Date: 03.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class UserDataDaoImpl implements BaseDao<UserData>, UserDataDao {
    private final static String SELECT_ALL_USER_DATA
            = "SELECT UserId, Email, FirstName, LastName, Birthdate, City FROM UserData";
    private final static String SELECT_USER_DATA_BY_ID = SELECT_ALL_USER_DATA + " where UserId=?";
    private final static String DELETE_USER_DATA_BY_ID = "DELETE FROM UserData WHERE UserId=?";
    private final static String INSERT_USER_DATA
            = "INSERT INTO UserData(UserId, Email, FirstName, LastName, Birthdate, City) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE_USER_DATA
            = "UPDATE UserData SET Email=?, FirstName=?, LastName=?, Birthdate=?, City=? WHERE UserId=?";

    private UserDataDaoImpl() {
    }

    private static class SingletonHolder {
        private final static UserDataDaoImpl INSTANCE = new UserDataDaoImpl();
    }

    public static UserDataDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<UserData> findAll() throws DaoException {
        List<UserData> usersData = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_USER_DATA)) {
                while (rs.next()) {
                    UserData userData = getUserDataFromResultSet(rs);
                    usersData.add(userData);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_USER_DATA, e);
        }
        return usersData;
    }

    @Override
    public Optional<UserData> findEntityById(long id) throws DaoException {
        UserData userData = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_DATA_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    userData = getUserDataFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_USER_DATA_BY_ID, e);
        }
        return userData != null ? Optional.of(userData) : Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_DATA_BY_ID)) {
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + DELETE_USER_DATA_BY_ID, e);
        }
        return result > 0;
    }

    @Override
    public boolean create(UserData entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER_DATA)) {
            ps.setLong(1, entity.getUserId());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            ps.setLong(5, entity.getBirthdate());
            ps.setString(6, entity.getCity());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_USER_DATA, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(UserData entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_DATA)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setLong(4, entity.getBirthdate());
            ps.setString(5, entity.getCity());
            ps.setLong(6, entity.getUserId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_USER_DATA, e);
        }
        return result > 0;
    }

    private UserData getUserDataFromResultSet(ResultSet rs) throws DaoException {
        UserData userData = new UserData();
        try {
            userData.setUserId(rs.getLong(1));
            userData.setEmail(rs.getString(2));
            userData.setFirstName(rs.getString(3));
            userData.setLastName(rs.getString(4));
            userData.setBirthdate(rs.getLong(5));
            userData.setCity(rs.getString(6));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userData;
    }
}
