import java.awt.*;

import java.awt.event.*;

import java.sql.*;
import java.io.FileWriter;
import java.io.File;
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Transaction extends JFrame
{
    private JPanel contentPane;
    private JLabel lblTitle;
    private JTextField Account;
    private JTextField Amount;
    private JButton btnWithdraw;
    private JButton Enter;
    private JButton Receipt;
    private JButton Back;
    String acn;
    String Message=" ";
    int Balance;
    public Transaction(String acn) //create constructor
    {
        this.acn=acn;
        setTitle("Transaction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(260, 130, 800, 600);
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


        JLabel lblTitle = new JLabel("Enter details");
        lblTitle.setFont(new Font("Ariel",Font.BOLD,40));
        lblTitle.setBounds(260, 50, 380, 70);
        contentPane.add(lblTitle);

        JLabel lblAccnt = new JLabel("Account Number");
        lblAccnt.setFont(new Font("Ariel",Font.BOLD,20));
        lblAccnt.setBounds(260, 134, 380, 20);
        contentPane.add(lblAccnt);

        Account = new JTextField();
        Account.setBounds(260, 164, 240, 30);
        contentPane.add(Account);

        JLabel lblAmnt = new JLabel("Amount");
        lblAmnt.setFont(new Font("Ariel",Font.BOLD,20));
        lblAmnt.setBounds(260, 204, 380, 30);
        contentPane.add(lblAmnt);

        Amount = new JTextField();
        Amount.setBounds(260, 234, 240, 30);
        contentPane.add(Amount);

        JLabel lblSubtitle = new JLabel();
        lblSubtitle.setFont(new Font("Ariel",Font.BOLD,30));
        lblSubtitle.setBounds(200, 364, 380, 40);
        contentPane.add(lblSubtitle);

        JButton Enter = new JButton("Enter");
        Enter.setFont(new Font("Standard", Font.PLAIN, 18));
        Enter.setBounds(320, 304, 120, 40);
        contentPane.add(Enter);
        Enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                try {
                    int New_Bal1;
                    int New_Bal2;
                    int Balance2;
                    int Balance;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                            "sql12388124", "Ty2wuHzTFR");
                    String Message=" ";
                    String text1 = Account.getText();
                    String text2 = Amount.getText();
                    int Account =Integer.parseInt(text1);
                    int Amount =Integer.parseInt(text2);
                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement(String.format("Select Balance from details where Acc_No=%s",Account));
                    ResultSet rs = st.executeQuery();
                    rs.next();
                    Balance   = rs.getInt(1);
                    PreparedStatement st2 = (PreparedStatement) connection
                            .prepareStatement(String.format("Select Balance from details where Acc_No=%s",acn));
                    ResultSet rs2 = st2.executeQuery();
                    rs2.next();
                    Balance2   = rs2.getInt(1);
                    if (Amount <= Balance2)
                    {

                            Message = "Transaction Successful!";
                            New_Bal1 = Balance + Amount;
                            lblSubtitle.setText(Message);
                        New_Bal2 = Balance2 - Amount;
                        String query2 = "update details set Balance = ? where Acc_no = ?";
                        PreparedStatement preparedStmt2 = connection.prepareStatement(query2);
                        preparedStmt2.setInt   (1, New_Bal2);
                        preparedStmt2.setInt   (2, Integer.parseInt(acn));
                        preparedStmt2.executeUpdate();

                    } else {
                        Message = "Transaction Failed! Try Again";
                        New_Bal1 = Balance;
                        lblSubtitle.setText(Message);
                    }

                    String query = "update details set Balance = ? where Acc_no = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt   (1, New_Bal1);
                    preparedStmt.setInt   (2,Account);
                    preparedStmt.executeUpdate();



                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        JButton Receipt = new JButton("Print Receipt");
        Receipt.setFont(new Font("Standard", Font.PLAIN, 18));
        Receipt.setBounds(280, 454, 200, 30);
        contentPane.add(Receipt);
        Receipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                File myObj = new File("Receipt.txt");
                try {
                    String text1 = Account.getText();
                    String text2 = Amount.getText();
                    FileWriter myWriter = new FileWriter("TransactionReceipt.txt");
                    myWriter.write("Sender Account_No : " +acn);
                    myWriter.write("\n\n");
                    myWriter.write("Reciever Account No : " + text1);
                    myWriter.write("\n\n");
                    myWriter.write("Amount : " + text2);
                    myWriter.close();
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        JButton Back = new JButton("Back to menu");
        Back.setFont(new Font("Standard", Font.PLAIN, 18));
        Back.setBounds(280, 494, 200, 30);
        contentPane.add(Back);
        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                new Menu(acn);
                dispose();
            }
        });

    }
}