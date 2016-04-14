package lab2_csv_reader;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static javax.swing.JFrame.*;

public class MainWindow implements ActionListener {
    private JPanel panel1;
    public JTable table1;
    private JTextField textField1;

    private JMenuBar menuBar1;
    private JFileChooser fileChooser1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private MainWindow() {
        initMenu();
        initFileChooser();
        initTable();
    }

    private void initMenu() {
        //create a menu bar
        menuBar1 = new JMenuBar();
        //create menus
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");
        // actionListener
        openMenuItem.addActionListener(this);

        //add menu items to menus
        fileMenu.add(openMenuItem);
        //add menu to menubar
        menuBar1.add(fileMenu);
        // add menu to panel
        panel1.add(menuBar1, BorderLayout.NORTH);
    }

    private void initFileChooser() {
        String userhome = System.getProperty("user.home");
        fileChooser1 = new JFileChooser(userhome + "\\Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Pliki csv", "csv"
        );

        fileChooser1.setFileFilter(filter);
        fileChooser1.setVisible(true);
    }

    private void initTable() {
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                int col = table1.getSelectedColumn();

                UniversalModel model = (UniversalModel) table1.getModel();
                MagazynRow r = (MagazynRow) model.getRowAt(row);

                System.out.println(r);
            }
        });

        // table filter
        textField1.setEnabled(false);
//        textField1.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                String text = textField1.getText();
//                TableRowSorter sorter = (TableRowSorter) table1.getRowSorter();
//                if (text.length() == 0) {
//                    sorter.setRowFilter(null);
//                } else {
//                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//                }
//                table1.repaint();
//            }
//        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Open":
                openMenuAction();
                break;
        }
    }


    private void openMenuAction() {
        int returnValue = fileChooser1.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser1.getSelectedFile();
            System.out.println(selectedFile.getPath());

            //! try to open file and read data
            CSVReader reader = null;
            try {
                //
                List<MagazynRow> rows = new ArrayList<>();
                //Build reader instance
                reader = new CSVReader(new FileReader(selectedFile.getPath()), ';');
                List<String[]> records = reader.readAll();
                Iterator<String[]> iterator = records.iterator();

                //skip header row
                String[] header = iterator.next();

                while (iterator.hasNext()) {
                    String[] record = iterator.next();
                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = format.parse(record[7]);
                    MagazynRow item = new MagazynRow(
                            NumberUtils.toInt(record[0].replaceAll("\\D+", "")),
                            record[1],
                            NumberUtils.toInt(record[2].replaceAll("\\D+", "")),
                            NumberUtils.toInt(record[3].replaceAll("\\D+", "")),
                            NumberUtils.toInt(record[4].replaceAll("\\D+", "")),
                            record[5],
                            NumberUtils.toDouble(record[6]),
                            new java.sql.Date(date.getTime())
                    );
                    rows.add(item);
                }

                UniversalModel tblModel = new UniversalModel(rows, header);
                table1.setModel(tblModel);

                TableRowSorter<UniversalModel> sorter = new TableRowSorter<>(tblModel);
                sorter.setComparator(1, new CardNumberComparator());
                table1.setRowSorter(sorter);
                textField1.setEnabled(true);

            } catch (Exception ex) {
                //logger.error("Error! file", ex);
                ex.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException | NullPointerException ex) {
                    //logger.error("Reader close error!", ex);
                    ex.printStackTrace();
                }
            }
        }
    }
}
