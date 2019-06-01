package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.entity.PlayedQuestion;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.exception.DaoException;

import java.util.List;

public interface PlayedQuestionDao {

    /**
     * Updates isOnAppeal parameter in the PlayedQuestion
     *
     * @param userId parameter for select
     * @param questionId parameter for select
     * @param isOnAppeal parameter for updating
     * @param isAnswered parameter for updating that can change after isOnAppeal changing
     * @return true if the status was updated
     * @throws DaoException
     */
    boolean updateAppealStatus(long userId, long questionId, boolean isOnAppeal, boolean isAnswered)
            throws DaoException;

    /**
     * Returns a List of all existing entities that on Appeal or not.
     *
     * @param isOnAppeal parameter for select all playedQuestions that on appeal or not
     * @return a List of all existing entities
     * @throws DaoException
     */
    List<PlayedQuestion> findByOnAppeal(boolean isOnAppeal) throws DaoException;
}
