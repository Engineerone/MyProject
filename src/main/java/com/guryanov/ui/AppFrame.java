package com.guryanov.ui;

import com.guryanov.button.*;
import com.guryanov.config.DBType;
import com.guryanov.handler.*;
import com.guryanov.interf.ButtonHandler;
import com.guryanov.interf.FileAction;
import com.guryanov.interf.UserMessage;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import static com.guryanov.config.ConfigSetting.*;

public class AppFrame extends JFrame {

    public static final float version = (float) 1.2;
    public static boolean saveStop = false;
    public static boolean sendEmailStop = false;
    public static FileAction fileAction = new FileHandler();
    public static UserMessage userMessage = new MessageHandler();
    public static JTextArea areaFileContain = new JTextArea("", 35, 30);
    public static JTextArea statusString = new JTextArea(5, 41);
    public static String[] column_names = {"#", "name", "email", "send"};
    public static DefaultTableModel tableModel = new DefaultTableModel(column_names, 0);
//    {
//        @Override
//        public boolean isCellEditable(int row, int column) {
//            return false;
//        }
//    };
    public static JTable dbTable = new JTable(tableModel);
    public static JTextArea areaEmailMessage = new JTextArea("", 32, 30);
    public static JTextField fieldEmailSubject = new JTextField();
    JPanel rightSidePanel = new JPanel(new BorderLayout());
    JPanel rightSidePanelSouthButton = new JPanel(new FlowLayout());
    JPanel rightSidePanelNorth = new JPanel(new BorderLayout());
    JPanel rightSidePanelSouth = new JPanel(new BorderLayout());
    JButton buttonSendEmail = new JButton();
    JButton buttonSendEmailStop = new JButton();
    public static JButton buttonLoadIntoTable = new JButton();
    public static JButton buttonLoadFromDB = new JButton();
    public static JButton buttonEraseDB = new JButton();
    public static JButton buttonSaveToDB = new JButton();
    public static JButton buttonSaveStop = new JButton();
    JPanel centralPanel = new JPanel(new BorderLayout());
    JPanel centralPanelNorth = new JPanel(new FlowLayout());
    JPanel centralPanelCenter = new JPanel(new FlowLayout());
    JPanel centralPanelSouth = new JPanel(new FlowLayout());
    JPanel leftSidePanel = new JPanel(new FlowLayout());
    JMenu toolMenu = new JMenu();
    public static JMenuItem createDB = new JMenuItem();
    public static JMenuItem deleteDB = new JMenuItem();
    public static JMenuItem about = new JMenuItem();
    JMenuItem prepareFile = new JMenuItem();
    JMenuItem setting = new JMenuItem();
    JMenu fileMenu = new JMenu();
    JMenu aboutMenu = new JMenu();
    JMenuItem fileOpenMenu = new JMenuItem();
    JMenuItem fileExitMenu = new JMenuItem();
    public static JFileChooser fileChooser = new JFileChooser();

    public AppFrame() {
        super("My project - SPAMMER");
        JPanel appWindow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JMenuBar menu = new JMenuBar();
        setBounds(500, 300, 1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menu);
        menu.add(createFileMenu());
        menu.add(createToolMenu());
        menu.add(createAboutMenu());
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
        fileMenu.setText("File");
        fileOpenMenu.setText("Open");
        fileExitMenu.setText("Exit");
        fileMenu.add(fileOpenMenu);
        fileMenu.addSeparator();
        fileMenu.add(fileExitMenu);
        fileOpenMenu.addActionListener(e -> new FileMenu().openFile(AppFrame.this));
        fileExitMenu.addActionListener(e -> new ExitMenu().exit());
        return fileMenu;
    }

