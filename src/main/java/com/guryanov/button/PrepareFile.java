package com.guryanov.button;

import com.guryanov.handler.FileLoad;
import com.guryanov.handler.FileSave;
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
//        String currentDirectory = fileChooser.getCurrentDirectory().getAbsolutePath();
//        String outputFilePath = currentDirectory + "\\outputfile.txt";
        if (result == JFileChooser.APPROVE_OPTION) {

            StringBuffer stringBuffer = new FileLoad().fileContain;
            new FileSave(stringBuffer);
//
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
//                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
//                while (reader.ready()) {
//                    String tempString = reader.readLine().trim() + "\n";
//                    if (!tempString.contains("@")) continue;
//
//                    int tabStatement = tempString.indexOf("@");
//                    String name = tempString.substring(0, tabStatement);
//                    writer.write(name + "\t" + tempString);
//                }
//                statusString.setText("");
//                statusString.append("Output file write completed: " + outputFilePath);
//            } catch (IOException ex) {
//                userMessage.ErrorExeption(ex);
//            }
        }
    }
}

