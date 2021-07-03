import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;


public class CustomerLogin extends JFrame {
    private JLabel image;
    private JPanel rootpanel;
    private JPanel Movie_name;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JButton bookButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        image = new JLabel(new ImageIcon("ProjectStockPhoto_50.jpg"));
    }

    public CustomerLogin(String username) {
        setBounds(100, 200, 1200, 400);
        setResizable(false);
        add(rootpanel);
        CBmovies();
        CBdates();
        CBtime();
        CBseats();

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seat_quality = comboBox4.getSelectedItem().toString();
                int seat_number = Integer.parseInt(textField1.getText());
                String mov = comboBox1.getSelectedItem().toString();
                String tim = comboBox3.getSelectedItem().toString();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
                    PreparedStatement ps = con.prepareStatement("select seat_number from seats where seat_quality = ?");
                    ps.setString(1, seat_quality);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int seat_available = Integer.parseInt(rs.getString("seat_number"));
                        if (seat_available < seat_number) {
                            JOptionPane.showMessageDialog(null,
                                    "Not enough Seats");
                        } else {
                            int seat_remaining = seat_available - seat_number;

                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
                            PreparedStatement pss = con.prepareStatement("update seats set seat_number = ? where seat_quality = ?");
                            pss.setString(1, String.valueOf(seat_available));
                            pss.setString(2, seat_quality);
                            pss.executeUpdate();
                            PreparedStatement ss = con.prepareStatement("select seat_price from seats where seat_quality = ?");
                            ss.setString(1, seat_quality);
                            ResultSet rr = ss.executeQuery();
                            if (rr.next()) {
                                int cost_of_one = Integer.parseInt(rr.getString("seat_price"));


                                int totalcost = seat_number * cost_of_one;
                                textField2.setText(textField2.getText() + mov);
                                textField3.setText(textField3.getText() + totalcost);
                                PreparedStatement sd = con.prepareStatement("select Movie_hallnum from Movies where Movie_name = ? AND Movie_timing = ?");
                                sd.setString(1, mov);
                                sd.setString(2, tim);
                                ResultSet hh = sd.executeQuery();
                                if(hh.next()){
                                int hal = Integer.parseInt(hh.getString("Movie_hallnum"));
                                    textField4.setText(textField4.getText() + hal);
                                        PreparedStatement cu = con.prepareStatement("INSERT INTO CustomerBooked (username, seat_quality, total_cost, Hall) VALUES ('" + username + "','"  + seat_quality + "','" + totalcost +"','" + hal +"' )");
                                        cu.executeUpdate();
                                    }

                                }




                        }
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }

    private void CBmovies() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
            PreparedStatement ps = con.prepareStatement("select distinct Movie_name from Movies");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBox1.addItem(rs.getString("Movie_name"));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void CBdates() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
            PreparedStatement ps = con.prepareStatement("select distinct Movie_date from Movies");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBox2.addItem(rs.getString("Movie_date"));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void CBtime() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
            PreparedStatement ps = con.prepareStatement("select distinct Movie_timing from Movies");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBox3.addItem(rs.getString("Movie_timing"));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void CBseats() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration4", "root", "spartanelites2");
            PreparedStatement ps = con.prepareStatement("select seat_quality from Seats");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBox4.addItem(rs.getString("seat_quality"));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
