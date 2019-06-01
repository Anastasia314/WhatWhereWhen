package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.exception.DaoException;

/**
 * Date: 11.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public interface UserDataDao {
    boolean delete(long id) throws DaoException;
}
