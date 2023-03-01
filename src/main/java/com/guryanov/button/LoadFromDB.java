package com.guryanov.button;

import com.guryanov.handler.*;

import java.sql.SQLException;
import java.util.List;

import static com.guryanov.ui.AppFrame.*;

public class LoadFromDB {
    public LoadFromDB() {
        try {
            List<String[]> result;
            result = new DatabaseHandler().LoadFromDB();
            tableModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                String[] value = result.get(i);
                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2], value[3]});
            }
            statusString.setText("");
            statusString.append("Load completed");
        } catch (SQLException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}
