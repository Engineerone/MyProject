package com.guryanov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbPort + "/" + dbname;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpuser(String name, String email) {
        String insert = "INSERT INTO "
                + Const.USER_TABLE + "("
                + Const.USER_NAME + ","
                + Const.USER_EMAIL + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void eraseDB() {
        String eraseDB = "DELETE FROM "+ Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(eraseDB);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadDB() {
//        String loadDB = "SELECT * FROM "+ Const.USER_TABLE;
//        try {
//            PreparedStatement prSt = getDbConnection().prepareStatement(loadDB);
//            prSt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }



}
