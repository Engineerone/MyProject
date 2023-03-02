package com.guryanov.button;

import com.guryanov.handler.*;

import static com.guryanov.ui.AppFrame.*;

import java.sql.Timestamp;
import java.util.*;

public class LoadIntoTable {
    public LoadIntoTable() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        statusString.setText("");
        statusString.append("Load start: " + timestamp);
        FileContainCheck fileContainCheck = new FileContainCheck(areaFileContain);
        Set<List<String>> result = fileContainCheck.result;
        if (result.size() > 0) {
            tableModel.setRowCount(0);
            int i = 0;
            for (List<String> resultString : result) {
                tableModel.insertRow(i, new Object[]{i + 1, resultString.get(0), resultString.get(1), ""});
                i++;
            }
            statusString.append("\nLoad completed");
        }
        timestamp = new Timestamp(System.currentTimeMillis());
        statusString.append("\n" + "Load end: " + timestamp);
    }
}

