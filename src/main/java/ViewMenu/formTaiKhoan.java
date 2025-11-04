/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import static Database.ConnectDB.getConnection;
import Model.phanKhu;
import Model.taiKhoan;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LENOVO
 */
public class formTaiKhoan extends javax.swing.JPanel {

    /**
     * Creates new form formTaiKhoan
     */
    public formTaiKhoan() {
        initComponents();
        txtMaTaiKhoan.setEnabled(false);
        radUser.setEnabled(false);
        radAdmin.setSelected(true);
        cboTenNhanVien.setSelectedItem(0);
        model = (DefaultTableModel) tblThongTinTaiKhoan.getModel();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
        loadTenNhanVienToComboBox();
    }

    DefaultTableModel model;
    private List<taiKhoan> danhsachtaikhoan = new ArrayList<>();

    public void loadDatatoArrayList() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.matKhau, tk.loaiTaiKhoan, tk.maNhanVien, nv.tenNhanVien "
                    + "FROM taiKhoan tk "
                    + "LEFT JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien";
            ResultSet rs = stmt.executeQuery(SQL);
            danhsachtaikhoan.clear();

            while (rs.next()) {
                String maTaiKhoan = rs.getString("maTaiKhoan");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                String loaiTaiKhoan = rs.getString("loaiTaiKhoan");
                String maNhanVien = rs.getString("maNhanVien");
                String tenNhanVien = rs.getString("tenNhanVien");

                taiKhoan tk = new taiKhoan(maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien, tenNhanVien);
                danhsachtaikhoan.add(tk);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "lỗi khi tải dữ liệu từ database!");
        }
    }

    public void loadDataArrayListtoTable() {
        model.setRowCount(0);
//      model = (DefaultTableModel) tblThongTinNhanVien.getModel();
        for (taiKhoan tk : danhsachtaikhoan) {
            model.addRow(new Object[]{
                tk.getMaTaiKhoan(), tk.getTenDangNhap(), tk.getMatKhau(), tk.getLoaiTaiKhoan(), tk.getTenNhanVien()});
        }
    }

    private void clearForm() {
        txtMaTaiKhoan.setText("");
        txtTenDangNhap.setText("");
        buttongrLoai.clearSelection();
        txtMatKhau.setText("");
        txtTimKiem.setText("");
        txtTenDangNhap.setEnabled(true);
        cboTenNhanVien.setSelectedIndex(0);
        cboTenNhanVien.setEnabled(true);
        radAdmin.setEnabled(true);
        radAdmin.setSelected(true);
        radUser.setEnabled(false);
        radUser.setSelected(false);
    }

    public void loadTenNhanVienToComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "SELECT tenNhanVien FROM NhanVien WHERE chucVu = N'Nhân viên bán hàng'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            cboTenNhanVien.removeAllItems();
            cboTenNhanVien.addItem("--Chọn tên nhân viên--"); // Thêm dòng mặc định
            while (rs.next()) {
                cboTenNhanVien.addItem(rs.getString("tenNhanVien"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tên nhân viên");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }

    public String getMaNhanVienFromTenNhanVien(String tenNhanVien) {
        String maNhanVien = "";
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maNhanVien FROM NhanVien WHERE tenNhanVien = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenNhanVien);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maNhanVien = rs.getString("maNhanVien");
            }
//            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã nhân viên: " + e.getMessage());
        }
        return maNhanVien;
    }

    public boolean isNhanVienDaCoTaiKhoan(String maNhanVien) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM taiKhoan WHERE maNhanVien = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maNhanVien);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                conn.close();
                return count > 0;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra tài khoản nhân viên!");
        }
        return false;
    }

    public boolean validateInput() {
        String maTaiKhoan = txtMaTaiKhoan.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = txtMatKhau.getText().trim();
        String loaiTaiKhoan = radAdmin.isSelected() ? "Admin" : radUser.isSelected() ? "User" : "";
//        String tenNhanVien = cbo.getSelectedItem().toString();

        // Kiểm tra rỗng
        if (tenDangNhap.isEmpty() || matKhau.isEmpty() | loaiTaiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
            return false;
        }
        //Kiểm tra trùng
        for (taiKhoan tk : danhsachtaikhoan) {
            if (tk.getTenDangNhap().equals(tenDangNhap)) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
                txtTenDangNhap.requestFocus();
                return false;
            }
            if (tk.getMaTaiKhoan().equals(maTaiKhoan)) {
                JOptionPane.showMessageDialog(this, "Mã tài khoản đã tồn tại!");
                txtMaTaiKhoan.requestFocus();
                return false;
            }
        }

        if (radUser.isSelected()) {
            String tenNhanVien = cboTenNhanVien.getSelectedItem().toString();
            if (tenNhanVien.equals("--Chọn tên nhân viên--")) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tên nhân viên.");
                return false;
            }

            String maNhanVien = getMaNhanVienFromTenNhanVien(tenNhanVien);
            if (isNhanVienDaCoTaiKhoan(maNhanVien)) {
                JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản!");
                return false;
            }
        }
        return true; //hợp lệ
    }

    public boolean validateInputSua() {
        String maTaiKhoan = txtMaTaiKhoan.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = txtMatKhau.getText().trim();
        String loaiTaiKhoan = radAdmin.isSelected() ? "Admin" : radUser.isSelected() ? "User" : "";

        // Kiểm tra rỗng
        if (tenDangNhap.isEmpty() || matKhau.isEmpty() | loaiTaiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
            return false;
        }
        return true; //hợp lệ
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttongrLoai = new javax.swing.ButtonGroup();
        headerPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();
        headerImgae = new javax.swing.JLabel();
        informationPanel = new javax.swing.JPanel();
        labelMaTK = new javax.swing.JLabel();
        labelTenDN = new javax.swing.JLabel();
        labelMatkhau = new javax.swing.JLabel();
        txtMaTaiKhoan = new javax.swing.JTextField();
        txtTenDangNhap = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelMatkhau1 = new javax.swing.JLabel();
        radAdmin = new javax.swing.JRadioButton();
        radUser = new javax.swing.JRadioButton();
        labelMatkhau2 = new javax.swing.JLabel();
        cboTenNhanVien = new javax.swing.JComboBox<>();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinTaiKhoan = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ TÀI KHOẢN ");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/verified-account.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerImgae)
                .addGap(213, 213, 213))
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

        labelMaTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaTK.setText("Mã tài khoản: ");

        labelTenDN.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenDN.setText("Tên đăng nhập: ");

        labelMatkhau.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMatkhau.setText("Mật khẩu: ");

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
        btnSua.setText("ĐỔI MẬT KHẨU ");
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

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

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

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/account.png"))); // NOI18N

        labelMatkhau1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMatkhau1.setText("Loại tài khoản:");

        buttongrLoai.add(radAdmin);
        radAdmin.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        radAdmin.setText("Admin");

        buttongrLoai.add(radUser);
        radUser.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        radUser.setText("User");

        labelMatkhau2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMatkhau2.setText("Tên nhân viên:");

        cboTenNhanVien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenNhanVienItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(informationPanelLayout.createSequentialGroup()
                            .addComponent(labelMaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(36, 36, 36)
                        .addComponent(btnSua)
                        .addGap(40, 40, 40)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(labelMatkhau1)
                        .addGap(18, 18, 18)
                        .addComponent(radAdmin)
                        .addGap(18, 18, 18)
                        .addComponent(radUser))
                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(informationPanelLayout.createSequentialGroup()
                            .addComponent(labelMatkhau2)
                            .addGap(18, 18, 18)
                            .addComponent(cboTenNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, informationPanelLayout.createSequentialGroup()
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelTenDN)
                                .addComponent(labelMatkhau))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(54, 54, 54)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(labelTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addComponent(labelIMG)
                        .addGap(38, 38, 38)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(labelIMG)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMatkhau2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMatkhau1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radAdmin)
                    .addComponent(radUser))
                .addGap(36, 36, 36)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua))
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDuLieu)
                    .addComponent(btnTimKiem)
                    .addComponent(btnLamMoi))
                .addGap(18, 18, 18))
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Loại tài khoản", "Tên nhân viên"
            }
        ));
        tblThongTinTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinTaiKhoan);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabelPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblThongTinTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để đổi mật khẩu!");
            return;
        }
        if (!validateInputSua()) {
            return;
        }
        try {
            Connection conn = ConnectDB.getConnection();
            String loaiTaiKhoan = radAdmin.isSelected() ? "Admin" : "User";
            String sql;
            PreparedStatement pstmt;
            if (loaiTaiKhoan.equals("Admin")) {
                // Chỉ cập nhật mật khẩu cho admin
                sql = "UPDATE taiKhoan SET matKhau = ? WHERE maTaiKhoan = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, txtMatKhau.getText());
                pstmt.setString(2, txtMaTaiKhoan.getText());
            } else {
                // Cập nhật cả mật khẩu và tên đăng nhập cho user
                sql = "UPDATE taiKhoan SET tenDangNhap = ?, matKhau = ? WHERE maTaiKhoan = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, txtTenDangNhap.getText());
                pstmt.setString(2, txtMatKhau.getText());
                pstmt.setString(3, txtMaTaiKhoan.getText());
            }

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!");
            clearForm();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đổi mật khẩu!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.matKhau, tk.loaiTaiKhoan, tk.maNhanVien, nv.tenNhanVien "
                    + "FROM taiKhoan tk "
                    + "LEFT JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            // Tạo workbook Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách Tài Khoản");

            // Tạo dòng tiêu đề
            String[] headers = {"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Loại tài khoản", "Tên nhân viên"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Ghi dữ liệu từng dòng
            int rowIndex = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("maTaiKhoan"));
                row.createCell(1).setCellValue(rs.getString("tenDangNhap"));
                row.createCell(2).setCellValue(rs.getString("matKhau"));
                row.createCell(3).setCellValue(rs.getString("loaiTaiKhoan"));
                row.createCell(4).setCellValue(rs.getString("tenNhanVien"));
            }

            // Tự động resize các cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Mở hộp thoại chọn nơi lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                FileOutputStream fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
                conn.close();

                JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công vào:\n" + filePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất dữ liệu: " + e.getMessage());
        }

    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblThongTinTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này không?",
                "Xác nhận xóa!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConnectDB.getConnection();
                String SQL = "DELETE FROM taiKhoan WHERE maTaiKhoan = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, txtMaTaiKhoan.getText());
                pstmt.execute();
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                clearForm();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa tài khoản!");
                JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
            }
            loadDatatoArrayList();
            loadDataArrayListtoTable();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
