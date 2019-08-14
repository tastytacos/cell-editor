package com.company.editor;

import com.company.editor.exceptions.FilenameContainingDotException;
import com.company.editor.exceptions.TextTransferException;
import com.company.editor.utils.*;
import com.company.factory.CellEditorMapPanel;
import com.company.factory.DoubleCellUnit;
import com.company.language_tools.EditorConfigs;
import com.company.language_tools.LanguageChoiceWindow;
import com.company.language_tools.LanguageManager;
import com.company.language_tools.languageble.LanguageButton;
import com.company.language_tools.languageble.LanguageChartPanel;
import com.company.language_tools.languageble.LanguageMenu;
import com.company.language_tools.languageble.LanguageMenuItem;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;


//todo provide mouse cursor control on table
public class CellEditor implements ActionListener {
    private String CURRENT_DIRECTORY_PATH = CellEditorTableConstants.CURRENT_DIRECTORY_PATH;
    private String filename; // Used for Save button
    private JFrame frame = new JFrame();
    private JTable table = new JTable();
    private JSplitPane splitPane;
    private DefaultTableModel defaultTableModel;
    private Map<String, FileManager> fileManagerHashMap = new HashMap<>();
    private LanguageChartPanel rightChartPanel;
    private Font font = new Font("sans-serif", Font.PLAIN, CellEditorTableConstants.DEFAULT_FONT_SIZE);
    private Locale defaultLocale = Locale.getDefault();
    // initializing locales
    private Locale englishLocale = new Locale("en", "EN");
    private Locale russianLocale = new Locale("ru", "RU");
    private Locale ukrainianLocale = new Locale("ua", "UA");
    private ResourceBundle resourceBundle;
    private String[] languages;


    private LanguageManager languageManager = new LanguageManager();
    private Map<String, Locale> localeMap = new HashMap<>();

    {
        frame.setBounds(CellEditorTableConstants.x_coordinate, CellEditorTableConstants.y_coordinate, CellEditorTableConstants.width, CellEditorTableConstants.height);
        filename = CellEditorTableConstants.DEFAULT_NAME;
        frame.setTitle(filename + CellEditorTableConstants.CELL_EDITOR);
        // initializing file managers
        fileManagerHashMap.put(CellEditorTableConstants.XLS_FORMAT, new XlsFile());
        fileManagerHashMap.put(CellEditorTableConstants.XLSX_FORMAT, new XlsxFile());
        fileManagerHashMap.put(CellEditorTableConstants.TXT_FORMAT, new TXTFile());
        // setting locales and their keys in localeMap
        localeMap.put(englishLocale.getLanguage(), englishLocale);
        localeMap.put(ukrainianLocale.getLanguage(), ukrainianLocale);
        localeMap.put(russianLocale.getLanguage(), russianLocale);
        //Next line provides displaying mistakes and messages on the appropriate screen
        TableUtils.setComponent(frame);
        initKeys();
    }


    public CellEditor() {
        defaultTableModel = TableUtils.fillTableModel(CellEditorTableConstants.DEFAULT_ROWS_AMOUNT,
                CellEditorTableConstants.DEFAULT_COLS_AMOUNT, CellEditorTableConstants.NAME_COLUMNS);
        defaultTableModel.addTableModelListener(tableModelListener);
        initComponents();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
    }

    public CellEditor(Locale givenLocale, EditorConfigs editorConfigs) {
        CellEditorTableConstants.setNameColumns(editorConfigs.getRowsName());
        defaultLocale = givenLocale;
        defaultTableModel = TableUtils.fillTableModel(CellEditorTableConstants.DEFAULT_ROWS_AMOUNT,
                CellEditorTableConstants.DEFAULT_COLS_AMOUNT, CellEditorTableConstants.NAME_COLUMNS);
        defaultTableModel.addTableModelListener(tableModelListener);
        initComponents();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
    }

