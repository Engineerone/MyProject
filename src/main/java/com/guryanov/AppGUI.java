package com.guryanov;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AppGUI extends JFrame {

    private JMenuBar menu = new JMenuBar();
    private JButton buttonLoadDB = new JButton("Load from DB");
    private JButton buttonEraseDB = new JButton("Erase DB");
    private JButton buttonSaveDB = new JButton("Save to DB");

    private JCheckBox checkBox = new JCheckBox("save including duplicates");
    private JButton buttonSendEmail = new JButton("Send e-mail");
    private JTextField fieldEmailSubject = new JTextField("My Project");
    JTextField statusString = new JTextField("Status field", 41);
    private JTextArea areaFileContain = new JTextArea("", 30, 30);
    private JTextArea areadEmailMessage = new JTextArea("Hello! That my first project !", 27, 30);
    private String[] column_names = {"#", "name", "email"};

    DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);
    private JTable dbTable = new JTable(tableModel);
    private JFileChooser fileChooser = new JFileChooser();

    public AppGUI() {
        super("My project");
        this.setBounds(100, 100, 1200, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menu);
        menu.add(createFileMenu());
        menu.add(createToolMenu());
        statusString.setEditable(false);
        areaFileContain.setEditable(false);

        JPanel grid1 = new JPanel(new FlowLayout());
        grid1.add(new JScrollPane(areaFileContain));

        JPanel grid2 = new JPanel(new BorderLayout());
        JPanel grid21 = new JPanel(new FlowLayout());
        grid21.add(buttonSaveDB);
        grid21.add(checkBox);
        grid21.add(buttonLoadDB);
        grid21.add(buttonEraseDB);

        JPanel grid22 = new JPanel(new FlowLayout());
        TableColumnModel columnModel = dbTable.getColumnModel();
        setJTableColumnsWidth(dbTable, 450, 10, 60, 60);
        grid22.add(new JScrollPane(dbTable));
        JPanel grid23 = new JPanel(new FlowLayout());
        grid23.add(statusString);
        grid2.add(grid21, BorderLayout.NORTH);
        grid2.add(grid22, BorderLayout.CENTER);
        grid2.add(grid23, BorderLayout.SOUTH);


        JPanel grid3 = new JPanel(new BorderLayout());
        JPanel grid31 = new JPanel(new BorderLayout());
        JPanel grid32 = new JPanel(new BorderLayout());
        grid31.add(fieldEmailSubject, BorderLayout.NORTH);
        grid31.add(new JScrollPane(areadEmailMessage), BorderLayout.SOUTH);
        grid32.add(buttonSendEmail, BorderLayout.NORTH);
        grid3.add(grid31, BorderLayout.NORTH);
        grid3.add(grid32, BorderLayout.SOUTH);

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        flow.add(grid1);
        flow.add(grid2);
        flow.add(grid3);

        Container container = getContentPane();
        container.add(flow);

        buttonSaveDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseHandler dbHandler = new DatabaseHandler();
                String areaFileContainString = areaFileContain.getText();
                String name;
                String email;
                String tempString = "";

                for (int i = 0; i < areaFileContainString.length(); i++) {
                    if (areaFileContainString.charAt(i) != '\n') {
                        tempString += areaFileContainString.charAt(i);
                    } else {
                        int tabStatement = tempString.indexOf('\t');
                        email = tempString.substring(0, tabStatement);
                        name = tempString.substring(tabStatement + 1);
                        tempString = "";
                        if (checkBox.isSelected()) {
                            dbHandler.updateRow(name, email);
                        } else {
                            dbHandler.signUpuser(name, email);
                        }
                    }
                }
                statusString.setText("Save complete");
            }
        });
        buttonEraseDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseHandler dbHandler = new DatabaseHandler();
                tableModel.setRowCount(0);
                dbHandler.eraseDB();
                statusString.setText("Erase complete");
            }
        });
        buttonLoadDB.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseHandler dbHandler = new DatabaseHandler();
                List<String[]> result = new ArrayList<>();
                result = dbHandler.LoadDB();
                tableModel.setRowCount(0);
                for (int i = 0; i < result.size(); i++) {
                    String[] value = result.get(i);
                    tableModel.insertRow(i, new Object[]{value[0], value[1], value[2]});
                }
                statusString.setText("Load complete");
            }

        });
        buttonSendEmail.addActionListener(new buttonHandler() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendEmail mymail = new SendEmail("avguryanow@yandex.ru", "My project test mail send");
                mymail.sendMessage("Hellow World !!!!!!!!!!");
            }
        });
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
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
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem exit = new JMenuItem("Exit");

        file.add(open);
        file.addSeparator();
        file.add(exit);

        open.addActionListener(new menuOpenListener());
        exit.addActionListener(new menuExitListener());

        return file;
    }

    private JMenu createToolMenu() {
        JMenu tool = new JMenu("Tools");
        JMenuItem createDB = new JMenuItem("CREATE DB");
        JMenuItem deleteDB = new JMenuItem("DELETE DB");
        tool.add(createDB);
        tool.addSeparator();
        tool.add(deleteDB);

        createDB.addActionListener(new menuCreateDBListener());
        deleteDB.addActionListener(new menuDeleteDBListener());

        return tool;
    }

    class menuDeleteDBListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.dropDB();
            statusString.setText("DB DELETED");
        }
    }

    class menuOpenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            StringBuffer buffer;
            buffer = new StringBuffer();
            fileChooser.setDialogTitle("Выбор директории");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(AppGUI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                    String fileString;
                    while (reader.ready()) {
                        fileString = reader.readLine() + "\n";
                        buffer.append(fileString);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                areaFileContain.setText(buffer.toString());
            }

        }
    }

//    class buttonSaveDBListener implements ActionListener {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//
//        public void actionPerformed(ActionEvent e) {
//            String areaFileContainString = areaFileContain.getText();
//            String name;
//            String email;
//            String tempString = "";
//
//            for (int i = 0; i < areaFileContainString.length(); i++) {
//                if (areaFileContainString.charAt(i) != '\n') {
//                    tempString += areaFileContainString.charAt(i);
//                } else {
//                    int tabStatement = tempString.indexOf('\t');
//                    email = tempString.substring(0, tabStatement);
//                    name = tempString.substring(tabStatement + 1, tempString.length());
//                    tempString = "";
//                    dbHandler.signUpuser(name, email);
//                }
//            }
//            statusString.setText("Save complete");
//        }
//    }

//    class buttonEraseDBListener implements ActionListener {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0);
//            dbHandler.eraseDB();
//            statusString.setText("Erase complete");
//        }
//    }

//    class buttonLoadDBListener implements ActionListener {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//        List<String[]> result = new ArrayList<>();
//        //Map<Integer, String[]> result = new HashMap<>();
//
//        public void actionPerformed(ActionEvent e) {
//            result = dbHandler.LoadDB();
//            tableModel.setRowCount(0);
//            for (int i = 0; i < result.size(); i++) {
//                String[] value = result.get(i);
//                tableModel.insertRow(i, new Object[]{value[0], value[1], value[2]});
//            }
//            statusString.setText("Load complete");
//        }
//
//    }

//    class buttonSendMailListener implements ActionListener {
//        //     DatabaseHandler dbHandler = new DatabaseHandler();
//        public void actionPerformed(ActionEvent e) {
//            SendEmail mymail = new SendEmail("avguryanow@yandex.ru", "My project test mail send");
//            mymail.sendMessage("Hellow World !!!!!!!!!!");
//        }
//    }

    class menuExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class menuCreateDBListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.createDB();
            statusString.setText("DB CREATED");
        }
    }
}
