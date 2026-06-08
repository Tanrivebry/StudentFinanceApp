import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {

        setTitle("Login Aplikasi Keuangan");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton loginBtn = new JButton("Login");
        add(new JLabel());
        add(loginBtn);

        loginBtn.addActionListener(e -> login());

        setLocationRelativeTo(null);
    }

    private void login() {

        String user = userField.getText();
        String pass = new String(passField.getPassword());

        // USER DEFAULT (sementara)
        if (user.equals("admin") && pass.equals("123")) {

            JOptionPane.showMessageDialog(this, "Login berhasil!");

            dispose(); // tutup login

            new FinanceAppGUI().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Username / Password salah!");
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}