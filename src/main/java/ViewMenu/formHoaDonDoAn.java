/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import Model.taiKhoan;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class formHoaDonDoAn extends javax.swing.JPanel {

    private boolean isSuaChiTiet = false;
    private HashMap<String, String> doAnMap = new HashMap<>(); // Ánh xạ tenDoAn -> maDoAn
    private HashMap<String, String> nhanVienMap = new HashMap<>(); // Ánh xạ tenNhanVien -> maNhanVien
    private int editingRow = -1; // -1 là không sửa
    private taiKhoan taiKhoanDangNhap;

    /**
     * Creates new form formHoaDonDoAn
     */
    public formHoaDonDoAn(taiKhoan taiKhoanDangNhap) {
        this.taiKhoanDangNhap = taiKhoanDangNhap;
        initComponents();
        loadHoaDon();
        loadCboMaDoAn();
        loadCboNhanVien();
    }

    private void loadHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);

        String sql;
        PreparedStatement pstmt = null;

        try (Connection conn = ConnectDB.getConnection()) {
            if (taiKhoanDangNhap.getLoaiTaiKhoan().equalsIgnoreCase("Admin")) {
                // Admin: hiển thị toàn bộ hóa đơn
                sql = "SELECT * FROM hoaDonDoAn";
                pstmt = conn.prepareStatement(sql);
            } else {
                // Nhân viên: chỉ hiển thị hóa đơn của mình
                sql = "SELECT * FROM hoaDonDoAn WHERE maNhanVien = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, taiKhoanDangNhap.getMaNhanVien());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHoaDon");
                    String maNV = rs.getString("maNhanVien");
                    Timestamp ngayMua = rs.getTimestamp("ngayMua");
                    double tongTien = rs.getDouble("tongTien");

                    model.addRow(new Object[]{maHD, maNV, ngayMua, tongTien});
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadCboMaDoAn() {
        try (Connection conn = ConnectDB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maDoAn, tenDoAn FROM banDoAn")) {

            cboTenDoAn.removeAllItems();
            doAnMap.clear();
            while (rs.next()) {
                String maDoAn = rs.getString("maDoAn");
                String tenDoAn = rs.getString("tenDoAn");
                cboTenDoAn.addItem(tenDoAn);
                doAnMap.put(tenDoAn, maDoAn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách món ăn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadCboNhanVien() {
        try {
            Connection conn = ConnectDB.getConnection();
            cboTenNhanVien.removeAllItems();

            if (taiKhoanDangNhap.getLoaiTaiKhoan().equalsIgnoreCase("Admin")) {
                // Load danh sách nhân viên có chức vụ "Nhân viên bán hàng" cho Admin
                String SQL = "SELECT * FROM NhanVien WHERE chucVu = N'Nhân viên bán hàng'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                while (rs.next()) {
                    String tenNhanVien = rs.getString("tenNhanVien");
                    String maNhanVien = rs.getString("maNhanVien");
                    cboTenNhanVien.addItem(tenNhanVien);
                    nhanVienMap.put(tenNhanVien, maNhanVien);
                }
            } else {
                // Load tên nhân viên tương ứng với tài khoản User
                String SQL = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, taiKhoanDangNhap.getMaNhanVien());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String tenNhanVien = rs.getString("tenNhanVien");
                    String maNhanVien = rs.getString("maNhanVien");
                    cboTenNhanVien.addItem(tenNhanVien);
                    nhanVienMap.put(tenNhanVien, maNhanVien);
                    cboTenNhanVien.setSelectedIndex(0); // Chọn tên nhân viên duy nhất
                    cboTenNhanVien.setEnabled(false); // Vô hiệu hóa combo box
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tên nhân viên");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }

    private void loadChiTietHoaDon(String maHoaDon) {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM chiTietHoaDonDoAn WHERE maHoaDon = ?")) {

            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHoaDon");
                String maDoAn = rs.getString("maDoAn");
                String tenDoAn = rs.getString("tenDoAn");
                int soLuong = rs.getInt("soLuong");
                model.addRow(new Object[]{maHD, maDoAn, tenDoAn, soLuong});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void LamMoiBangHoaDon() {
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "SELECT * FROM hoaDonDoAn";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("maHoaDon"),
                    rs.getString("maNhanVien"),
                    rs.getTimestamp("ngayMua"),
                    rs.getFloat("tongTien")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới bảng hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        headerPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();
        headerImgae = new javax.swing.JLabel();
        informationPanel = new javax.swing.JPanel();
        labelMaNV = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelMaHD = new javax.swing.JLabel();
        labelLoaive = new javax.swing.JLabel();
        cboTenDoAn = new javax.swing.JComboBox<>();
        labelMaVe = new javax.swing.JLabel();
        labelNgayMua = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        btnCapNhap = new javax.swing.JButton();
        cboTenNhanVien = new javax.swing.JComboBox<>();
        dtpThoigianNgayMua = new com.github.lgooddatepicker.components.DateTimePicker();
        panelHoaDon = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panelCTHD = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ HÓA ĐƠN ĐỒ ĂN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/bill.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerImgae)
                .addGap(206, 206, 206))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae)
                    .addComponent(headerLabel))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        informationPanel.setBackground(new java.awt.Color(204, 204, 204));

        labelMaNV.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaNV.setText("Tên nhân viên:");

        btnThem.setBackground(new java.awt.Color(76, 175, 80));
        btnThem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-add-25.png"))); // NOI18N
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(76, 175, 80));
        btnSua.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-pencil-25.png"))); // NOI18N
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setBackground(new java.awt.Color(76, 175, 80));
        btnXuatDuLieu.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXuatDuLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-xls-export-25.png"))); // NOI18N
        btnXuatDuLieu.setText("XUẤT DỮ LIỆU");
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(76, 175, 80));
        btnLamMoi.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-update-25.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        labelTimKiem.setBackground(new java.awt.Color(51, 153, 255));
        labelTimKiem.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        labelTimKiem.setForeground(new java.awt.Color(51, 153, 255));
        labelTimKiem.setText("Tìm kiếm:");

        btnXoa.setBackground(new java.awt.Color(76, 175, 80));
        btnXoa.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-delete-25.png"))); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(76, 175, 80));
        btnTimKiem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-search-25.png"))); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/payment.png"))); // NOI18N

        labelMaHD.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaHD.setText("Mã hóa đơn:");

        labelLoaive.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelLoaive.setText("Số lượng:");

        cboTenDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTenDoAnMouseClicked(evt);
            }
        });

        labelMaVe.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaVe.setText("Tên đồ ăn:");

        labelNgayMua.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelNgayMua.setText("Ngày mua:");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        btnLuu.setBackground(new java.awt.Color(76, 175, 80));
        btnLuu.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-add-25.png"))); // NOI18N
        btnLuu.setText("LƯU");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnCapNhap.setBackground(new java.awt.Color(76, 175, 80));
        btnCapNhap.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnCapNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-pencil-25.png"))); // NOI18N
        btnCapNhap.setText("CẬP NHẬP");
        btnCapNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labelMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelMaVe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelNgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(labelLoaive))
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addComponent(cboTenDoAn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTenNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtpThoigianNgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(labelTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                                .addComponent(labelIMG)
                                .addGap(38, 38, 38))))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)
                        .addGap(12, 12, 12)
                        .addComponent(btnLuu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(19, 19, 19)
                        .addComponent(btnCapNhap)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(labelIMG))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtpThoigianNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaVe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTenDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelLoaive, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 29, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnLamMoi)
                    .addComponent(btnCapNhap))
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDuLieu)
                    .addComponent(btnTimKiem)
                    .addComponent(btnLuu)
                    .addComponent(btnSua))
                .addContainerGap())
        );

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Ngày mua", "Tổng tiền"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        panelHoaDon.setViewportView(tblHoaDon);

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã đồ ăn", "Tên đồ ăn", "Số lượng"
            }
        ));
        tblChiTietHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietHoaDonMouseClicked(evt);
            }
        });
        panelCTHD.setViewportView(tblChiTietHoaDon);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelHoaDon, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(panelCTHD)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboTenDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTenDoAnMouseClicked
        // TODO add your handling code here:
