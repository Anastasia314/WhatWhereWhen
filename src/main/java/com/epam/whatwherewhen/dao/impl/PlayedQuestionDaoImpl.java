package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.dao.PlayedQuestionDao;
import com.epam.whatwherewhen.entity.PlayedQuestion;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;

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
public class PlayedQuestionDaoImpl implements BaseDao<PlayedQuestion>, PlayedQuestionDao {
    private final static String SELECT_ALL_PLAYED_QUESTIONS
            = "SELECT QuestionId, UserId, Answered, Answer, IsOnAppeal FROM PlayedQuestion";

    private final static String SELECT_ALL_BY_APPEAL
            = "SELECT PlayedQuestion.QuestionId, UserId, Answered, PlayedQuestion.Answer, IsOnAppeal, Question.Answer" +
            " FROM PlayedQuestion LEFT JOIN Question ON PlayedQuestion.QuestionId = Question.QuestionId " +
            "WHERE IsOnAppeal=?";

    private final static String SELECT_PLAYED_QUESTION_BY_ID = SELECT_ALL_PLAYED_QUESTIONS + " where QuestionId=?";

    private final static String INSERT_PLAYED_QUESTION
            = "INSERT INTO PlayedQuestion(QuestionId, UserId, Answered, Answer, IsOnAppeal) VALUES(?,?,?,?,?)";

    private final static String UPDATE_PLAYED_QUESTION
            = "UPDATE PlayedQuestion SET Answer=?, Answered=?, IsOnAppeal=? WHERE QuestionId=? AND UserId=?";

    private final static String UPDATE_APPEAL_STATUS
            = "UPDATE PlayedQuestion SET IsOnAppeal=?, Answered=? WHERE QuestionId=? AND UserId=?";

    private PlayedQuestionDaoImpl() {
    }

    private static class SingletonHolder {
        private final static PlayedQuestionDaoImpl INSTANCE = new PlayedQuestionDaoImpl();
    }

    public static PlayedQuestionDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<PlayedQuestion> findAll() throws DaoException {
        List<PlayedQuestion> playedQuestions = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_PLAYED_QUESTIONS)) {
                while (rs.next()) {
                    playedQuestions.add(getPlayedQuestionFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_PLAYED_QUESTIONS, e);
        }
        return playedQuestions;
    }

    @Override
    public List<PlayedQuestion> findByOnAppeal(boolean isOnAppeal) throws DaoException {
        List<PlayedQuestion> playedQuestions = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BY_APPEAL)) {
            ps.setBoolean(1, isOnAppeal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs != null && rs.next()) {
                    PlayedQuestion playedQuestion = getPlayedQuestionFromResultSet(rs);
                    playedQuestion.setCorrectAnswer(rs.getString(6));
                    playedQuestions.add(playedQuestion);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_BY_APPEAL, e);
        }
        return playedQuestions;
    }

    @Override
    public Optional<PlayedQuestion> findEntityById(long id) throws DaoException {
        PlayedQuestion playedQuestion = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_PLAYED_QUESTION_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    playedQuestion = getPlayedQuestionFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_PLAYED_QUESTION_BY_ID, e);
        }
        return playedQuestion != null ? Optional.of(playedQuestion) : Optional.empty();
    }

    @Override
    public boolean create(PlayedQuestion entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_PLAYED_QUESTION)) {
            ps.setLong(1, entity.getQuestionId());
            ps.setLong(2, entity.getUserId());
            ps.setBoolean(3, entity.isAnswered());
            ps.setString(4, entity.getAnswer());
            ps.setBoolean(5, entity.isOnAppeal());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_PLAYED_QUESTION, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(PlayedQuestion entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PLAYED_QUESTION)) {
            ps.setString(1, entity.getAnswer());
            ps.setBoolean(2, entity.isAnswered());
            ps.setBoolean(3, entity.isOnAppeal());
            ps.setLong(4, entity.getQuestionId());
            ps.setLong(5, entity.getUserId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_PLAYED_QUESTION, e);
        }
        return result > 0;
    }

    @Override
    public boolean updateAppealStatus(long userId, long questionId, boolean isOnAppeal, boolean isAnswered)
            throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_APPEAL_STATUS)) {
            ps.setBoolean(1, isOnAppeal);
            ps.setBoolean(2, isAnswered);
            ps.setLong(3, questionId);
            ps.setLong(4, userId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_APPEAL_STATUS, e);
        }
        return result > 0;
    }

    private PlayedQuestion getPlayedQuestionFromResultSet(ResultSet rs) throws DaoException {
        PlayedQuestion question = new PlayedQuestion();
        try {
            question.setQuestionId(rs.getLong(1));
            question.setUserId(rs.getLong(2));
            question.setAnswered(rs.getBoolean(3));
            question.setAnswer(rs.getString(4));
            question.setOnAppeal(rs.getBoolean(5));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return question;
    }
}
