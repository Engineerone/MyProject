package com.guryanov.button;

import com.guryanov.handler.NoticeHandler;
import com.guryanov.ui.AppFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;

import static com.guryanov.ui.AppFrame.*;

public class PrepareFile {
    public PrepareFile(AppFrame frame) {
        fileChooser.setDialogTitle("Directory selection");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("d:\\outputfile.txt"))) {
                while (reader.ready()) {
                    String tempString = reader.readLine().trim() + "\n";
                    if (!tempString.contains("@")) continue;
                    int tabStatement = tempString.indexOf("@");
                    String name = tempString.substring(0, tabStatement);
                    writer.write(name + "\t" + tempString);
                }
                statusString.append("\n" + "Output file write completed");
            } catch (IOException ex) {
                new NoticeHandler().IOError(ex.getMessage());
            }

        }
    }
}

