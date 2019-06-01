package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.dao.impl.ArticleDaoImpl;
import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleService {

    /**
     * Counts existing articles amount.
     *
     * @return long articles amount
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    long countArticlesAmount() throws ServiceException;

    /**
     * Returns limited number of articles.
     *
     * @param amount  amount of elements for select
     * @param offset  offset in result set
     * @param authors empty Map to return two types from method
     * @return list of articles
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    List<Article> findArticlesByParts(long amount, long offset, Map<Long, User> authors) throws ServiceException;

    /**
     * Calculates left offset for article's list to find articles before {@param lastArticle}.
     *
     * @param lastArticle    number of last displayed article
     * @param articlesAmount amount of all existing articles
     * @return long left offset
     */
    long countLeftOffset(long lastArticle, long articlesAmount);

    /**
     * Returns Article by articleId.
     *
     * @param articleId Id of article
     * @return Optional of Article
     * @throws ServiceException if input data is incorrect or
     *                          {@link com.epam.whatwherewhen.exception.DaoException} exception occurred
     */
    Optional<Article> findArticle(long articleId) throws ServiceException;
}
