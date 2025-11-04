/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import Model.taiKhoan;
import ViewChiTiet.chiTietDoAn;
import ViewChiTiet.chiTietDoLuuNiem;
import ViewChiTiet.chiTietDoanhThuDoAn;
import ViewChiTiet.chiTietDoanhThuVe;
import ViewChiTiet.chiTietGuiDo;
import ViewChiTiet.chiTietHoaDon;
import ViewChiTiet.chiTietHoaDonDoAn;
import ViewChiTiet.chiTietKhachHang;
import ViewChiTiet.chiTietNhanVien;
import ViewChiTiet.chiTietPhanKhu;
import ViewChiTiet.chiTietSuKien;
import ViewChiTiet.chiTietTaiKhoan;
import ViewChiTiet.chiTietThietBi;
import ViewChiTiet.chiTietTongDoanhThu;
import ViewChiTiet.chiTietTroChoi;
import ViewChiTiet.chiTietVe;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class formTrangChu extends javax.swing.JPanel {

    /**
     * Creates new form formTrangChu
     */
    public formTrangChu() {
        initComponents();
        loadSoLuongTaiKhoan();
        loadSoLuongPhanKhu();
        loadSoLuongTroChoi();
        loadSoLuongThietBi();
        loadSoLuongDoAn();
        loadSoLuongDoLuuNiem();
        loadSoLuongSuKien();
        loadSoLuongNhanVien();
        loadSoLuongVe();
        loadSoLuongHoaDon();
        loadSoLuongKhachHang();
        loadSoLuongGuiDo();
        loadDoanhThuVe();
        loadDoanhThuDoAn();
        loadTongDoanhThu();
        loadSoLuongHoaDonDoAn();
    }

    public void loadSoLuongTaiKhoan() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM taiKhoan";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoTaiKhoan.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm tài khoản!");
        }
    }

    public void loadSoLuongPhanKhu() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM phanKhu";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoPhanKhu.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm phân khu!");
        }
    }

    public void loadSoLuongTroChoi() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM troChoi";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoTroChoi.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm trò chơi!");
        }
    }

    public void loadSoLuongThietBi() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM thietBi";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoThietBi.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm thiết bị!");
        }
    }

    public void loadSoLuongDoAn() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM banDoAn";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoDoAn.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm đồ ăn!");
        }
    }

    public void loadSoLuongDoLuuNiem() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM doLuuNiem";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoDoLuuNiem.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm đồ lưu niệm!");
        }
    }

    public void loadSoLuongSuKien() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM suKien";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoSuKien.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm sự kiện!");
        }
    }

    public void loadSoLuongNhanVien() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM NhanVien";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoNhanVien.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm nhân viên!");
        }
    }

    public void loadSoLuongVe() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM Ve";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoVe.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm vé!");
        }
    }

    public void loadSoLuongHoaDon() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM HoaDon";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoHoaDon.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm hóa đơn!");
        }
    }

    public void loadSoLuongKhachHang() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM khachHang";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoKhachHang.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm khách hàng!");
        }
    }

    public void loadSoLuongGuiDo() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM guiDo";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoGuiDo.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm gửi đồ!");
        }
    }

    public void loadDoanhThuVe() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int tongDoanhThu = rs.getInt("tongDoanhThu");
                lblDoanhThuVe.setText(String.format("%,d VND", tongDoanhThu)); // format tiền đẹp
            } else {
                lblDoanhThuVe.setText("0 VND"); // Phòng khi không có dữ liệu
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thống kê doanh thu vé!");
        }
    }

    public void loadDoanhThuDoAn() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT SUM(tongTien) AS tongDoanhThu FROM hoaDonDoAn";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                float tongDoanhThu = rs.getFloat("tongDoanhThu");
                lblDoanhThuDoAn.setText(String.format("%,.0f VND", tongDoanhThu));
            } else {
                lblDoanhThuDoAn.setText("0 VND");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thống kê doanh thu đồ ăn!");
        }
    }

    public void loadTongDoanhThu() {
        try {
            Connection conn = ConnectDB.getConnection();
            // Doanh thu vé 
            int doanhThuVe = 0;
            Statement stmtVe = conn.createStatement();
            ResultSet rsVe = stmtVe.executeQuery("SELECT SUM(tongTien) AS tongVe FROM HoaDon");
            if (rsVe.next()) {
                doanhThuVe = rsVe.getInt("tongVe");
            }
            rsVe.close();
            stmtVe.close();
            // Doanh thu đồ ăn 
            float doanhThuDoAn = 0;
            Statement stmtDoAn = conn.createStatement();
            ResultSet rsDoAn = stmtDoAn.executeQuery("SELECT SUM(tongTien) AS tongDoAn FROM hoaDonDoAn");
            if (rsDoAn.next()) {
                doanhThuDoAn = rsDoAn.getFloat("tongDoAn");
            }
            rsDoAn.close();
            stmtDoAn.close();
            // Tổng doanh thu: 
            float tongDoanhThu = doanhThuVe + doanhThuDoAn;
            lblTongDoanhThu.setText(String.format("%,.0f VND", tongDoanhThu));

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tính tổng doanh thu!");
        }
    }

    public void loadSoLuongHoaDonDoAn() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT COUNT(*) AS soLuong FROM hoaDonDoAn";
            ResultSet rs = stmt.executeQuery(SQL);

            if (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                lblSoHoaDonDoAn.setText(String.valueOf(soLuong));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đếm số lượng hóa đơn đồ ăn!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gridPanel1 = new javax.swing.JPanel();
        panelTaiKhoan = new javax.swing.JPanel();
        labelTK = new javax.swing.JLabel();
        lblSoTaiKhoan = new javax.swing.JLabel();
        lblIMG = new javax.swing.JLabel();
        panelPhanKhu = new javax.swing.JPanel();
        labelPK = new javax.swing.JLabel();
        lblSoPhanKhu = new javax.swing.JLabel();
        lblIMG1 = new javax.swing.JLabel();
        panelTroChoi = new javax.swing.JPanel();
        labelTT = new javax.swing.JLabel();
        lblSoTroChoi = new javax.swing.JLabel();
        lblIMG2 = new javax.swing.JLabel();
        gridPanel2 = new javax.swing.JPanel();
        panelThietBi = new javax.swing.JPanel();
        labelTB = new javax.swing.JLabel();
        lblSoThietBi = new javax.swing.JLabel();
        lblIMG3 = new javax.swing.JLabel();
        panelSuKien = new javax.swing.JPanel();
        labelSK = new javax.swing.JLabel();
        lblSoSuKien = new javax.swing.JLabel();
        lblIMG4 = new javax.swing.JLabel();
        panelNhanVien = new javax.swing.JPanel();
        labelNV = new javax.swing.JLabel();
        lblSoNhanVien = new javax.swing.JLabel();
        lblIMG5 = new javax.swing.JLabel();
        gridPanel3 = new javax.swing.JPanel();
        panelKhachHang = new javax.swing.JPanel();
        labelKH = new javax.swing.JLabel();
        lblSoKhachHang = new javax.swing.JLabel();
        lblIMG6 = new javax.swing.JLabel();
        panelGuiDo = new javax.swing.JPanel();
        labelGD = new javax.swing.JLabel();
        lblSoGuiDo = new javax.swing.JLabel();
        lblIMG7 = new javax.swing.JLabel();
        panelVe = new javax.swing.JPanel();
        labelVE = new javax.swing.JLabel();
        lblSoVe = new javax.swing.JLabel();
        lblIMG8 = new javax.swing.JLabel();
        gridPanel4 = new javax.swing.JPanel();
        panelHoaDon = new javax.swing.JPanel();
        labelHD = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();
        lblIMG9 = new javax.swing.JLabel();
        panelDoAn = new javax.swing.JPanel();
        labelDA = new javax.swing.JLabel();
        lblSoDoAn = new javax.swing.JLabel();
        lblIMG10 = new javax.swing.JLabel();
        panelDoLuuNiem = new javax.swing.JPanel();
        labelDLN = new javax.swing.JLabel();
        lblSoDoLuuNiem = new javax.swing.JLabel();
        lblIMG11 = new javax.swing.JLabel();
        gridPanel6 = new javax.swing.JPanel();
        panelHoaDonDoAn = new javax.swing.JPanel();
        labelHD2 = new javax.swing.JLabel();
        lblSoHoaDonDoAn = new javax.swing.JLabel();
        lblIMG13 = new javax.swing.JLabel();
        panelDoanhThuVe = new javax.swing.JPanel();
        labelDA1 = new javax.swing.JLabel();
        lblDoanhThuVe = new javax.swing.JLabel();
        lblIMG14 = new javax.swing.JLabel();
        panelDoanhThuDoAn = new javax.swing.JPanel();
        labelDLN1 = new javax.swing.JLabel();
        lblDoanhThuDoAn = new javax.swing.JLabel();
        lblIMG15 = new javax.swing.JLabel();
        panelTongDoanhThu = new javax.swing.JPanel();
        labelDA2 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        lblIMG16 = new javax.swing.JLabel();

        gridPanel1.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

        panelTaiKhoan.setBackground(new java.awt.Color(240, 240, 240));
        panelTaiKhoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTaiKhoanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelTaiKhoanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelTaiKhoanMouseExited(evt);
            }
        });

        labelTK.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelTK.setText("Tài Khoản");

        lblSoTaiKhoan.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoTaiKhoan.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/profile (1).png"))); // NOI18N

        javax.swing.GroupLayout panelTaiKhoanLayout = new javax.swing.GroupLayout(panelTaiKhoan);
        panelTaiKhoan.setLayout(panelTaiKhoanLayout);
        panelTaiKhoanLayout.setHorizontalGroup(
            panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTaiKhoanLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addGroup(panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelTK)
                    .addComponent(lblSoTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelTaiKhoanLayout.setVerticalGroup(
            panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTaiKhoanLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG)
                    .addGroup(panelTaiKhoanLayout.createSequentialGroup()
                        .addComponent(lblSoTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTK)))
                .addContainerGap())
        );

        gridPanel1.add(panelTaiKhoan);

        panelPhanKhu.setBackground(new java.awt.Color(240, 240, 240));
        panelPhanKhu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelPhanKhu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPhanKhuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPhanKhuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelPhanKhuMouseExited(evt);
            }
        });

        labelPK.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelPK.setText("Phân Khu");

        lblSoPhanKhu.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoPhanKhu.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/maps-and-location.png"))); // NOI18N

        javax.swing.GroupLayout panelPhanKhuLayout = new javax.swing.GroupLayout(panelPhanKhu);
        panelPhanKhu.setLayout(panelPhanKhuLayout);
        panelPhanKhuLayout.setHorizontalGroup(
            panelPhanKhuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPhanKhuLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(panelPhanKhuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelPK)
                    .addComponent(lblSoPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelPhanKhuLayout.setVerticalGroup(
            panelPhanKhuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPhanKhuLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelPhanKhuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG1)
                    .addGroup(panelPhanKhuLayout.createSequentialGroup()
                        .addComponent(lblSoPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPK)))
                .addContainerGap())
        );

        gridPanel1.add(panelPhanKhu);

        panelTroChoi.setBackground(new java.awt.Color(240, 240, 240));
        panelTroChoi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelTroChoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTroChoiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelTroChoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelTroChoiMouseExited(evt);
            }
        });

        labelTT.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelTT.setText("Trò Chơi");

        lblSoTroChoi.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoTroChoi.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/board-game_1.png"))); // NOI18N

        javax.swing.GroupLayout panelTroChoiLayout = new javax.swing.GroupLayout(panelTroChoi);
        panelTroChoi.setLayout(panelTroChoiLayout);
        panelTroChoiLayout.setHorizontalGroup(
            panelTroChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTroChoiLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(panelTroChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelTT)
                    .addComponent(lblSoTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelTroChoiLayout.setVerticalGroup(
            panelTroChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTroChoiLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelTroChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG2)
                    .addGroup(panelTroChoiLayout.createSequentialGroup()
                        .addComponent(lblSoTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTT)))
                .addContainerGap())
        );

        gridPanel1.add(panelTroChoi);

        gridPanel2.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

        panelThietBi.setBackground(new java.awt.Color(240, 240, 240));
        panelThietBi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelThietBi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelThietBiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelThietBiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelThietBiMouseExited(evt);
            }
        });

        labelTB.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelTB.setText("Thiết Bị");

        lblSoThietBi.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoThietBi.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/mouse.png"))); // NOI18N

        javax.swing.GroupLayout panelThietBiLayout = new javax.swing.GroupLayout(panelThietBi);
        panelThietBi.setLayout(panelThietBiLayout);
        panelThietBiLayout.setHorizontalGroup(
            panelThietBiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThietBiLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(panelThietBiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelTB)
                    .addComponent(lblSoThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelThietBiLayout.setVerticalGroup(
            panelThietBiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThietBiLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelThietBiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG3)
                    .addGroup(panelThietBiLayout.createSequentialGroup()
                        .addComponent(lblSoThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTB)))
                .addContainerGap())
        );

        gridPanel2.add(panelThietBi);

        panelSuKien.setBackground(new java.awt.Color(240, 240, 240));
        panelSuKien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelSuKien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelSuKienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelSuKienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelSuKienMouseExited(evt);
            }
        });

        labelSK.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelSK.setText("Sự Kiện");

        lblSoSuKien.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoSuKien.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/event-list.png"))); // NOI18N

        javax.swing.GroupLayout panelSuKienLayout = new javax.swing.GroupLayout(panelSuKien);
        panelSuKien.setLayout(panelSuKienLayout);
        panelSuKienLayout.setHorizontalGroup(
            panelSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuKienLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(panelSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelSK)
                    .addComponent(lblSoSuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelSuKienLayout.setVerticalGroup(
            panelSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuKienLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG4)
                    .addGroup(panelSuKienLayout.createSequentialGroup()
                        .addComponent(lblSoSuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSK)))
                .addContainerGap())
        );

        gridPanel2.add(panelSuKien);

        panelNhanVien.setBackground(new java.awt.Color(240, 240, 240));
        panelNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseExited(evt);
            }
        });

        labelNV.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelNV.setText("Nhân Viên");

        lblSoNhanVien.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoNhanVien.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/id.png"))); // NOI18N

        javax.swing.GroupLayout panelNhanVienLayout = new javax.swing.GroupLayout(panelNhanVien);
        panelNhanVien.setLayout(panelNhanVienLayout);
        panelNhanVienLayout.setHorizontalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhanVienLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblIMG5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelNV)
                    .addComponent(lblSoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        panelNhanVienLayout.setVerticalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhanVienLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG5)
                    .addGroup(panelNhanVienLayout.createSequentialGroup()
                        .addComponent(lblSoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNV)))
                .addContainerGap())
        );

        gridPanel2.add(panelNhanVien);

        gridPanel3.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

        panelKhachHang.setBackground(new java.awt.Color(240, 240, 240));
        panelKhachHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelKhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelKhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelKhachHangMouseExited(evt);
            }
        });

        labelKH.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelKH.setText("Khách Hàng");

        lblSoKhachHang.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoKhachHang.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/customer-service.png"))); // NOI18N

        javax.swing.GroupLayout panelKhachHangLayout = new javax.swing.GroupLayout(panelKhachHang);
        panelKhachHang.setLayout(panelKhachHangLayout);
        panelKhachHangLayout.setHorizontalGroup(
            panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKhachHangLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblIMG6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelKH)
                    .addComponent(lblSoKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        panelKhachHangLayout.setVerticalGroup(
            panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKhachHangLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIMG6)
                    .addGroup(panelKhachHangLayout.createSequentialGroup()
                        .addComponent(lblSoKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelKH)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        gridPanel3.add(panelKhachHang);

        panelGuiDo.setBackground(new java.awt.Color(240, 240, 240));
        panelGuiDo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelGuiDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGuiDoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelGuiDoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelGuiDoMouseExited(evt);
            }
        });

        labelGD.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelGD.setText("Gửi Đồ");

        lblSoGuiDo.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoGuiDo.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/school-bag.png"))); // NOI18N

        javax.swing.GroupLayout panelGuiDoLayout = new javax.swing.GroupLayout(panelGuiDo);
        panelGuiDo.setLayout(panelGuiDoLayout);
        panelGuiDoLayout.setHorizontalGroup(
            panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGuiDoLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                    .addGroup(panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelGD)
                        .addComponent(lblSoGuiDo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelGuiDoLayout.setVerticalGroup(
            panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGuiDoLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelGuiDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG7)
                        .addGroup(panelGuiDoLayout.createSequentialGroup()
                            .addComponent(lblSoGuiDo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelGD)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel3.add(panelGuiDo);

        panelVe.setBackground(new java.awt.Color(240, 240, 240));
        panelVe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelVeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelVeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelVeMouseExited(evt);
            }
        });

        labelVE.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelVE.setText("Vé");

        lblSoVe.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoVe.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/tickets.png"))); // NOI18N

        javax.swing.GroupLayout panelVeLayout = new javax.swing.GroupLayout(panelVe);
        panelVe.setLayout(panelVeLayout);
        panelVeLayout.setHorizontalGroup(
            panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelVeLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                    .addGroup(panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelVE)
                        .addComponent(lblSoVe, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelVeLayout.setVerticalGroup(
            panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelVeLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG8)
                        .addGroup(panelVeLayout.createSequentialGroup()
                            .addComponent(lblSoVe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelVE)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel3.add(panelVe);

        gridPanel4.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

        panelHoaDon.setBackground(new java.awt.Color(240, 240, 240));
        panelHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelHoaDonMouseExited(evt);
            }
        });

        labelHD.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelHD.setText("Hóa Đơn");

        lblSoHoaDon.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoHoaDon.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/bill_1.png"))); // NOI18N

        javax.swing.GroupLayout panelHoaDonLayout = new javax.swing.GroupLayout(panelHoaDon);
        panelHoaDon.setLayout(panelHoaDonLayout);
        panelHoaDonLayout.setHorizontalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                    .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelHD)
                        .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelHoaDonLayout.setVerticalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG9)
                        .addGroup(panelHoaDonLayout.createSequentialGroup()
                            .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelHD)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel4.add(panelHoaDon);

        panelDoAn.setBackground(new java.awt.Color(240, 240, 240));
        panelDoAn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoAnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoAnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoAnMouseExited(evt);
            }
        });

        labelDA.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelDA.setText("Đồ Ăn");

        lblSoDoAn.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoDoAn.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/burger.png"))); // NOI18N

        javax.swing.GroupLayout panelDoAnLayout = new javax.swing.GroupLayout(panelDoAn);
        panelDoAn.setLayout(panelDoAnLayout);
        panelDoAnLayout.setHorizontalGroup(
            panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDoAnLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG10)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                    .addGroup(panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelDA)
                        .addComponent(lblSoDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelDoAnLayout.setVerticalGroup(
            panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDoAnLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG10)
                        .addGroup(panelDoAnLayout.createSequentialGroup()
                            .addComponent(lblSoDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelDA)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel4.add(panelDoAn);

        panelDoLuuNiem.setBackground(new java.awt.Color(240, 240, 240));
        panelDoLuuNiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelDoLuuNiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoLuuNiemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoLuuNiemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoLuuNiemMouseExited(evt);
            }
        });

        labelDLN.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelDLN.setText("Đồ Lưu Niệm");

        lblSoDoLuuNiem.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoDoLuuNiem.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/gift-bag.png"))); // NOI18N

        javax.swing.GroupLayout panelDoLuuNiemLayout = new javax.swing.GroupLayout(panelDoLuuNiem);
        panelDoLuuNiem.setLayout(panelDoLuuNiemLayout);
        panelDoLuuNiemLayout.setHorizontalGroup(
            panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDoLuuNiemLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG11)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                    .addGroup(panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelDLN)
                        .addComponent(lblSoDoLuuNiem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelDoLuuNiemLayout.setVerticalGroup(
            panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDoLuuNiemLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelDoLuuNiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG11)
                        .addGroup(panelDoLuuNiemLayout.createSequentialGroup()
                            .addComponent(lblSoDoLuuNiem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelDLN)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel4.add(panelDoLuuNiem);

        gridPanel6.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

        panelHoaDonDoAn.setBackground(new java.awt.Color(240, 240, 240));
        panelHoaDonDoAn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelHoaDonDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHoaDonDoAnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHoaDonDoAnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelHoaDonDoAnMouseExited(evt);
            }
        });

        labelHD2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelHD2.setText("Hóa Đơn ĐA");

        lblSoHoaDonDoAn.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblSoHoaDonDoAn.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/restaurant.png"))); // NOI18N

        javax.swing.GroupLayout panelHoaDonDoAnLayout = new javax.swing.GroupLayout(panelHoaDonDoAn);
        panelHoaDonDoAn.setLayout(panelHoaDonDoAnLayout);
        panelHoaDonDoAnLayout.setHorizontalGroup(
            panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
            .addGroup(panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonDoAnLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG13)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                    .addGroup(panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelHD2)
                        .addComponent(lblSoHoaDonDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(28, 28, 28)))
        );
        panelHoaDonDoAnLayout.setVerticalGroup(
            panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonDoAnLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelHoaDonDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG13)
                        .addGroup(panelHoaDonDoAnLayout.createSequentialGroup()
                            .addComponent(lblSoHoaDonDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelHD2)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        gridPanel6.add(panelHoaDonDoAn);

        panelDoanhThuVe.setBackground(new java.awt.Color(240, 240, 240));
        panelDoanhThuVe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelDoanhThuVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoanhThuVeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoanhThuVeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoanhThuVeMouseExited(evt);
            }
        });

        labelDA1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelDA1.setText("Doanh thu vé");

        lblDoanhThuVe.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        lblDoanhThuVe.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/tickets_2.png"))); // NOI18N

        javax.swing.GroupLayout panelDoanhThuVeLayout = new javax.swing.GroupLayout(panelDoanhThuVe);
        panelDoanhThuVe.setLayout(panelDoanhThuVeLayout);
        panelDoanhThuVeLayout.setHorizontalGroup(
            panelDoanhThuVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoanhThuVeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIMG14)
                .addGap(52, 52, 52)
                .addComponent(labelDA1)
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuVeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDoanhThuVe, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelDoanhThuVeLayout.setVerticalGroup(
            panelDoanhThuVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoanhThuVeLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblDoanhThuVe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDoanhThuVeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIMG14)
                    .addComponent(labelDA1))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        gridPanel6.add(panelDoanhThuVe);

        panelDoanhThuDoAn.setBackground(new java.awt.Color(240, 240, 240));
        panelDoanhThuDoAn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelDoanhThuDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoanhThuDoAnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoanhThuDoAnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoanhThuDoAnMouseExited(evt);
            }
        });

        labelDLN1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelDLN1.setText("Doanh thu đồ ăn");

        lblDoanhThuDoAn.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        lblDoanhThuDoAn.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/booth.png"))); // NOI18N

        javax.swing.GroupLayout panelDoanhThuDoAnLayout = new javax.swing.GroupLayout(panelDoanhThuDoAn);
        panelDoanhThuDoAn.setLayout(panelDoanhThuDoAnLayout);
        panelDoanhThuDoAnLayout.setHorizontalGroup(
            panelDoanhThuDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuDoAnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIMG15)
                .addGap(18, 18, 18)
                .addComponent(labelDLN1)
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(panelDoanhThuDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuDoAnLayout.createSequentialGroup()
                    .addContainerGap(99, Short.MAX_VALUE)
                    .addComponent(lblDoanhThuDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        panelDoanhThuDoAnLayout.setVerticalGroup(
            panelDoanhThuDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDoanhThuDoAnLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(panelDoanhThuDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIMG15)
                    .addComponent(labelDLN1))
                .addContainerGap())
            .addGroup(panelDoanhThuDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDoanhThuDoAnLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(lblDoanhThuDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        gridPanel6.add(panelDoanhThuDoAn);

        panelTongDoanhThu.setBackground(new java.awt.Color(240, 240, 240));
        panelTongDoanhThu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panelTongDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTongDoanhThuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelTongDoanhThuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelTongDoanhThuMouseExited(evt);
            }
        });

        labelDA2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelDA2.setText("Tổng doanh thu");

        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(51, 153, 255));

        lblIMG16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/salary.png"))); // NOI18N

        javax.swing.GroupLayout panelTongDoanhThuLayout = new javax.swing.GroupLayout(panelTongDoanhThu);
        panelTongDoanhThu.setLayout(panelTongDoanhThuLayout);
        panelTongDoanhThuLayout.setHorizontalGroup(
            panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
            .addGroup(panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTongDoanhThuLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(lblIMG16)
                    .addGroup(panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTongDoanhThuLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 133, Short.MAX_VALUE)
                            .addComponent(labelDA2)
                            .addGap(28, 28, 28))
                        .addGroup(panelTongDoanhThuLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap()))))
        );
        panelTongDoanhThuLayout.setVerticalGroup(
            panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
            .addGroup(panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTongDoanhThuLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(panelTongDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIMG16)
                        .addGroup(panelTongDoanhThuLayout.createSequentialGroup()
                            .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelDA2)))
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gridPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(gridPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(gridPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(gridPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(gridPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(gridPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gridPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(gridPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gridPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gridPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void panelPhanKhuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhanKhuMouseClicked
        // TODO add your handling code here:
        chiTietPhanKhu formCTPK = new chiTietPhanKhu();
        formCTPK.loadPhanKhuCards();
        formCTPK.setVisible(true);
    }//GEN-LAST:event_panelPhanKhuMouseClicked

    private void panelPhanKhuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhanKhuMouseEntered
        // TODO add your handling code here:
        panelPhanKhu.setBackground(new Color(210, 210, 210));
        panelPhanKhu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelPhanKhuMouseEntered

    private void panelPhanKhuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhanKhuMouseExited
        // TODO add your handling code here:
        panelPhanKhu.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelPhanKhu.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelPhanKhuMouseExited

    private void panelTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTaiKhoanMouseClicked
        // TODO add your handling code here:
        chiTietTaiKhoan formCTTK = new chiTietTaiKhoan();
        formCTTK.setVisible(true);
    }//GEN-LAST:event_panelTaiKhoanMouseClicked

    private void panelTroChoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTroChoiMouseClicked
        // TODO add your handling code here:
        chiTietTroChoi formCTTC = new chiTietTroChoi();
        formCTTC.loadTroChoiCards();
        formCTTC.setVisible(true);
    }//GEN-LAST:event_panelTroChoiMouseClicked

    private void panelTroChoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTroChoiMouseEntered
        // TODO add your handling code here:
        panelTroChoi.setBackground(new Color(210, 210, 210));
        panelTroChoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelTroChoiMouseEntered

    private void panelTroChoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTroChoiMouseExited
        // TODO add your handling code here:
        panelTroChoi.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelTroChoi.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelTroChoiMouseExited

    private void panelTaiKhoanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTaiKhoanMouseEntered
        // TODO add your handling code here:
        panelTaiKhoan.setBackground(new Color(210, 210, 210));
        panelTaiKhoan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelTaiKhoanMouseEntered

    private void panelTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTaiKhoanMouseExited
        // TODO add your handling code here:
        panelTaiKhoan.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelTaiKhoan.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelTaiKhoanMouseExited

    private void panelThietBiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThietBiMouseClicked
        // TODO add your handling code here:
        chiTietThietBi formCTTB = new chiTietThietBi();
        formCTTB.loadTroChoiComboBox();
        formCTTB.setVisible(true);
    }//GEN-LAST:event_panelThietBiMouseClicked

    private void panelThietBiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThietBiMouseEntered
        // TODO add your handling code here:
        panelThietBi.setBackground(new Color(210, 210, 210));
        panelThietBi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelThietBiMouseEntered

    private void panelThietBiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThietBiMouseExited
        // TODO add your handling code here:
        panelThietBi.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelThietBi.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelThietBiMouseExited

    private void panelSuKienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSuKienMouseClicked
        // TODO add your handling code here:
        chiTietSuKien formCTSK = new chiTietSuKien();
        formCTSK.setVisible(true);
    }//GEN-LAST:event_panelSuKienMouseClicked

    private void panelSuKienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSuKienMouseEntered
        // TODO add your handling code here:
        panelSuKien.setBackground(new Color(210, 210, 210));
        panelSuKien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelSuKienMouseEntered

    private void panelSuKienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSuKienMouseExited
        // TODO add your handling code here:
        panelSuKien.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelSuKien.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelSuKienMouseExited

    private void panelNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseClicked
        // TODO add your handling code here:
        chiTietNhanVien formCTNV = new chiTietNhanVien();
        formCTNV.setVisible(true);
    }//GEN-LAST:event_panelNhanVienMouseClicked

    private void panelNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseEntered
        // TODO add your handling code here:
        panelNhanVien.setBackground(new Color(210, 210, 210));
        panelNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelNhanVienMouseEntered

    private void panelNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseExited
        // TODO add your handling code here:
        panelNhanVien.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelNhanVien.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelNhanVienMouseExited

    private void panelKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelKhachHangMouseClicked
        // TODO add your handling code here:
        chiTietKhachHang formCTKH = new chiTietKhachHang();
        formCTKH.setVisible(true);
    }//GEN-LAST:event_panelKhachHangMouseClicked

    private void panelKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelKhachHangMouseEntered
        // TODO add your handling code here:
        panelKhachHang.setBackground(new Color(210, 210, 210));
        panelKhachHang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelKhachHangMouseEntered

    private void panelKhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelKhachHangMouseExited
        // TODO add your handling code here:
        panelKhachHang.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelKhachHang.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelKhachHangMouseExited

    private void panelGuiDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuiDoMouseClicked
        // TODO add your handling code here:
        chiTietGuiDo formCTGD = new chiTietGuiDo();
        formCTGD.setVisible(true);
    }//GEN-LAST:event_panelGuiDoMouseClicked

    private void panelGuiDoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuiDoMouseEntered
        // TODO add your handling code here:
        panelGuiDo.setBackground(new Color(210, 210, 210));
        panelGuiDo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelGuiDoMouseEntered

    private void panelGuiDoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuiDoMouseExited
        // TODO add your handling code here:
        panelGuiDo.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelGuiDo.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelGuiDoMouseExited

    private void panelVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelVeMouseClicked
        // TODO add your handling code here:
        chiTietVe formCTVE = new chiTietVe();
        formCTVE.setVisible(true);
    }//GEN-LAST:event_panelVeMouseClicked

    private void panelVeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelVeMouseEntered
        // TODO add your handling code here:
        panelVe.setBackground(new Color(210, 210, 210));
        panelVe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelVeMouseEntered

    private void panelVeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelVeMouseExited
        // TODO add your handling code here:
        panelVe.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelVe.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelVeMouseExited

    private void panelHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonMouseClicked
        // TODO add your handling code here:
        chiTietHoaDon formCTHD = new chiTietHoaDon();
        formCTHD.setVisible(true);
    }//GEN-LAST:event_panelHoaDonMouseClicked

    private void panelHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonMouseEntered
        // TODO add your handling code here:
        panelHoaDon.setBackground(new Color(210, 210, 210));
        panelHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelHoaDonMouseEntered

    private void panelHoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonMouseExited
        // TODO add your handling code here:
        panelHoaDon.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelHoaDon.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelHoaDonMouseExited

    private void panelDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoAnMouseClicked
        // TODO add your handling code here:
        chiTietDoAn formCTDA = new chiTietDoAn();
        formCTDA.setVisible(true);
    }//GEN-LAST:event_panelDoAnMouseClicked

    private void panelDoAnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoAnMouseEntered
        // TODO add your handling code here:
        panelDoAn.setBackground(new Color(210, 210, 210));
        panelDoAn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelDoAnMouseEntered

    private void panelDoAnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoAnMouseExited
        // TODO add your handling code here:
        panelDoAn.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelDoAn.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelDoAnMouseExited

    private void panelDoLuuNiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoLuuNiemMouseClicked
        // TODO add your handling code here:
        chiTietDoLuuNiem formCTDLN = new chiTietDoLuuNiem();
        formCTDLN.setVisible(true);
    }//GEN-LAST:event_panelDoLuuNiemMouseClicked

    private void panelDoLuuNiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoLuuNiemMouseEntered
        // TODO add your handling code here:
        panelDoLuuNiem.setBackground(new Color(210, 210, 210));
        panelDoLuuNiem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelDoLuuNiemMouseEntered

    private void panelDoLuuNiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoLuuNiemMouseExited
        // TODO add your handling code here:
        panelDoLuuNiem.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelDoLuuNiem.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelDoLuuNiemMouseExited

    private void panelHoaDonDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonDoAnMouseClicked
        // TODO add your handling code here:
        chiTietHoaDonDoAn formCTHDDA = new chiTietHoaDonDoAn();
        formCTHDDA.setVisible(true);
    }//GEN-LAST:event_panelHoaDonDoAnMouseClicked

    private void panelHoaDonDoAnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonDoAnMouseEntered
        // TODO add your handling code here:
        panelHoaDonDoAn.setBackground(new Color(210, 210, 210));
        panelHoaDonDoAn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelHoaDonDoAnMouseEntered

    private void panelHoaDonDoAnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHoaDonDoAnMouseExited
        // TODO add your handling code here:
        panelHoaDonDoAn.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelHoaDonDoAn.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelHoaDonDoAnMouseExited

    private void panelDoanhThuVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuVeMouseClicked
        // TODO add your handling code here:
        chiTietDoanhThuVe formCTDTVE = new chiTietDoanhThuVe();
        formCTDTVE.setVisible(true);
    }//GEN-LAST:event_panelDoanhThuVeMouseClicked

    private void panelDoanhThuVeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuVeMouseEntered
        // TODO add your handling code here:
        panelDoanhThuVe.setBackground(new Color(210, 210, 210));
        panelDoanhThuVe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelDoanhThuVeMouseEntered

    private void panelDoanhThuVeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuVeMouseExited
        // TODO add your handling code here:
        panelDoanhThuVe.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelDoanhThuVe.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelDoanhThuVeMouseExited

    private void panelDoanhThuDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuDoAnMouseClicked
        // TODO add your handling code here:
        chiTietDoanhThuDoAn formCTDTDA = new chiTietDoanhThuDoAn();
        formCTDTDA.setVisible(true);
    }//GEN-LAST:event_panelDoanhThuDoAnMouseClicked

    private void panelDoanhThuDoAnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuDoAnMouseEntered
        // TODO add your handling code here:
        panelDoanhThuDoAn.setBackground(new Color(210, 210, 210));
        panelDoanhThuDoAn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelDoanhThuDoAnMouseEntered

    private void panelDoanhThuDoAnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuDoAnMouseExited
        // TODO add your handling code here:
        panelDoanhThuDoAn.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelDoanhThuDoAn.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelDoanhThuDoAnMouseExited

    private void panelTongDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTongDoanhThuMouseClicked
        // TODO add your handling code here:
        chiTietTongDoanhThu formCTTDT = new chiTietTongDoanhThu();
        formCTTDT.setVisible(true);
    }//GEN-LAST:event_panelTongDoanhThuMouseClicked

    private void panelTongDoanhThuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTongDoanhThuMouseEntered
        // TODO add your handling code here:
        panelTongDoanhThu.setBackground(new Color(210, 210, 210));
        panelTongDoanhThu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelTongDoanhThuMouseEntered

    private void panelTongDoanhThuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTongDoanhThuMouseExited
        // TODO add your handling code here:
        panelTongDoanhThu.setBackground(new Color(240, 240, 240)); // màu gốc nhạt
        panelTongDoanhThu.setCursor(Cursor.getDefaultCursor()); // con trỏ chuột bình thường
    }//GEN-LAST:event_panelTongDoanhThuMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gridPanel1;
    private javax.swing.JPanel gridPanel2;
    private javax.swing.JPanel gridPanel3;
    private javax.swing.JPanel gridPanel4;
    private javax.swing.JPanel gridPanel6;
    private javax.swing.JLabel labelDA;
    private javax.swing.JLabel labelDA1;
    private javax.swing.JLabel labelDA2;
    private javax.swing.JLabel labelDLN;
    private javax.swing.JLabel labelDLN1;
    private javax.swing.JLabel labelGD;
    private javax.swing.JLabel labelHD;
    private javax.swing.JLabel labelHD2;
    private javax.swing.JLabel labelKH;
    private javax.swing.JLabel labelNV;
    private javax.swing.JLabel labelPK;
    private javax.swing.JLabel labelSK;
    private javax.swing.JLabel labelTB;
    private javax.swing.JLabel labelTK;
    private javax.swing.JLabel labelTT;
    private javax.swing.JLabel labelVE;
    private javax.swing.JLabel lblDoanhThuDoAn;
    private javax.swing.JLabel lblDoanhThuVe;
    private javax.swing.JLabel lblIMG;
    private javax.swing.JLabel lblIMG1;
    private javax.swing.JLabel lblIMG10;
    private javax.swing.JLabel lblIMG11;
    private javax.swing.JLabel lblIMG13;
    private javax.swing.JLabel lblIMG14;
    private javax.swing.JLabel lblIMG15;
    private javax.swing.JLabel lblIMG16;
    private javax.swing.JLabel lblIMG2;
    private javax.swing.JLabel lblIMG3;
    private javax.swing.JLabel lblIMG4;
    private javax.swing.JLabel lblIMG5;
    private javax.swing.JLabel lblIMG6;
    private javax.swing.JLabel lblIMG7;
    private javax.swing.JLabel lblIMG8;
    private javax.swing.JLabel lblIMG9;
    private javax.swing.JLabel lblSoDoAn;
    private javax.swing.JLabel lblSoDoLuuNiem;
    private javax.swing.JLabel lblSoGuiDo;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblSoHoaDonDoAn;
    private javax.swing.JLabel lblSoKhachHang;
    private javax.swing.JLabel lblSoNhanVien;
    private javax.swing.JLabel lblSoPhanKhu;
    private javax.swing.JLabel lblSoSuKien;
    private javax.swing.JLabel lblSoTaiKhoan;
    private javax.swing.JLabel lblSoThietBi;
    private javax.swing.JLabel lblSoTroChoi;
    private javax.swing.JLabel lblSoVe;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JPanel panelDoAn;
    private javax.swing.JPanel panelDoLuuNiem;
    private javax.swing.JPanel panelDoanhThuDoAn;
    private javax.swing.JPanel panelDoanhThuVe;
    private javax.swing.JPanel panelGuiDo;
    private javax.swing.JPanel panelHoaDon;
    private javax.swing.JPanel panelHoaDonDoAn;
    private javax.swing.JPanel panelKhachHang;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhanKhu;
    private javax.swing.JPanel panelSuKien;
    private javax.swing.JPanel panelTaiKhoan;
    private javax.swing.JPanel panelThietBi;
    private javax.swing.JPanel panelTongDoanhThu;
    private javax.swing.JPanel panelTroChoi;
    private javax.swing.JPanel panelVe;
    // End of variables declaration//GEN-END:variables
}