    private JMenu createToolMenu() {
        toolMenu.setText("Tools");
        createDB.setText("Create DB");
        deleteDB.setText("Delete DB");
        prepareFile.setText("Prepare file");
        setting.setText("Settings");
        toolMenu.add(createDB);
        toolMenu.add(deleteDB);
        toolMenu.addSeparator();
        toolMenu.add(prepareFile);
        toolMenu.addSeparator();
        toolMenu.add(setting);
        createDB.addActionListener(e -> CreateDB.create());
        deleteDB.addActionListener(e -> DeleteDB.delete());
        prepareFile.addActionListener(e -> PrepareFile.getFile(AppFrame.this));
        setting.addActionListener((ButtonHandler) e -> {
            JFrame frame = new JFrame();
            frame.setBounds(550, 300, 300, 520);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel = new JPanel(new BorderLayout());
            JPanel panelNorth = new JPanel(new GridLayout(17, 1));
            JPanel panelSouth = new JPanel();

            panelNorth.add(new JLabel("DB type"));
            Enum[] dbtype = {DBType.MySQL, DBType.PostgreSQL};
            JComboBox field_dbtype = new JComboBox(dbtype);
            field_dbtype.setSelectedItem(db_type);
            panelNorth.add(field_dbtype);

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

            panelNorth.add(new JLabel("DB table 'ColumnSend'"));
            JTextField field_db_table_columnSend = new JTextField(db_table_columnSend);
            panelNorth.add(field_db_table_columnSend);

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

            panelNorth.add(new JLabel("Use real email send"));
            JCheckBox checkBox_realSend = new JCheckBox();
            checkBox_realSend.setSelected(realSend);
            panelNorth.add(checkBox_realSend);

            panelNorth.add(new JLabel("Use program with DB"));
            JCheckBox checkBox_useWithDB = new JCheckBox();
            checkBox_useWithDB.setSelected(useWithDB);
            panelNorth.add(checkBox_useWithDB);

            JButton buttonSave = new JButton("Save");
            JButton buttonVersionCheck = new JButton("DB connection test");
            panelSouth.add(buttonSave);
            panelSouth.add(buttonVersionCheck);

            panel.add(panelNorth, BorderLayout.NORTH);
            panel.add(panelSouth, BorderLayout.SOUTH);
            frame.add(panel);
            frame.setVisible(true);

            buttonSave.addActionListener(e1 -> {
                db_type = (DBType) field_dbtype.getSelectedItem();
                db_schema = field_db_schema.getText().trim();
                db_table_name = field_db_table_name.getText().trim();
                db_table_columnName = field_db_table_columnName.getText().trim();
                db_table_columnEmail = field_db_table_columnEmail.getText().trim();
                db_host = field_db_host.getText().trim();
                db_port = field_db_port.getText().trim();
                db_user = field_db_user.getText().trim();
                db_secr = field_db_secr.getText().trim();
                email_smtp_server = field_email_smtp_server.getText().trim();
                email_smtp_port = field_email_smtp_port.getText().trim();
                email_smtp_user = field_email_smtp_user.getText().trim();
                email_smtp_secr = field_email_smtp_secr.getText().trim();
                email_fieldFrom = field_email_fieldFrom.getText().trim();
                realSend = checkBox_realSend.isSelected();
                useWithDB = checkBox_useWithDB.isSelected();
                ChangeButtonVisible.change();
                frame.dispose();
            });
            buttonVersionCheck.addActionListener(e1 -> VersionDB.Test(
                    field_db_host.getText(),
                    field_db_port.getText(),
                    field_db_user.getText(),
                    field_db_secr.getText(),
                    field_dbtype.getSelectedItem().toString()));
        });
        return toolMenu;
    }

    private JMenu createAboutMenu() {
        aboutMenu.setText("Help");
        about.setText("About");
        aboutMenu.add(about);
        about.addActionListener(e -> AboutMenu.about());
        return aboutMenu;
    }

    private JPanel createLeftSidePanel() {
        areaFileContain.setEditable(false);
        areaFileContain.setText("File format:\nname< tab >email\n\nExample:\nname\temail@domain.com");
        areaFileContain.append("\n\nIf your file only contains emails,\nuse the " + "Prepare file" + " item to convert\nit to name< tab >email format");
        leftSidePanel.add(new JScrollPane(areaFileContain));
        return leftSidePanel;
    }

    private JPanel createCentralPanel() {
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        dbTable.setRowSorter(sorter);
        buttonLoadIntoTable.setText("Load table");
        buttonSaveToDB.setText("Add to DB");
        buttonSaveStop.setText("Add stop");
        buttonLoadFromDB.setText("Load DB");
        buttonEraseDB.setText("Erase DB");
        ChangeButtonVisible.change();
        statusString.setEditable(false);
        centralPanelNorth.add(buttonLoadIntoTable);
        centralPanelNorth.add(buttonSaveToDB);
        centralPanelNorth.add(buttonSaveStop);
        centralPanelNorth.add(buttonLoadFromDB);
        centralPanelNorth.add(buttonEraseDB);
        setJTableColumnsWidth(dbTable, 450, 10, 40, 40, 10);
        centralPanelCenter.add(new JScrollPane(dbTable));
        statusString.setCaretPosition(statusString.getDocument().getLength());
        JScrollPane scrollStatus = new JScrollPane(statusString);
        scrollStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollStatus.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        centralPanelSouth.add(scrollStatus);
        centralPanel.add(centralPanelNorth, BorderLayout.NORTH);
        centralPanel.add(centralPanelCenter, BorderLayout.CENTER);
        centralPanel.add(centralPanelSouth, BorderLayout.SOUTH);
        buttonLoadIntoTable.addActionListener(e -> LoadIntoTable.load());
        buttonSaveToDB.addActionListener(e -> SaveToDBStart.start());
        buttonEraseDB.addActionListener(e -> EraseDB.erase());
        buttonLoadFromDB.addActionListener(e -> LoadFromDB.load());
        buttonSaveStop.addActionListener(e -> SaveToDBStop.stop());
        return centralPanel;
    }

    private JPanel createRightSidePanel() {

        buttonSendEmail.setText("Send e-mail");
        buttonSendEmailStop.setText("Send stop");
        fieldEmailSubject.setText("Introduce my PROJECT SPAMMER");
        areaEmailMessage.setText("Hello <name>");
        rightSidePanelSouthButton.add(buttonSendEmail);
        rightSidePanelSouthButton.add(buttonSendEmailStop);
        rightSidePanelNorth.add(fieldEmailSubject, BorderLayout.NORTH);
        rightSidePanelNorth.add(new JScrollPane(areaEmailMessage), BorderLayout.SOUTH);
        rightSidePanelSouth.add(rightSidePanelSouthButton);
        rightSidePanel.add(rightSidePanelNorth, BorderLayout.NORTH);
        rightSidePanel.add(rightSidePanelSouth, BorderLayout.SOUTH);
        buttonSendEmail.addActionListener(e -> SendEmailStart.start());
        buttonSendEmailStop.addActionListener(e -> SendEmailStop.stop());
        return rightSidePanel;
    }
}