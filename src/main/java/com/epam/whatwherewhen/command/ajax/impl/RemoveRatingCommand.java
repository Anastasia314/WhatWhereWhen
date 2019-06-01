package com.epam.whatwherewhen.command.ajax.impl;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommand;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
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
public class RemoveRatingCommand implements AjaxCommand {

    private final static Logger logger = LogManager.getLogger();
    private final static int REMOVED_RATING = 0;

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long userId = Long.parseLong(req.getParameter(RequestParameter.USER_ID));

        try {
            PrintWriter out = resp.getWriter();
            UserServiceImpl.getInstance().removeRating(userId);
            out.println(REMOVED_RATING);
            out.close();
        } catch (ServiceException e) {
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            logger.error(e);
        }
    }
}