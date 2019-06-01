package com.epam.whatwherewhen.command.ajax;

/**
 * Date: 20.03.2019
 *
 * Contains commands coming to the {@link com.epam.whatwherewhen.controller.AjaxServlet}.
 * Uses in {@link AjaxCommandMap} class.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
final class AjaxCommandType {
    final static String SHOW_ARTICLE = "show_article";
    final static String CHECK_ANSWER = "commit_answer";
    final static String CHANGE_LOCALE = "change_locale";
    final static String FILE_APPEAL = "file_appeal";
    final static String APPROVE_APPEAL = "approve_appeal";
    final static String APPROVE_QUESTION = "approve_question";
    final static String REMOVE_POINTS = "remove_points";

    private AjaxCommandType() {
    }
}
