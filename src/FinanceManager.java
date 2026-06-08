import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FinanceManager {

    private ArrayList<Transaction> transactions;
    private final String FILE_PATH = "transactions.txt";

    public FinanceManager() {

        transactions = new ArrayList<>();

        System.out.println("FinanceManager aktif...");

        loadFromFile();

        System.out.println("Total transaksi: " + transactions.size());
    }

    // ================= GET TRANSACTIONS =================
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // ================= ADD INCOME =================
    public void addIncome(String description,
                          double amount,
                          String date,
                          String category) {

        transactions.add(new Transaction(
                "Pemasukan",
                description,
                amount,
                date,
                category
        ));

        saveToFile();
    }

    // ================= ADD EXPENSE =================
    public void addExpense(String description,
                           double amount,
                           String date,
                           String category) {

        transactions.add(new Transaction(
                "Pengeluaran",
                description,
                amount,
                date,
                category
        ));

        saveToFile();
    }

    // ================= BALANCE =================
    public double getBalance() {

        double balance = 0;

        for (Transaction t : transactions) {

            if (t.getType().equals("Pemasukan")) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }

        return balance;
    }

    // ================= SAVE FILE =================
    public void saveToFile() {

        try {

            FileWriter writer = new FileWriter(FILE_PATH);

            for (Transaction t : transactions) {

                writer.write(
                        t.getType() + "," +
                        t.getDescription() + "," +
                        t.getAmount() + "," +
                        t.getDate() + "," +
                        t.getCategory() + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Gagal save: " + e.getMessage());
        }
    }

    // ================= LOAD FILE =================
    private void loadFromFile() {

        try {

            File file = new File(FILE_PATH);

            if (!file.exists()) {
                System.out.println("File belum ada, dibuat baru.");
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();
                String[] data = line.split(",");

                if (data.length == 5) {

                    transactions.add(new Transaction(
                            data[0],
                            data[1],
                            Double.parseDouble(data[2]),
                            data[3],
                            data[4]
                    ));
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Gagal load: " + e.getMessage());
        }
    }
}