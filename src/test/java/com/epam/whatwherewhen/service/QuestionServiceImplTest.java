package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.entity.Question;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.QuestionServiceImpl;
import org.testng.annotations.Test;

import java.util.HashMap;

public class QuestionServiceImplTest {
    private final QuestionServiceImpl service = QuestionServiceImpl.getInstance();

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateQuestion() throws ServiceException {
        service.createQuestion(new Question());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindQuestionsByParts() throws ServiceException {
        service.findQuestionsByParts(1, -12, -1, new HashMap<>());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCommitUserAnswer() throws ServiceException {
        service.commitUserAnswer(1, 1, null, false);
    }
}