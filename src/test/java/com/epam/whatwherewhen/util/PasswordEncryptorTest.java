package com.epam.whatwherewhen.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordEncryptorTest {

    @Test(dataProvider = "passwordsForEncryption")
    public void testEncryptPassword(String password, String expectedEncrypted) {
        String result = PasswordEncryptor.encryptPassword(password);
        assertEquals(expectedEncrypted,result);
    }

    @DataProvider(name = "passwordsForEncryption")
    public Object[][] passwordsForEncryption() {
        return new Object[][]{
                {"Password", "dc647eb65e6711e155375218212b3964"},
                {"Passwor", "99720e9d12313f383a3abe8116e74b69"},
                {"Passwo", "6682cb4efbaea40f138fb080e345b056"},
                {"Passw", "a00d2b6c29c8eddabce29f44c4799714"}
        };
    }
}