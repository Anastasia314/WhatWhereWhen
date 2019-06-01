package com.epam.whatwherewhen.command.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 20.03.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public interface AjaxCommand {
    /**
     * Executes a specific command and returns Router object.
     *
     * @param req  client request from a servlet
     * @param resp response to client
     */
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
