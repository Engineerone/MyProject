package com.guryanov.button;

import static com.guryanov.ui.AppFrame.*;

import com.guryanov.handler.*;

import java.util.ArrayList;
import java.util.List;

public class SendEmail implements Runnable {
    public SendEmail() {
    }

    @Override
    public void run() {
        String name, email, emalSubject, emailText;
        EmailHandler mymail = new EmailHandler();
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 3).toString().equals("send")) continue;
            String[] resultString = new String[5];
            name = tableModel.getValueAt(i, 1).toString();
            email = tableModel.getValueAt(i, 2).toString();
            emalSubject = fieldEmailSubject.getText();
            emailText = areaEmailMessage.getText().replaceAll("<name>", name);
            resultString[0] = String.valueOf(i);
            resultString[1] = name;
            resultString[2] = email;
            resultString[3] = emalSubject;
            resultString[4] = emailText;
            result.add(resultString);
        }
        mymail.sendMessage(result);
    }
}