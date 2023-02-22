package com.guryanov.button;

import static com.guryanov.ui.AppFrame.*;

import com.guryanov.handler.*;

import java.sql.SQLException;


public class SendEmail {
    public SendEmail() {
        String name;
        String email;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            name = tableModel.getValueAt(i, 1).toString();
            email = tableModel.getValueAt(i, 2).toString();
            EmailHandler mymail = new EmailHandler(email, fieldEmailSubject.getText());
            if (mymail.sendMessage(areaEmailMessage.getText().replaceAll("<name>", name))) {
                try {
                    new DatabaseHandler().updatetRow(name, email);
                    new LoadFromDB();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    statusString.append("\n" + "Send " + status);
                }
                statusString.append("\n" + name + " " + email + " message sended");
            } else statusString.append("\n" + name + " " + email + " message NOT sended");
        }
    }
}
