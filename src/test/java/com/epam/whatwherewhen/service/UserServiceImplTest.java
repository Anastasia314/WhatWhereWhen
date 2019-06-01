package com.epam.whatwherewhen.service;

import com.epam.whatwherewhen.entity.User;
import com.epam.whatwherewhen.entity.UserData;
import com.epam.whatwherewhen.exception.ServiceException;
import com.epam.whatwherewhen.service.impl.UserServiceImpl;
import org.testng.annotations.Test;

public class UserServiceImplTest {
    private final UserServiceImpl service = UserServiceImpl.getInstance();

    @Test(expectedExceptions = ServiceException.class)
    public void testAuthenticateUser() throws ServiceException {
        service.authenticateUser("A", "");
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegistryUser() throws ServiceException {
        service.registryUser(new User(), "akavzovich@mail.ru", "");
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testChangeUserData() throws ServiceException {
        service.changeUserData(new UserData());
    }
}