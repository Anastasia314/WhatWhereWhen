package com.epam.whatwherewhen.filter;

import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/admin/*", "/jsp/private/*", "/controller"})
public class PrivatePageRedirectFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final static String COMMAND = "command";
    private final static String PRIVATE_URL_PATTERN = "/jsp/private/";
    private final static String ADMIN_URL_PATTERN = "/jsp/admin/";

    private enum PrivateCommand {
        LOAD_USER_DATA,
        LOAD_QUESTIONS,
        CHANGE_USERDATA,
        SEND_QUESTION,
        COMMIT_ANSWER,
        FILE_APPEAL;

        static boolean contains(String searching) {
            if (searching == null) return false;
            for (PrivateCommand command : PrivateCommand.values()) {
                if (command.toString().toLowerCase().startsWith(searching)) {
                    return true;
                }
            }
            return false;
        }
    }

    private enum AdminCommand {
        LOAD_ADMIN_DATA,
        REMOVE_POINTS,
        APPROVE_QUESTION,
        APPROVE_APPEAL;

        static boolean contains(String searching) {
            if (searching == null) return false;
            for (AdminCommand command : AdminCommand.values()) {
                if (command.toString().toLowerCase().startsWith(searching)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        String command = httpRequest.getParameter(COMMAND);
        User user = (User) httpRequest.getSession().getAttribute(RequestParameter.USER);

        if ((user == null
                && (PrivateCommand.contains(command) || httpRequest.getRequestURI().contains(PRIVATE_URL_PATTERN)
                || AdminCommand.contains(command)) || httpRequest.getRequestURI().contains(ADMIN_URL_PATTERN))
                || (user != null && !user.isAdmin()
                && (AdminCommand.contains(command)) || httpRequest.getRequestURI().contains(ADMIN_URL_PATTERN))) {
            logger.error("Unauthorized request " + httpRequest.getRequestURI() + " from " + req.getRemoteAddr());
            httpResponse.sendRedirect(httpRequest.getContextPath() + PagePath.INDEX_PAGE);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
