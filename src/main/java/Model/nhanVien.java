/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class nhanVien {
    private String manv;
    private String tennv;
    private Date ngaysinh;
    private String sdt;
    private String chucvu;
    private String gioitinh;

    public nhanVien() {
    }

    public nhanVien(String manv, String tennv, Date ngaysinh, String sdt, String chucvu, String gioitinh) {
        this.manv = manv;
        this.tennv = tennv;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.chucvu = chucvu;
        this.gioitinh = gioitinh;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
    
    
}
