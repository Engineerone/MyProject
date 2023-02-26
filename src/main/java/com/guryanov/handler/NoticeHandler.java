package com.guryanov.handler;

import com.guryanov.ui.AppFrame;
import javax.swing.*;
import java.sql.SQLException;

public class NoticeHandler {

    public void createDB() {
        JOptionPane.showMessageDialog(
                null,
                "База данных создана",
                "Output", JOptionPane.INFORMATION_MESSAGE);
    }
    public void dbDrop() {
        JOptionPane.showMessageDialog(null,
                "База данных удалена",
                "Output", JOptionPane.INFORMATION_MESSAGE);
    }


    void dbExist(String error) {
        JOptionPane.showMessageDialog(
                null,
                "База данных с таким именем уже создана"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }

    void dbNotFound(String error) {
        JOptionPane.showMessageDialog(
                null,
                "База данных не найдена"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }



    void unknowError(String error) {
        JOptionPane.showMessageDialog(
                null,
                "Неизвестная ошибка"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }

    void communicationsDBError(String error) {
        JOptionPane.showMessageDialog(
                null,
                "Ошибка соединения с базой данных"+error,
                "Ounput",
                JOptionPane.ERROR_MESSAGE);
    }

    void authorizationDBError(String error) {
        JOptionPane.showMessageDialog(
                null,
                "Ошибка авторизации"+error,
                "Ounput",
                JOptionPane.ERROR_MESSAGE);
    }

    void getClassNotFoundException(String error) {
        JOptionPane.showMessageDialog(
                null,
                "Класс не найден"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }
    public void IOError(String error) {
        JOptionPane.showMessageDialog(
                null,
                "Ошибка обработки файла"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }

    public void FileNotFoundException(String error){
        JOptionPane.showMessageDialog(
                null,
                "Файл не найден"+error,
                "Output", JOptionPane.ERROR_MESSAGE);
    }
    public void getSQLExceptionNotice(SQLException ex) {
        if (ex.getSQLState().startsWith("28")) {
            authorizationDBError("\n"+ex.getMessage());
        } else if (ex.getSQLState().startsWith("08")) {
            communicationsDBError("\n"+ex.getMessage());
        } else if (ex.getSQLState().startsWith("42")) {
            dbNotFound("\n"+ex.getMessage());
        } else if (ex.getSQLState().startsWith("HY")) {
            dbExist("\n"+ex.getMessage());
        } else {
            unknowError("\n"+ex.getMessage());
        }
        AppFrame.status = "error";
    }

    public void getClassNotFoundException(ClassNotFoundException ex) {
        getClassNotFoundException("\n"+ex.getMessage());
        AppFrame.status = "error";
    }
}