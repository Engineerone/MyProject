package com.guryanov.handler;

import com.guryanov.button.SendEmail;

import static com.guryanov.ui.AppFrame.statusString;
import static com.guryanov.ui.AppFrame.userMessage;

public class SendEmailStart {

    public static void start() {
        boolean threatSaveStart = false;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("save thread")) threatSaveStart = true;
        }
        if (!threatSaveStart) {
            boolean threatStart = false;
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getName().equals("send thread")) threatStart = true;
            }
            if (!threatStart) {
                SendEmail sendEmail = new SendEmail();
                Thread thread = new Thread(sendEmail, "send thread");
                statusString.append("\n" + "send thread id " + thread.getId());
                thread.start();
            } else {
                userMessage.info("Send in progress...");
            }
        } else userMessage.info("Complete the save first !");
    }
}