//                String tuKhoa = txtTimKiem.getText().trim();
//    if (tuKhoa.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập hoặc mã tài khoản để tìm kiếm.");
//        return;
//    }
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String SQL = "SELECT * FROM taiKhoan WHERE maTaikhoan LIKE N'%" + txtTimKiem.getText().trim() +"%' "
//                + "OR tenDangNhap LIKE N'%" + txtTimKiem.getText().trim() +"%'  ";
//    //        System.out.println(SQL);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(SQL);
//        danhsachtaikhoan.clear();
//        model.setRowCount(0);
//        while(rs.next()){
//                String maTaiKhoan = rs.getString("maTaiKhoan");
//                String tenDangNhap = rs.getString("tenDangNhap");
//                String matKhau = rs.getString("matKhau");
//                String loaiTaiKhoan = rs.getString("loaiTaiKhoan");
//
//                taiKhoan tk = new taiKhoan(maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan);
//                danhsachtaikhoan.add(tk);
//        }
//        if (danhsachtaikhoan.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản phù hợp.");
//        }
//        conn.close();
//        loadDataArrayListtoTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//              JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm tài khoản!");
//              JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//        }

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        tblThongTinTaiKhoan.clearSelection();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return; // dừng lại nếu không hợp lệ
        }
        try {
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt;
            if (radUser.isSelected()) {
                String tenNhanVien = cboTenNhanVien.getSelectedItem().toString();
                String maNhanVien = getMaNhanVienFromTenNhanVien(tenNhanVien);
                String SQL = "INSERT INTO taiKhoan (tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, txtTenDangNhap.getText());
                pstmt.setString(2, txtMatKhau.getText());
                pstmt.setString(3, "User");
                pstmt.setString(4, maNhanVien);
            } else {
                String SQL = "INSERT INTO taiKhoan (tenDangNhap, matKhau, loaiTaiKhoan) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, txtTenDangNhap.getText());
                pstmt.setString(2, txtMatKhau.getText());
                pstmt.setString(3, "Admin");
            }
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
            clearForm();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm tài khoản!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi" + e.getMessage());
        }
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblThongTinTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinTaiKhoanMouseClicked
        // TODO add your handling code here:
        int SelectedRow = tblThongTinTaiKhoan.getSelectedRow();
        if (SelectedRow != -1) {
            //Lấy dữ liệu từ bảng
            String maTaiKhoan = model.getValueAt(SelectedRow, 0).toString();
            String tenDangNhap = model.getValueAt(SelectedRow, 1).toString();
            String matKhau = model.getValueAt(SelectedRow, 2).toString();
            String loaiTaiKhoan = model.getValueAt(SelectedRow, 3).toString();
            Object tenNhanVienObj = model.getValueAt(SelectedRow, 4);
            String tenNhanVien = (tenNhanVienObj != null) ? tenNhanVienObj.toString() : "";

            //Đổ dữ liệu lại lên form
            txtMaTaiKhoan.setText(maTaiKhoan);
            txtTenDangNhap.setText(tenDangNhap);
//            txtTenDangNhap.setEnabled(false);
            txtMatKhau.setText(matKhau);
            // Xử lý phân quyền
            if (loaiTaiKhoan.equalsIgnoreCase("Admin")) {
                radAdmin.setSelected(true);
                radAdmin.setEnabled(true);

                radUser.setSelected(false);
                radUser.setEnabled(false);

                txtTenDangNhap.setEnabled(false); // Không sửa tên đăng nhập admin
                cboTenNhanVien.setSelectedIndex(0); // Không liên kết nhân viên
                cboTenNhanVien.setEnabled(false);   // Disable combobox
            } else if (loaiTaiKhoan.equalsIgnoreCase("User")) {
                radUser.setSelected(true);
                radUser.setEnabled(true);

                radAdmin.setSelected(false);
                radAdmin.setEnabled(false);

                txtTenDangNhap.setEnabled(true); // Cho sửa tên đăng nhập user

                // Nếu có tên nhân viên thì gán vào combobox
                if (!tenNhanVien.isEmpty()) {
                    cboTenNhanVien.setSelectedItem(tenNhanVien);
                } else {
                    cboTenNhanVien.setSelectedIndex(0); // Mặc định
                }
                cboTenNhanVien.setEnabled(false); // Không cho đổi nhân viên khi đang sửa
            }
        }
    }//GEN-LAST:event_tblThongTinTaiKhoanMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        danhsachtaikhoan.clear();
        //float so =0;  tìm kiếm mà có số
        try {
            Connection conn = ConnectDB.getConnection();
            //if(txtTimKiem.getText().isEmpty())
            //so = 0;
            //else if (txtTimkiem.getText().chars().allMatch(Character:isDigit))
            //so = Float.parseFloat(txtTimkiem.getText());
            // or Price = " + so + "   viết ở câu lệnh truy vấn 
            String SQL = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.matKhau, tk.loaiTaiKhoan, " +
             "tk.maNhanVien, nv.tenNhanVien " +
             "FROM taiKhoan tk " +
             "LEFT JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien " +
             "WHERE tk.maTaiKhoan LIKE N'%" + txtTimKiem.getText().trim() + "%' " +
             "OR tk.tenDangNhap LIKE N'%" + txtTimKiem.getText().trim() + "%' " +
             "OR tk.loaiTaiKhoan LIKE N'%" + txtTimKiem.getText().trim() + "%' " +
             "OR nv.tenNhanVien LIKE N'%" + txtTimKiem.getText().trim() + "%'";

//        System.out.println(SQL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String maTaiKhoan = rs.getString("maTaiKhoan");
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                String loaiTaiKhoan = rs.getString("loaiTaiKhoan");
                String maNhanVien = rs.getString("maNhanVien");
                String tenNhanVien = rs.getString("tenNhanVien");

                taiKhoan tk = new taiKhoan(maTaiKhoan, tenDangNhap, matKhau, loaiTaiKhoan, maNhanVien,tenNhanVien);
                danhsachtaikhoan.add(tk);
            }
            conn.close();
            loadDataArrayListtoTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm tài khoản!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cboTenNhanVienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenNhanVienItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selected = cboTenNhanVien.getSelectedItem().toString();
            if (!selected.equals("--Chọn tên nhân viên--")) {
                radUser.setEnabled(true);
                radUser.setSelected(true);

                radAdmin.setEnabled(false);
                radAdmin.setSelected(false);
            } else {
                radAdmin.setEnabled(true);
                radAdmin.setSelected(true);

                radUser.setEnabled(false);
                radUser.setSelected(false);
            }
        }
    }//GEN-LAST:event_cboTenNhanVienItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.ButtonGroup buttongrLoai;
    private javax.swing.JComboBox<String> cboTenNhanVien;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaTK;
    private javax.swing.JLabel labelMatkhau;
    private javax.swing.JLabel labelMatkhau1;
    private javax.swing.JLabel labelMatkhau2;
    private javax.swing.JLabel labelTenDN;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JRadioButton radAdmin;
    private javax.swing.JRadioButton radUser;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinTaiKhoan;
    private javax.swing.JTextField txtMaTaiKhoan;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
