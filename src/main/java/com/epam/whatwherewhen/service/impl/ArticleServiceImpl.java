package com.epam.whatwherewhen.service.impl;

import com.epam.whatwherewhen.dao.impl.ArticleDaoImpl;
import com.epam.whatwherewhen.dao.impl.UserDataDaoImpl;
import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.DaoException;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.ArticleService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.whatwherewhen.command.RequestParameter.ARTICLE_DISPLAY_NUM;

/**
 * Date: 28.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ArticleServiceImpl implements ArticleService {
    private ArticleServiceImpl() {
    }

    private static class SingletonHolder {
        private final static ArticleServiceImpl INSTANCE = new ArticleServiceImpl();
    }

    public static ArticleServiceImpl getInstance() {
        return ArticleServiceImpl.SingletonHolder.INSTANCE;
    }

    public long countArticlesAmount() throws ServiceException {
        try {
            return ArticleDaoImpl.getInstance().countArticlesAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Article> findArticlesByParts(long amount, long offset, Map<Long, User> authors)
            throws ServiceException {
        if (offset < 0 || amount < 0 || authors == null) {
            throw new ServiceException("Illegal negative input.");
        }
        try {
            return ArticleDaoImpl.getInstance().findArticlesByParts(amount, offset, authors);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long countLeftOffset(long lastArticle, long articlesAmount) {
        long offset = lastArticle == articlesAmount && lastArticle % ARTICLE_DISPLAY_NUM != 0 ?
                lastArticle - lastArticle % ARTICLE_DISPLAY_NUM - ARTICLE_DISPLAY_NUM :
                lastArticle - 2 * ARTICLE_DISPLAY_NUM;
        return offset > 0 ? offset : 0;
    }

    public Optional<Article> findArticle(long articleId) throws ServiceException {
        try {
            return ArticleDaoImpl.getInstance().findEntityById(articleId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
