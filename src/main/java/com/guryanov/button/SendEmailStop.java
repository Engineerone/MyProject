package com.guryanov.button;

import static com.guryanov.ui.AppFrame.statusString;

public class SendEmailStop {

    public static void stop() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("send thread")) {
                t.stop();
                statusString.append("\nemail send stop");
            }
        }
    }
}
