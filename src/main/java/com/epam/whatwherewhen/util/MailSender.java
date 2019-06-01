package com.epam.whatwherewhen.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.epam.whatwherewhen.util.MailProperty.*;


/**
 * Date: 21.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class MailSender extends Thread {
    private final static Logger logger = LogManager.getLogger();
    private String sendToEmail;
    private String messageText;
    private String mailSubject;
    private String mailTextTemplate;
    private String sendFromEmail;
    private String password;
    private Properties properties;

    public MailSender(String sendToEmail, String messageText) {
        this.setName(getClass().getName());
        this.sendToEmail = sendToEmail;
        this.messageText = messageText;
    }

    public void run() {
        try {
            init();
        } catch (MissingResourceException e) {
            logger.error("Error while sending email" + e);
            Thread.currentThread().interrupt();
        }
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendFromEmail, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendFromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendToEmail));
            message.setSubject(mailSubject);
            message.setText(mailTextTemplate + " " + messageText);
            Transport.send(message);
            logger.info("The letter was sent.");
        } catch (MessagingException e) {
            logger.error("Error while sending email" + e);
        }
    }

    private void init() throws MissingResourceException {
        properties = new Properties();
        ResourceBundle dbBundle = ResourceBundle.getBundle(MAIL_BUNDLE_NAME);
        properties.put(MAIL_AUTHENTICATION, dbBundle.getString(MAIL_AUTHENTICATION));
        properties.put(MAIL_SMTP_START, dbBundle.getString(MAIL_SMTP_START));
        properties.put(MAIL_SMTP_HOST, dbBundle.getString(MAIL_SMTP_HOST));
        properties.put(MAIL_SMTP_PORT, dbBundle.getString(MAIL_SMTP_PORT));
        properties.put(MAIL_SMTP_DEBUG, dbBundle.getString(MAIL_SMTP_DEBUG));
        sendFromEmail = dbBundle.getString(MAIL_FROM);
        password = dbBundle.getString(MAIL_PASSWORD);
        mailSubject = dbBundle.getString(MAIL_SUBJECT);
        mailTextTemplate = dbBundle.getString(MAIL_TEXT);
    }
}
