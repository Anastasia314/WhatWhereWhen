package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * Date: 11.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public interface QuestionDao {

    /**
     * Removes an entity and returns true if it was deleted.
     *
     * @param id removal criterion
     * @return true if the entity was deleted
     * @throws DaoException
     */
    boolean delete(long id) throws DaoException;

    /**
     * Returns a List of not answered questions for determined user.
     *
     * @param userId  parameter for select
     * @param amount  amount of elements for select
     * @param offset  offset in result set
     * @param authors empty Map to return two types from method
     * @return list of questions
     * @throws DaoException
     */
    List<Question> findQuestionsByParts(long userId, long amount, long offset, Map<Long, User> authors)
            throws DaoException;

    /**
     * Returns the number of questions that active and not answered for determined user.
     *
     * @param userId parameter for select
     * @return long number of questions
     * @throws DaoException
     */
    long countActiveQuestions(long userId) throws DaoException;

    /**
     * Returns a List of all existing entities that active or not.
     *
     * @param isActiveQuestion parameter for select all active or inactive questions
     * @return a List of all existing entities
     * @throws DaoException
     */
    List<Question> findByIsActive(boolean isActiveQuestion) throws DaoException;

    /**
     * Updates some fields of existing entity.
     *
     * @param question parameter for update
     * @throws DaoException
     */
    void updateQuestionPart(Question question) throws DaoException;
}
