package com.guryanov;

import javax.swing.*;
import java.lang.reflect.Executable;

public class Notice {

    void dbCreated() {
        JOptionPane.showMessageDialog(
                null,
                "База данных создана",
                "Output", JOptionPane.INFORMATION_MESSAGE);
    }

    void dbExist() {
        JOptionPane.showMessageDialog(
                null,
                "База данных уже создана",
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

    void unknowError() {
        JOptionPane.showMessageDialog(
                null,
                "Неизвестная ошибка",
                "Output", JOptionPane.ERROR_MESSAGE);

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
}