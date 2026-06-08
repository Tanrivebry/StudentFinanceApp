public class Transaction {

    private String type;
    private String description;
    private double amount;
    private String date;
    private String category;

    public Transaction(String type, String description,
                       double amount, String date,
                       String category) {

        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return type + " | " +
               description + " | Rp" +
               amount + " | " +
               date + " | " +
               category;
    }
}