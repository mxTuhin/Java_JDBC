/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_1_database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Wasif Shafaet Chowdhury
 */
public class JDBC_1_Database_Connection 
{
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception 
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cse_310_demo_project?", "root", "");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID, FirstName, Email FROM user_information");
        while (rs.next()) 
        {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        }

        Scanner scn = new Scanner(System.in);
        System.out.println("\nPress \n1 for registration \n2 for Login \n3 for Edit \n4 for Delete");
        int choise = scn.nextInt();
        PreparedStatement ps;
        if (choise == 1) 
        {
            scn.nextLine();
            System.out.println("Please Enter Your First Name");
            String fName = scn.nextLine();
            System.out.println("Please Enter Your Last Name");
            String lName = scn.nextLine();
            System.out.println("Please Enter Your Phone No");
            String pno = scn.nextLine();
            System.out.println("Please Enter Your Address");
            String add = scn.nextLine();
            System.out.println("Please Enter Your Email");
            String emailAdd = scn.nextLine();
            System.out.println("Please Enter Your Password");
            String pwd = scn.nextLine();
            System.out.println("Please Enter Your Gender");
            String gender = scn.nextLine();
            System.out.println("Please Enter Your Date of Birth");
            String dob = scn.nextLine();
            
            ps = con.prepareStatement("SELECT email FROM user_information WHERE email = ?");
            ps.setString(1, emailAdd);
            rs = ps.executeQuery();
            
            if(rs.next() == true)
            {
                System.out.println("Your Email Address Alreaedy Exists");
            }
            else
            {
                ps = con.prepareStatement("INSERT INTO user_information (FirstName,LastName,PhoneNo,Address,Email,Password,Gender,DOB) VALUES (?,?,?,?,?,?,?,?)");
                ps.setString(1, fName);
                ps.setString(2, lName);
                ps.setString(3, pno);
                ps.setString(4, add);
                ps.setString(5, emailAdd);
                ps.setString(6, pwd);
                ps.setString(7, gender);
                ps.setString(8, dob);
                ps.executeUpdate();
            }
        } 
        else if (choise == 2) 
        {
            System.out.println("Please Enter Username");
            String userName = scn.next();
            System.out.println("Please Enter Password");
            String pass = scn.next();

            ps = con.prepareStatement("SELECT email, password FROM user_information WHERE email = ? AND password = ?");
            
            ps.setString(1, userName);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            //boolean loginCredential = false;
            if (rs.next() == true) 
            {
                //loginCredential = true;
                ps = con.prepareStatement("SELECT * FROM user_information WHERE email = ? AND password = ?");
                ps.setString(1, userName);
                ps.setString(2, pass);
                rs = ps.executeQuery();
                rs.next();

                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(6);
                String userID = rs.getString(1);
                String phoneNo = rs.getString(4);
                String address = rs.getString(5);
                String dateOfBirth = rs.getString(9);

                System.out.println("");
                System.out.println("Hello " + firstName + " " + lastName);
                System.out.println("Email         : " + email);
                System.out.println("User ID       : " + userID);
                System.out.println("Phone No      : 0" + phoneNo);
                System.out.println("Address       : " + address);
                System.out.println("Date of Birth : " + dateOfBirth);
            } 
	    else 
            {
                System.out.println("Username or Password doesn't match");
            }
        }
        else if(choise==3){
            System.out.println("Please Enter Username");
            String userName = scn.next();

            ps = con.prepareStatement("SELECT * FROM user_information WHERE email = ?");

            ps.setString(1, userName);
            rs = ps.executeQuery();
            //boolean loginCredential = false;
            if (rs.next())
            {
                scn.nextLine();
                System.out.println("Please Enter Your First Name");
                String fName = scn.nextLine();
                System.out.println("Please Enter Your Last Name");
                String lName = scn.nextLine();
                System.out.println("Please Enter Your Phone No");
                String pno = scn.nextLine();
                System.out.println("Please Enter Your Address");
                String add = scn.nextLine();
                System.out.println("Please Enter Your Password");
                String pwd = scn.nextLine();
                System.out.println("Please Enter Your Gender");
                String gender = scn.nextLine();
                System.out.println("Please Enter Your Date of Birth");
                String dob = scn.nextLine();

                ps = con.prepareStatement("UPDATE user_information SET FirstName=?,LastName=?,PhoneNo=?,Address=?,Password=?,Gender=?,DOB=?");
                ps.setString(1, fName);
                ps.setString(2, lName);
                ps.setString(3, pno);
                ps.setString(4, add);
                ps.setString(5, pwd);
                ps.setString(6, gender);
                ps.setString(7, dob);
                ps.executeUpdate();
            }


        }
        else if(choise==4){
            System.out.println("Please Enter Username");
            String userName = scn.next();
            ps = con.prepareStatement("DELETE FROM user_information WHERE email = ?");

            ps.setString(1, userName);
            ps.executeUpdate();
        }
        st.close();
        con.close();
    }
}
