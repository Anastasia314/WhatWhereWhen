package com.epam.whatwherewhen.service.impl;

import com.epam.whatwherewhen.dao.impl.PlayedQuestionDaoImpl;
import com.epam.whatwherewhen.dao.impl.QuestionDaoImpl;
import com.epam.whatwherewhen.entity.PlayedQuestion;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.QuestionService;
import com.epam.whatwherewhen.validation.RequestValidator;

import java.util.List;
import java.util.Map;

import static com.epam.whatwherewhen.command.RequestParameter.QUESTION_DISPLAY_NUM;

/**
 * Date: 28.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class QuestionServiceImpl implements QuestionService {
    private QuestionServiceImpl() {
    }

    private static class SingletonHolder {
        private final static QuestionServiceImpl INSTANCE = new QuestionServiceImpl();
    }

    public static QuestionServiceImpl getInstance() {
        return QuestionServiceImpl.SingletonHolder.INSTANCE;
    }

    public List<Question> findNonActiveQuestions() throws ServiceException {
        try {
            return QuestionDaoImpl.getInstance().findByIsActive(false);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<PlayedQuestion> findQuestionsOnAppeal() throws ServiceException {
        try {
            return PlayedQuestionDaoImpl.getInstance().findByOnAppeal(true);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean createQuestion(Question question) throws ServiceException {
        if (!RequestValidator.getInstance().isValidQuestion(question)) {
            throw new ServiceException("Invalid input data");
        }
        try {
            return QuestionDaoImpl.getInstance().create(question);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long countActiveQuestionsAmount(long userId) throws ServiceException {
        try {
            return QuestionDaoImpl.getInstance().countActiveQuestions(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Question> findQuestionsByParts(long userId, long amount, long offset, Map<Long, User> authors)
            throws ServiceException {
        if (offset < 0 || amount < 0 || authors == null) {
            throw new ServiceException("Invalid input data.");
        }
        try {
            return QuestionDaoImpl.getInstance().findQuestionsByParts(userId, amount, offset, authors);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void commitUserAnswer(long userId, long questionId, String userAnswer, boolean answerResult)
            throws ServiceException {
        if (userAnswer == null || questionId < 0 || userId < 0) {
            throw new ServiceException("Invalid input data.");
        }
        try {
            PlayedQuestionDaoImpl.getInstance()
                    .create(new PlayedQuestion(questionId, userId, answerResult, userAnswer));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long countLeftOffset(long lastQuestion, long questionsAmount) {
        long offset = lastQuestion == questionsAmount && lastQuestion % QUESTION_DISPLAY_NUM != 0 ?
                lastQuestion - lastQuestion % QUESTION_DISPLAY_NUM - QUESTION_DISPLAY_NUM :
                lastQuestion - 2 * QUESTION_DISPLAY_NUM;
        return offset > 0 ? offset : 0;
    }

    public boolean changeAppealStatus(long userId, long questionId, boolean isOnAppeal, boolean isAnswered)
            throws ServiceException {
        try {
            return PlayedQuestionDaoImpl.getInstance().updateAppealStatus(userId, questionId, isOnAppeal, isAnswered);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void processQuestion(Question question) throws ServiceException {
        if (!RequestValidator.getInstance().isValidQuestionForUpdate(question)) {
            throw new ServiceException("Invalid input data");
        }
        try {
            if (question.isActive()) {
                QuestionDaoImpl.getInstance().updateQuestionPart(question);
            } else {
                QuestionDaoImpl.getInstance().delete(question.getQuestionId());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
