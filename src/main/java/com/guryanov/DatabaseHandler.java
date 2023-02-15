package com.guryanov;

import javax.xml.transform.sax.SAXSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends MySQLConfigs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbPort + "/" + dbname;
        //  Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public Connection getDbConnectionWODB()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbPort;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpuser(String name, String email) {
        String insert = "INSERT INTO "
                + Constants.USER_TABLE + "("
                + Constants.USER_NAME + ","
                + Constants.USER_EMAIL + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Запись "+name+" "+email+" существует");
        }
                catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRow(String name, String email) {
        String update =
//                "UPDATE " + Constants.USER_TABLE
//                        + "SET" + Constants.USER_NAME + "=" + name + ","
//                        + Constants.USER_EMAIL + "=" + email
//                        + "WHERE " + Constants.USER_NAME + " =" + name + ","
//                        + Constants.USER_EMAIL + " = " + email;
                "UPDATE USERS SET NAME = ?, EMAIL = ? WHERE NAME = ? AND EMAIL= ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.setString(3, name);
            prSt.setString(4, email);
            prSt.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Запись"+name+" "+email+"существует");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        }

    }

    public void eraseDB() {
        String eraseDB = "DELETE FROM " + Constants.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(eraseDB);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List LoadDB() {
        String LoadDB = "SELECT * FROM " + Constants.USER_TABLE + " ORDER BY id";
        List<String[]> result = new ArrayList<>();
        //Map<Integer, String[]> result = new HashMap<>();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(LoadDB);
            prSt.execute();
            ResultSet resultSet = prSt.getResultSet();
            while (resultSet.next()) {
                String[] dbString = new String[3];
                dbString[0] = resultSet.getString(1);
                dbString[1] = resultSet.getString(2);
                dbString[2] = resultSet.getString(3);
                result.add(dbString);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void createDB() {
        try {
            String sqlQuery;
            sqlQuery = "CREATE DATABASE projectdb";
            PreparedStatement prSt;
            prSt = getDbConnectionWODB().prepareStatement(sqlQuery);
            prSt.execute();

            sqlQuery = "CREATE TABLE users (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "name varchar(45) NOT NULL ," +
                    "email varchar(45) NOT NULL UNIQUE ," +
                    "PRIMARY KEY (`id`)" +
                    ")";
            prSt = getDbConnection().prepareStatement(sqlQuery);
            prSt.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropDB() {
        String sqlQuery;
        PreparedStatement prSt;

        try {
            sqlQuery = "DROP DATABASE projectdb";
            prSt = getDbConnection().prepareStatement(sqlQuery);
            prSt.execute();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
