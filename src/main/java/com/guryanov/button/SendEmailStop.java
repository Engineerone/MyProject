package com.guryanov.button;

import static com.guryanov.ui.AppFrame.statusString;
import static com.guryanov.ui.AppFrame.sendEmailStop;


public class SendEmailStop {

    public static void stop() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("send thread")) {
                sendEmailStop = true;
                statusString.append("\nemail send stop");
            }
        }
    }
}
