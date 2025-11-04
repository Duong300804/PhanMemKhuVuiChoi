/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class phanKhu {
    private String maPhanKhu;
    private String tenPhanKhu;
    private String moTa;
    private String viTri;
    private float dienTich;

    public phanKhu() {
    }

    public phanKhu(String maPhanKhu, String tenPhanKhu, String moTa, String viTri, float dienTich) {
        this.maPhanKhu = maPhanKhu;
        this.tenPhanKhu = tenPhanKhu;
        this.moTa = moTa;
        this.viTri = viTri;
        this.dienTich = dienTich;
    }

    public String getMaPhanKhu() {
        return maPhanKhu;
    }

    public void setMaPhanKhu(String maPhanKhu) {
        this.maPhanKhu = maPhanKhu;
    }

    public String getTenPhanKhu() {
        return tenPhanKhu;
    }

    public void setTenPhanKhu(String tenPhanKhu) {
        this.tenPhanKhu = tenPhanKhu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public float getDienTich() {
        return dienTich;
    }

    public void setDienTich(float dienTich) {
        this.dienTich = dienTich;
    }
    
    
}
