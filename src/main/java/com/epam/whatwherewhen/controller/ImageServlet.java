package com.epam.whatwherewhen.controller;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.epam.whatwherewhen.command.RequestParameter.SERVER_MESSAGE;

@WebServlet(urlPatterns = {"/image"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024)
public class ImageServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger();
    private final static String UPDATING_ERROR = "message.error.updating";
    private final static String DISPLAY_COMMAND = "display";
    private final static String UPLOAD_COMMAND = "upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String command = req.getParameter(RequestParameter.COMMAND);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(RequestParameter.USER);
        if (DISPLAY_COMMAND.equals(command)) {
            String authorId = req.getParameter(RequestParameter.AUTHOR_ID);
            if (authorId != null) {
                try {
                    Map<Long, User> authors = (Map<Long, User>) session.getAttribute(RequestParameter.ARTICLE_AUTHORS);
                    user = authors.get(Long.parseLong(authorId));
                    if (user == null) {
                        user = ((Map<Long, User>) session.getAttribute(RequestParameter.QUESTION_AUTHORS))
                                .get(Long.parseLong(authorId));
                    }
                } catch (NumberFormatException e) {
                    logger.error("Illegal parameter in request");
                }
            }
            try {
                if (user != null && user.getPhoto() != null) {
                    res.getOutputStream().write(user.getPhoto().getBytes(1, (int) user.getPhoto().length()));
                }
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        if (UPLOAD_COMMAND.equals(command)) {
            Part part = req.getPart(RequestParameter.IMAGE);
            String path = req.getParameter(RequestParameter.URI);
            Router router = new Router(path);
            try {
                if (UserServiceImpl.getInstance().updateImage(user, part)) {
                    session.setAttribute(RequestParameter.USER, user);
                } else {
                    router.setPathWithParameter(path, SERVER_MESSAGE, UPDATING_ERROR);
                }
            } catch (ServiceException e) {
                logger.error(e);
                router.setPathWithParameter(path, SERVER_MESSAGE, UPDATING_ERROR);
            }
            res.sendRedirect(router.getPagePath());
        }
    }
}
