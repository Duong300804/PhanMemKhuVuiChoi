/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Thuy Tien
 */
public class guiDo {
    
    private String maGuiDo;
    private String maTuDo;
    private String maKhachHang;
    private Date thoiGianGui;

    public guiDo() {
    }

    public guiDo(String maGuiDo, String maTuDo, String maKhachHang, Date thoiGianGui) {
        this.maGuiDo = maGuiDo;
        this.maTuDo = maTuDo;
        this.maKhachHang = maKhachHang;
        this.thoiGianGui = thoiGianGui;
    }

    public String getMaGuiDo() {
        return maGuiDo;
    }

    public void setMaGuiDo(String maGuiDo) {
        this.maGuiDo = maGuiDo;
    }

    public String getMaTuDo() {
        return maTuDo;
    }

    public void setMaTuDo(String maTuDo) {
        this.maTuDo = maTuDo;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Date getThoiGianGui() {
        return thoiGianGui;
    }

    public void setThoiGianGui(Date thoiGianGui) {
        this.thoiGianGui = thoiGianGui;
    }
}
