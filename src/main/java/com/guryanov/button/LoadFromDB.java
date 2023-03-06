package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;
import java.util.List;

import static com.guryanov.ui.AppFrame.*;

public class LoadFromDB {
    public static void load() {
        try {
            List<String[]> result;
            result = new DatabaseHandler().loadDB();
            tableModel.setRowCount(0);
            int count=0;
            for (int i = 0; i < result.size(); i++) {
                String[] value = result.get(i);
                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2], value[3]});
                count++;
            }
            statusString.append("\nload completed (" + count + " rows)");
        } catch (SQLException ex) {
            userMessage.error(ex);
        }
    }
}
