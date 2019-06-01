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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 21.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class InitializeStartPageCommand implements Command {

    private final static Logger logger = LogManager.getLogger();
    private final static int INITIAL_OFFSET = 0;

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.MAIN_PAGE);
        try {
            ArticleService service = ArticleServiceImpl.getInstance();
            long articlesAmount = service.countArticlesAmount();
            Map<Long, User> authors = new HashMap<>();
            List<Article> articleList = service.findArticlesByParts(ARTICLE_DISPLAY_NUM, INITIAL_OFFSET, authors);
            Collections.reverse(articleList);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute(ARTICLE_LIST, articleList);
            httpSession.setAttribute(ARTICLE_AUTHORS, authors);
            httpSession.setAttribute(NEWEST_ARTICLES, articleList);
            httpSession.setAttribute(ARTICLES_AMOUNT, articlesAmount);
            httpSession.setAttribute(ARTICLE_DISPLAY_AMOUNT, ARTICLE_DISPLAY_NUM);
            httpSession.setAttribute(LAST_ARTICLE_NUM, ARTICLE_DISPLAY_NUM);
        } catch (ServiceException e) {
            logger.error(e);
            router.setPagePath(PagePath.ERROR_PAGE);
        }
        return router;
    }
}
