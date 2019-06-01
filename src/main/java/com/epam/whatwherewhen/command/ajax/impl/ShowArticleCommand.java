package com.epam.whatwherewhen.command.ajax.impl;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommand;
import com.epam.whatwherewhen.entity.Article;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.ArticleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Date: 10.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ShowArticleCommand implements AjaxCommand {

    private final static Logger logger = LogManager.getLogger();
    private final static String DATE_FORMAT = "dd MMMM yyyy";

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            PrintWriter out = resp.getWriter();
            JSONObject jsonObj = new JSONObject();
            long articleId = Long.parseLong(req.getParameter(RequestParameter.ARTICLE_ID));
            Optional<Article> article = ArticleServiceImpl.getInstance().findArticle(articleId);
            article.ifPresent(data -> {
                jsonObj.put(RequestParameter.AUTHOR_ID, data.getAuthorId());
                jsonObj.put(RequestParameter.DATE, new SimpleDateFormat(DATE_FORMAT).format(new Date(data.getDate())));
                jsonObj.put(RequestParameter.HEADER, data.getHeader());
                jsonObj.put(RequestParameter.THEME, data.getTheme());
                jsonObj.put(RequestParameter.BODY, data.getBody());
                jsonObj.put(RequestParameter.PHOTO, data.getPhoto());
            });
            out.print(jsonObj.toString());
            out.close();
        } catch (ServiceException e) {
            logger.error(e);
        }
    }

}
