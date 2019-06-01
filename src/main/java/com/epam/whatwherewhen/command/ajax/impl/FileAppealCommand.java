package com.epam.whatwherewhen.command.ajax.impl;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommand;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.epam.whatwherewhen.command.RequestParameter.USER;

/**
 * Date: 10.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class FileAppealCommand implements AjaxCommand {

    private final static Logger logger = LogManager.getLogger();
    private final static boolean APPEAL_STATUS = true;
    private final static boolean ANSWERED_STATUS = false;

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long questionId = Long.parseLong(req.getParameter(RequestParameter.QUESTION_ID));
        User user = (User) req.getSession().getAttribute(USER);

        try {
            PrintWriter out = resp.getWriter();
            QuestionServiceImpl.getInstance()
                    .changeAppealStatus(user.getUserId(), questionId, APPEAL_STATUS, ANSWERED_STATUS);
            out.println(resp.SC_OK);
            out.close();
        } catch (ServiceException e) {
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            logger.error(e);
        }
    }
}
