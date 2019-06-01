package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 02.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class SendQuestionCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.USER_DATA_PAGE, Router.RouteType.REDIRECT);
        long userId = ((User) req.getSession().getAttribute(USER)).getUserId();
        String type = req.getParameter(QUESTION_TYPE);
        String body = req.getParameter(QUESTION_BODY);
        String answer = req.getParameter(QUESTION_ANSWER);
        String source = req.getParameter(QUESTION_SOURCE);
        try {
            QuestionServiceImpl.getInstance()
                    .createQuestion(new Question(userId, type, body, answer, new Date().getTime(), source));
            router.setPathWithParameter(PagePath.QUESTION_PAGE, SERVER_MESSAGE, BundleKey.QUESTION_CREATING_SUCCESS);
        } catch (ServiceException e) {
            logger.error(e);
            router.setPathWithParameter(PagePath.QUESTION_PAGE, SERVER_MESSAGE, BundleKey.QUESTION_CREATING_ERROR);
        }
        return router;
    }
}