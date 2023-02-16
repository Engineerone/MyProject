package com.guryanov;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class AppFrame extends JFrame {

    private JTextArea areaFileContain = new JTextArea("", 30, 30);
    public JTextField statusString = new JTextField("", 41);
    String status = "";

    AppFrame() {
        super("My project");
        JPanel appWindow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JMenuBar menu = new JMenuBar();
        this.setBounds(100, 100, 1200, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menu);

        menu.add(createFileMenu());
        menu.add(createToolMenu());

        appWindow.add(createLeftSidePanel());
        appWindow.add(new CentralPanel());
        appWindow.add(createRightSidePanel());

        Container container = getContentPane();
        container.add(appWindow);
    }

//    private void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
//        double total = 0;
//        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
//            total += percentages[i];
//        }
//        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
//            TableColumn column = table.getColumnModel().getColumn(i);
//            column.setPreferredWidth((int)
//                    (tablePreferredWidth * (percentages[i] / total)));
//        }
//    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu();
        JMenuItem fileOpenMenu = new JMenuItem();
        JMenuItem fileExitMenu = new JMenuItem();
        JFileChooser fileChooser = new JFileChooser();
        fileMenu.setText("File");
        fileOpenMenu.setText("Open");
        fileExitMenu.setText("Exit");
        fileMenu.add(fileOpenMenu);
        fileMenu.addSeparator();
        fileMenu.add(fileExitMenu);
        fileOpenMenu.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuffer buffer;
                fileChooser.setDialogTitle("Directory selection");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(AppFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                        String fileString;
                        areaFileContain.setText("");
                        while (reader.ready()) {
                            fileString = reader.readLine();
                            areaFileContain.append(fileString);
                            areaFileContain.append("\n");
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        fileExitMenu.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return fileMenu;
    }

    private JMenu createToolMenu() {

        JMenu toolMenu = new JMenu();
        JMenuItem createDB = new JMenuItem();
        JMenuItem deleteDB = new JMenuItem();

        toolMenu.setText("Tools");
        createDB.setText("CREATE DB");
        deleteDB.setText("DELETE DB");
        toolMenu.add(createDB);
        toolMenu.addSeparator();
        toolMenu.add(deleteDB);
        createDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseHandler dbHandler = new DatabaseHandler();
                dbHandler.createDB();
                statusString.setText("DB CREATED");
            }
        });
        deleteDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseHandler dbHandler = new DatabaseHandler();
                dbHandler.dropDB();
                statusString.setText("DB DELETED");
            }
        });

        return toolMenu;
    }

    private JPanel createLeftSidePanel() {
        JPanel leftSidePanel = new JPanel(new FlowLayout());
        areaFileContain.setEditable(false);
        areaFileContain.setText("");
        leftSidePanel.add(new JScrollPane(areaFileContain));

        return leftSidePanel;
    }

