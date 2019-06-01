package com.epam.whatwherewhen.command.ajax.impl;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommand;
import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Date: 16.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ApproveQuestionCommand implements AjaxCommand {

    private final static Logger logger = LogManager.getLogger();

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionId(Long.parseLong(req.getParameter(RequestParameter.QUESTION_ID)));
        updatedQuestion.setType(req.getParameter(RequestParameter.QUESTION_TYPE));
        updatedQuestion.setBody(req.getParameter(RequestParameter.QUESTION_BODY));
        updatedQuestion.setAnswer(req.getParameter(RequestParameter.QUESTION_ANSWER));
        updatedQuestion.setSource(req.getParameter(RequestParameter.QUESTION_SOURCE));
        updatedQuestion.setActive(Boolean.parseBoolean(req.getParameter(RequestParameter.IS_APPROVED)));
        try {
            PrintWriter out = resp.getWriter();
            QuestionServiceImpl.getInstance().processQuestion(updatedQuestion);
            out.println(resp.SC_OK);
            out.close();
        } catch (ServiceException e) {
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            logger.error(e);
        }
    }
}
