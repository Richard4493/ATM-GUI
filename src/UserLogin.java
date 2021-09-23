import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserLogin extends JFrame {

    private JTextField acno;
    private JPasswordField pno;
    private JButton btn,btn2;
    private JLabel lbl1,lbl3,lbl4,lbl12;
    private JPanel ul;

    public static void main(String[] args) {

        UserLogin frame = new UserLogin();
        frame.setVisible(true);
    }

    /* Create the frame. */
    public UserLogin() {
        setBounds(260, 130, 1000, 600);
        setResizable(false);
        ul = new JPanel();
        setContentPane(ul);
        ul.setLayout(null);

        lbl1 = new JLabel("WELCOME TO SWISS BANK ATM SERVICE");
        lbl1.setFont(new Font("Times New Roman",Font.BOLD,40));
        lbl1.setForeground(Color.BLUE);
        lbl1.setBounds(100, 15, 850, 90);
        ul.add(lbl1);

        lbl12 = new JLabel("Please Login To Use ATM Service or press Exit to quit");
        lbl12.setFont(new Font("Standard",Font.ITALIC,35));
        lbl12.setForeground(Color.RED);
        lbl12.setBounds(100, 105, 950, 90);
        ul.add(lbl12);


        acno = new JTextField();
        acno.setFont(new Font("Standard", Font.PLAIN, 32));
        acno.setBounds(480, 250, 280, 70);
        ul.add(acno);

        pno = new JPasswordField();
        pno.setFont(new Font("Standard", Font.PLAIN, 32));
        pno.setBounds(480, 350, 280, 70);
        ul.add(pno);


        lbl4 = new JLabel("Account No:");
        lbl4.setBackground(Color.BLACK);
        lbl4.setForeground(Color.BLACK);
        lbl4.setFont(new Font("Standard", Font.PLAIN, 31));
        lbl4.setBounds(250, 250, 250, 50);
        ul.add(lbl4);

        lbl3 = new JLabel("PIN");
        lbl3.setForeground(Color.BLACK);
        lbl3.setBackground(Color.CYAN);
        lbl3.setFont(new Font("Standard", Font.PLAIN, 31));
        lbl3.setBounds(250, 350, 190, 50);
        ul.add(lbl3);


        btn = new JButton("Login");
        btn.setFont(new Font("Standard", Font.PLAIN, 26));
        btn.setBounds(420, 450, 160, 70);
        ul.add(btn);
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String acn = acno.getText();
                String pin = pno.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/sql12388124",
                            "sql12388124", "Ty2wuHzTFR");

                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Select  Acc_No,pin from user1 where Acc_No=? and pin=?");

                    st.setString(1,acn);
                    st.setString(2, pin);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        JOptionPane.showMessageDialog(btn, "You have successfully logged in to Bank Server");
                        new Menu(acn);
                    } else {
                        JOptionPane.showMessageDialog(btn, "Wrong AC.No or PIN given, Try again");
                        acno.setText("");
                        pno.setText("");
                    }
                } catch (Exception ea) {
                    System.out.println(ea.toString());
                }
            }
        });
        btn2 = new JButton("EXIT");
        btn2.setFont(new Font("Standard", Font.PLAIN, 26));
        btn2.setBounds(620, 450, 160, 70);
        ul.add(btn2);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ea) {
                System.exit(0);
            }
        });

    }
}
class Menu implements ActionListener {
    JFrame fr;
    JButton b1,b2,b3,b4,b5;
    JLabel lb1, lb2, lb3;
    ButtonGroup bg;
    String acn;
    Menu(String acn) {
        this.acn= acn;
        fr = new JFrame();
        fr.setLayout(null);
        fr.setTitle("ATM Transaction Page");
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setBounds(260, 130, 800, 400);
        Container c = fr.getContentPane();
        c.setBackground(Color.white);
        lb1 = new JLabel("Welcome");
        lb1.setBounds(310, 24, 300, 90);
        lb1.setFont(new Font("Ariel",Font.BOLD,40));
        fr.add(lb1);
        lb2 = new JLabel("Choose Your Option");
        lb2.setFont(new Font("Ariel",Font.BOLD,20));
        lb2.setBounds(300, 104, 350, 20);
        fr.add(lb2);
        b1 = new JButton("Withdraw");
        b1.setBounds(131, 165, 160, 23);
        b1.setFont(new Font("Standard", Font.PLAIN, 18));
        fr.add(b1);
        b2 = new JButton("Transaction");
        b2.setFont(new Font("Standard", Font.PLAIN, 18));
        b2.setBounds(501, 165, 160, 23);
        fr.add(b2);
        b3 = new JButton("Balance");
        b3.setFont(new Font("Standard", Font.PLAIN, 18));
        b3.setBounds(131, 265, 160, 23);
        fr.add(b3);
        b4 = new JButton("Change Pin");
        b4.setFont(new Font("Standard", Font.PLAIN, 18));
        b4.setBounds(501, 265, 160, 23);
        fr.add(b4);
        b5 = new JButton("Exit");
        b5.setFont(new Font("Standard", Font.PLAIN, 18));
        b5.setBounds(331, 295, 100, 23);
        fr.add(b5);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        fr.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {

                new Withdraw(acn);
                fr.dispose();
            }
        if (e.getSource() == b2) {

            new Transaction(acn);
            fr.dispose();
        }
        if (e.getSource() == b4) {

            new Pin(acn);
            fr.dispose();
        }
        if (e.getSource() == b3) {
            new Balance(acn);
            fr.dispose();
        }
        if (e.getSource() == b5) {
            fr.dispose();
        }

            }

}

