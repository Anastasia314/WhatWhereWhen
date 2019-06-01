package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.controller.Router;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.epam.whatwherewhen.command.RequestParameter.USER;

/**
 * Date: 27.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class LoadUserDataCommand implements Command {

    private final static Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router(PagePath.USER_DATA_PAGE);
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(USER);
        try {
            Optional<UserData> userData = UserServiceImpl.getInstance().findUserData(user.getUserId());
            userData.ifPresent(data -> httpSession.setAttribute(RequestParameter.USER_DATA, data));
        } catch (ServiceException e) {
            logger.error(e);
            router.setPagePath(PagePath.ERROR_PAGE);
        }
        return router;
    }
}
