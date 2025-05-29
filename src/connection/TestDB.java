/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

/**
 *
 * @author Tamilpc
 */
import java.sql.*;
/**
 *
 * @author Tamilpc
 */
public class TestDB {
    public static void main(String[] args) {
        Connection con = ConnectionProvider.getCon();
        if (con == null) {
            System.out.println("Database connection failed!");
        } else {
            System.out.println("Connected successfully!");
        }
    }
}
