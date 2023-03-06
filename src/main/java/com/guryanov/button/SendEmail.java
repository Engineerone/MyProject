package com.guryanov.button;

import static com.guryanov.ui.AppFrame.*;

import com.guryanov.handler.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendEmail implements Runnable {

    static Timestamp timestamp;

    @Override
    public void run() {
        int count;
        timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\nsend start -> " + timestamp);
        String name, email, emalSubject, emailText;
        EmailHandler mymail = new EmailHandler();
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 3).toString().equals("send")) {
                statusString.append("\nemail already sent -> " + tableModel.getValueAt(i, 2));
                continue;
            }
            List<String> resultString;
            name = tableModel.getValueAt(i, 1).toString();
            email = tableModel.getValueAt(i, 2).toString();
            emalSubject = fieldEmailSubject.getText();
            emailText = areaEmailMessage.getText().replaceAll("<name>", name);
            resultString = Arrays.asList(String.valueOf(i), name, email, emalSubject, emailText);
            result.add(resultString);
        }
        count = mymail.sendMessage(result);
        statusString.append("\nsend completed (real sent " + count + " email)");
        timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\nsend end -> " + timestamp);
    }
}