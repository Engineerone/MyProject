package com.guryanov.button;

import com.guryanov.handler.NoticeHandler;
import com.guryanov.ui.AppFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.guryanov.ui.AppFrame.*;

public class FileMenu {
    public FileMenu(AppFrame frame){
        fileChooser.setDialogTitle("Directory selection");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String tempString;
                areaFileContain.setText("");
                while (reader.ready()) {
                    tempString = reader.readLine().trim();
                    if (tempString.length() == 0 | !tempString.contains("\t") | !tempString.contains("@")) continue;
                    if (tempString.startsWith("\n")) continue;
                    areaFileContain.append(tempString + "\n");
                }
            } catch (IOException ex) {
                new NoticeHandler().IOError(ex.getMessage());
            }
        }
    }
}
