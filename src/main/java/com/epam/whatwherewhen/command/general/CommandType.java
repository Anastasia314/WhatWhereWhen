package com.epam.whatwherewhen.command.general;

/**
 * Date: 16.02.2019
 *
 * Contains commands coming to the {@link com.epam.whatwherewhen.controller.BasicServlet}.
 * Uses in {@link CommandMap} class.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
enum CommandType {
    DEFAULT_COMMAND,
    SIGN_OUT,
    SIGN_IN,
    SIGN_UP,
    INITIALIZE_START_PAGE,
    LOAD_USER_DATA,
    LOAD_QUESTIONS,
    CHANGE_USERDATA,
    LOAD_ARTICLES,
    SEND_QUESTION,
    LOAD_ADMIN_DATA
}
