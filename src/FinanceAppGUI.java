import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class FinanceAppGUI extends JFrame {

    private FinanceManager manager;

    private JTextField descField;
    private JTextField amountField;
    private JTextField dateField;
    private JTextField categoryField;

    private DefaultTableModel tableModel;
    private JTable table;

    private JLabel balanceLabel;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel diffLabel;

    public FinanceAppGUI() {

        manager = new FinanceManager();

        setTitle("Aplikasi Keuangan Mahasiswa");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= INPUT PANEL =================
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 8, 8));

        descField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField();
        categoryField = new JTextField();

        inputPanel.add(new JLabel("Keterangan"));
        inputPanel.add(descField);

        inputPanel.add(new JLabel("Nominal"));
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Tanggal"));
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Kategori"));
        inputPanel.add(categoryField);

        add(inputPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{"Type", "Desc", "Amount", "Date", "Category"}, 0
        );

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= STAT PANEL =================
        JPanel statPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        balanceLabel = new JLabel();
        incomeLabel = new JLabel();
        expenseLabel = new JLabel();
        diffLabel = new JLabel();

        statPanel.add(balanceLabel);
        statPanel.add(incomeLabel);
        statPanel.add(expenseLabel);
        statPanel.add(diffLabel);

        add(statPanel, BorderLayout.EAST);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton incomeBtn = new JButton("Pemasukan");
        JButton expenseBtn = new JButton("Pengeluaran");
        JButton deleteBtn = new JButton("Hapus");
        JButton barChartBtn = new JButton("Bar Chart");
        JButton pieChartBtn = new JButton("Pie Chart");
        JButton pdfBtn = new JButton("Export PDF");

        buttonPanel.add(incomeBtn);
        buttonPanel.add(expenseBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(barChartBtn);
        buttonPanel.add(pieChartBtn);
        buttonPanel.add(pdfBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ================= ACTION =================
        incomeBtn.addActionListener(e -> addIncome());
        expenseBtn.addActionListener(e -> addExpense());
        deleteBtn.addActionListener(e -> deleteTransaction());

        barChartBtn.addActionListener(e -> openBarChart());
        pieChartBtn.addActionListener(e -> openPieChart());
        pdfBtn.addActionListener(e -> exportPDF());

        refresh();
        // applyDarkMode();
        styleButtons(incomeBtn, expenseBtn, deleteBtn, barChartBtn, pieChartBtn, pdfBtn);
    }

    // ================= ADD INCOME =================
    private void addIncome() {
        manager.addIncome(
                descField.getText(),
                Double.parseDouble(amountField.getText()),
                dateField.getText(),
                categoryField.getText()
        );
        refresh();
    }

    // ================= ADD EXPENSE =================
    private void addExpense() {
        manager.addExpense(
                descField.getText(),
                Double.parseDouble(amountField.getText()),
                dateField.getText(),
                categoryField.getText()
        );
        refresh();
    }

    // ================= DELETE =================
    private void deleteTransaction() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        manager.getTransactions().remove(row);
        manager.saveToFile();

        refresh();
    }

    // ================= TABLE =================
    private void loadTable() {

        tableModel.setRowCount(0);

        for (Transaction t : manager.getTransactions()) {

            tableModel.addRow(new Object[]{
                    t.getType(),
                    t.getDescription(),
                    t.getAmount(),
                    t.getDate(),
                    t.getCategory()
            });
        }
    }

    // ================= STATS =================
    private void updateStats() {

        double income = 0;
        double expense = 0;

        for (Transaction t : manager.getTransactions()) {

            if (t.getType().equals("Pemasukan")) income += t.getAmount();
            else expense += t.getAmount();
        }

        balanceLabel.setText("Saldo: Rp " + manager.getBalance());
        incomeLabel.setText("Pemasukan: Rp " + income);
        expenseLabel.setText("Pengeluaran: Rp " + expense);
        diffLabel.setText("Selisih: Rp " + (income - expense));
    }

    private void refresh() {
        loadTable();
        updateStats();
    }

    // ================= CHART =================
    private void openBarChart() {
        new FinanceChart(manager.getTransactions()).setVisible(true);
    }

    private void openPieChart() {
        new FinancePieChart(manager.getTransactions()).setVisible(true);
    }

    // ================= PDF EXPORT =================
    private void exportPDF() {

    try {

        String fileName = "laporan_keuangan.pdf";

        Document document = new Document();

        PdfWriter.getInstance(document, new java.io.FileOutputStream(fileName));

        document.open();

        document.add(new Paragraph("LAPORAN KEUANGAN MAHASISWA\n\n"));

        for (Transaction t : manager.getTransactions()) {

            document.add(new Paragraph(
                    t.getType() + " | " +
                    t.getDescription() + " | Rp" +
                    t.getAmount() + " | " +
                    t.getDate() + " | " +
                    t.getCategory()
            ));
        }

        document.add(new Paragraph("\nSaldo: Rp " + manager.getBalance()));

        document.close();

        JOptionPane.showMessageDialog(this, "PDF berhasil dibuat!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error PDF: " + e.getMessage());
    }
}

    // ================= DARK MODE =================
    // private void applyDarkMode() {

    //     Color bg = new Color(30, 30, 30);
    //     Color fg = Color.WHITE;

    //     getContentPane().setBackground(bg);

    //     for (Component c : getContentPane().getComponents()) {
    //         c.setBackground(bg);
    //         c.setForeground(fg);
    //     }

    //     table.setBackground(new Color(45, 45, 45));
    //     table.setForeground(Color.WHITE);
    //     table.setGridColor(Color.GRAY);
    // }

    // ================= BUTTON STYLE =================
    private void styleButtons(JButton... buttons) {

        buttons[0].setBackground(new Color(46, 204, 113));
        buttons[1].setBackground(new Color(231, 76, 60));
        buttons[2].setBackground(new Color(149, 165, 166));
        buttons[3].setBackground(new Color(52, 152, 219));
        buttons[4].setBackground(new Color(155, 89, 182));
        buttons[5].setBackground(new Color(241, 196, 15));

        for (JButton b : buttons) {
            b.setForeground(Color.BLACK);
            b.setFocusPainted(false);
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        new FinanceAppGUI().setVisible(true);
    }
}