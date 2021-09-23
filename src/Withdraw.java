import java.awt.*;

import java.awt.event.*;

import java.sql.*;
import java.io.FileWriter;
import java.io.File;
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

public class Withdraw extends JFrame
{
    private JPanel contentPane;
    private JLabel lblTitle;
    private JTextField Amount;
    private JButton btnWithdraw;
    private JButton Enter;
    private JButton Receipt;
    private JButton Back;
    String acn;
    int Balance;
    int New_Bal;
    public Withdraw(String acn) //create constructor
    {
        this.acn=acn;
        setTitle("Withdrawal");
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

        JLabel lblTitle = new JLabel("Enter Amount for Withdrawal");
        lblTitle.setFont(new Font("Ariel",Font.BOLD,20));
        lblTitle.setBounds(250, 34, 380, 100);
        contentPane.add(lblTitle);
        JLabel lblSubtitle = new JLabel();
        lblSubtitle.setFont(new Font("Ariel",Font.BOLD,20));
        lblSubtitle.setBounds(250, 154, 400, 100);
        contentPane.add(lblSubtitle);
        Amount = new JTextField();
        Amount.setBounds(200, 134, 240, 30);
        contentPane.add(Amount);

        JButton Enter = new JButton("Enter");
        Enter.setFont(new Font("Standard", Font.PLAIN, 18));
        Enter.setBounds(450, 134, 120, 30);
        contentPane.add(Enter);
        Enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                            "sql12388124", "Ty2wuHzTFR");
                    String Message=" ";
                    String text = Amount.getText();
                    int Amount =Integer.parseInt(text);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement(String.format("Select Balance from details where Acc_No=%s",acn));
                    ResultSet rs = st.executeQuery();
                    rs.next();
                    Balance   = rs.getInt(1);
                    if (Amount > Balance) {
                        Message = "Not enough balance!";
                        New_Bal = Balance;
                    } else {
                        Message = "Transaction is Succesful!";
                        New_Bal = Balance - Amount;
                    }
                    lblSubtitle.setText(Message);
                    String query = "update details set Balance = ? where Acc_no = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt   (1, New_Bal);
                    preparedStmt.setInt   (2, Integer.parseInt(acn));
                    preparedStmt.executeUpdate();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        JButton Receipt = new JButton("Print Receipt");
        Receipt.setFont(new Font("Standard", Font.PLAIN, 18));
        Receipt.setBounds(280, 244, 200, 30);
        contentPane.add(Receipt);
        Receipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                File myObj = new File("WithdrawReceipt.txt");
                try {
                    FileWriter myWriter = new FileWriter("WithdrawReceipt.txt");
                    myWriter.write("Acount_No : " +acn);
                    myWriter.write("\n\n");
                    myWriter.write("Current Balance : " + New_Bal);
                    myWriter.close();
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

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