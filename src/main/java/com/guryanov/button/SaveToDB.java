package com.guryanov.button;

import com.guryanov.handler.*;

import static com.guryanov.ui.AppFrame.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class SaveToDB {
    public SaveToDB(boolean useDB) {
        FileContainCheck fileContainCheck = new FileContainCheck(areaFileContain);
        Set<List<String>> result = fileContainCheck.result;
        if (result.size() > 0) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statusString.setText("");
            statusString.append("Save start: " + timestamp);
            try {
                if (!useDB) {
                    tableModel.setRowCount(0);
                    int i = 0;
                    for (List<String> resultString : result) {
                        tableModel.insertRow(i, new Object[]{i + 1, resultString.get(0), resultString.get(1), ""});
                        i++;
                    }
                } else
                    new DatabaseHandler().insertRow(result);
                statusString.append("\nSave completed");
            } catch (SQLException ex) {
                userMessage.ErrorExeption(ex);
            }
            timestamp = new Timestamp(System.currentTimeMillis());
            statusString.append("\n" + "Save end: " + timestamp);
        }

    }
}

