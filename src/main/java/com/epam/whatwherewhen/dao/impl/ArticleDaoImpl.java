package com.epam.whatwherewhen.dao.impl;

import com.epam.whatwherewhen.pool.ConnectionPool;
import com.epam.whatwherewhen.pool.ProxyConnection;
import com.epam.whatwherewhen.dao.ArticleDao;
import com.epam.whatwherewhen.dao.BaseDao;
import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Date: 03.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ArticleDaoImpl implements BaseDao<Article>, ArticleDao {
    private final static String SELECT_ALL_ARTICLES
            = "SELECT ArticleId, AuthorId, Theme,Header,Body,Date,Photo FROM Article ORDER BY Date";
    private final static String SELECT_ARTICLE_BY_ID = "SELECT ArticleId, AuthorId, Theme,Header,Body,Date,Photo" +
            " FROM Article WHERE ArticleId=?";
    private final static String SELECT_ARTICLES_PART = "SELECT ArticleId, AuthorId, Theme,Header,Body,Date," +
            " Article.Photo, User.Login, User.Rating, User.IsActive, User.Photo FROM Article JOIN User" +
            " ON Article.AuthorId = User.UserId ORDER BY Article.Date LIMIT ? OFFSET ?;";
    private final static String INSERT_ARTICLE
            = "INSERT INTO Article(AuthorId, Theme,Header,Body,Date,Photo) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE_ARTICLE
            = "UPDATE Article SET AuthorId=?, Theme=?,Header=?,Body=?,Date=?,Photo=? WHERE ArticleId=?";
    private final static String DELETE_ARTICLE_BY_ID = "DELETE FROM Article WHERE ArticleId=?";
    private final static String SELECT_ARTICLES_COUNT = "SELECT COUNT(*) FROM Article";

    private ArticleDaoImpl() {
    }

    private static class SingletonHolder {
        private final static ArticleDaoImpl INSTANCE = new ArticleDaoImpl();
    }

    public static ArticleDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public List<Article> findAll() throws DaoException {
        List<Article> articles = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_ARTICLES)) {
                while (rs.next()) {
                    Article article = getArticleFromResultSet(rs);
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ALL_ARTICLES, e);
        }
        return articles;
    }

    @Override
    public Optional<Article> findEntityById(long id) throws DaoException {
        Article article = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ARTICLE_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null && rs.next()) {
                    article = getArticleFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ARTICLE_BY_ID, e);
        }
        return article != null ? Optional.of(article) : Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_ARTICLE_BY_ID)) {
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + DELETE_ARTICLE_BY_ID, e);
        }
        return result > 0;
    }

    @Override
    public boolean create(Article entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_ARTICLE)) {
            ps.setLong(1, entity.getAuthorId());
            ps.setString(2, entity.getTheme());
            ps.setString(3, entity.getHeader());
            ps.setString(4, entity.getBody());
            ps.setLong(5, entity.getDate());
            ps.setString(6, entity.getPhoto());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + INSERT_ARTICLE, e);
        }
        return result == 1;
    }

    @Override
    public boolean update(Article entity) throws DaoException {
        int result;
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ARTICLE)) {
            ps.setLong(1, entity.getAuthorId());
            ps.setString(2, entity.getTheme());
            ps.setString(3, entity.getHeader());
            ps.setString(4, entity.getBody());
            ps.setLong(5, entity.getDate());
            ps.setString(6, entity.getPhoto());
            ps.setLong(7, entity.getArticleId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed make " + UPDATE_ARTICLE, e);
        }
        return result > 0;
    }

    @Override
    public List<Article> findArticlesByParts(long amount, long offset, Map<Long, User> authors) throws DaoException {
        List<Article> articles = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ARTICLES_PART)) {
            ps.setLong(1, amount);
            ps.setLong(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Article article = getArticleFromResultSet(rs);
                    articles.add(article);
                    User user = getUserFromResultSet(rs);
                    authors.put(user.getUserId(), user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed make " + SELECT_ARTICLES_PART, e);
        }
        return articles;
    }


    public long countArticlesAmount() throws DaoException {
        return countRowsAmount(SELECT_ARTICLES_COUNT);
    }

    private Article getArticleFromResultSet(ResultSet rs) throws DaoException {
        Article article = new Article();
        try {
            article.setArticleId(rs.getLong(1));
            article.setAuthorId(rs.getLong(2));
            article.setTheme(rs.getString(3));
            article.setHeader(rs.getString(4));
            article.setBody(rs.getString(5));
            article.setDate(rs.getLong(6));
            article.setPhoto(rs.getString(7));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return article;
    }

    private User getUserFromResultSet(ResultSet rs) throws DaoException {
        User user = new User();
        try {
            user.setUserId(rs.getLong(2));
            user.setLogin(rs.getString(8));
            user.setRating(rs.getLong(9));
            user.setActive(rs.getBoolean(10));
            user.setPhoto(rs.getBlob(11));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }
}
