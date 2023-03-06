package com.guryanov.handler;

import com.guryanov.button.Query;
import com.guryanov.interf.SQLQuery;
import com.guryanov.config.DBType;
import com.guryanov.config.ConfigSetting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.guryanov.ui.AppFrame.*;

public class DatabaseHandler extends ConfigSetting {
    private String connectionString;
    private String queryString;
    private SQLQuery sqlQuery = new Query();

    public Connection getDBConnection() throws SQLException {
        connectionString = sqlQuery.getConnection((DBType) db_type, db_schema);
        Connection dbConnection = DriverManager.getConnection(connectionString, db_user, db_secr);
        return dbConnection;
    }

    public int insertRow(List<List<String>> result) throws SQLException {
        int count = 0;
        try (Connection connection = getDBConnection()) {

            for (List<String> resultString : result) {
                if (!saveStop) {
                    try {
                        queryString = sqlQuery.getInsert((DBType) db_type);
                        PreparedStatement prSt = connection.prepareStatement(queryString);
                        prSt.setString(1, resultString.get(0));
                        prSt.setString(2, resultString.get(1));
                        prSt.setString(3, resultString.get(2));
                        prSt.executeUpdate();
                        statusString.append("\n" + count + " line written -> " + resultString.get(0) + " " + resultString.get(1));

                        count++;
                    } catch (SQLIntegrityConstraintViolationException e) {
                        statusString.append("\n" + "email exists in DB -> " + resultString.get(0) + " " + resultString.get(1));
                    }
                    statusString.setCaretPosition(statusString.getDocument().getLength() - 3);
                } else break;
            }
        }
        saveStop = false;
        return count;
    }

    public void updateRow(String name, String email) throws SQLException {
        try (Connection connection = getDBConnection()) {
            try {
                queryString = sqlQuery.getUpdate((DBType) db_type);
                PreparedStatement prSt = connection.prepareStatement(queryString);
                prSt.setString(1, "send");
                prSt.setString(2, name);
                prSt.setString(3, email);
                prSt.executeUpdate();
            } catch (
                    SQLIntegrityConstraintViolationException e) {
                statusString.append("\n" + e.getMessage());
            }
            statusString.setCaretPosition(statusString.getDocument().getLength() - 3);
        }
    }

    public void eraseDB() throws SQLException {
        try (Connection connection = getDBConnection()) {
            queryString = sqlQuery.getErase((DBType) db_type);
            PreparedStatement prSt = connection.prepareStatement(queryString);
            prSt.executeUpdate();
        }
    }

    public List loadDB() throws SQLException {
        try (Connection connection = getDBConnection()) {
            List<String[]> result = new ArrayList<>();
            queryString = sqlQuery.getLoadFromDB((DBType) db_type);
            PreparedStatement prSt = connection.prepareStatement(queryString);
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
        connectionString = sqlQuery.getConnection((DBType) db_type);
        queryString = sqlQuery.getCreateDB((DBType) db_type);
        try (PreparedStatement prSt = DriverManager.getConnection(connectionString, db_user, db_secr)
                .prepareStatement(queryString)) {
            prSt.execute();
        }
        queryString = sqlQuery.getTableDB((DBType) db_type);
        try (PreparedStatement prSt = getDBConnection()
                .prepareStatement(queryString)) {
            {
                prSt.execute();
            }
        }
    }

    public void dropDB() throws SQLException {
        queryString = sqlQuery.getDropDB((DBType) db_type);
        if (db_type == DBType.MySQL) {
            connectionString = sqlQuery.getConnection((DBType) db_type, db_schema);
        } else if (db_type == DBType.PostgreSQL) {
            connectionString = sqlQuery.getConnection((DBType) db_type, "root");
        }

        try (
                PreparedStatement prSt = DriverManager.getConnection(connectionString, db_user, db_secr)
                        .prepareStatement(queryString)) {
            prSt.execute();
        }

    }

    public List<String> versionDB(String[] connectionData) throws SQLException {
        connectionString = sqlQuery.getConnection(connectionData[0], connectionData[1], DBType.valueOf(connectionData[4]));
        List<String> result = new ArrayList<>();
        queryString = sqlQuery.getVersionDB(DBType.valueOf(connectionData[4]));
        try (PreparedStatement prSt = DriverManager.getConnection(connectionString, connectionData[2], connectionData[3])
                .prepareStatement(queryString)) {
            prSt.execute();
            ResultSet resultSet = prSt.getResultSet();
            while (resultSet.next()) {
                result.add("Connection successful !");
                result.add(resultSet.getString(1));
            }
        }
        return result;
    }
}

