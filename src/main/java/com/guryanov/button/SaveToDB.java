package com.guryanov.button;

import com.guryanov.handler.DatabaseHandler;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.*;

import static com.guryanov.ui.AppFrame.*;


public class SaveToDB {
    public SaveToDB() {
        List<List<String>> result = new ArrayList<>();
        String name, email;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        statusString.setText("");
        statusString.append("Save start: " + timestamp);
        if (tableModel.getRowCount() > 0) {
            try {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    List<String> resultString;
                    name = tableModel.getValueAt(i, 1).toString();
                    email = tableModel.getValueAt(i, 2).toString();
                    resultString = Arrays.asList(name, email);
                    result.add(resultString);
                }
                new DatabaseHandler().insertRow(result);
                statusString.append("\nSave completed");
            } catch (SQLException ex) {
                userMessage.ErrorExeption(ex);
            }
            timestamp = new Timestamp(System.currentTimeMillis());
            statusString.append("\n" + "Save end: " + timestamp);
        } else {
            statusString.setText("");
            statusString.append("List empty");
        }
    }
}