//    private JPanel createCentralPanel() {
//        JButton buttonLoadFromDB = new JButton();
//        JButton buttonEraseDB = new JButton();
//        JButton buttonSaveToDB = new JButton();
//
//        JPanel centralPanel = new JPanel(new BorderLayout());
//        JPanel centralPanelNorth = new JPanel(new FlowLayout());
//        JPanel centralPanelCenter = new JPanel(new FlowLayout());
//        JPanel centralPanelSouth = new JPanel(new FlowLayout());
//
//
//        String[] column_names = {"#", "name", "email"};
//        DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);
//        JTable dbTable = new JTable(tableModel);
//        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
//        dbTable.setRowSorter(sorter);
//
//        buttonSaveToDB.setText("Save to DB");
//        buttonLoadFromDB.setText("Load from DB");
//        buttonEraseDB.setText("Erase DB");
//
//        statusString.setText("Status string");
//        statusString.setEditable(false);
//
//        centralPanelNorth.add(buttonSaveToDB);
//        centralPanelNorth.add(buttonLoadFromDB);
//        centralPanelNorth.add(buttonEraseDB);
//
//        setJTableColumnsWidth(dbTable, 450, 10, 60, 60);
//        centralPanelCenter.add(new JScrollPane(dbTable));
//        centralPanelSouth.add(statusString);
//        centralPanel.add(centralPanelNorth, BorderLayout.NORTH);
//        centralPanel.add(centralPanelCenter, BorderLayout.CENTER);
//        centralPanel.add(centralPanelSouth, BorderLayout.SOUTH);
//
//        buttonSaveToDB.addActionListener(new buttonHandler() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (areaFileContain.getText().length() == 0) {
//                    statusString.setText("File not load or text area is empty.");
//                    return;
//                }
//                try {
//                    DatabaseHandler dbHandler = new DatabaseHandler();
//                    String areaFileContainString = areaFileContain.getText();
//                    String name;
//                    String email;
//                    String tempString = "";
//                    List<String> insertResult = new ArrayList<>();
//
//                    for (int i = 0; i < areaFileContainString.length(); i++) {
//                        if (areaFileContainString.charAt(i) != '\n') {
//                            tempString += areaFileContainString.charAt(i);
//                        } else {
//                            int tabStatement = tempString.indexOf('\t');
//                            name = tempString.substring(0, tabStatement);
//                            email = tempString.substring(tabStatement + 1);
//                            tempString = "";
//                            insertResult.add(dbHandler.insertRow(name, email));
//                        }
//                    }
//                    if (insertResult.size() > 0) {
//                        String result = "";
//                        for (String s : insertResult) {
//                            result += s + '\n';
//                        }
//                        new Notice().unknowError(result);
//                    }
//
//                    status = "complite";
//                } catch (SQLException ex) {
//                    if (ex.getSQLState().startsWith("28")) {
//                        new Notice().authorizationDBError();
//                    } else if (ex.getSQLState().startsWith("08")) {
//                        new Notice().communicationsDBError();
//                    } else {
//                        new Notice().unknowError(ex.getMessage());
//                    }
//                    status = "error";
//                } catch (ClassNotFoundException ex) {
//                    //throw new RuntimeException(ex);
//                    new Notice().unknowError();
//                    status = "error";
//                } finally {
//                    statusString.setText("Save " + status);
//                }
//            }
//        });
//        buttonEraseDB.addActionListener(new buttonHandler() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    DatabaseHandler dbHandler = new DatabaseHandler();
//                    tableModel.setRowCount(0);
//                    dbHandler.eraseDB();
//                    statusString.setText("Erase complete");
//                } catch (SQLException ex) {
//                    if (ex.getSQLState().startsWith("28")) {
//                        new Notice().authorizationDBError();
//                    } else if (ex.getSQLState().startsWith("08")) {
//                        new Notice().communicationsDBError();
//                    } else {
//                        new Notice().unknowError();
//                    }
//                } catch (ClassNotFoundException ex) {
//                    //throw new RuntimeException(ex);
//                    new Notice().unknowError();
//                }
//            }
//        });
//        buttonLoadFromDB.addActionListener(new buttonHandler() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    DatabaseHandler dbHandler = new DatabaseHandler();
//                    List<String[]> result; //= new ArrayList<>();
//                    result = dbHandler.LoadFromDB();
//                    tableModel.setRowCount(0);
//                    for (int i = 0; i < result.size(); i++) {
//                        String[] value = result.get(i);
//                        tableModel.insertRow(i, new Object[]{value[0], value[1], value[2]});
//                    }
//                    statusString.setText("Load complete");
//                } catch (SQLException ex) {
//                    if (ex.getSQLState().startsWith("28")) {
//                        new Notice().authorizationDBError();
//                    } else if (ex.getSQLState().startsWith("08")) {
//                        new Notice().communicationsDBError();
//                    } else {
//                        new Notice().unknowError();
//                    }
//                } catch (ClassNotFoundException ex) {
//                    //throw new RuntimeException(ex);
//                    new Notice().unknowError();
//                }
//            }
//        });
//
//        return centralPanel;
//    }

    private JPanel createRightSidePanel() {
        JTextArea areadEmailMessage = new JTextArea("", 27, 30);
        JTextField fieldEmailSubject = new JTextField();
        JPanel rightSidePanel = new JPanel(new BorderLayout());
        JPanel rightSidePanelNorth = new JPanel(new BorderLayout());
        JPanel rightSidePanelSouth = new JPanel(new BorderLayout());
        JButton buttonSendEmail = new JButton();

        buttonSendEmail.setText("Send e-mail");
        fieldEmailSubject.setText("My project");
        areadEmailMessage.setText("Hello");
        rightSidePanelNorth.add(fieldEmailSubject, BorderLayout.NORTH);
        rightSidePanelNorth.add(new JScrollPane(areadEmailMessage), BorderLayout.SOUTH);
        rightSidePanelSouth.add(buttonSendEmail, BorderLayout.NORTH);
        rightSidePanel.add(rightSidePanelNorth, BorderLayout.NORTH);
        rightSidePanel.add(rightSidePanelSouth, BorderLayout.SOUTH);
        buttonSendEmail.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendEmail mymail = new SendEmail("avguryanow@yandex.ru", "My project test mail send");
                mymail.sendMessage("Hellow World !!!!!!!!!!");
            }
        });
        return rightSidePanel;
    }
}