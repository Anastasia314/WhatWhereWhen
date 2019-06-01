package com.epam.whatwherewhen.command.general;

import com.epam.whatwherewhen.controller.Router;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Command {
    /**
     * Executes a specific command and returns Router object.
     *
     * @param request client request from a servlet
     * @return object of Router
     */
    Router execute(HttpServletRequest request);
}
