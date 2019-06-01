package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.entity.PlayedQuestion;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    /**
     * Returns a list of Questions in witch isActive = false
     *
     * @return list of Questions
     * @throws ServiceException {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    List<Question> findNonActiveQuestions() throws ServiceException;

    /**
     * Returns a list of PlayedQuestions in witch isOnAppeal = true
     *
     * @return list of PlayedQuestions
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    List<PlayedQuestion> findQuestionsOnAppeal() throws ServiceException;

    /**
     * Creates new Question in database.
     *
     * @param question for creating
     * @return true if the question was created
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean createQuestion(Question question) throws ServiceException;

    /**
     * Calculates amount of all active questions that not played for concrete user.
     *
     * @param userId id of user to chose not played questions
     * @return long amount of active questions
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    long countActiveQuestionsAmount(long userId) throws ServiceException;

    /**
     * Returns a list of Questions selected from given position and with limited size.
     *
     * @param userId  id of user to chose not played questions
     * @param amount  limit for selected amount
     * @param offset  position in general list
     * @param authors a map of questions authors
     * @return list of Questions selected from given position and with limited size
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    List<Question> findQuestionsByParts(long userId, long amount, long offset, Map<Long, User> authors)
            throws ServiceException;

    /**
     * Writes user answer in database.
     *
     * @param userId       long
     * @param questionId   long
     * @param userAnswer   String user answer
     * @param answerResult boolean result of an answer
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    void commitUserAnswer(long userId, long questionId, String userAnswer, boolean answerResult)
            throws ServiceException;

    /**
     * Calculates left offset for question's list to find questions before {@param lastQuestion}.
     *
     * @param lastQuestion    last displayed question
     * @param questionsAmount amount of all existing questions
     * @return long left  offset
     */
    long countLeftOffset(long lastQuestion, long questionsAmount);

    /**
     * Changes Question isOnAppeal status and return true if it was changed.
     *
     * @param userId     long
     * @param questionId long
     * @param isOnAppeal boolean
     * @param isAnswered boolean
     * @return true if isOnAppeal status was changed
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    boolean changeAppealStatus(long userId, long questionId, boolean isOnAppeal, boolean isAnswered)
            throws ServiceException;

    /**
     * Deletes question or makes it active.
     *
     * @param question processed Question
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    void processQuestion(Question question) throws ServiceException;
}
