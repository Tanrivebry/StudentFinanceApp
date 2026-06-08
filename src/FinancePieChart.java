import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.ArrayList;

public class FinancePieChart extends JFrame {

    public FinancePieChart(ArrayList<Transaction> transactions) {

        setTitle("Pie Chart Keuangan");
        setSize(500, 400);

        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {

            if (t.getType().equals("Pemasukan")) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Pemasukan", income);
        dataset.setValue("Pengeluaran", expense);

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribusi Keuangan",
                dataset,
                true,
                true,
                false
        );

        setContentPane(new ChartPanel(chart));
    }
}