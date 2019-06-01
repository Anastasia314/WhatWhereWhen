package com.epam.whatwherewhen.validation;

import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.UserData;

/**
 * Date: 17.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class RequestValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]{5,25}+@[A-Za-z0-9.-]{2,8}\\.[A-Za-z]{2,6}$";
    private static final String LOGIN_REGEX = "^[A-Za-zA-Яа-я0-9]{5,15}$";
    private static final String PASSWORD_REGEX = "^[A-Za-zA-Яа-я0-9]{5,15}$";
    private static final String NAME_REGEX = "^[A-Za-zA-Яа-я]{2,40}$";

    private RequestValidator() {
    }

    private static class SingletonHolder {
        private final static RequestValidator INSTANCE = new RequestValidator();
    }

    public static RequestValidator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Checks login and password by pattern and returns true if they are correct.
     *
     * @param login    of String
     * @param password of String
     * @return true if login and password are correct
     */
    public boolean isValidLoginPassword(String login, String password) {
        return login != null && login.matches(LOGIN_REGEX)
                && password != null && password.matches(PASSWORD_REGEX);

    }

    /**
     * Checks email by pattern and returns true if it's correct.
     *
     * @param email of String
     * @return true if email is correct
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);

    }

    /**
     * Checks UserData object and returns true if it's correct.
     *
     * @param userData of type UserData
     * @return true if userData is correct
     */
    public boolean isValidUserData(UserData userData) {
        return userData != null && isValidEmail(userData.getEmail()) && isValidName(userData.getLastName())
                && isValidName(userData.getFirstName()) && userData.getUserId() > 0;
    }

    /**
     * Checks Question object and returns true if it's correct.
     *
     * @param question of type Question
     * @return true if question is correct
     */
    public boolean isValidQuestion(Question question) {
        return question != null && question.getType() != null && question.getAnswer() != null
                && question.getBody() != null && question.getSource() != null && question.getDate() > 0;
    }

    /**
     * Checks Question object and returns true if it's correct.
     *
     * @param question of type Question, should contain correct type, body, answer and source to be correct
     * @return true if question is correct
     */
    public boolean isValidQuestionForUpdate(Question question) {
        return question != null && question.getType() != null && question.getAnswer() != null
                && question.getBody() != null && question.getSource() != null;
    }

    /**
     * Checks name and returns true if it's correct.
     *
     * @param name of String
     * @return true if name is correct
     */
    private boolean isValidName(String name) {
        return name != null && name.matches(NAME_REGEX);

    }
}
