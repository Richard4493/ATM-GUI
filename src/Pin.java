import java.awt.*;

import java.awt.event.*;

import java.sql.*;

import javax.swing.*;

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

public class Pin extends JFrame
{
    private JPanel contentPane;
    private JLabel lblTitle;
    private JTextField PIN;
    private JButton Change;
    private JButton Back;
    String acn;
    int New_Pin;
    public Pin(String acn) //create constructor
    {
        this.acn=acn;
        setTitle("Change PIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(260, 130, 800, 400);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                    "sql12388124", "Ty2wuHzTFR");

        } catch (Exception ea) {
            ea.printStackTrace();
        }
             JLabel lblTitle = new JLabel("Enter New PIN");
             lblTitle.setFont(new Font("Standard",Font.BOLD,40));
             lblTitle.setBounds(245, 50, 380, 70);
             contentPane.add(lblTitle);


             PIN = new JPasswordField();
            PIN.setBounds(260, 134, 240, 30);
            contentPane.add(PIN);

        JLabel lblSubtitle = new JLabel();
        lblSubtitle.setFont(new Font("Ariel",Font.BOLD,20));
        lblSubtitle.setBounds(265, 314, 400, 30);
        contentPane.add(lblSubtitle);

        JButton Change = new JButton("Change PIN");
        Change.setFont(new Font("Standard", Font.PLAIN, 18));
        Change.setBounds(300, 204, 160, 40);
        contentPane.add(Change);
        Change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                            "sql12388124", "Ty2wuHzTFR");
                    String Message=" ";
                    String pn = PIN.getText();
                    int New_Pin =Integer.parseInt(pn);
                    String query = "update user1 set pin = ? where Acc_no = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt   (1, New_Pin);
                    preparedStmt.setInt   (2, Integer.parseInt(acn));
                    preparedStmt.executeUpdate();
                    Message = "PIN Change Succesful!";
                    lblSubtitle.setText(Message);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton Back = new JButton("Back to menu");
        Back.setFont(new Font("Standard", Font.PLAIN, 18));
        Back.setBounds(280, 274, 200, 30);
        contentPane.add(Back);
        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                new Menu(acn);
                dispose();
            }
        });

    }
}