package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;

import java.sql.SQLException;
import java.util.List;

import static com.guryanov.ui.AppFrame.userMessage;

public class VersionDB {

    public static boolean Test(String... connectionData) {
        try {
            List<String> result = new DatabaseHandler().versionDB(connectionData);
            StringBuffer message = new StringBuffer();
            for (String tempString : result) {
                message.append(tempString + "\n");
            }
            userMessage.info(message.toString());
            return true;
        } catch (SQLException ex) {
            userMessage.error(ex);
            return false;
        }
    }
}


