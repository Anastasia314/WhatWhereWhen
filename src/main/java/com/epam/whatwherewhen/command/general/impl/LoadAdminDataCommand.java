package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.PlayedQuestion;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.QuestionService;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Date: 12.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class LoadAdminDataCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.ADMIN_PAGE);
        HttpSession httpSession = req.getSession();
        QuestionService questionService = QuestionServiceImpl.getInstance();
        try {
            List<User> userList = UserServiceImpl.getInstance().findAllUsers();
            userList.sort((o1, o2) -> (int) (o2.getRating() - o1.getRating()));
            httpSession.setAttribute(RequestParameter.USER_LIST, userList);
            List<PlayedQuestion> appealQuestions = questionService.findQuestionsOnAppeal();
            httpSession.setAttribute(RequestParameter.APPEAL_QUESTIONS, appealQuestions);
            List<Question> sentQuestions = questionService.findNonActiveQuestions();
            httpSession.setAttribute(RequestParameter.SENT_QUESTIONS, sentQuestions);
        } catch (ServiceException e) {
            logger.error(e);
            router.setPagePath(PagePath.ERROR_PAGE);
        }
        return router;
    }
}
