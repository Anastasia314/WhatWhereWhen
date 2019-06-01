package com.epam.whatwherewhen.command.general.impl;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Date: 16.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class SignOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return new Router(PagePath.INDEX_PAGE, Router.RouteType.REDIRECT);
    }
}
