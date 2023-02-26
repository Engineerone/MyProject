package com.guryanov.button;

import com.guryanov.App;
import com.guryanov.ui.AppFrame;

import static com.guryanov.config.ConfigSetting.*;
import static com.guryanov.ui.AppFrame.tableModel;

public class ChangeSave {
    public ChangeSave(boolean usedb) {
        useWithDB = usedb;
        if (!usedb) {
            AppFrame.buttonSaveToDB.setText("Save to table");
            AppFrame.buttonLoadFromDB.setEnabled(false);
            AppFrame.buttonEraseDB.setEnabled(false);
            AppFrame.createDB.setEnabled(false);
            AppFrame.deleteDB.setEnabled(false);
        } else {
            AppFrame.buttonSaveToDB.setText("Save to DB");
            AppFrame.buttonLoadFromDB.setEnabled(true);
            AppFrame.buttonEraseDB.setEnabled(true);
            AppFrame.createDB.setEnabled(true);
            AppFrame.deleteDB.setEnabled(true);
        }
        tableModel.setRowCount(0);
    }
}
