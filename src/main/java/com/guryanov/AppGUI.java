package com.guryanov;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class AppGUI extends JFrame {

    private JMenuBar menu = new JMenuBar();
    private JButton buttonLoadDB = new JButton("Load from DB");
    private JButton buttonEraseDB = new JButton("Erase DB");
    private JButton buttonSaveDB = new JButton("Save to DB");
    private JButton buttondSendEmail = new JButton("Send e-mail");
    private JTextField fieldEmailSubject = new JTextField("Email subject");
    private JTextArea areaFileContain = new JTextArea("File contain", 30, 30);
    private JTextArea areadEmailMessage = new JTextArea("Email text", 27, 30);
    private String[] column_names = {"#", "name", "email"};
    private JTable dbTable = new JTable(new DefaultTableModel(column_names, 25));
    private JFileChooser fileChooser = new JFileChooser();

    public AppGUI() {
        super("My project");
        this.setBounds(100, 100, 1200, 580);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menu);
        menu.add(createFileMenu());

        JPanel grid1 = new JPanel(new FlowLayout());
        grid1.add(new JScrollPane(areaFileContain));


        JPanel grid2 = new JPanel(new BorderLayout());


        JPanel grid21 = new JPanel(new FlowLayout());
        grid21.add(buttonSaveDB);
        grid21.add(buttonLoadDB);
        grid21.add(buttonEraseDB);
        //    grid2.add(dbTable);

        JPanel grid22 = new JPanel(new FlowLayout());


        TableColumnModel columnModel = dbTable.getColumnModel();

        setJTableColumnsWidth(dbTable, 450, 10, 60, 60);


        //        columnModel.getColumn(0).setPreferredWidth(50);
//        columnModel.getColumn(1).setPreferredWidth(100);
//        columnModel.getColumn(2).setPreferredWidth(100);
//        dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //       dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        grid2.add(new JScrollPane(dbTable));


        grid2.add(grid21, BorderLayout.NORTH);
        grid2.add(grid22, BorderLayout.SOUTH);


        JPanel grid3 = new JPanel(new BorderLayout());
        JPanel grid31 = new JPanel(new BorderLayout());
        JPanel grid32 = new JPanel(new BorderLayout());


        grid31.add(fieldEmailSubject, BorderLayout.NORTH);
        grid31.add(new JScrollPane(areadEmailMessage), BorderLayout.SOUTH);

        grid32.add(buttondSendEmail, BorderLayout.NORTH);


        grid3.add(grid31, BorderLayout.NORTH);
        grid3.add(grid32, BorderLayout.SOUTH);


        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        flow.add(grid1);
        flow.add(grid2);
        flow.add(grid3);

        Container container = getContentPane();
        container.add(flow);


//        Container container = this.getContentPane();
//        container.setLayout(new FlowLayout(FlowLayout.CENTER));
////        container.setLayout(new GridLayout(1, 3, 2, 2));
//
//        container.add(fieldFileContain);
//        container.add(dbTable);
//        container.add(fieldEmailMessage);

//        Container buttonContainer = this.getContentPane();
//        buttonContainer.setLayout(new FlowLayout());
//        buttonContainer.add(buttonSaveDB);
        //  container.add(buttonContainer);

//        ButtonGroup group = new ButtonGroup();
//        group.add(buttonEraseDB);
//        group.add(buttonSaveDB);
//        group.add(buttondSendEmail);
//        group.add(buttonLoadDB);


//        container.add(buttonLoadDB);
//        container.add(buttonEraseDB);
//        container.add(buttonSaveDB);
//        container.add(buttondSendEmail);

        //  container.add(radio1);
        // radio1.setSelected(true);
        //  container.add(radio2);
        // container.add(check);
        buttonSaveDB.addActionListener(new buttonSaveDBListener());
        buttonEraseDB.addActionListener(new buttonEraseDBListener());
        buttonLoadDB.addActionListener(new buttonLoadDBListener());
        buttondSendEmail.addActionListener(new buttonSendMailListener());
        //   container.add(fileChooser);
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
                                             double... percentages) {
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
        JMenuItem open = new JMenuItem("Open", new ImageIcon("images/open.png"));
        JMenuItem exit = new JMenuItem(new ExitAction());
        exit.setIcon(new ImageIcon("images/exit.png"));
        file.add(open);
        file.addSeparator();
        file.add(exit);
        open.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                StringBuffer buffer;
                buffer = new StringBuffer();
                fileChooser.setDialogTitle("Выбор директории");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(AppGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try (FileReader reader = new FileReader(fileChooser.getSelectedFile())) {
                        int i = 1;
                        while (i != -1) {
                            i = reader.read();
                            char ch = (char) i;
                            buffer.append(ch);
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                areaFileContain.setText(buffer.toString());
            }
        });
        return file;
    }


    class buttonSaveDBListener implements ActionListener {
        DatabaseHandler dbHandler = new DatabaseHandler();

        public void actionPerformed(ActionEvent e) {
            dbHandler.signUpuser(areaFileContain.getText(), areaFileContain.getText());
        }
    }

    class buttonEraseDBListener implements ActionListener {
        DatabaseHandler dbHandler = new DatabaseHandler();

        public void actionPerformed(ActionEvent e) {

            dbHandler.eraseDB();
        }
    }


    class buttonLoadDBListener implements ActionListener {
        DatabaseHandler dbHandler = new DatabaseHandler();

        public void actionPerformed(ActionEvent e) {

            dbHandler.LoadDB();
        }
    }

    class buttonSendMailListener implements ActionListener {
   //     DatabaseHandler dbHandler = new DatabaseHandler();

        public void actionPerformed(ActionEvent e) {
            SendEmail mymail = new SendEmail("avguryanow@yandex.ru","My project test mail send");
            mymail.sendMessage("Hellow World !!!!!!!!!!");

        }
    }


    class ExitAction extends AbstractAction {
//        private static final long serialVersionUID = 1L;

        ExitAction() {
            putValue(NAME, "Exit");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
