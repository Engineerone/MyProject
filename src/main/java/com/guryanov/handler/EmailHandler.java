package com.guryanov.handler;

import com.guryanov.config.ConfigSetting;
import com.guryanov.ui.AppFrame;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import static com.guryanov.ui.AppFrame.statusString;

public class EmailHandler extends ConfigSetting {
    private Message message = null;

    public EmailHandler(final String emailTo, final String thema) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", email_smtp_server);
        properties.put("mail.smtp.port", email_smtp_port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Authenticator auth = new EmailAuthenticator(email_smtp_user, email_smtp_secr);
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(false);
        InternetAddress email_from = new InternetAddress(email_fieldFrom);
        InternetAddress email_to = new InternetAddress(emailTo);
        message = new MimeMessage(session);
        message.setFrom(email_from);
        message.setRecipient(Message.RecipientType.TO, email_to);
        message.setSubject(thema);
        AppFrame.status = "completed";
    }

    public boolean sendMessage(final String text) {
        boolean result = true;
        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            message.setContent(mmp);
            if (realSend) {
                Transport.send(message);
                result = true;
            }
        } catch (MessagingException e) {
            statusString.append("\n" + " " + e.getMessage());
        }
        return result;
    }
}
