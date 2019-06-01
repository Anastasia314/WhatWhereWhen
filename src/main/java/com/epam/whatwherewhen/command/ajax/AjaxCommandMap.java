package com.epam.whatwherewhen.command.ajax;

import com.epam.whatwherewhen.command.RequestParameter;
import com.epam.whatwherewhen.command.ajax.impl.*;

import java.util.HashMap;
import java.util.Map;

import static com.epam.whatwherewhen.command.ajax.AjaxCommandType.*;

/**
 * Date: 20.03.2019
 *
 * Sets a match between a ajax command and its handler.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class AjaxCommandMap {

    private final Map<String, AjaxCommand> map = new HashMap<String, AjaxCommand>() {
        {
            this.put(SHOW_ARTICLE, new ShowArticleCommand());
            this.put(CHECK_ANSWER, new CommitAnswerCommand());
            this.put(FILE_APPEAL, new FileAppealCommand());
            this.put(APPROVE_APPEAL, new ApproveAppealCommand());
            this.put(APPROVE_QUESTION, new ApproveQuestionCommand());
            this.put(REMOVE_POINTS, new RemoveRatingCommand());
            this.put(CHANGE_LOCALE, (req, res) ->
                    req.getSession().setAttribute(RequestParameter.LOCALE, req.getParameter(RequestParameter.LOCALE)));
        }
    };

    private final static AjaxCommandMap INSTANCE = new AjaxCommandMap();

    private AjaxCommandMap() {
    }

    public static AjaxCommandMap getInstance() {
        return INSTANCE;
    }

    public AjaxCommand get(String command) {
        return map.get(command);
    }
}
