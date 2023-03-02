package com.guryanov.handler;

import com.guryanov.ui.AppFrame;

import static com.guryanov.config.ConfigSetting.*;

public class ChangeButtonVisible {
    public ChangeButtonVisible() {
        if (!useWithDB) {
            AppFrame.buttonSaveToDB.setEnabled(false);
            AppFrame.buttonLoadFromDB.setEnabled(false);
            AppFrame.buttonEraseDB.setEnabled(false);
            AppFrame.createDB.setEnabled(false);
            AppFrame.deleteDB.setEnabled(false);
        } else {
            AppFrame.buttonSaveToDB.setEnabled(true);
            AppFrame.buttonLoadFromDB.setEnabled(true);
            AppFrame.buttonEraseDB.setEnabled(true);
            AppFrame.createDB.setEnabled(true);
            AppFrame.deleteDB.setEnabled(true);
        }
    }
}
