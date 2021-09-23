import java.awt.*;

import java.awt.event.*;

import java.sql.*;

import javax.swing.*;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Wrapper;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Balance extends JFrame
{
    private JPanel contentPane;
    private JLabel lblBalance;
    private JLabel lblAmount;
    private JButton Receipt;
    private JButton Back;
    String acn;
    public Balance(String acn) //create constructor
    {
        this.acn=acn;
        setTitle("Balance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(260, 130, 800, 400);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                    "sql12388124", "Ty2wuHzTFR");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement(String.format("Select Balance from details where Acc_No=%s",acn));
            ResultSet rs = st.executeQuery();
            rs.next();
            JLabel lblBalance = new JLabel("Current Balance : " + rs.getString(1));
            lblBalance.setFont(new Font("Ariel",Font.BOLD,45));
            lblBalance.setBounds(100, 94, 700, 100);
            contentPane.add(lblBalance);
            JButton Receipt = new JButton("Print Receipt");
            Receipt.setFont(new Font("Standard", Font.PLAIN, 18));
            Receipt.setBounds(280, 244, 200, 30);
            contentPane.add(Receipt);
            Receipt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ea) {
                    File myObj = new File("Receipt.txt");
                    try {
                        FileWriter myWriter = new FileWriter("Receipt.txt");
                        myWriter.write("Account_No : " +acn);
                        myWriter.write("\n\n");
                        myWriter.write("Balance : " + rs.getString(1));
                        myWriter.close();
                    }
                    catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            });
        }
        catch (Exception ea)
        {
            ea.printStackTrace();
        }


        JButton Back = new JButton("Back to menu");
        Back.setFont(new Font("Standard", Font.PLAIN, 18));
        Back.setBounds(280, 284, 200, 30);
        contentPane.add(Back);
        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                new Menu(acn);
                dispose();
            }
        });
    }
}