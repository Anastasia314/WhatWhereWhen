package com.epam.whatwherewhen.command;

/**
 * Date: 16.02.2019
 *
 * Contains different request parameters.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public final class RequestParameter {
    public final static String ENCODING = "encoding";
    public final static String LOCALE = "locale";
    public final static String COMMAND = "command";
    public final static String URI = "uri";

    public final static String USER = "user";
    public final static String USER_ID = "userId";
    public final static String USER_DATA = "userData";
    public final static String LAST_NAME = "lastName";
    public final static String FIRST_NAME = "firstName";
    public final static String EMAIL = "email";
    public final static String BIRTHDAY = "birthday";
    public final static String CITY = "city";
    public final static String IMAGE = "image";
    public final static String PASSWORD = "password";
    public final static String LOGIN = "login";
    public final static String NEW_PASSWORD = "newPassword";
    public final static String NEW_LOGIN = "newLogin";
    public final static String NEW_EMAIL = "newEmail";
    public final static String CONFIRM_PASSWORD = "confirmPassword";

    public final static String PHOTO = "photo";
    public final static String DATE = "date";
    public final static String HEADER = "header";
    public final static String THEME = "theme";
    public final static String BODY = "body";
    public final static String LAST_ARTICLE_NUM = "lastArticleNum";
    public final static String ARTICLES_AMOUNT = "articlesAmount";
    public final static String ARTICLE_LIST = "articleList";
    public final static String ARTICLE_AUTHORS = "articleAuthors";
    public final static String ARTICLE_ID = "articleId";
    public final static String AUTHOR_ID = "authorId";
    public final static String NEWEST_ARTICLES = "newestArticleList";
    public final static String ARTICLE_DISPLAY_AMOUNT = "articleDisplayNum";
    public final static long ARTICLE_DISPLAY_NUM = 2;

    public final static String LOADING_DIRECT = "direction";
    public final static String DIRECT_NEXT = "next";
    public final static String DIRECT_PREVIOUS = "previous";

    public final static long QUESTION_DISPLAY_NUM = 5;
    public final static String QUESTION_ID = "questionId";
    public final static String QUESTION_LIST = "questionList";
    public final static String LAST_QUESTION_NUM = "lastQuestionNum";
    public final static String QUESTIONS_AMOUNT = "questionsAmount";
    public final static String QUESTION_AUTHORS = "questionAuthors";
    public final static String QUESTION_DISPLAY_AMOUNT = "questionDisplayNum";
    public final static String QUESTION_BODY = "body";
    public final static String QUESTION_ANSWER = "answer";
    public final static String QUESTION_SOURCE = "source";
    public final static String QUESTION_TYPE = "type";
    public final static String SENT_QUESTIONS = "sentQuestions";
    public final static String APPEAL_QUESTIONS = "appealQuestions";
    public final static String IS_APPROVED = "isApproved";

    public final static String USER_ANSWER = "userAnswer";
    public final static String ANSWER_RESULT = "answerResult";

    public final static String SERVER_MESSAGE = "serverMessage";

    public final static String USER_LIST = "userList";
    private RequestParameter() {
    }
}
