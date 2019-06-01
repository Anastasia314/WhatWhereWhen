package com.epam.whatwherewhen.controller;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.AjaxCommandMap;
import org.json.JSONException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajaxController")
public class AjaxServlet extends HttpServlet {
    private final static String CONTENT_TYPE = "application/json";

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req, res);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        String command = req.getParameter(RequestParameter.COMMAND);
        resp.setContentType(CONTENT_TYPE);
        AjaxCommandMap.getInstance().get(command).execute(req, resp);
    }
}