//        btnThem.setEnabled(true);
//        btnSua.setEnabled(false);
    }//GEN-LAST:event_cboTenDoAnMouseClicked

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
//        btnThem.setEnabled(false);
//        btnSua.setEnabled(true);
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        if (row >= 0) {
            String maHD = tblHoaDon.getValueAt(row, 0).toString();
            String maNV = tblHoaDon.getValueAt(row, 1).toString();
            Timestamp ngayMua = (Timestamp) tblHoaDon.getValueAt(row, 2);

            txtMaHoaDon.setText(maHD);

            String tenNhanVien = null;
            for (Map.Entry<String, String> entry : nhanVienMap.entrySet()) {
                if (entry.getValue().equals(maNV)) {
                    tenNhanVien = entry.getKey();
                    break;
                }
            }
            cboTenNhanVien.setSelectedItem(tenNhanVien);

            dtpThoigianNgayMua.setDateTimeStrict(LocalDateTime.ofInstant(ngayMua.toInstant(), ZoneId.systemDefault()));

            loadChiTietHoaDon(maHD);
            btnThem.setEnabled(false);
            btnSua.setEnabled(false);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        String maHoaDon = txtMaHoaDon.getText().trim();
        String tenDoAn = (String) cboTenDoAn.getSelectedItem();
        String soLuongStr = txtSoLuong.getText().trim();

        if (maHoaDon.isEmpty() || tenDoAn == null || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin chi tiết hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maDoAn = doAnMap.get(tenDoAn);
        if (maDoAn == null) {
            JOptionPane.showMessageDialog(this, "Món ăn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isTrungMaHD = false;
        DefaultTableModel modelHD = (DefaultTableModel) tblHoaDon.getModel();
        for (int i = 0; i < modelHD.getRowCount(); i++) {
            if (maHoaDon.equals(modelHD.getValueAt(i, 0))) {
                isTrungMaHD = true;
                break;
            }
        }

        if (!isSuaChiTiet && isTrungMaHD) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại! Vui lòng nhập mã khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelCT = (DefaultTableModel) tblChiTietHoaDon.getModel();
        for (int i = 0; i < modelCT.getRowCount(); i++) {
            String maHDTrongBangCT = modelCT.getValueAt(i, 0).toString();
            if (!maHDTrongBangCT.equals(maHoaDon)) {
                JOptionPane.showMessageDialog(this, "Tất cả chi tiết hóa đơn phải có cùng mã hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maDoAnTrongBang = modelCT.getValueAt(i, 1).toString();
            if (maDoAnTrongBang.equals(maDoAn)) {
                JOptionPane.showMessageDialog(this, "Mã đồ ăn đã tồn tại trong chi tiết hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Object[] rowData = {maHoaDon, maDoAn, tenDoAn, soLuong};
        modelCT.addRow(rowData);
        JOptionPane.showMessageDialog(this, "Đã thêm chi tiết hóa đơn!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        cboTenDoAn.setSelectedIndex(-1);
        txtSoLuong.setText("");
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (editingRow == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng chi tiết nào để sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tenDoAn = (String) cboTenDoAn.getSelectedItem();
        String soLuongText = txtSoLuong.getText().trim();

        if (tenDoAn == null || soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!soLuongText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maDoAn = doAnMap.get(tenDoAn);
        if (maDoAn == null) {
            JOptionPane.showMessageDialog(this, "Món ăn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setValueAt(maDoAn, editingRow, 1);
        model.setValueAt(tenDoAn, editingRow, 2);
        model.setValueAt(Integer.parseInt(soLuongText), editingRow, 3);

        JOptionPane.showMessageDialog(this, "Đã sửa tạm thời trên bảng. Nhấn 'CẬP NHẬT' để lưu vào CSDL.", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        btnCapNhap.setEnabled(true);
        btnSua.setEnabled(false);
        btnThem.setEnabled(true);
        editingRow = -1;
        txtMaHoaDon.setEnabled(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int rowChiTiet = tblChiTietHoaDon.getSelectedRow();
        int rowHoaDon = tblHoaDon.getSelectedRow();

        if (rowChiTiet != -1) {
            String maHoaDon = tblChiTietHoaDon.getValueAt(rowChiTiet, 0).toString();
            String maDoAn = tblChiTietHoaDon.getValueAt(rowChiTiet, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa chi tiết hóa đơn này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = ConnectDB.getConnection()) {
                    PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM chiTietHoaDonDoAn WHERE maHoaDon = ? AND maDoAn = ?");
                    ps.setString(1, maHoaDon);
                    ps.setString(2, maDoAn);
                    ps.executeUpdate();

                    DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
                    model.removeRow(rowChiTiet);

                    PreparedStatement psTong = conn.prepareStatement(
                            "UPDATE hoaDonDoAn SET tongTien = ("
                            + "SELECT ISNULL(SUM(c.soLuong * b.giaDoAn), 0) "
                            + "FROM chiTietHoaDonDoAn c JOIN banDoAn b ON c.maDoAn = b.maDoAn "
                            + "WHERE c.maHoaDon = ?) WHERE maHoaDon = ?");
                    psTong.setString(1, maHoaDon);
                    psTong.setString(2, maHoaDon);
                    psTong.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Đã xóa chi tiết hóa đơn thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa chi tiết hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (rowHoaDon != -1) {
            String maHoaDon = tblHoaDon.getValueAt(rowHoaDon, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa toàn bộ hóa đơn này (cùng chi tiết)?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = ConnectDB.getConnection()) {
                    PreparedStatement ps1 = conn.prepareStatement(
                            "DELETE FROM chiTietHoaDonDoAn WHERE maHoaDon = ?");
                    ps1.setString(1, maHoaDon);
                    ps1.executeUpdate();

                    PreparedStatement ps2 = conn.prepareStatement(
                            "DELETE FROM hoaDonDoAn WHERE maHoaDon = ?");
                    ps2.setString(1, maHoaDon);
                    ps2.executeUpdate();

                    DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
                    model.removeRow(rowHoaDon);

                    DefaultTableModel modelCT = (DefaultTableModel) tblChiTietHoaDon.getModel();
                    modelCT.setRowCount(0);

                    JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn và chi tiết liên quan.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa trong bảng hóa đơn hoặc chi tiết hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        String maHoaDon = txtMaHoaDon.getText().trim();
        String tenNhanVien = (String) cboTenNhanVien.getSelectedItem();
        LocalDateTime ngayMua = dtpThoigianNgayMua.getDateTimeStrict();

        if (maHoaDon.isEmpty() || tenNhanVien == null || ngayMua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maNhanVien = nhanVienMap.get(tenNhanVien);
        if (maNhanVien == null) {
            JOptionPane.showMessageDialog(this, "Nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp ngayMuaSQL = Timestamp.valueOf(ngayMua);

        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có chi tiết hóa đơn nào để lưu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConnectDB.getConnection()) {
            conn.setAutoCommit(false);

            for (int i = 0; i < model.getRowCount(); i++) {
                String maDoAn = model.getValueAt(i, 1).toString();
                int soLuongMua = Integer.parseInt(model.getValueAt(i, 3).toString());

                PreparedStatement psCheck = conn.prepareStatement(
                        "SELECT soLuong FROM banDoAn WHERE maDoAn = ?");
                psCheck.setString(1, maDoAn);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    int tonKho = rs.getInt("soLuong");
                    if (soLuongMua > tonKho) {
                        JOptionPane.showMessageDialog(this, "Không đủ tồn kho cho món: " + model.getValueAt(i, 2), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy món ăn: " + model.getValueAt(i, 2), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
                rs.close();
                psCheck.close();
            }

            double tongTien = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                String maDoAn = model.getValueAt(i, 1).toString();
                int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());

                PreparedStatement psGia = conn.prepareStatement(
                        "SELECT giaDoAn FROM banDoAn WHERE maDoAn = ?");
                psGia.setString(1, maDoAn);
                ResultSet rsGia = psGia.executeQuery();
                if (rsGia.next()) {
                    double gia = rsGia.getDouble("giaDoAn");
                    tongTien += gia * soLuong;
                }
                rsGia.close();
                psGia.close();
            }

            PreparedStatement psHD = conn.prepareStatement(
                    "INSERT INTO hoaDonDoAn (maHoaDon, maNhanVien, ngayMua, tongTien) VALUES (?, ?, ?, ?)");
            psHD.setString(1, maHoaDon);
            psHD.setString(2, maNhanVien);
            psHD.setTimestamp(3, ngayMuaSQL);
            psHD.setDouble(4, tongTien);
            psHD.executeUpdate();

            PreparedStatement psCT = conn.prepareStatement(
                    "INSERT INTO chiTietHoaDonDoAn (maHoaDon, maDoAn, tenDoAn, soLuong) VALUES (?, ?, ?, ?)");
            PreparedStatement psTruSL = conn.prepareStatement(
                    "UPDATE banDoAn SET soLuong = soLuong - ? WHERE maDoAn = ?");

            for (int i = 0; i < model.getRowCount(); i++) {
                String maDoAn = model.getValueAt(i, 1).toString();
                String tenDoAn = model.getValueAt(i, 2).toString();
                int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());

                psCT.setString(1, maHoaDon);
                psCT.setString(2, maDoAn);
                psCT.setString(3, tenDoAn);
                psCT.setInt(4, soLuong);
                psCT.addBatch();

                psTruSL.setInt(1, soLuong);
                psTruSL.setString(2, maDoAn);
                psTruSL.addBatch();
            }
            psCT.executeBatch();
            psTruSL.executeBatch();

            conn.commit();
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            model.setRowCount(0);
            loadHoaDon();
            txtMaHoaDon.setText("");
            dtpThoigianNgayMua.clear();
            txtSoLuong.setText("");
            cboTenDoAn.setSelectedIndex(-1);
            cboTenNhanVien.setSelectedIndex(-1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String keyword = txtTimKiem.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConnectDB.getConnection()) {
            String query = "SELECT * FROM hoaDonDoAn WHERE CONCAT(maHoaDon, ' ', maNhanVien) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);

            boolean found = false;
            while (rs.next()) {
                found = true;
                Object[] row = {
                    rs.getString("maHoaDon"),
                    rs.getString("maNhanVien"),
                    rs.getTimestamp("ngayMua"),
                    rs.getFloat("tongTien")
                };
                model.addRow(row);
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                LamMoiBangHoaDon();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        editingRow = -1;
        btnCapNhap.setEnabled(false);
        btnSua.setEnabled(false);
        btnThem.setEnabled(true);
        txtMaHoaDon.setEnabled(true);
        txtMaHoaDon.setText("");
        txtSoLuong.setText("");
        cboTenDoAn.setSelectedIndex(-1);
        cboTenNhanVien.setSelectedIndex(-1);
        LamMoiBangHoaDon();
        DefaultTableModel modelChiTiet = (DefaultTableModel) tblChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        JTable table = tblChiTietHoaDon;
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        fileChooser.setSelectedFile(new File("xuat_du_lieu.csv"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"))) {
                pw.write('\uFEFF');
                for (int i = 0; i < model.getColumnCount(); i++) {
                    pw.print(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) {
                        pw.print(",");
                    }
                }
                pw.println();
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        pw.print(value != null ? value.toString() : "");
                        if (j < model.getColumnCount() - 1) {
                            pw.print(",");
                        }
                    }
                    pw.println();
                }
                JOptionPane.showMessageDialog(this, "Đã xuất dữ liệu vào:\n" + file.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblChiTietHoaDon.getSelectedRow();
        if (row == -1) {
            return;
        }
        txtMaHoaDon.setText(tblChiTietHoaDon.getValueAt(row, 0).toString());
        String tenDoAn = tblChiTietHoaDon.getValueAt(row, 2).toString();
        cboTenDoAn.setSelectedItem(tenDoAn);
        txtSoLuong.setText(tblChiTietHoaDon.getValueAt(row, 3).toString());

        txtMaHoaDon.setEnabled(false);
        txtSoLuong.setEnabled(true);
        cboTenDoAn.setEnabled(true);

        btnThem.setEnabled(false);
        btnSua.setEnabled(true);

        editingRow = row;
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void btnCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapActionPerformed
        // TODO add your handling code here:
        try (Connection conn = ConnectDB.getConnection()) {
            DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
            int rowCount = model.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                String maHoaDon = model.getValueAt(i, 0).toString();
                String maDoAn = model.getValueAt(i, 1).toString();
                String tenDoAn = model.getValueAt(i, 2).toString();
                int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());

                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE chiTietHoaDonDoAn SET tenDoAn = ?, soLuong = ? WHERE maHoaDon = ? AND maDoAn = ?");
                ps.setString(1, tenDoAn);
                ps.setInt(2, soLuong);
                ps.setString(3, maHoaDon);
                ps.setString(4, maDoAn);
                ps.executeUpdate();
            }

            PreparedStatement psTong = conn.prepareStatement(
                    "UPDATE hoaDonDoAn SET tongTien = ("
                    + "SELECT SUM(c.soLuong * b.giaDoAn) "
                    + "FROM chiTietHoaDonDoAn c JOIN banDoAn b ON c.maDoAn = b.maDoAn "
                    + "WHERE c.maHoaDon = ?) WHERE maHoaDon = ?");
            String maHD = txtMaHoaDon.getText();
            psTong.setString(1, maHD);
            psTong.setString(2, maHD);
            psTong.executeUpdate();

            JOptionPane.showMessageDialog(this, "Dữ liệu đã được cập nhật vào CSDL.", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            btnCapNhap.setEnabled(false);
            btnSua.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCapNhapActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhap;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboTenDoAn;
    private javax.swing.JComboBox<String> cboTenNhanVien;
    private com.github.lgooddatepicker.components.DateTimePicker dtpThoigianNgayMua;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelLoaive;
    private javax.swing.JLabel labelMaHD;
    private javax.swing.JLabel labelMaNV;
    private javax.swing.JLabel labelMaVe;
    private javax.swing.JLabel labelNgayMua;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JScrollPane panelCTHD;
    private javax.swing.JScrollPane panelHoaDon;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
