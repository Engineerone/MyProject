package com.guryanov;


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
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static com.guryanov.ClearString.getClearString;
import static com.guryanov.ConfigSetting.*;

public class AppFrame extends JFrame {

    private JTextArea areaFileContain = new JTextArea("", 30, 30);
    private static JTextField statusString = new JTextField("", 41);
    static String status = "";
    private NoticeHandler notice = new NoticeHandler();


    private String[] column_names = {"#", "name", "email", "send"};
    private DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);
    private JTable dbTable = new JTable(tableModel);


    AppFrame() {
        super("My project - SPAMMER");
        JPanel appWindow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JMenuBar menu = new JMenuBar();
        this.setBounds(500, 300, 1200, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menu);

        menu.add(createFileMenu());
        menu.add(createToolMenu());
        appWindow.add(createLeftSidePanel());
        appWindow.add(createCentralPanel());
        appWindow.add(createRightSidePanel());
        Container container = getContentPane();
        container.add(appWindow);
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
        fileOpenMenu.addActionListener((ButtonHandler) e -> {
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
        });
        fileExitMenu.addActionListener((ButtonHandler) e -> System.exit(0));
        return fileMenu;
    }

    private JMenu createToolMenu() {

        JMenu toolMenu = new JMenu();
        JMenuItem createDB = new JMenuItem();
        JMenuItem deleteDB = new JMenuItem();
        JMenuItem setting = new JMenuItem();

        toolMenu.setText("Tools");
        createDB.setText("Create DB");
        deleteDB.setText("Delete DB");
        setting.setText("Settings");

        toolMenu.add(createDB);
        toolMenu.add(deleteDB);
        toolMenu.addSeparator();
        toolMenu.add(setting);
        createDB.addActionListener((ButtonHandler) e -> {
            try {
                DatabaseHandler dbHandler = new DatabaseHandler();
                dbHandler.createDB();
                notice.dbCreated();
            } catch (SQLException ex) {
                notice.getSQLExceptionNotice(ex);
            } catch (ClassNotFoundException ex) {
                notice.getClassNotFoundException(ex);
            } finally {
                statusString.setText("DB create " + status);
            }
        });
        deleteDB.addActionListener((ButtonHandler) e -> {
            try {
                DatabaseHandler dbHandler = new DatabaseHandler();
                dbHandler.dropDB();
                notice.dbDrop();
            } catch (SQLException ex) {
                notice.getSQLExceptionNotice(ex);
            } catch (ClassNotFoundException ex) {
                notice.getClassNotFoundException(ex);
            } finally {
                statusString.setText("DB delete " + status);
            }
        });
        setting.addActionListener((ButtonHandler) e -> {
            JFrame frame = new JFrame();
            frame.setBounds(550, 300, 300, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel = new JPanel(new BorderLayout());
            JPanel panelNorth = new JPanel(new GridLayout(15, 1));
            JPanel panelSouth = new JPanel();


            panelNorth.add(new JLabel("DB name"));
            JTextField field_db_schema = new JTextField(db_schema);
            panelNorth.add(field_db_schema);

            panelNorth.add(new JLabel("DB table name"));
            JTextField field_db_table_name = new JTextField(db_table_name);
            panelNorth.add(field_db_table_name);

            panelNorth.add(new JLabel("DB table 'ColumnName'"));
            JTextField field_db_table_columnName = new JTextField(db_table_columnName);
            panelNorth.add(field_db_table_columnName);

            panelNorth.add(new JLabel("DB table 'ColumnEmail'"));
            JTextField field_db_table_columnEmail = new JTextField(db_table_columnEmail);
            panelNorth.add(field_db_table_columnEmail);

            panelNorth.add(new JLabel("DB host"));
            JTextField field_db_host = new JTextField(db_host);
            panelNorth.add(field_db_host);

            panelNorth.add(new JLabel("DB port"));
            JTextField field_db_port = new JTextField(db_port);
            panelNorth.add(field_db_port);

            panelNorth.add(new JLabel("DB user"));
            JTextField field_db_user = new JTextField(db_user);
            panelNorth.add(field_db_user);

            panelNorth.add(new JLabel("DB password"));
            JTextField field_db_secr = new JTextField(db_secr);
            panelNorth.add(field_db_secr);

            panelNorth.add(new JLabel("Email SMTP server"));
            JTextField field_email_smtp_server = new JTextField(email_smtp_server);
            panelNorth.add(field_email_smtp_server);

            panelNorth.add(new JLabel("Email SMTP port"));
            JTextField field_email_smtp_port = new JTextField(email_smtp_port);
            panelNorth.add(field_email_smtp_port);

            panelNorth.add(new JLabel("Email SMTP user"));
            JTextField field_email_smtp_user = new JTextField(email_smtp_user);
            panelNorth.add(field_email_smtp_user);

            panelNorth.add(new JLabel("Email SMTP password"));
            JTextField field_email_smtp_secr = new JTextField(email_smtp_secr);
            panelNorth.add(field_email_smtp_secr);

            panelNorth.add(new JLabel("Email field From"));
            JTextField field_email_fieldFrom = new JTextField(email_fieldFrom);
            panelNorth.add(field_email_fieldFrom);

            panelNorth.add(new JLabel("Real send"));
            JCheckBox checkBox_realSend = new JCheckBox();
            checkBox_realSend.setSelected(realSend);
            panelNorth.add(checkBox_realSend);

            JButton button = new JButton("Save");
            panelSouth.add(button);

            panel.add(panelNorth, BorderLayout.NORTH);
            panel.add(panelSouth, BorderLayout.SOUTH);
            frame.add(panel);
            frame.setVisible(true);

            button.addActionListener((ButtonHandler) e1 -> {

                db_schema = getClearString(field_db_schema.getText());
                db_table_name = getClearString(field_db_table_name.getText());
                db_table_columnName = getClearString(field_db_table_columnName.getText());
                db_table_columnEmail = getClearString(field_db_table_columnEmail.getText());
                db_host = getClearString(field_db_host.getText());
                db_port = getClearString(field_db_port.getText());
                db_user = getClearString(field_db_user.getText());
                db_secr = getClearString(field_db_secr.getText());
                email_smtp_server = getClearString(field_email_smtp_server.getText());
                email_smtp_port = getClearString(field_email_smtp_port.getText());
                email_smtp_user = getClearString(field_email_smtp_user.getText());
                email_smtp_secr = getClearString(field_email_smtp_secr.getText());
                email_fieldFrom = getClearString(field_email_fieldFrom.getText());
                realSend = checkBox_realSend.isSelected();
                frame.dispose();
            });
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

    private JPanel createCentralPanel() {
        JButton buttonLoadFromDB = new JButton();
        JButton buttonEraseDB = new JButton();
        JButton buttonSaveToDB = new JButton();

        JPanel centralPanel = new JPanel(new BorderLayout());
        JPanel centralPanelNorth = new JPanel(new FlowLayout());
        JPanel centralPanelCenter = new JPanel(new FlowLayout());
        JPanel centralPanelSouth = new JPanel(new FlowLayout());


//        String[] column_names = {"#", "name", "email"};
//        DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);

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

        setJTableColumnsWidth(dbTable, 450, 10, 40, 40, 10);
        centralPanelCenter.add(new JScrollPane(dbTable));
        centralPanelSouth.add(statusString);
        centralPanel.add(centralPanelNorth, BorderLayout.NORTH);
        centralPanel.add(centralPanelCenter, BorderLayout.CENTER);
        centralPanel.add(centralPanelSouth, BorderLayout.SOUTH);

        buttonSaveToDB.addActionListener((ButtonHandler) e -> {

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
                List<String> insertResult = new ArrayList<>();

                for (int i = 0; i < areaFileContainString.length(); i++) {
                    if (areaFileContainString.charAt(i) != '\n') {
                        tempString += areaFileContainString.charAt(i);
                    } else {
                        int tabStatement = tempString.indexOf('\t');
                        name = tempString.substring(0, tabStatement);
                        email = tempString.substring(tabStatement + 1);
                        tempString = "";
                        dbHandler.insertRow(name, email);
                    }
                }
            } catch (SQLException ex) {
                new NoticeHandler().getSQLExceptionNotice(ex);
            } catch (ClassNotFoundException ex) {
                new NoticeHandler().getClassNotFoundException(ex);
            } finally {
                statusString.setText("Save " + status);
            }
        });
        buttonEraseDB.addActionListener((ButtonHandler) e -> {
            try {
                DatabaseHandler dbHandler = new DatabaseHandler();
                tableModel.setRowCount(0);
                dbHandler.eraseDB();
            } catch (SQLException ex) {
                notice.getSQLExceptionNotice(ex);
            } catch (ClassNotFoundException ex) {
                notice.getClassNotFoundException(ex);
            } finally {
                statusString.setText("Erase " + status);
            }
        });
        buttonLoadFromDB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        return centralPanel;
    }

    private JPanel createRightSidePanel() {
        JTextArea areaEmailMessage = new JTextArea("", 27, 30);
        JTextField fieldEmailSubject = new JTextField();
        JPanel rightSidePanel = new JPanel(new BorderLayout());
        JPanel rightSidePanelNorth = new JPanel(new BorderLayout());
        JPanel rightSidePanelSouth = new JPanel(new BorderLayout());
        JButton buttonSendEmail = new JButton();

        buttonSendEmail.setText("Send e-mail");
        fieldEmailSubject.setText("Introduce my first JAVA project - SPAMMER");
        areaEmailMessage.setText("Hello <name>");
        rightSidePanelNorth.add(fieldEmailSubject, BorderLayout.NORTH);
        rightSidePanelNorth.add(new JScrollPane(areaEmailMessage), BorderLayout.SOUTH);
        rightSidePanelSouth.add(buttonSendEmail, BorderLayout.NORTH);
        rightSidePanel.add(rightSidePanelNorth, BorderLayout.NORTH);
        rightSidePanel.add(rightSidePanelSouth, BorderLayout.SOUTH);
        buttonSendEmail.addActionListener((ButtonHandler) e -> {
            String name = "";
            String email = "";
            DatabaseHandler databaseHandler = new DatabaseHandler();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                name = tableModel.getValueAt(i, 1).toString();
                email = tableModel.getValueAt(i, 2).toString();
                EmailHandler mymail = new EmailHandler(email, fieldEmailSubject.getText());
                if (mymail.sendMessage(areaEmailMessage.getText().replaceAll("<name>",name))) {
                    try {
                        databaseHandler.updatetRow(name, email);
                        updateTable();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(name+" "+email+" message sended");
                } else System.out.println(name+" "+email+" message NOT sended");

            }
        });
        return rightSidePanel;
    }

    void updateTable() {
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            List<String[]> result;
            result = dbHandler.LoadFromDB();
            tableModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                String[] value = result.get(i);
                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2], value[3]});
            }
        } catch (SQLException ex) {
            notice.getSQLExceptionNotice(ex);
        } catch (ClassNotFoundException ex) {
            notice.getClassNotFoundException(ex);
        } finally {
            statusString.setText("Load " + status);
        }
    }
}