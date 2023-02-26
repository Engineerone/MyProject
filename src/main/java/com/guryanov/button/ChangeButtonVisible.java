package com.guryanov.button;

import com.guryanov.ui.AppFrame;

import static com.guryanov.config.ConfigSetting.*;
import static com.guryanov.ui.AppFrame.tableModel;

public class ChangeButtonVisible {
    public ChangeButtonVisible() {

        if (!useWithDB) {
            AppFrame.buttonSaveToDB.setText("Load into table");
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
