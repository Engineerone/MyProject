package com.guryanov.handler;

import com.guryanov.config.ConfigSetting;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.guryanov.ui.AppFrame.*;

public class EmailHandler extends ConfigSetting {
    private Session session;

    public EmailHandler() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", email_smtp_server);
        properties.put("mail.smtp.port", email_smtp_port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email_smtp_user, email_smtp_secr);
            }
        };
        session = Session.getDefaultInstance(properties, auth);
    }

    public void sendMessage(List<String[]> result) {

        for (String[] resultString : result) {
            try {
                Message message;
                InternetAddress email_from = new InternetAddress(email_fieldFrom);
                InternetAddress email_to = new InternetAddress(resultString[2]);
                message = new MimeMessage(session);
                message.setFrom(email_from);
                message.setRecipient(Message.RecipientType.TO, email_to);
                message.setSubject(resultString[3]);
                Multipart mmp = new MimeMultipart();
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(resultString[4], "text/plain; charset=utf-8");
                mmp.addBodyPart(bodyPart);
                message.setContent(mmp);
                if (realSend) {
                    Transport.send(message);
                }
                if (useWithDB)
                    new DatabaseHandler().updatetRow(Integer.valueOf(resultString[0]), resultString[1], resultString[2]);
                tableModel.setValueAt("send", Integer.valueOf(resultString[0]), 3);

            } catch (SQLException ex) {
                userMessage.ErrorExeption(ex);
            } catch (MessagingException ex) {
                userMessage.ErrorExeption(ex);
            }
        }
        dbTable.scrollRectToVisible(dbTable.getCellRect(result.size() - 15, 3, true));
    }
}
