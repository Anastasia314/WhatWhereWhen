package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.ArticleService;
import com.epam.whatwherewhen.service.impl.ArticleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 28.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class LoadArticlesCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.MAIN_PAGE);
        HttpSession httpSession = req.getSession();
        long lastArticle = (long) httpSession.getAttribute(LAST_ARTICLE_NUM);
        List<Article> articleList;
        String direction = req.getParameter(LOADING_DIRECT);
        try {
            ArticleService service = ArticleServiceImpl.getInstance();
            long articlesAmount = service.countArticlesAmount();
            httpSession.setAttribute(ARTICLES_AMOUNT, articlesAmount);
            HashMap<Long, User> authors = new HashMap<>();

            if (direction != null && direction.equals(DIRECT_NEXT) && articlesAmount > lastArticle) {
                articleList = service.findArticlesByParts(ARTICLE_DISPLAY_NUM, lastArticle, authors);
                httpSession.setAttribute(LAST_ARTICLE_NUM, lastArticle + articleList.size());
                httpSession.setAttribute(ARTICLE_LIST, articleList);
                httpSession.setAttribute(ARTICLE_AUTHORS, authors);
            } else if (direction != null && direction.equals(DIRECT_PREVIOUS) && ARTICLE_DISPLAY_NUM < lastArticle) {
                long offset = service.countLeftOffset(lastArticle, articlesAmount);
                articleList = service.findArticlesByParts(ARTICLE_DISPLAY_NUM, offset, authors);
                httpSession.setAttribute(LAST_ARTICLE_NUM, offset + articleList.size());
                httpSession.setAttribute(ARTICLE_LIST, articleList);
                httpSession.setAttribute(ARTICLE_AUTHORS, authors);
            }
        } catch (ServiceException e) {
            logger.error(e);
            router.setPagePath(PagePath.ERROR_PAGE);
        }
        return router;
    }
}