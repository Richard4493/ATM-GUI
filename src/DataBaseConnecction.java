import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DataBaseConnection {
    Connection con;
    int acn_no;
    String name;

    DataBaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12388124", "sql12388124",
                    "Ty2wuHzTFR");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    boolean loginUser(int acn_no, String pin) {
        try{
            PreparedStatement p = con.prepareStatement("select Acc_No,pin,namefrom user1");
            ResultSet set = p.executeQuery();
            acn_no  = set.getInt(1);
            name = set.getString(2);
            while(set.next()){
                if(set.getInt(1) == acn_no &&set.getString(2) == pin )
                    return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    int get_balance() {
        try{
            PreparedStatement p = con.prepareStatement("SELECT balace from details where Acc_No=?");
            p.setInt(1 , acn_no);
            ResultSet set = p.executeQuery();
            set.next();
            return set.getInt(1);
        }
        catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    int get_balance(int acn_no) {
        try{
            PreparedStatement p = con.prepareStatement("SELECT balace from details where Acc_No=?");
            p.setInt(1 , acn_no);
            ResultSet set = p.executeQuery();
            set.next();
            return set.getInt(1);
        }
        catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    boolean change_balance(int balance){
        try {
            PreparedStatement p = con.prepareStatement("update details set balance=? where Acc_No=?");
            p.setInt(1,balance);
            p.setInt(2,acn_no);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean change_balance(int acn_no , int balance){
        try {
            PreparedStatement p = con.prepareStatement("update details set balance=? where Acc_No=?");
            p.setInt(1,balance);
            p.setInt(2,acn_no);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean transaction(int racn,int amnt){
        try {
            int balance = get_balance();
            if(amnt>balance){
                return false;
            }
            if(change_balance(racn , get_balance(racn)+amnt))
                change_balance(balance - amnt);
            return true;
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return false;
    }
    boolean pinchange(int newpin){
        try {
            PreparedStatement p = con.prepareStatement("update user1 set pin=? where Acc_No=?");
            p.setInt(1,newpin);
            p.setInt(2,acn_no);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}