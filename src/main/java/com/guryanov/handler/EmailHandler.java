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

    public int sendMessage(List<List<String>> result) {
        int count = 0;
        for (List<String> resultString : result) {
            if (!sendEmailStop) {
                try {
                    Message message;
                    InternetAddress email_from = new InternetAddress(email_fieldFrom);
                    InternetAddress email_to = new InternetAddress(resultString.get(2));
                    message = new MimeMessage(session);
                    message.setFrom(email_from);
                    message.setRecipient(Message.RecipientType.TO, email_to);
                    message.setSubject(resultString.get(3));
                    Multipart mmp = new MimeMultipart();
                    MimeBodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setContent(resultString.get(4), "text/plain; charset=utf-8");
                    mmp.addBodyPart(bodyPart);
                    message.setContent(mmp);
                    if (realSend) {
                        Transport.send(message);
                        statusString.append("\nemail sent -> " + resultString.get(2));
                        if (useWithDB) new DatabaseHandler().updateRow(resultString.get(1), resultString.get(2));
                        count++;
                    }
                } catch (MessagingException ex) {
                    statusString.append("\n" + ex.getMessage());
                } catch (SQLException ex) {
                    userMessage.error(ex);
                    break;
                }
                tableModel.setValueAt("send", Integer.valueOf(resultString.get(0)), 3);
            } else break;
            dbTable.scrollRectToVisible(dbTable.getCellRect(result.size() - 15, 3, true));
        }
        sendEmailStop = false;
        return count;
    }
}
