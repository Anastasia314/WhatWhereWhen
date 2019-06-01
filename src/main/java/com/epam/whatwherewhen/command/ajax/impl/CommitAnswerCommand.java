package com.epam.whatwherewhen.command.ajax.impl;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommand;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.QuestionService;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 10.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class CommitAnswerCommand implements AjaxCommand {
    private final static Logger logger = LogManager.getLogger();

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        boolean answerResult = Boolean.parseBoolean(req.getParameter(RequestParameter.ANSWER_RESULT));
        String userAnswer = req.getParameter(RequestParameter.USER_ANSWER);
        long questionId = Long.parseLong(req.getParameter(RequestParameter.QUESTION_ID));
        long lastQuestion = (Long) session.getAttribute(LAST_QUESTION_NUM);
        User user = (User) req.getSession().getAttribute(USER);
        QuestionService service = QuestionServiceImpl.getInstance();

        try {
            PrintWriter out = resp.getWriter();
            service.commitUserAnswer(user.getUserId(), questionId, userAnswer, answerResult);
            long updatedRating = UserServiceImpl.getInstance().increaseRating(user.getUserId());
            user.setRating(updatedRating);
            session.setAttribute(RequestParameter.USER, user);
            long questionsNum = service.countActiveQuestionsAmount(user.getUserId());
            List<Question> questions = (List<Question>) session.getAttribute(QUESTION_LIST);
            questions.removeIf(question -> question.getQuestionId() == questionId);
            session.setAttribute(QUESTION_LIST, questions);
            session.setAttribute(QUESTIONS_AMOUNT, questionsNum);
            session.setAttribute(LAST_QUESTION_NUM, lastQuestion - 1);
            out.println(resp.SC_OK);
            out.close();
        } catch (ServiceException e) {
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            logger.error(e);
        }

    }
}
