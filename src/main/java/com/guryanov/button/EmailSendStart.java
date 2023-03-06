package com.guryanov.button;

import static com.guryanov.ui.AppFrame.statusString;

public class EmailSendStart {

    public static void start() {
        SendEmail sendEmail = new SendEmail();
        Thread thread = new Thread(sendEmail, "Send thread");
        thread.start();
        statusString.append("\n" + "Send thread id " + thread.getId());
    }
}
