package com.guryanov.handler;

import com.guryanov.config.DBType;
import com.guryanov.config.ConfigSetting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.guryanov.ui.AppFrame.*;

public class DatabaseHandler extends ConfigSetting {
    private String sqlQuery = "";

    public Connection getDbConnection() throws SQLException {
        String connectionString = null;
        if (db_type == DBType.MySQL)
            connectionString = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_schema;
        else if (db_type == DBType.PostgreSQL)
            connectionString = "jdbc:postgresql://" + db_host + ":" + db_port + "/" + db_schema;
        Connection dbConnection = DriverManager.getConnection(connectionString, db_user, db_secr);
        return dbConnection;
    }

    public void insertRow(List<List<String>> result) throws SQLException {
        try (Connection connection = getDbConnection()) {
            for (List<String> resultString : result) {
                try {
                    sqlQuery =
                            "INSERT INTO "
                                    + db_table_name + "("
                                    + db_table_columnName + ","
                                    + db_table_columnEmail + ","
                                    + db_table_columnSend + ")" +
                                    "VALUES(?,?,?)";
                    PreparedStatement prSt = connection.prepareStatement(sqlQuery);
                    prSt.setString(1, resultString.get(0));
                    prSt.setString(2, resultString.get(1));
                    prSt.setString(3, "");
                    prSt.executeUpdate();
                    statusString.append("\n" + "Line written " + resultString.get(0) + " " + resultString.get(1));
                } catch (SQLIntegrityConstraintViolationException e) {
                    statusString.append("\n" + "Email exists SKIP: " + resultString.get(0) + " " + resultString.get(1));
                }

            }
        }
    }

    public void updatetRow(List<List<String>> result) throws SQLException {
        try (Connection connection = getDbConnection()) {
            for (List<String> resultString : result) {
                try {
                    sqlQuery =
                            "UPDATE "
                                    + db_table_name
                                    + " SET "
                                    + db_table_columnSend + "=?"
                                    + "WHERE "
                                    + db_table_columnName + "=?"
                                    + "AND "
                                    + db_table_columnEmail + "=?";

                    PreparedStatement prSt = connection.prepareStatement(sqlQuery);
                    prSt.setString(1, "send");
                    prSt.setString(2, resultString.get(1));
                    prSt.setString(3, resultString.get(2));
                    prSt.executeUpdate();
//                    ResultSet resultSet = prSt.getResultSet();
//                    if (resultSet == null)
//                        statusString.append("\nRow not found in database, SEND not save in DB: " + resultString.get(1) + " " + resultString.get(2));
                } catch (
                        SQLIntegrityConstraintViolationException e) {
                    statusString.append("\n" + e.getMessage());
                }

            }
        }
    }

    public void eraseDB() throws SQLException {
        sqlQuery =
                "DELETE FROM "
                        + db_table_name;
        try (PreparedStatement prSt = getDbConnection()
                .prepareStatement(sqlQuery)) {
            prSt.executeUpdate();
        }
    }

    public List LoadFromDB() throws SQLException {
        List<String[]> result = new ArrayList<>();
        sqlQuery =
                "SELECT * FROM "
                        + db_table_name
                        + " ORDER BY id";
        try (PreparedStatement prSt = getDbConnection()
                .prepareStatement(sqlQuery)) {
            prSt.execute();
            ResultSet resultSet = prSt.getResultSet();
            while (resultSet.next()) {
                String[] dbString = new String[4];
                dbString[0] = resultSet.getString(1);
                dbString[1] = resultSet.getString(2);
                dbString[2] = resultSet.getString(3);
                dbString[3] = resultSet.getString(4);
                result.add(dbString);
            }
            return result;
        }
    }

    public void createDB() throws SQLException {
        String connectionString = "jdbc:mysql://" + db_host + ":" + db_port + "/";
        sqlQuery = "CREATE DATABASE " + db_schema;
        try (PreparedStatement prSt = DriverManager.getConnection(connectionString, db_user, db_secr)
                .prepareStatement(sqlQuery)) {
            prSt.execute();
        }
        sqlQuery =
                "CREATE TABLE "
                        + db_table_name + "(" +
                        "id int NOT NULL AUTO_INCREMENT," +
                        "name varchar(100) NOT NULL ," +
                        "email varchar(100) NOT NULL UNIQUE ," +
                        "send varchar(45) NOT NULL ," +
                        "PRIMARY KEY (`id`)" +
                        ")";
        try (PreparedStatement prSt = getDbConnection()
                .prepareStatement(sqlQuery)) {
            {
                prSt.execute();
            }
        }
    }

    public void dropDB() throws SQLException {
        sqlQuery =
                "DROP DATABASE "
                        + db_schema;
        try (PreparedStatement prSt = getDbConnection()
                .prepareStatement(sqlQuery)) {
            prSt.execute();
        }
    }
}