package com.epam.whatwherewhen.dao;

import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    boolean delete(long id) throws DaoException;

    /**
     * @param amount  amount of elements for select
     * @param offset  offset in result set
     * @param authors empty Map to return two types from method
     * @return list of articles
     * @throws DaoException
     */
    List<Article> findArticlesByParts(long amount, long offset, Map<Long, User> authors) throws DaoException;
}
