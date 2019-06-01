package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.QuestionService;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 24.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class LoadQuestionsCommand implements Command {

    private final static Logger logger = LogManager.getLogger();
    private final static int START_COUNT = 0;


    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.QUESTION_PAGE);
        HttpSession session = req.getSession();
        long userId = ((User) session.getAttribute(USER)).getUserId();
        session.setAttribute(QUESTION_DISPLAY_AMOUNT, QUESTION_DISPLAY_NUM);
        Long lastQuestionParam = (Long) session.getAttribute(LAST_QUESTION_NUM);
        long lastQuestion = lastQuestionParam != null ? lastQuestionParam : START_COUNT;
        List<Question> questions;
        String direction = req.getParameter(LOADING_DIRECT);
        try {
            QuestionService service = QuestionServiceImpl.getInstance();
            final long questionsNum = service.countActiveQuestionsAmount(userId);
            session.setAttribute(QUESTIONS_AMOUNT, questionsNum);
            HashMap<Long, User> authors = new HashMap<>();
            if ((direction != null && direction.equals(DIRECT_NEXT) && questionsNum > lastQuestion)
                    || (direction == null && lastQuestion == START_COUNT && questionsNum > lastQuestion)) {
                questions = service.findQuestionsByParts(userId, QUESTION_DISPLAY_NUM, lastQuestion, authors);
                session.setAttribute(LAST_QUESTION_NUM, lastQuestion + questions.size());
                session.setAttribute(QUESTION_LIST, questions);
                session.setAttribute(QUESTION_AUTHORS, authors);
            } else if (direction != null && direction.equals(DIRECT_PREVIOUS) && QUESTION_DISPLAY_NUM < lastQuestion) {
                long offset = service.countLeftOffset(lastQuestion, questionsNum);
                questions = service.findQuestionsByParts(userId, QUESTION_DISPLAY_NUM, offset, authors);
                session.setAttribute(LAST_QUESTION_NUM, offset + questions.size());
                session.setAttribute(QUESTION_LIST, questions);
                session.setAttribute(QUESTION_AUTHORS, authors);
            }
        } catch (ServiceException e) {
            logger.error(e);
            router.setPagePath(PagePath.ERROR_PAGE);
        }
        return router;
    }
}