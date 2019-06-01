package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.dao.QuestionDao;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuestionDaoImpl implements BaseDao<Question>, QuestionDao {

    private final static String SELECT_ALL_QUESTIONS
            = "SELECT QuestionId, AuthorId, Type,Body,Answer,Date,Source,IsActive, Photo FROM Question";

    private final static String SELECT_QUESTIONS_BY_ACTIVE
            = "SELECT QuestionId, AuthorId,Type,Body,Answer,Date,Source,IsActive,Photo FROM Question WHERE IsActive=?";

    private final static String SELECT_QUESTION_BY_ID = SELECT_ALL_QUESTIONS + " where QuestionId=?";

    private final static String SELECT_QUESTIONS_PART = "SELECT Question.QuestionId, AuthorId, Type," +
            " Body, Question.Answer, Date, Source, Question.IsActive, Question.Photo, User.Login, User.Rating," +
            " User.IsActive, User.Photo FROM Question JOIN User ON Question.AuthorId = User.UserId  " +
            "WHERE Question.IsActive=1 AND QuestionId NOT IN " +
            "(SELECT playedquestion.QuestionId FROM playedquestion WHERE playedquestion.UserId=?) " +
            " LIMIT ? OFFSET ?;";

    private final static String INSERT_QUESTION
            = "INSERT INTO Question(AuthorId, Type,Body,Answer,Date,Source,Photo) VALUES(?,?,?,?,?,?,?)";

    private final static String UPDATE_QUESTION = "UPDATE Question " +
            "SET AuthorId=?, Type=?,Body=?,Answer=?,Date=?,Source=?,IsActive=?,Photo=? WHERE QuestionId=?";

    private final static String UPDATE_QUESTION_PART = "UPDATE Question SET Type=?, Body=?, Answer=?, Source=?," +
            " IsActive=? WHERE QuestionId=?";

    private final static String DELETE_QUESTION_BY_ID = "DELETE FROM Question WHERE QuestionId=?";

    private final static String SELECT_ACTIVE_QUESTIONS_COUNT = "SELECT COUNT(QuestionId) FROM question WHERE" +
            " Question.questionId NOT IN (SELECT question.QuestionId FROM Question " +
            "LEFT JOIN PlayedQuestion ON Question.QuestionId = PlayedQuestion.QuestionId " +
            "WHERE PlayedQuestion.UserId = ?) and Question.IsActive=1;";

    private QuestionDaoImpl() {
    }

    private static class SingletonHolder {
        private final static QuestionDaoImpl INSTANCE = new QuestionDaoImpl();
    }

    public static QuestionDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<Question> findAll() throws DaoException {
        List<Question> questions = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_QUESTIONS)) {
                while (rs.next()) {
                    Question question = getQuestionFromResultSet(rs);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_QUESTIONS, e);
        }
        return questions;
    }

    @Override
    public Optional<Question> findEntityById(long id) throws DaoException {
        Question question = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_QUESTION_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    question = getQuestionFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_QUESTION_BY_ID, e);
        }
        return question != null ? Optional.of(question) : Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUESTION_BY_ID)) {
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + DELETE_QUESTION_BY_ID, e);
        }
        return result > 0;
    }

    @Override
    public boolean create(Question entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_QUESTION)) {
            ps.setLong(1, entity.getAuthorId());
            ps.setString(2, entity.getType());
            ps.setString(3, entity.getBody());
            ps.setString(4, entity.getAnswer());
            ps.setLong(5, entity.getDate());
            ps.setString(6, entity.getSource());
            ps.setString(7, entity.getPhoto());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_QUESTION, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(Question entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUESTION)) {
            ps.setLong(1, entity.getAuthorId());
            ps.setString(2, entity.getType());
            ps.setString(3, entity.getBody());
            ps.setString(4, entity.getAnswer());
            ps.setLong(5, entity.getDate());
            ps.setString(6, entity.getSource());
            ps.setBoolean(7, entity.isActive());
            ps.setString(8, entity.getPhoto());
            ps.setLong(9, entity.getQuestionId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_QUESTION, e);
        }
        return result > 0;
    }

    @Override
    public void updateQuestionPart(Question question) throws DaoException {
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUESTION_PART)) {
            ps.setString(1, question.getType());
            ps.setString(2, question.getBody());
            ps.setString(3, question.getAnswer());
            ps.setString(4, question.getSource());
            ps.setBoolean(5, question.isActive());
            ps.setLong(6, question.getQuestionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_QUESTION_PART, e);
        }
    }

    @Override
    public long countActiveQuestions(long userId) throws DaoException {
        long rowsAmount = 0;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ACTIVE_QUESTIONS_COUNT)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    rowsAmount = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ACTIVE_QUESTIONS_COUNT, e);
        }
        return rowsAmount;
    }

    @Override
    public List<Question> findQuestionsByParts(long userId, long amount, long offset, Map<Long, User> authors)
            throws DaoException {
        List<Question> questions = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_QUESTIONS_PART)) {
            ps.setLong(1, userId);
            ps.setLong(2, amount);
            ps.setLong(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question question = getQuestionFromResultSet(rs);
                    questions.add(question);
                    User user = getUserFromResultSet(rs);
                    authors.put(user.getUserId(), user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_QUESTIONS_PART, e);
        }
        return questions;
    }

    @Override
    public List<Question> findByIsActive(boolean isActiveQuestion) throws DaoException {
        List<Question> questions = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_QUESTIONS_BY_ACTIVE)) {
            ps.setBoolean(1, isActiveQuestion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question question = getQuestionFromResultSet(rs);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_QUESTIONS_BY_ACTIVE, e);
        }
        return questions;
    }

    private Question getQuestionFromResultSet(ResultSet rs) throws DaoException {
        Question question = new Question();
        try {
            question.setQuestionId(rs.getLong(1));
            question.setAuthorId(rs.getLong(2));
            question.setType(rs.getString(3));
            question.setBody(rs.getString(4));
            question.setAnswer(rs.getString(5));
            question.setDate(rs.getLong(6));
            question.setSource(rs.getString(7));
            question.setActive(rs.getBoolean(8));
            question.setPhoto(rs.getString(9));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return question;
    }

    private User getUserFromResultSet(ResultSet rs) throws DaoException {
        User user = new User();
        try {
            user.setUserId(rs.getLong(2));
            user.setLogin(rs.getString(10));
            user.setRating(rs.getLong(11));
            user.setActive(rs.getBoolean(12));
            user.setPhoto(rs.getBlob(13));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }
}
