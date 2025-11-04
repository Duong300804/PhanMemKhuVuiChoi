/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class thietBi {
    private String maThietBi;
    private String maTroChoi;
    private String tenTroChoi;
    private String tenThietBi;
    private String tinhTrang;

    public thietBi() {
    }

    public thietBi(String maThietBi, String maTroChoi, String tenTroChoi, String tenThietBi, String tinhTrang) {
        this.maThietBi = maThietBi;
        this.maTroChoi = maTroChoi;
        this.tenTroChoi = tenTroChoi;
        this.tenThietBi = tenThietBi;
        this.tinhTrang = tinhTrang;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getMaTroChoi() {
        return maTroChoi;
    }

    public void setMaTroChoi(String maTroChoi) {
        this.maTroChoi = maTroChoi;
    }

    public String getTenTroChoi() {
        return tenTroChoi;
    }

    public void setTenTroChoi(String tenTroChoi) {
        this.tenTroChoi = tenTroChoi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    
}
