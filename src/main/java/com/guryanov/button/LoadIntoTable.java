package com.guryanov.button;

import com.guryanov.handler.*;

import static com.guryanov.ui.AppFrame.*;

import java.util.*;

public class LoadIntoTable {

    public static void load() {
        Set<List<String>> result = FileCheck.checkFileLoadTable(areaFileContain);
        int i = 0;
        if (!result.isEmpty()) {
            tableModel.setRowCount(0);
            for (List<String> resultString : result) {
                tableModel.insertRow(i, new Object[]{i + 1, resultString.get(0), resultString.get(1), ""});
                i++;
            }
        }
    }
}

