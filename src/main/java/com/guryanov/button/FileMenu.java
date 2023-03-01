package com.guryanov.button;

import com.guryanov.handler.FileLoad;
import com.guryanov.ui.AppFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static com.guryanov.ui.AppFrame.*;

public class FileMenu {
    public FileMenu(AppFrame frame) {
        fileChooser.setDialogTitle("Directory selection");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            areaFileContain.setText("");
            areaFileContain.append(String.valueOf(new FileLoad().fileContain));
        }
    }
}
