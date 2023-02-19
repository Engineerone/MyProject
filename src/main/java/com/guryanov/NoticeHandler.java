package com.guryanov;

import javax.swing.*;
import java.sql.SQLException;

public class NoticeHandler {


    void dbCreated() {
        JOptionPane.showMessageDialog(
                null,
                "База данных создана",
                "Output", JOptionPane.INFORMATION_MESSAGE);
    }

    void dbExist() {
        JOptionPane.showMessageDialog(
                null,
                "База данных с таким именем уже создана",
                "Output", JOptionPane.ERROR_MESSAGE);
    }

    void dbNotFound() {
        JOptionPane.showMessageDialog(
                null,
                "База данных не найдена",
                "Output", JOptionPane.ERROR_MESSAGE);

    }

    void dbDrop() {
        JOptionPane.showMessageDialog(
                null,
                "База данных удалена",
                "Output", JOptionPane.INFORMATION_MESSAGE);
    }

    void unknowError(String error) {
        JOptionPane.showMessageDialog(
                null,
                error,
                "Output", JOptionPane.ERROR_MESSAGE);

    }

    void communicationsDBError() {
        JOptionPane.showMessageDialog(
                null,
                "Ошибка соединения с базой данных",
                "Ounput",
                JOptionPane.ERROR_MESSAGE);
    }

    void authorizationDBError() {
        JOptionPane.showMessageDialog(
                null,
                "Ошибка авторизации",
                "Ounput",
                JOptionPane.ERROR_MESSAGE);
    }

    void getSQLExceptionNotice(SQLException ex) {
        if (ex.getSQLState().startsWith("28")) {
            new NoticeHandler().authorizationDBError();
        } else if (ex.getSQLState().startsWith("08")) {
            new NoticeHandler().communicationsDBError();
        } else if (ex.getSQLState().startsWith("42")) {
            new NoticeHandler().dbNotFound();
        } else if (ex.getSQLState().startsWith("HY")) {
            new NoticeHandler().dbExist();
        } else {
            new NoticeHandler().unknowError(ex.getMessage());
        }
        AppFrame.status = "error";
    }

    void getClassNotFoundException(ClassNotFoundException ex) {
        new NoticeHandler().unknowError(ex.getMessage());
        AppFrame.status = "error";
    }


}