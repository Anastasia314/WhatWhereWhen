package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 20.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class SignUpCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.LOGIN_PAGE, Router.RouteType.REDIRECT);
        String newLogin = req.getParameter(NEW_LOGIN);
        String newPassword = req.getParameter(NEW_PASSWORD);
        String newEmail = req.getParameter(NEW_EMAIL);
        String confirmPassword = req.getParameter(CONFIRM_PASSWORD);
        User user = new User(newLogin, newPassword);
        try {
            if (UserServiceImpl.getInstance().registryUser(user, newEmail, confirmPassword)) {
                router.setPathWithParameter(PagePath.LOGIN_PAGE, SERVER_MESSAGE, BundleKey.REGISTRATION_SUCCESS);
            } else {
                router.setPathWithParameter(PagePath.LOGIN_PAGE, SERVER_MESSAGE, BundleKey.REGISTRATION_ERROR);
            }
        } catch (ServiceException e) {
            logger.error(e);
            router.setPathWithParameter(PagePath.LOGIN_PAGE, SERVER_MESSAGE, BundleKey.REGISTRATION_ERROR);
        }
        return router;
    }
}
