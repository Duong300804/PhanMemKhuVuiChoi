/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;

import Database.ConnectDB;
import ViewLogin.formTrangBatDau;

/**
 *
 * @author LENOVO
 */
public class QuanLyKhuVuiChoi {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ConnectDB.getConnection();
        formTrangBatDau formTrangBatDau = new formTrangBatDau();
        formTrangBatDau.setVisible(true);
    }
}
