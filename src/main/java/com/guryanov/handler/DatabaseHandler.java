package com.guryanov.handler;

import com.guryanov.ui.AppFrame;
import com.guryanov.config.ConfigSetting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.guryanov.ui.AppFrame.statusString;

public class DatabaseHandler extends ConfigSetting {
    private Connection dbConnection;
    private String sqlQuery = "";
    private PreparedStatement prSt;

    public Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_schema;
        dbConnection = DriverManager.getConnection(connectionString, db_user, db_secr);
        return dbConnection;
    }

    public void insertRow(String name, String email) throws SQLException {
        sqlQuery =
                "INSERT INTO "
                        + db_table_name + "("
                        + db_table_columnName + ","
                        + db_table_columnEmail + ","
                        + db_table_columnSend + ")" +
                        "VALUES(?,?,?)";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.setString(3, "");
            prSt.executeUpdate();
            AppFrame.status = "completed";
        } catch (SQLIntegrityConstraintViolationException e) {
            statusString.append("\n"+"Запись уже есть - " + name + " " + email);
        }
    }

    public void updatetRow(String name, String email) throws SQLException  {
        sqlQuery =
                "UPDATE "
                        + db_table_name
                        + " SET "
                        + db_table_columnSend + "=?"
                        + "WHERE "
                        + db_table_columnName + "=?"
                        + "AND "
                        + db_table_columnEmail + "=?";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            prSt.setString(1, "send");
            prSt.setString(2, name);
            prSt.setString(3, email);
            prSt.executeUpdate();
            AppFrame.status = "completed";
        } catch (SQLIntegrityConstraintViolationException e) {
            statusString.append("\n"+e.getMessage());
        }
    }

    public void eraseDB() throws SQLException, ClassNotFoundException {
        sqlQuery =
                "DELETE FROM "
                        + db_table_name;
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            prSt.executeUpdate();
            AppFrame.status = "completed";
        }
    }

    public List LoadFromDB() throws SQLException, ClassNotFoundException {
        List<String[]> result = new ArrayList<>();
        sqlQuery =
                "SELECT * FROM "
                        + db_table_name
                        + " ORDER BY id";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            prSt.execute();
            ResultSet resultSet = prSt.getResultSet();
            while (resultSet.next()) {
                String[] dbString = new String[4];
                dbString[0] = resultSet.getString(1);
                dbString[1] = resultSet.getString(2);
                dbString[2] = resultSet.getString(3);
                dbString[3] = resultSet.getString(4);
                result.add(dbString);
                AppFrame.status = "completed";
            }
            return result;
        }
    }

    public void createDB() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + db_host + ":" + db_port + "/";
        sqlQuery = "CREATE DATABASE " + db_schema;
        try (PreparedStatement prSt = DriverManager.getConnection(connectionString, db_user, db_secr).prepareStatement(sqlQuery)) {
            prSt.execute();
        }
        sqlQuery =
                "CREATE TABLE "
                        + db_table_name + "(" +
                        "id int NOT NULL AUTO_INCREMENT," +
                        "name varchar(45) NOT NULL ," +
                        "email varchar(45) NOT NULL UNIQUE ," +
                        "send varchar(45) NOT NULL ," +
                        "PRIMARY KEY (`id`)" +
                        ")";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            {
                prSt.execute();
                AppFrame.status = "completed";
            }
        }
    }

    public void dropDB() throws SQLException, ClassNotFoundException {
        sqlQuery =
                "DROP DATABASE "
                        + db_schema;
        try (PreparedStatement prSt = getDbConnection().prepareStatement(sqlQuery)) {
            prSt.execute();
            AppFrame.status = "completed";
        }
    }
}