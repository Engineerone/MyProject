package com.guryanov.handler;

import com.guryanov.button.SaveToDB;

import static com.guryanov.ui.AppFrame.statusString;
import static com.guryanov.ui.AppFrame.userMessage;

public class SaveToDBStart {
    public static void start() {
        boolean threatStart = false;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("save thread")) threatStart = true;
        }
        if (!threatStart) {
            SaveToDB saveToDB = new SaveToDB();
            Thread thread = new Thread(saveToDB, "save thread");
            statusString.append("\n" + "save thread id " + thread.getId());
            thread.start();
        } else {
            userMessage.info("Save in progress...");
        }
    }
}


