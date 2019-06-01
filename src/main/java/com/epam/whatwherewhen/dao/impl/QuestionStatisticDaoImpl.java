package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;
import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.entity.QuestionStatistic;
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
public class QuestionStatisticDaoImpl implements BaseDao<QuestionStatistic> {
    private final static String SELECT_ALL_QUESTIONS_STATISTIC
            = "SELECT QuestionId, Answered, NotAnswered,Liked,Disliked FROM QuestionStatistic";
    private final static String SELECT_STATISTIC_BY_ID = SELECT_ALL_QUESTIONS_STATISTIC + " where QuestionId=?";
    private final static String INSERT_QUESTION_STATISTIC
            = "INSERT INTO QuestionStatistic(QuestionId, Answered, NotAnswered,Liked,Disliked) VALUES(?,?,?,?,?)";
    private final static String UPDATE_QUESTION_STATISTIC
            = "UPDATE QuestionStatistic SET  Answered=?, NotAnswered=?,Liked=?,Disliked=? WHERE QuestionId=?";

    private QuestionStatisticDaoImpl() {
    }

    private static class SingletonHolder {
        private final static QuestionStatisticDaoImpl INSTANCE = new QuestionStatisticDaoImpl();
    }

    public static QuestionStatisticDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<QuestionStatistic> findAll() throws DaoException {
        List<QuestionStatistic> questionStatistics = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_QUESTIONS_STATISTIC)) {
                while (rs.next()) {
                    QuestionStatistic questionStatistic = getQuestionStatisticFromResultSet(rs);
                    questionStatistics.add(questionStatistic);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_QUESTIONS_STATISTIC, e);
        }
        return questionStatistics;
    }

    @Override
    public Optional<QuestionStatistic> findEntityById(long id) throws DaoException {
        QuestionStatistic questionStatistic = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_STATISTIC_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    questionStatistic = getQuestionStatisticFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_STATISTIC_BY_ID, e);
        }
        return questionStatistic != null ? Optional.of(questionStatistic) : Optional.empty();
    }

    @Override
    public boolean create(QuestionStatistic entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_QUESTION_STATISTIC)) {
            ps.setLong(1, entity.getQuestionId());
            ps.setLong(2, entity.getAnswered());
            ps.setLong(3, entity.getNotAnswered());
            ps.setLong(4, entity.getLiked());
            ps.setLong(5, entity.getDisliked());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_QUESTION_STATISTIC, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(QuestionStatistic entity) throws DaoException{
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUESTION_STATISTIC)) {
            ps.setLong(1, entity.getAnswered());
            ps.setLong(2, entity.getNotAnswered());
            ps.setLong(3, entity.getLiked());
            ps.setLong(4, entity.getDisliked());
            ps.setLong(5, entity.getQuestionId());
            ps.setLong(7, entity.getQuestionId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_QUESTION_STATISTIC, e);
        }
        return result > 0;
    }

    private QuestionStatistic getQuestionStatisticFromResultSet(ResultSet rs) throws DaoException {
        QuestionStatistic questionStatistic = new QuestionStatistic();
        try {
            questionStatistic.setQuestionId(rs.getLong(1));
            questionStatistic.setAnswered(rs.getLong(2));
            questionStatistic.setNotAnswered(rs.getLong(3));
            questionStatistic.setLiked(rs.getLong(4));
            questionStatistic.setDisliked(rs.getLong(4));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return questionStatistic;
    }
}
