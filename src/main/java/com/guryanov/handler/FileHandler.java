package com.guryanov.handler;

import com.guryanov.interf.FileAction;

import java.io.*;

import static com.guryanov.ui.AppFrame.*;

public class FileHandler implements FileAction {
    @Override
    public StringBuffer Load(File file) {
        StringBuffer fileContain = new StringBuffer();
        try (FileReader reader = new FileReader(file)) {
            while (reader.ready()) {
                char ch = (char) reader.read();
                fileContain.append(ch);
            }
        } catch (IOException ex) {
            userMessage.ErrorExeption(ex);
        }
        return fileContain;
    }

    @Override
    public void Save(StringBuffer stringBuffer, String fileName, String currentDirectory) {
        fileName = fileName.substring(0, fileName.indexOf('.'));
        String outputFilePath = currentDirectory + "\\" + fileName + "-out.txt";
        try (FileWriter fileWriter = new FileWriter((outputFilePath))) {
            fileWriter.write(String.valueOf(stringBuffer));
            userMessage.InfoMessage("Save completed " + outputFilePath);
        } catch (IOException ex) {
            userMessage.ErrorExeption(ex);
        }
    }
}
