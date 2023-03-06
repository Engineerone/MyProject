package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.*;

import static com.guryanov.ui.AppFrame.*;

public class SaveToDB {

    public static void save() {
        List<List<String>> result = new ArrayList<>();
        String name, email;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int count;
        // statusString.setText("");
        statusString.append("\nSave start -> " + timestamp);
        if (tableModel.getRowCount() > 0) {
            try {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    List<String> resultString;
                    name = tableModel.getValueAt(i, 1).toString();
                    email = tableModel.getValueAt(i, 2).toString();
                    resultString = Arrays.asList(name, email);
                    result.add(resultString);
                }
                count = new DatabaseHandler().insertRow(result);
                statusString.append("\nSave completed (" + count + " rows)");
            } catch (SQLException ex) {
                userMessage.error(ex);
            }
            timestamp = new Timestamp(System.currentTimeMillis());
            statusString.append("\nSave end -> " + timestamp);
        } else {
            //  statusString.setText("");
            statusString.append("\nList empty");
        }
    }
}
