/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class ConnectDB {
    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:sqlserver://localhost:1433;"
                           + "databaseName=QuanLyKhuVuiChoi;"
                           + "user=sa;password=123456789;"
                           + "encrypt=true;"
                           + "trustServerCertificate=true";
                conn = DriverManager.getConnection(url);
                System.out.println("Ket noi thanh cong!");
            }
        } catch (SQLException e) {
            System.out.println("Ket noi that bai!");
            e.printStackTrace();
        }
        return conn;
    }
    
//    public static void closeConnection() {
//    if (conn != null) {
//        try {
//            conn.close();
//            System.out.println("Đã đóng kết nối");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
}
