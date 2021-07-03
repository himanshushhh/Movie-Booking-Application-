import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AdminLogin extends JFrame {
    String time[] = {"9:00 am", "11:30 am", "2:30 pm", "5:00 pm", "9:00 pm"};
    String Hall_Number[] = {"1", "2", "3", "4"};
    private JComboBox comboBox1;
    private JTextField textField1;
    private JPanel rootPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel image;
    private JComboBox comboBox2;
    private JButton confirmButton;
    private JLabel Image;

    /*public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminLogin frame = new AdminLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
     */
    private void createUIComponents(){
        // TODO: place custom component creation code here
        image = new JLabel(new ImageIcon("ProjectStockPhoto_50.jpg"));
        comboBox1 = new JComboBox(time);
        comboBox2 = new JComboBox(Hall_Number);
    }


    public AdminLogin() {

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 100, 760, 400);
        setResizable(false);
        add(rootPanel);


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String movie_id = textField2.getText();
                String movie_name = textField1.getText();
                String movie_date = textField3.getText();
                String movie_time = comboBox1.getSelectedItem().toString();
                String movie_hallnum = comboBox2.getSelectedItem().toString();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");

                    String query = "INSERT INTO Movies (Movie_id, Movie_name, Movie_timing, Movie_date, Movie_hallnum) VALUES ('" + movie_id + "','" + movie_name + "','" + movie_time + "','" + movie_date + "','" + movie_hallnum + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);

                    if (x == 0) {
                        JOptionPane.showMessageDialog(confirmButton, "This movie_id exists");
                    } else {
                        JOptionPane.showMessageDialog(confirmButton,
                                "" + movie_id + ":" + movie_name + " movie is successfully registered");

                        connection.close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }
}

