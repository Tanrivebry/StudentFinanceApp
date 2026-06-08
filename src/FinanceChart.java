import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;

public class FinanceChart extends JFrame {

    public FinanceChart(ArrayList<Transaction> transactions) {

        setTitle("Grafik Keuangan");
        setSize(600, 400);

        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {

            if (t.getType().equals("Pemasukan")) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(income, "Pemasukan", "Income");
        dataset.addValue(expense, "Pengeluaran", "Expense");

        JFreeChart chart = ChartFactory.createBarChart(
                "Statistik Keuangan",
                "Kategori",
                "Jumlah (Rp)",
                dataset
        );

        setContentPane(new ChartPanel(chart));
    }
}