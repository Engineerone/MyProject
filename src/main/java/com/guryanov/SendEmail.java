package com.guryanov;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {
    private Message message = null;
    protected static String SMTP_SERVER = Const.SMTP_SERVER;
    protected static String SMTP_Port = Const.SMTP_Port;
    protected static String SMTP_AUTH_USER = Const.SMTP_AUTH_USER;
    protected static String SMTP_AUTH_PWD = Const.SMTP_AUTH_PWD;
    protected static String EMAIL_FROM = Const.EMAIL_FROM;
    protected static String FILE_PATH = null;
    protected static String REPLY_TO = null;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public SendEmail(final String emailTo, final String thema) {
        // Настройка SMTP SSL
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_Port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        try {
            Authenticator auth = new EmailAuthenticator(SMTP_AUTH_USER,
                    SMTP_AUTH_PWD);
            Session session = Session.getDefaultInstance(properties, auth);
            session.setDebug(false);

            InternetAddress email_from = new InternetAddress(EMAIL_FROM);
            InternetAddress email_to = new InternetAddress(emailTo);
            InternetAddress reply_to = (REPLY_TO != null) ?
                    new InternetAddress(REPLY_TO) : null;
            message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject(thema);
            if (reply_to != null)
                message.setReplyTo(new Address[]{reply_to});

        } catch (AddressException e) {
            System.err.println(e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }
    public boolean sendMessage (final String text)
    {
        boolean result = false;
        try {
            // Содержимое сообщения
            Multipart mmp = new MimeMultipart();
            // Текст сообщения
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            // Вложение файла в сообщение
         //   if (FILE_PATH != null) {
          //      MimeBodyPart mbr = createFileAttachment(FILE_PATH);
          //      mmp.addBodyPart(mbr);
          //  }
            // Определение контента сообщения
            message.setContent(mmp);
            // Отправка сообщения
            Transport.send(message);
            result = true;
        } catch (MessagingException e){
            // Ошибка отправки сообщения
            System.err.println(e.getMessage());
        }
        return result;
    }


}