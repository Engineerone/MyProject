package com.guryanov;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;

import javax.swing.*;
import javax.xml.transform.sax.SAXSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DatabaseHandler extends MySQLConfigs {
    private Connection dbConnection;
    private String sqlQuery = "";
    private PreparedStatement prSt;

    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbPort + "/" + dbname;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public String insertRow(String name, String email) throws SQLException, ClassNotFoundException {
        String result = "";
        sqlQuery = "INSERT INTO "
                + Constants.USER_TABLE + "("
                + Constants.USER_NAME + ","
                + Constants.USER_EMAIL + ")" +
                "VALUES(?,?)";
        try {
            prSt = getDbConnection().prepareStatement(sqlQuery);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            result = "Запись уже есть - "+name+" "+email;
        }
        return result;
    }

    public void eraseDB() throws SQLException, ClassNotFoundException {
        sqlQuery = "DELETE FROM " + Constants.USER_TABLE;
        //       try {
        prSt = getDbConnection().prepareStatement(sqlQuery);
        prSt.executeUpdate();
//        } catch (SQLException | ClassNotFoundException e) {
//            new Notice().unknowError();
//        }
    }

    public List LoadFromDB() throws SQLException, ClassNotFoundException {
        sqlQuery = "SELECT * FROM " + Constants.USER_TABLE + " ORDER BY id";
        List<String[]> result = new ArrayList<>();
        //      try {
        prSt = getDbConnection().prepareStatement(sqlQuery);
        prSt.execute();
        ResultSet resultSet = prSt.getResultSet();
        while (resultSet.next()) {
            String[] dbString = new String[3];
            dbString[0] = resultSet.getString(1);
            dbString[1] = resultSet.getString(2);
            dbString[2] = resultSet.getString(3);
            result.add(dbString);
        }
//        }
//        catch (CommunicationsException e) {
//            new Notice().communicationsDBError();
//        }
//        catch (SQLException | ClassNotFoundException e) {
//            new Notice().unknowError();
//        }

        return result;
    }

    public void createDB() {
        try {
            String connectionString = "jdbc:mysql://" + dbhost + ":" + dbPort + "/";

            sqlQuery = "CREATE DATABASE projectdb";
            prSt = DriverManager.getConnection(connectionString, dbUser, dbPass).prepareStatement(sqlQuery);
            prSt.execute();

            sqlQuery = "CREATE TABLE users (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "name varchar(45) NOT NULL ," +
                    "email varchar(45) NOT NULL UNIQUE ," +
                    "PRIMARY KEY (`id`)" +
                    ")";
            prSt = getDbConnection().prepareStatement(sqlQuery);
            prSt.execute();
            new Notice().dbCreated();
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getMessage().equals("Can't create database 'projectdb'; database exists")) {
                new Notice().dbExist();
            }
        }
    }

    public void dropDB() {
        try {
            sqlQuery = "DROP DATABASE projectdb";
            prSt = getDbConnection().prepareStatement(sqlQuery);
            prSt.execute();
            new Notice().dbDrop();
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getMessage().equals("Unknown database 'projectdb'")) {
                new Notice().dbNotFound();
            }
        }
    }
}