    public CellEditor(final CellEditorMapPanel panel, final Object key) {
        final DoubleCellUnit unit = (DoubleCellUnit) panel.getMap().get(key);
        defaultTableModel = handleTableModel(unit.getUnitDefaultTableModel());
        defaultTableModel.addTableModelListener(tableModelListener);
        initComponents();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!(TableUtils.validData(defaultTableModel))) {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                } else {
                    List<Double> firstValue = TableUtils.getListFromModelColumn(defaultTableModel, 0);
                    List<Double> secondValue = TableUtils.getListFromModelColumn(defaultTableModel, 1);
                    unit.setUnitFirstValue(firstValue);
                    unit.setUnitSecondValue(secondValue);
                    unit.setUnitTableModel(defaultTableModel);
                    panel.setMapElement(key, unit);
                    e.getWindow().dispose();
                }
            }
        });
    }

    private TableModelListener tableModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            try {
                rightChartPanel.setChart(GraphBuilder.getXYChart(defaultTableModel, resourceBundle.getString("chart_panel_title"),
                        resourceBundle.getString("chart_panel_x_axis_name"),
                        resourceBundle.getString("chart_panel_y_axis_name")));
            } catch (NumberFormatException e1) {
                TableUtils.displayMessageOnScreen(WarningMessageFactory.getWarningFromRowColsMessage(
                        e.getFirstRow() + 1,
                        e.getColumn() + 1,
                        resourceBundle),
                        resourceBundle.getString("language_error_message_label"), JOptionPane.ERROR_MESSAGE);
            }
        }
    };


    private void initKeys() {
        KeyStroke pasteHotKey = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
        KeyController.getActionMap().put(pasteHotKey, new AbstractAction("paste") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    defaultTableModel = TableUtils.paste(table);
                    defaultTableModel.addTableModelListener(tableModelListener);
                    rightChartPanel.setChart(GraphBuilder.getXYChart(defaultTableModel, resourceBundle.getString("chart_panel_title"),
                            resourceBundle.getString("chart_panel_x_axis_name"),
                            resourceBundle.getString("chart_panel_y_axis_name")));
                    table.setModel(defaultTableModel);
                } catch (TextTransferException exception) {
                    exception.printStackTrace();
                }
            }
        });
        //
        KeyStroke addRowHotKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.ALT_DOWN_MASK);
        KeyController.getActionMap().put(addRowHotKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_row();
            }
        });
        //
        KeyStroke deleteRowHotKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK);
        KeyController.getActionMap().put(deleteRowHotKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete_row();
                } catch (IndexOutOfBoundsException e1) {
                    TableUtils.displayMessageOnScreen(WarningMessageFactory.getDeletingImpossibleMessage(resourceBundle));
                    e1.printStackTrace();
                }
            }
        });
        // Add previously created keys to ActionMap and "connect" them with the listener
        KeyController.setup();
    }

    public void actionPerformed(ActionEvent action) {
        switch (action.getActionCommand()) {
            case CellEditorTableConstants.SAVE_AS_BUTTON_COMMAND:
                save_table_as();
                break;
            case CellEditorTableConstants.SAVE_BUTTON_COMMAND:
                save_table(filename);
                break;
            case CellEditorTableConstants.LOAD_BUTTON_COMMAND:
                defaultTableModel = load_table();
                defaultTableModel.setColumnIdentifiers(CellEditorTableConstants.NAME_COLUMNS);
                defaultTableModel.addTableModelListener(tableModelListener);
                rightChartPanel.setChart(GraphBuilder.getXYChart(defaultTableModel,
                        resourceBundle.getString("chart_panel_title"),
                        resourceBundle.getString("chart_panel_x_axis_name"),
                        resourceBundle.getString("chart_panel_y_axis_name")));
                table.setModel(defaultTableModel);
                break;
            case CellEditorTableConstants.ADD_ROW_BUTTON_COMMAND:
                add_row();
                break;
            case CellEditorTableConstants.DELETE_ROW_BUTTON_COMMAND:
                try {
                    delete_row();
                    table.setModel(defaultTableModel);
                } catch (ArrayIndexOutOfBoundsException e) {
                    TableUtils.displayMessageOnScreen(WarningMessageFactory.getDeletingImpossibleMessage(resourceBundle));

                    e.printStackTrace();
                }
                break;
            case CellEditorTableConstants.BUILD_XY_GRAPH_COMMAND:
                if (TableUtils.validData(defaultTableModel)) {
                    defaultTableModel = TableUtils.checkForBlankCells(table);
                    Rectangle windowRect = new Rectangle(
                            CellEditorTableConstants.x_coordinate,
                            CellEditorTableConstants.y_coordinate,
                            CellEditorTableConstants.width,
                            CellEditorTableConstants.height);
                    GraphBuilder.displayXYLineGraph(defaultTableModel,
                            windowRect,
                            resourceBundle.getString("chart_panel_title"),
                            resourceBundle.getString("chart_panel_x_axis_name"),
                            resourceBundle.getString("chart_panel_y_axis_name"));
                    table.setModel(defaultTableModel);
                }
                break;
            case CellEditorTableConstants.LANGUAGE_SETTINGS_COMMAND:
                String[] langArray = new String[]{
                        englishLocale.getLanguage(),
                        russianLocale.getLanguage(),
                        ukrainianLocale.getLanguage()};
                String chosenLanguage = LanguageChoiceWindow.displayWindow(frame, languages, resourceBundle,
                        "language_window_title", "language_window_message");
                System.out.println(chosenLanguage);
                if (chosenLanguage != null) {
                    defaultLocale = localeMap.get(StringInterpretation.getStringFromStrings(languages, langArray, chosenLanguage));
                    languageManager.changeLanguage(CellEditorTableConstants.RESOURCE_BUNDLE_BASE_NAME, defaultLocale);
                    resourceBundle = ResourceBundle.getBundle(CellEditorTableConstants.RESOURCE_BUNDLE_BASE_NAME, defaultLocale);
                }
                break;
        }
    }

    private void add_row() {
        defaultTableModel.addRow(new Double[]{0.0, 0.0});
    }


    private void delete_row() throws ArrayIndexOutOfBoundsException {
        if (table.getSelectedRowCount() == 0)
            defaultTableModel.removeRow(table.getRowCount() - 1);
        else {
            int rows_selected = table.getSelectedRowCount();
            int selectedRow = table.getSelectedRow();
            for (int i = 0; i < rows_selected; i++) {
                defaultTableModel.removeRow(selectedRow);
            }
        }
    }


    private DefaultTableModel handleTableModel(DefaultTableModel model) {
        DefaultTableModel defaultTableModel;
        if (model.getColumnCount() == 0) {
            defaultTableModel = TableUtils.fillTableModel(CellEditorTableConstants.DEFAULT_ROWS_AMOUNT,
                    CellEditorTableConstants.DEFAULT_COLS_AMOUNT, CellEditorTableConstants.NAME_COLUMNS);
            // Otherwise just set already created model
        } else {
            defaultTableModel = model;
        }
        return defaultTableModel;
    }


    private void initComponents() {
        resourceBundle = ResourceBundle.getBundle(CellEditorTableConstants.RESOURCE_BUNDLE_BASE_NAME, defaultLocale);
        languages = new String[]{
                resourceBundle.getString("language_str_english"),
                resourceBundle.getString("language_str_russian"),
                resourceBundle.getString("language_str_ukrainian")};
        LanguageButton plusButton;
        LanguageButton minusButton;
        // set buttons
        JMenuBar topMenuBar = initMenuBar();
        // add the menu bar to a frame
        frame.setJMenuBar(topMenuBar);
        //
        plusButton = TableUtils.createButton("add_row_label", resourceBundle.getString("add_row_label"), CellEditorTableConstants.ADD_ROW_BUTTON_COMMAND, font);
        plusButton.addActionListener(this);
        plusButton.setMargin(new Insets(2, 17, 2, 25));
        //
        minusButton = TableUtils.createButton("delete_row_label", resourceBundle.getString("delete_row_label"), CellEditorTableConstants.DELETE_ROW_BUTTON_COMMAND, font);
        minusButton.addActionListener(this);
        // setting the panel with adding and deleting the rows in table
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        topPanel.add(plusButton);
        topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        topPanel.add(minusButton);
        topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        frame.add(topPanel, BorderLayout.NORTH);
        //
        table.setModel(defaultTableModel);
        table.getTableHeader().setFont(font);
        table.setFont(font);
        JScrollPane scroll_pane = new JScrollPane(table);
        //
        rightChartPanel = new LanguageChartPanel(GraphBuilder.getXYChart(defaultTableModel, resourceBundle.getString("chart_panel_title"),
                resourceBundle.getString("chart_panel_x_axis_name"),
                resourceBundle.getString("chart_panel_y_axis_name")), "chart_panel_title");
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll_pane, rightChartPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.9);
        frame.add(splitPane);
        //
        languageManager.subscribeElement(plusButton, minusButton, rightChartPanel);
        //
        frame.setVisible(true);
    }

    private JMenuBar initMenuBar() {
        JMenuBar topMenuBar = new JMenuBar();
        //
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        //
        LanguageMenu fileMenu = new LanguageMenu("file_menu_label");
        fileMenu.setText(resourceBundle.getString("file_menu_label"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        //
        LanguageMenuItem loadFileMenuItem = new LanguageMenuItem("load_menu_item_label");
        loadFileMenuItem.setText(resourceBundle.getString("load_menu_item_label"));
        loadFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        loadFileMenuItem.setActionCommand(CellEditorTableConstants.LOAD_BUTTON_COMMAND);
        loadFileMenuItem.addActionListener(this);
        //
        fileMenu.add(loadFileMenuItem);
        //
        LanguageMenuItem saveFileMenuItem = new LanguageMenuItem("save_menu_item_label");
        saveFileMenuItem.setText(resourceBundle.getString("save_menu_item_label"));
        saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveFileMenuItem.setActionCommand(CellEditorTableConstants.SAVE_BUTTON_COMMAND);
        saveFileMenuItem.addActionListener(this);
        //
        fileMenu.add(saveFileMenuItem);
        //
        LanguageMenuItem saveAsFileMenuItem = new LanguageMenuItem("save_as_menu_item_label");
        saveAsFileMenuItem.setText(resourceBundle.getString("save_as_menu_item_label"));
        saveAsFileMenuItem.setAccelerator(KeyStroke.getKeyStroke("control alt S"));
        saveAsFileMenuItem.setActionCommand(CellEditorTableConstants.SAVE_AS_BUTTON_COMMAND);
        saveAsFileMenuItem.addActionListener(this);
        //
        fileMenu.add(saveAsFileMenuItem);
        languageManager.subscribeElement(fileMenu, loadFileMenuItem, saveFileMenuItem, saveAsFileMenuItem);
        //
        LanguageMenu graphMenu = new LanguageMenu("graph_menu_label");
        graphMenu.setText(resourceBundle.getString("graph_menu_label"));
        graphMenu.setMnemonic(KeyEvent.VK_G);
        //
        LanguageMenuItem buildXYGraphMenuItem = new LanguageMenuItem("build_xy_grap_menu_item_label");
        buildXYGraphMenuItem.setText(resourceBundle.getString("build_xy_grap_menu_item_label"));
        buildXYGraphMenuItem.setActionCommand(CellEditorTableConstants.BUILD_XY_GRAPH_COMMAND);
        buildXYGraphMenuItem.addActionListener(this);
        //
        graphMenu.add(buildXYGraphMenuItem);
        languageManager.subscribeElement(graphMenu, buildXYGraphMenuItem);
        //
        LanguageMenu settingsMenu = new LanguageMenu("settings_menu_label");
        settingsMenu.setText(resourceBundle.getString("settings_menu_label"));
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        //
        LanguageMenuItem languageMenuItem = new LanguageMenuItem("language_menu_item_label");
        languageMenuItem.setText(resourceBundle.getString("language_menu_item_label"));
        languageMenuItem.setActionCommand(CellEditorTableConstants.LANGUAGE_SETTINGS_COMMAND);
        languageMenuItem.addActionListener(this);
        //
        settingsMenu.add(languageMenuItem);
        languageManager.subscribeElement(settingsMenu, languageMenuItem);
        //
        topMenuBar.add(fileMenu);
        topMenuBar.add(graphMenu);
        topMenuBar.add(settingsMenu);
        return topMenuBar;
    }


    private void save_table(String pathname) {
        if (pathname.equals(CellEditorTableConstants.DEFAULT_NAME)) {
            save_table_as();
        } else {
            if (TableUtils.validData(defaultTableModel)) {
                defaultTableModel = TableUtils.checkForBlankCells(table);

                File file = new File(pathname);
                defaultTableModel = TableUtils.sortTable(table);
                table.setModel(defaultTableModel);
                try {
                    save(file, defaultTableModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save_table_as() {
        if (TableUtils.validData(defaultTableModel)) {
            defaultTableModel = TableUtils.checkForBlankCells(table);
            CustomJFileChooser fc = new CustomJFileChooser(CURRENT_DIRECTORY_PATH);

            int returnVal = fc.showDialog(frame, "Save file as");
            if (returnVal == CustomJFileChooser.APPROVE_OPTION) {
                CURRENT_DIRECTORY_PATH = fc.getCurrentDirectory().getAbsolutePath();
                File file = fc.getSelectedFile();
                String fileExtension;
                try {
                    TableUtils.getFileExtension(file);
                } catch (IndexOutOfBoundsException | FilenameContainingDotException e) {
                    // Catching this exceptions means that the extension wasn't typed or the name of the file contains
                    // one or more dots without extension .txt, .xls or any other allowed extension (i.e. filename is 08.09.2010)
                    // In both of these cases file should be determined according to it's FileFilter
                    CustomFileFilter customFileFilter = (CustomFileFilter) fc.getFileFilter();
                    System.out.println(customFileFilter.getMyExtension());
                    fileExtension = customFileFilter.getMyExtension();
                    String newFile = file.toString() + fileExtension;
                    file = new File(newFile);
                } finally {
                    defaultTableModel = TableUtils.sortTable(table);
                    defaultTableModel.addTableModelListener(tableModelListener);
                    table.setModel(defaultTableModel);
                    try {
                        save(file, defaultTableModel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    filename = file.getAbsolutePath();
                }
            }
        }
    }

    /**
     * This method calls the specified in {@link FileManager} extended classes save method.
     * To do this it initializes {@link FileManager} variable according of the format of the file to save.
     * After, {@link FileManager} save method is called, which is overridden in each extended class.
     * The key of this map is {@link String} extension of files (etc. .xls, .xlsx, .txt)
     */
    private void save(File saveFile, DefaultTableModel defaultTableModel) throws IOException {
        String fileName = saveFile.toString();
        String fileExtension = null;
        try {
            fileExtension = TableUtils.getFileExtension(saveFile);
        } catch (FilenameContainingDotException e) {
            e.printStackTrace();
        }
        File file = new File(fileName);
        System.out.println(fileName);
        FileManager fileManager = fileManagerHashMap.get(fileExtension);
        fileManager.save(file, defaultTableModel);
    }


    private DefaultTableModel load_table() {
        DefaultTableModel fromLoadFileModel;
        CustomJFileChooser forLoadFileChooser;
        forLoadFileChooser = new CustomJFileChooser(CURRENT_DIRECTORY_PATH);
        List<Double> load_data;
        int cols_amount;
        int rows_amount;
        int returnVal = forLoadFileChooser.showDialog(frame, "Load File");
        if (returnVal == CustomJFileChooser.APPROVE_OPTION) {     // File is chosen and opened
            CURRENT_DIRECTORY_PATH = forLoadFileChooser.getCurrentDirectory().getAbsolutePath();
            File file = forLoadFileChooser.getSelectedFile();
            String fileExtension = null;
            try {
                fileExtension = TableUtils.getFileExtension(file);
            } catch (FilenameContainingDotException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            FileManager fileManager = fileManagerHashMap.get(fileExtension);
            try {
                load_data = fileManager.load(file);
            } catch (FileNotFoundException e) {
                load_data = null;
                TableUtils.displayMessageOnScreen(WarningMessageFactory.getFileNotFoundMessage(resourceBundle));
                e.printStackTrace();
            } catch (Exception e) {
                load_data = null;
                TableUtils.displayMessageOnScreen(WarningMessageFactory.getDamagedFileMessage(resourceBundle));
                e.printStackTrace();
            }

            if (load_data != null) {
                filename = file.getAbsolutePath();
                frame.setTitle(filename + CellEditorTableConstants.CELL_EDITOR);
            }
            rows_amount = load_data.size() / 2;
            cols_amount = CellEditorTableConstants.DEFAULT_COLS_AMOUNT;
            fromLoadFileModel = TableUtils.fillTableModel(rows_amount, cols_amount, CellEditorTableConstants.NAME_COLUMNS, load_data);
        } else
            fromLoadFileModel = defaultTableModel;
        return fromLoadFileModel;
    }
}
