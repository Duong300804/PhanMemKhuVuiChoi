/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class troChoi {
    private String maTroChoi;
    private String maPhanKhu;
    private String tenPhanKhu;
    private String tenTroChoi;
    private String moTa;

    public troChoi(String maTroChoi, String maPhanKhu, String tenPhanKhu, String tenTroChoi, String moTa) {
        this.maTroChoi = maTroChoi;
        this.maPhanKhu = maPhanKhu;
        this.tenPhanKhu = tenPhanKhu;
        this.tenTroChoi = tenTroChoi;
        this.moTa = moTa;
    }

    public troChoi() {
    }

    public String getMaTroChoi() {
        return maTroChoi;
    }

    public void setMaTroChoi(String maTroChoi) {
        this.maTroChoi = maTroChoi;
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

    public String getTenTroChoi() {
        return tenTroChoi;
    }

    public void setTenTroChoi(String tenTroChoi) {
        this.tenTroChoi = tenTroChoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    
}
