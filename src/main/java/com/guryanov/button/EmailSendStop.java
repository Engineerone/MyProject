package com.guryanov.button;

import static com.guryanov.ui.AppFrame.statusString;

public class EmailSendStop {

    public static void stop() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("Send thread")) {
                t.stop();
                statusString.append("\nEmail send stop");
            }
        }
    }
}
