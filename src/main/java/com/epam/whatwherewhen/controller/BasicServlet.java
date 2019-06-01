package com.epam.whatwherewhen.controller;

import com.epam.whatwherewhen.command.general.Command;
import com.epam.whatwherewhen.command.general.CommandMap;
import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.command.RequestParameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class BasicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Command command = CommandMap.getInstance().get(req.getParameter(RequestParameter.COMMAND));
        Router router = command.execute(req);
        if (router != null) {
            String page = router.getPagePath();
            if (router.getRouteType() == Router.RouteType.FORWARD) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(page);
                requestDispatcher.forward(req, res);
            } else {
                res.sendRedirect(req.getContextPath() + page);
            }
        } else {
            req.getSession().invalidate();
            res.sendRedirect(PagePath.INDEX_PAGE);
        }
    }
}
