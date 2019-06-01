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
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.epam.whatwherewhen.command.RequestParameter.*;

/**
 * Date: 16.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class SignInCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.MAIN_PAGE, Router.RouteType.REDIRECT);
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        Optional<User> user;
        try {
            user = UserServiceImpl.getInstance().authenticateUser(login, password);
            HttpSession httpSession = req.getSession();
            if (user.isPresent()) {
                httpSession.setAttribute(USER, user.get());
            } else {
                router.setPathWithParameter(PagePath.LOGIN_PAGE, SERVER_MESSAGE, BundleKey.VALIDATION_ERROR);
            }
        } catch (ServiceException e) {
            logger.error(e);
            router.setPathWithParameter(PagePath.LOGIN_PAGE, SERVER_MESSAGE, BundleKey.VALIDATION_ERROR);
        }
        return router;
    }
}
