package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.entity.Entity;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public interface BaseDao<T extends Entity> {
    /**
     * Returns a List of all existing entities.
     *
     * @return a List of all existing entities
     * @throws DaoException
     */
    List<T> findAll() throws DaoException;

    /**
     * Finds entity by id and returns it in optional.
     *
     * @param id searching criterion
     * @return Optional witch contain null or searched entity
     * @throws DaoException
     */
    Optional<T> findEntityById(long id) throws DaoException;

    /**
     * Creates a new entry and returns true if it was created.
     *
     * @param entity entity for a new entry
     * @return true if a new entry was created
     * @throws DaoException
     */
    boolean create(T entity) throws DaoException;

    /**
     * Updates existing entity and returns boolean result of the operation.
     *
     * @param entity entity with updated parameters
     * @return boolean result
     * @throws DaoException
     */
    boolean update(T entity) throws DaoException;

    /**
     * Returns number of rows by given query.
     *
     * @param query String query with conditions for select
     * @return long number of rows
     * @throws DaoException
     */
    default long countRowsAmount(final String query) throws DaoException {
        long rowsAmount = 0;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs != null && rs.next()) {
                    rowsAmount = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + query, e);
        }
        return rowsAmount;
    }

}
