package com.epam.whatwherewhen.validation;

import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.entity.UserData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RequestValidatorTest {

    @Test(dataProvider = "loginPasswordData")
    public void testIsValidLoginPassword(String login, String password, boolean expected) {
        boolean result = RequestValidator.getInstance().isValidLoginPassword(login, password);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "emailsData")
    public void testIsValidEmail(String email, boolean expected) {
        boolean result = RequestValidator.getInstance().isValidEmail(email);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "usersData")
    public void testIsValidUserData(UserData userData, boolean expected) {
        boolean result = RequestValidator.getInstance().isValidUserData(userData);
        assertEquals(result, expected);
    }

    @Test(dataProvider = "questionsData")
    public void testIsValidQuestionData(Question question, boolean expected) {
        boolean result = RequestValidator.getInstance().isValidQuestion(question);
        assertEquals(result, expected);
    }

    @DataProvider(name = "emailsData")
    public Object[][] emailsData() {
        return new Object[][]{
                {"akavzovich@mail.ru", true},
                {"akavzovich@gmail.com", true},
                {"akavzovichmail.ru", false},
                {"akavzovich@mailru", false},
                {"ch@mail.ru", false},
                {"akavzovich", false}
        };
    }

    @DataProvider(name = "loginPasswordData")
    public Object[][] loginPasswordData() {
        return new Object[][]{
                {"login", "password", true},
                {"login1", "password1", true},
                {"log'in", "password", false},
                {"login", "pas13sworddddddddddddddddddd", false},
                {"logi", "password", false},
                {"login", "", false}
        };
    }

    @DataProvider(name = "usersData")
    public Object[][] usersData() {
        return new Object[][]{
                {new UserData(1, "akakaa@mail.ru", "firstName", "lastName",
                        1232131142), true},
                {new UserData(-2, "akak@mail.com", "firstNamefirstNam", "lastNamelastNa",
                        1232131142), false},
                {new UserData(2, "akak@mail", "firstNane", "lastName",
                        1232131142), false},
                {new UserData(), false},
                {null, false}
        };
    }

    @DataProvider(name = "questionsData")
    public Object[][] questionsData() {
        return new Object[][]{
                {new Question(1, "type", "body", "answer", 1231312, "source"),
                        true},
                {new Question(1, null, "body", "answer", 1231312, "source"),
                        false},
                {new Question(1, "type", "body", "answer", 0, "source"),
                        false},
                {new Question(), false},
                {null, false}
        };
    }

}