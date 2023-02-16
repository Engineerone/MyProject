package com.guryanov;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CentralPanel extends JPanel {

    private JTextArea areaFileContain = new JTextArea("", 30, 30);
    public JTextField statusString = new JTextField("", 41);
    String status = "";

    public JPanel CentralPanel() {

        JPanel centralPanel = new JPanel(new BorderLayout());
        JPanel centralPanelNorth = new JPanel(new FlowLayout());
        JPanel centralPanelCenter = new JPanel(new FlowLayout());
        JPanel centralPanelSouth = new JPanel(new FlowLayout());

        JButton buttonLoadFromDB = new JButton();
        JButton buttonEraseDB = new JButton();
        JButton buttonSaveToDB = new JButton();

        String[] column_names = {"#", "name", "email"};
        DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);
        JTable dbTable = new JTable(tableModel);
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        dbTable.setRowSorter(sorter);

        buttonSaveToDB.setText("Save to DB");
        buttonLoadFromDB.setText("Load from DB");
        buttonEraseDB.setText("Erase DB");

        statusString.setText("Status string");
        statusString.setEditable(false);

        centralPanelNorth.add(buttonSaveToDB);
        centralPanelNorth.add(buttonLoadFromDB);
        centralPanelNorth.add(buttonEraseDB);

        setJTableColumnsWidth(dbTable, 450, 10, 60, 60);
        centralPanelCenter.add(new JScrollPane(dbTable));
        centralPanelSouth.add(statusString);
        centralPanel.add(centralPanelNorth, BorderLayout.NORTH);
        centralPanel.add(centralPanelCenter, BorderLayout.CENTER);
        centralPanel.add(centralPanelSouth, BorderLayout.SOUTH);

        buttonSaveToDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areaFileContain.getText().length() == 0) {
                    statusString.setText("File not load or text area is empty.");
                    return;
                }
                try {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    String areaFileContainString = areaFileContain.getText();
                    String name;
                    String email;
                    String tempString = "";
                    java.util.List<String> insertResult = new ArrayList<>();

                    for (int i = 0; i < areaFileContainString.length(); i++) {
                        if (areaFileContainString.charAt(i) != '\n') {
                            tempString += areaFileContainString.charAt(i);
                        } else {
                            int tabStatement = tempString.indexOf('\t');
                            name = tempString.substring(0, tabStatement);
                            email = tempString.substring(tabStatement + 1);
                            tempString = "";
                            insertResult.add(dbHandler.insertRow(name, email));
                        }
                    }
                    if (insertResult.size() > 0) {
                        String result = "";
                        for (String s : insertResult) {
                            result += s + '\n';
                        }
                        new Notice().unknowError(result);
                    }

                    status = "complite";
                } catch (SQLException ex) {
                    if (ex.getSQLState().startsWith("28")) {
                        new Notice().authorizationDBError();
                    } else if (ex.getSQLState().startsWith("08")) {
                        new Notice().communicationsDBError();
                    } else {
                        new Notice().unknowError(ex.getMessage());
                    }
                    status = "error";
                } catch (ClassNotFoundException ex) {
                    //throw new RuntimeException(ex);
                    new Notice().unknowError();
                    status = "error";
                } finally {
                    statusString.setText("Save " + status);
                }
            }
        });
        buttonEraseDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    tableModel.setRowCount(0);
                    dbHandler.eraseDB();
                    statusString.setText("Erase complete");
                } catch (SQLException ex) {
                    if (ex.getSQLState().startsWith("28")) {
                        new Notice().authorizationDBError();
                    } else if (ex.getSQLState().startsWith("08")) {
                        new Notice().communicationsDBError();
                    } else {
                        new Notice().unknowError();
                    }
                } catch (ClassNotFoundException ex) {
                    //throw new RuntimeException(ex);
                    new Notice().unknowError();
                }
            }
        });
        buttonLoadFromDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    List<String[]> result; //= new ArrayList<>();
                    result = dbHandler.LoadFromDB();
                    tableModel.setRowCount(0);
                    for (int i = 0; i < result.size(); i++) {
                        String[] value = result.get(i);
                        tableModel.insertRow(i, new Object[]{value[0], value[1], value[2]});
                    }
                    statusString.setText("Load complete");
                } catch (SQLException ex) {
                    if (ex.getSQLState().startsWith("28")) {
                        new Notice().authorizationDBError();
                    } else if (ex.getSQLState().startsWith("08")) {
                        new Notice().communicationsDBError();
                    } else {
                        new Notice().unknowError();
                    }
                } catch (ClassNotFoundException ex) {
                    //throw new RuntimeException(ex);
                    new Notice().unknowError();
                }
            }
        });

        return centralPanel;
    }

    private void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }
}
