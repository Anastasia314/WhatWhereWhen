package com.epam.whatwherewhen.util;

/**
 * Date: 06.03.2019
 *
 * Contains properties for mail sender connection.
 * Uses in {@link MailSender} class
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
final class MailProperty {
    final static String MAIL_BUNDLE_NAME = "mail";
    final static String MAIL_AUTHENTICATION = "mail.smtp.auth";
    final static String MAIL_SMTP_START = "mail.smtp.starttls.enable";
    final static String MAIL_SMTP_HOST = "mail.smtp.host";
    final static String MAIL_SMTP_PORT = "mail.smtp.port";
    final static String MAIL_SMTP_DEBUG = "mail.smtp.debug";
    final static String MAIL_FROM = "mail.from";
    final static String MAIL_PASSWORD = "mail.password";
    final static String MAIL_SUBJECT = "mail.subject";
    final static String MAIL_TEXT = "mail.text";

    public MailProperty() {
    }
}
