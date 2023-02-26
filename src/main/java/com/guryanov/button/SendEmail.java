package com.guryanov.button;

import static com.guryanov.config.ConfigSetting.useWithDB;
import static com.guryanov.ui.AppFrame.*;

import com.guryanov.handler.*;
import com.guryanov.ui.AppFrame;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.sql.SQLException;


public class SendEmail implements Runnable {
    public SendEmail() {
//        String name;
//        String email;
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            name = tableModel.getValueAt(i, 1).toString();
//            email = tableModel.getValueAt(i, 2).toString();
//            EmailHandler mymail = new EmailHandler(email, fieldEmailSubject.getText());
//            if (mymail.sendMessage(areaEmailMessage.getText().replaceAll("<name>", name))) {
//                try {
//                    new DatabaseHandler().updatetRow(name, email);
//                    new LoadFromDB();
//                } catch (SQLException ex) {
//                    new NoticeHandler().getSQLExceptionNotice(ex);
//                } finally {
//                    statusString.append("\n" + "Send " + status);
//                }
//                statusString.append("\n" + name + " " + email + " message sended");
//            } else statusString.append("\n" + name + " " + email + " message NOT sended");
//        }
    }

    @Override
    public void run() {
        String name;
        String email;
        String send;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            name = tableModel.getValueAt(i, 1).toString();
            email = tableModel.getValueAt(i, 2).toString();
            send = tableModel.getValueAt(i, 3).toString();
            if (!send.equals("send")) {
                try {
                    EmailHandler mymail = new EmailHandler(email, fieldEmailSubject.getText());
                    if (mymail.sendMessage(areaEmailMessage.getText().replaceAll("<name>", name))) {
                        try {
                            if (useWithDB) {
                                new DatabaseHandler().updatetRow(name, email);
                                tableModel.setValueAt("send",i,3);
                            } else
                                tableModel.setValueAt("send",i,3);
                        } catch (SQLException ex) {
                            new NoticeHandler().getSQLExceptionNotice(ex);
                        } finally {
                            statusString.append("\n" + "Send " + status);
                        }
                        statusString.append("\n" + name + " " + email + " message sended");
                        dbTable.scrollRectToVisible(dbTable.getCellRect(i + 20, 3, true));
                    } else statusString.append("\n" + name + " " + email + " message NOT sended");
                } catch (MessagingException ex) {
                    AppFrame.status = "error";
                    statusString.append("\n" + ex.getMessage());
                }
            }
        }
    }
}
