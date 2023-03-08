package com.guryanov.button;

import static com.guryanov.ui.AppFrame.saveStop;
import static com.guryanov.ui.AppFrame.statusString;

public class SaveToDBStop {
    public static void stop() {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("save thread")) {
                saveStop = true;
                statusString.append("\nsave stop");
            }
        }
    }
}
