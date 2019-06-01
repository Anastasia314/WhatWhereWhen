package com.epam.whatwherewhen.command.general;

import com.epam.whatwherewhen.command.PagePath;
import com.epam.whatwherewhen.command.general.impl.*;
import com.epam.whatwherewhen.controller.Router;

import java.util.EnumMap;

import static com.epam.whatwherewhen.command.general.CommandType.*;

/**
 * Date: 16.02.2019
 *
 * Sets a match between a command and its handler.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class CommandMap {
    private final EnumMap<CommandType, Command> map = new EnumMap<CommandType, Command>(CommandType.class) {
        {
            this.put(DEFAULT_COMMAND, (req) -> new Router(PagePath.MAIN_PAGE));
            this.put(SIGN_OUT, new SignOutCommand());
            this.put(SIGN_IN, new SignInCommand());
            this.put(SIGN_UP, new SignUpCommand());
            this.put(INITIALIZE_START_PAGE, new InitializeStartPageCommand());
            this.put(LOAD_QUESTIONS, new LoadQuestionsCommand());
            this.put(LOAD_USER_DATA, new LoadUserDataCommand());
            this.put(CHANGE_USERDATA, new ChangeUserDataCommand());
            this.put(LOAD_ARTICLES, new LoadArticlesCommand());
            this.put(SEND_QUESTION, new SendQuestionCommand());
            this.put(LOAD_ADMIN_DATA, new LoadAdminDataCommand());
        }
    };
    private final static CommandMap INSTANCE = new CommandMap();

    private CommandMap() {
    }

    public static CommandMap getInstance() {
        return INSTANCE;
    }

    public Command get(CommandType key) {
        return map.get(key);
    }

    public Command get(String command) {
        CommandType key;
        try {
            key = CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            key = DEFAULT_COMMAND;
        }
        return map.get(key);
    }
}
