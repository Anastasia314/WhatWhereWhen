package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 28.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class ChangeUserDataCommand implements Command {

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.USER_DATA_PAGE, Router.RouteType.REDIRECT);
        HttpSession session = req.getSession();
        long userId = ((User) session.getAttribute(USER)).getUserId();
        String lastName = req.getParameter(LAST_NAME);
        String firstName = req.getParameter(FIRST_NAME);
        String email = req.getParameter(EMAIL);
        String city = req.getParameter(CITY);
        String birthdayParam = req.getParameter(BIRTHDAY);
        try {
            Date birthday = new SimpleDateFormat(DATE_FORMAT).parse(birthdayParam);
            UserData userData = new UserData(userId, email, firstName, lastName, birthday.getTime(), city);
            UserServiceImpl.getInstance().changeUserData(userData);
            session.setAttribute(USER_DATA, userData);
        } catch (ServiceException | ParseException e) {
            logger.error(e);
            router.setPathWithParameter(PagePath.USER_DATA_PAGE, SERVER_MESSAGE, BundleKey.UPDATING_ERROR);
        }
        return router;
    }
}
