package com.guryanov.button;

import static com.guryanov.ui.AppFrame.statusString;

public class SaveToDBStop {
    public static void stop() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("save thread")) {
                t.stop();
                statusString.append("\nsave stop");
            }
        }
    }

}
