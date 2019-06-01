package com.epam.whatwherewhen.filter;

import com.epam.whatwherewhen.command.RequestParameter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8",
        description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(RequestParameter.ENCODING);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (encoding != null && !encoding.equalsIgnoreCase(req.getCharacterEncoding())) {
            req.setCharacterEncoding(encoding);
            res.setCharacterEncoding(encoding);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
