package com.guryanov.handler;

import java.io.*;

import static com.guryanov.ui.AppFrame.fileChooser;
import static com.guryanov.ui.AppFrame.userMessage;

public class FileSave {
    public FileSave(StringBuffer stringBuffer) {
        String currentDirectory = fileChooser.getCurrentDirectory().getAbsolutePath();
        String outputFilePath = currentDirectory + "\\outputfile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
        } catch (IOException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}