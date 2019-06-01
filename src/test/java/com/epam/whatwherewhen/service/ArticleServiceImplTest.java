package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.ArticleServiceImpl;
import org.testng.annotations.Test;

import java.util.HashMap;

public class ArticleServiceImplTest {
    private final ArticleServiceImpl service = ArticleServiceImpl.getInstance();

    @Test(expectedExceptions = ServiceException.class)
    public void testFindArticlesByParts() throws ServiceException {
        service.findArticlesByParts(-12, 0, new HashMap<>());
    }
}