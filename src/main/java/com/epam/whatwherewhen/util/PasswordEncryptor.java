package com.epam.whatwherewhen.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Date: 12.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class PasswordEncryptor {
    private final static Logger logger = LogManager.getLogger();
    private final static String ALGORITHM = "MD5";

    /**
     * Encrypts password with MD5 algorithm and returns encrypted password.
     *
     * @param password of type String for encryption
     * @return encrypted password
     */
    public static String encryptPassword(String password) {
        String encryptedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(password.getBytes(), 0, password.length());
            encryptedPassword = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return encryptedPassword;
    }
}
