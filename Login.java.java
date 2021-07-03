/*      READ ME
    we are using database mysql localhost port 3306, database name = "registration4", database password = "spartanelites2"
    admin username and password = admin
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel passwordTextFieldPanel;
    private JPanel ButtonPanel;
    private JPanel EmptyPanel;
    private JPanel rootPanel;
    private JButton Login;

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 100, 760, 300);
        setResizable(false);

        add(rootPanel);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");

                    String query = "INSERT INTO CustomerLoginCredentials (username, pass) VALUES ('" + username + "','" + password + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);

                    if (x == 0) {
                        JOptionPane.showMessageDialog(registerButton, "This user exists");
                    } else {
                        JOptionPane.showMessageDialog(registerButton,
                                "Welcome, " + username + " Your account is successfully created");

                        connection.close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();
                if (username.equals("admin") && password.equals("admin")) {
                    AdminLogin adminLogin = new AdminLogin();
                    adminLogin.setVisible(true);
                }
                else{
                    JFrame f1 = new JFrame();
                    JLabel l, l0;
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
                        PreparedStatement ps = con.prepareStatement("select username from CustomerLoginCredentials where username=? and pass=?");
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next())
                        {
                            CustomerLogin customerLogin = new CustomerLogin(username);
                            customerLogin.setVisible(true);
                        } else
                        {
                            JOptionPane.showMessageDialog(null,
                                    "Incorrect username or password..Try Again with correct detail");
                        }
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }
}


