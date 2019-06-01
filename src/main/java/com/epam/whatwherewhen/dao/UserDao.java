package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Optional;

/**
 * Date: 03.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public interface UserDao {
    /**
     * Removes an entity and returns true if it was deleted.
     *
     * @param login parameter for select
     * @param password parameter for select
     * @return true if the entity was deleted
     * @throws DaoException
     */
    boolean delete(String login, String password) throws DaoException;

    /**
     * Removes an entity and returns true if it was deleted.
     *
     * @param id parameter for select
     * @return true if the entity was deleted
     * @throws DaoException
     */
    boolean delete(long id) throws DaoException;

    /**
     * Finds entity by login and password and returns it in optional.
     *
     * @param login parameter for select
     * @param password parameter for select
     * @return Optional witch contain null or searched entity
     * @throws DaoException
     */
    Optional<User> findByLoginPassword(String login, String password) throws DaoException;

    /**
     * Finds entity by login and returns it in optional.
     *
     * @param login parameter for select
     * @return Optional witch contain null or searched entity
     * @throws DaoException
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Updates entity photo by id and returns true if it was updated.
     *
     * @param userId parameter to find entity for update
     * @param photo new photo for update
     * @return true if the photo was updated
     * @throws DaoException
     */
    boolean updatePhoto(long userId, InputStream photo) throws DaoException;

    /**
     * Updates entity photo by id and returns true if it was updated.
     *
     * @param userId parameter to find entity for update
     * @return new user rating
     * @throws DaoException
     */
    boolean updateRating(long userId, long rating) throws DaoException;

    /**
     * Finds entity photo by id and returns it as a Blob.
     *
     * @param UserId parameter for select
     * @return entity photo as a Blob
     * @throws DaoException
     */
    Blob findUserPhoto(long UserId) throws DaoException;
}
