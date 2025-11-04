/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import static Database.ConnectDB.getConnection;
import Model.CTHD;
import Model.taiKhoan;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LENOVO
 */
public class formHoaDon extends javax.swing.JPanel {

    DefaultTableModel model;
    private Map<String, String> maNhanVienMap = new HashMap<>();
    private Map<String, Integer> giaVeMap = new HashMap<>();
    private Map<String, String> maVeMap = new HashMap<>();
    List<CTHD> list = new ArrayList<>();
    private taiKhoan taiKhoanDangNhap;

    /**
     * Creates new form formHoaDon
     */
    public formHoaDon(taiKhoan taiKhoanDangNhap) {
        this.taiKhoanDangNhap = taiKhoanDangNhap;
        initComponents();
        btnLuu.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnCapNhap.setEnabled(false);
        dcNgayMua.setDateFormatString("dd/MM/yyyy");
        loadTenNhanVienToComboBox();
        loadcboMaVe();
        loadtblHD();
    }

    private void clearFields() {
        txtMaHoaDon.setText("");
        txtSoLuong.setText("");
        txtTimKiem.setText("");
        dcNgayMua.setDate(null);
    }

    public void loadTenNhanVienToComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            cboTenNhanVien.removeAllItems();
            cboTenNhanVien.addItem("--Chọn tên nhân viên--"); // Thêm dòng mặc định

            if (taiKhoanDangNhap.getLoaiTaiKhoan().equalsIgnoreCase("Admin")) {
                // Load danh sách nhân viên có chức vụ "Nhân viên bán hàng" cho Admin
                String SQL = "SELECT * FROM NhanVien WHERE chucVu = N'Nhân viên bán hàng'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                while (rs.next()) {
                    String tenNhanVien = rs.getString("tenNhanVien");
                    String maNhanVien = rs.getString("maNhanVien");
                    cboTenNhanVien.addItem(tenNhanVien);
                    maNhanVienMap.put(tenNhanVien, maNhanVien);
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
                    maNhanVienMap.put(tenNhanVien, maNhanVien);
                    cboTenNhanVien.setSelectedIndex(1); // Chọn tên nhân viên duy nhất
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

    public void loadcboMaVe() {
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM Ve";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            cboMaVe.removeAllItems();
            cboMaVe.addItem("--Chọn loại vé--");
            maVeMap.clear();
            giaVeMap.clear();
            while (rs.next()) {
                String tenVe = rs.getString("loaiVe");
                String maVe = rs.getString("maVe");
                int giaVe = rs.getInt("giaVe");
                cboMaVe.addItem(tenVe);
                maVeMap.put(tenVe, maVe);
                giaVeMap.put(maVe, giaVe);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải mã vé: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadtblHD() {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try (Connection conn = getConnection()) {
            String sql;
            PreparedStatement pstmt;

            if (taiKhoanDangNhap.getLoaiTaiKhoan().equalsIgnoreCase("Admin")) {
                // Admin: hiển thị tất cả hóa đơn
                sql = "SELECT hd.*, nv.tenNhanVien FROM HoaDon hd JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien";
                pstmt = conn.prepareStatement(sql);
            } else {
                // Nhân viên: chỉ hiển thị hóa đơn của mình
                sql = "SELECT hd.*, nv.tenNhanVien FROM HoaDon hd JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien WHERE hd.maNhanVien = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, taiKhoanDangNhap.getMaNhanVien());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHoaDon");
                    String tenNV = rs.getString("tenNhanVien");
                    java.util.Date utilDate = rs.getDate("ngayMua");
                    String ngayMua = (utilDate != null) ? dateFormat.format(utilDate) : "";
                    int tongTien = rs.getInt("tongTien");

                    model.addRow(new Object[]{maHD, tenNV, ngayMua, tongTien});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
            e.printStackTrace();
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
        cboMaVe = new javax.swing.JComboBox<>();
        labelMaVe = new javax.swing.JLabel();
        labelNgayMua = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        btnCapNhap = new javax.swing.JButton();
        cboTenNhanVien = new javax.swing.JComboBox<>();
        dcNgayMua = new com.toedter.calendar.JDateChooser();
        panelHoaDon = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panelCTHD = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ HÓA ĐƠN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/bill.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(246, 246, 246))
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

        cboMaVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMaVeMouseClicked(evt);
            }
        });

        labelMaVe.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaVe.setText("Loại vé:");

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
                            .addComponent(cboMaVe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTenNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dcNgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaVe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaVe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "Mã hóa đơn", "Tên nhân viên", "Ngày mua", "Tổng tiền"
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
                "Mã hóa đơn", "Loại vé", "Số lượng"
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
                .addComponent(panelHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        int selectedRow = tblChiTietHoaDon.getSelectedRow();
        String maVe = cboMaVe.getSelectedItem().toString();
        int soLuongMoi = Integer.parseInt(txtSoLuong.getText());
        if (soLuongMoi < 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        if (soLuongMoi == 0) {
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Số lượng bằng 0, dòng đã được xóa khỏi bảng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            model.setValueAt(maVe, selectedRow, 1);
            model.setValueAt(soLuongMoi, selectedRow, 2);
        }
        String maHoaDon = txtMaHoaDon.getText().trim();
        boolean hoaDonExists = false;
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM HoaDon WHERE maHoaDon = ?")) {
            pstmt.setString(1, maHoaDon);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                hoaDonExists = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra mã hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (hoaDonExists) {
            btnCapNhap.setEnabled(true);
            btnLuu.setEnabled(false);
        } else {
            btnCapNhap.setEnabled(false);
            btnLuu.setEnabled(true);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) tblHoaDon.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
        }
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("DanhSachHoaDon");
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(model.getValueAt(i, j).toString());
                }
            }
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                workbook.write(fos);
                JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công tới:\n" + fileToSave.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelHD = (DefaultTableModel) tblHoaDon.getModel();
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnLuu.setEnabled(false);
        btnCapNhap.setEnabled(false);
        String timKiem = txtTimKiem.getText().toString();
        if (timKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn để tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            loadtblHD();
            return;
        }
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, timKiem);
            ResultSet rs = pstmt.executeQuery();
            modelHD.setRowCount(0);
            int ketQua = 0;
            while (rs.next()) {
                ketQua += 1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String maHD = rs.getString("maHoaDon");
                String maNV = rs.getString("maNhanVien");
                java.util.Date utilDate = rs.getDate("ngayMua");
                String ngayMua = (utilDate != null) ? dateFormat.format(utilDate) : "";
                int tongTien = rs.getInt("tongTien");

                modelHD.addRow(new Object[]{maHD, maNV, ngayMua, tongTien});
            }
            rs.close();
            pstmt.close();
            con.close();
            if (ketQua == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với tiêu chí: " + timKiem, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadtblHD();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearFields();
        loadTenNhanVienToComboBox();
        loadcboMaVe();
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);
        loadtblHD();
        txtMaHoaDon.setEditable(true);
        cboTenNhanVien.setEnabled(true);
        dcNgayMua.setEnabled(true);
        cboMaVe.setEnabled(true);
        txtSoLuong.setEditable(true);
        btnThem.setEnabled(true);
        btnLuu.setEnabled(true);
        btnCapNhap.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(false);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        String maHoaDon = txtMaHoaDon.getText().trim();
        String tenNhanVien = cboTenNhanVien.getSelectedItem() != null ? cboTenNhanVien.getSelectedItem().toString() : "";
        String maNhanVien = maNhanVienMap.get(tenNhanVien);
        java.util.Date ngayMua = dcNgayMua.getDate();
        DefaultTableModel chiTietModel = (DefaultTableModel) tblChiTietHoaDon.getModel();

        if (maHoaDon.isEmpty() || maNhanVien.isEmpty() || ngayMua == null || chiTietModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn và chi tiết hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tongTien = 0;
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            String tenVe = chiTietModel.getValueAt(i, 1).toString();
            String maVe = maVeMap.get(tenVe);
            int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
            int giaVe = giaVeMap.get(maVe);
            tongTien += giaVe * soLuong;
        }

        String sqlHoaDon = "INSERT INTO HoaDon (maHoaDon, maNhanVien, ngayMua, tongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmtHoaDon = conn.prepareStatement(sqlHoaDon)) {
            pstmtHoaDon.setString(1, maHoaDon);
            pstmtHoaDon.setString(2, maNhanVien);
            pstmtHoaDon.setDate(3, new java.sql.Date(ngayMua.getTime()));
            pstmtHoaDon.setInt(4, tongTien);
            pstmtHoaDon.executeUpdate();

            String sqlChiTiet = "INSERT INTO ChiTietHoaDon (maHoaDon, maVe, soLuong) VALUES (?, ?, ?)";
            try (PreparedStatement pstmtChiTiet = conn.prepareStatement(sqlChiTiet)) {
                for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                    String tenVe = chiTietModel.getValueAt(i, 1).toString();
                    String maVe = maVeMap.get(tenVe);
                    int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
                    pstmtChiTiet.setString(1, maHoaDon);
                    pstmtChiTiet.setString(2, maVe);
                    pstmtChiTiet.setInt(3, soLuong);
                    pstmtChiTiet.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            chiTietModel.setRowCount(0);
            loadtblHD();
            txtMaHoaDon.setEditable(true);
            cboTenNhanVien.setEnabled(true);
            dcNgayMua.setEnabled(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), "Đã tồn tại mã hoá đơn này", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        DefaultTableModel model1 = (DefaultTableModel) tblHoaDon.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        model.setRowCount(0);
        int selectedRow = tblHoaDon.getSelectedRow();
        String maHoaDon = model1.getValueAt(selectedRow, 0).toString();
        String tenNhanVien = model1.getValueAt(selectedRow, 1).toString();
        String ngayMuaStr = model1.getValueAt(selectedRow, 2).toString();
        txtMaHoaDon.setText(maHoaDon);
        cboTenNhanVien.setSelectedItem(tenNhanVien);
        try {
            if (!ngayMuaStr.isEmpty()) {
                java.util.Date ngayMua = dateFormat.parse(ngayMuaStr);
                dcNgayMua.setDate(ngayMua);
            } else {
                dcNgayMua.setDate(null);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày mua: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            dcNgayMua.setDate(null);
        }
        txtMaHoaDon.setEditable(false);
        txtSoLuong.setEditable(false);
        cboMaVe.setEnabled(false);
        cboTenNhanVien.setEnabled(false);
        dcNgayMua.setEnabled(false);
        btnLuu.setEnabled(false);
        btnCapNhap.setEnabled(false);
        btnThem.setEnabled(false);
        btnXoa.setEnabled(true);
        String sql = "SELECT cthd.*, v.loaiVe FROM ChiTietHoaDon cthd JOIN Ve v ON cthd.maVe = v.maVe WHERE cthd.maHoaDon = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maHoaDon);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHoaDon");
                    String maVe = rs.getString("maVe");
                    String loaiVe = rs.getString("loaiVe");
                    int soLuong = rs.getInt("soLuong");
                    model.addRow(new Object[]{maHD, loaiVe, soLuong});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblChiTietHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietHoaDonMouseClicked
        // TODO add your handling code here:
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        int selectedRow = tblChiTietHoaDon.getSelectedRow();
        String tenVe = model.getValueAt(selectedRow, 1).toString();
        String soLuong = model.getValueAt(selectedRow, 2).toString();
        txtSoLuong.setText(soLuong);
        cboMaVe.setSelectedItem(tenVe);
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
        txtSoLuong.setEditable(true);
        cboMaVe.setEnabled(true);
    }//GEN-LAST:event_tblChiTietHoaDonMouseClicked

    private void cboMaVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMaVeMouseClicked
        // TODO add your handling code here:
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
    }//GEN-LAST:event_cboMaVeMouseClicked

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelCTHD = (DefaultTableModel) tblChiTietHoaDon.getModel();
        int selectedRow = tblChiTietHoaDon.getSelectedRow();
        DefaultTableModel modelHD = (DefaultTableModel) tblHoaDon.getModel();
        int selectedRow1 = tblHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            modelCTHD.removeRow(selectedRow);
            btnCapNhap.setEnabled(true);
        } else if (selectedRow1 >= 0) {
            String maHoaDon1 = String.valueOf(modelHD.getValueAt(selectedRow1, 0)).trim();
            try {
                Connection con = getConnection();
                String sqlDeleteChiTiet = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ?";
                PreparedStatement pstmtChiTietHoaDon = con.prepareStatement(sqlDeleteChiTiet);
                pstmtChiTietHoaDon.setString(1, maHoaDon1);
                pstmtChiTietHoaDon.executeUpdate();
                String sqlHoaDon = "DELETE FROM HoaDon WHERE maHoaDon = ?";
                PreparedStatement pstmtHoaDon = con.prepareStatement(sqlHoaDon);
                pstmtHoaDon.setString(1, maHoaDon1);
                pstmtHoaDon.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xoá hóa đơn và chi tiết hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                modelCTHD.setRowCount(0);
                loadtblHD();
                loadTenNhanVienToComboBox();
                loadcboMaVe();
                txtMaHoaDon.setEditable(true);
                cboTenNhanVien.setEnabled(true);
                dcNgayMua.setEnabled(true);
                cboMaVe.setEnabled(true);
                txtSoLuong.setEditable(true);
                btnThem.setEnabled(true);
                btnLuu.setEnabled(true);
                btnCapNhap.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(false);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xoá hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapActionPerformed
        // TODO add your handling code here:
        String maHoaDon = txtMaHoaDon.getText().trim();
        DefaultTableModel chiTietModel = (DefaultTableModel) tblChiTietHoaDon.getModel();
        int tongTien = 0;
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            String tenVe = chiTietModel.getValueAt(i, 1).toString();
            String maVe = maVeMap.get(tenVe);
            int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
            int giaVe = giaVeMap.get(maVe);
            tongTien += giaVe * soLuong;
        }
        try {
            Connection conn = getConnection();
            if (tongTien == 0) {
                String sqlDeleteChiTiet = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ?";
                PreparedStatement pstmtChiTietHoaDon = conn.prepareStatement(sqlDeleteChiTiet);
                pstmtChiTietHoaDon.setString(1, maHoaDon);
                pstmtChiTietHoaDon.executeUpdate();
                String sqlHoaDon = "DELETE FROM HoaDon WHERE maHoaDon = ?";
                PreparedStatement pstmtHoaDon = conn.prepareStatement(sqlHoaDon);
                pstmtHoaDon.setString(1, maHoaDon);
                pstmtHoaDon.executeUpdate();
            } else {
                String sqlHoaDon = "UPDATE HoaDon SET tongTien = ? WHERE maHoaDon = ?";
                PreparedStatement pstmtHoaDon = conn.prepareStatement(sqlHoaDon);
                pstmtHoaDon.setInt(1, tongTien);
                pstmtHoaDon.setString(2, maHoaDon);
                int rowsAffected = pstmtHoaDon.executeUpdate();
                if (rowsAffected == 0) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String sqlDeleteChiTiet = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ?";
                try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDeleteChiTiet)) {
                    pstmtDelete.setString(1, maHoaDon);
                    pstmtDelete.executeUpdate();
                }

                String sqlInsertChiTiet = "INSERT INTO ChiTietHoaDon (maHoaDon, maVe, soLuong) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertChiTiet)) {
                    for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                        String tenVe = chiTietModel.getValueAt(i, 1).toString();
                        String maVe = maVeMap.get(tenVe);
                        int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 2).toString());
                        pstmtInsert.setString(1, maHoaDon);
                        pstmtInsert.setString(2, maVe);
                        pstmtInsert.setInt(3, soLuong);
                        pstmtInsert.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn và chi tiết hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            chiTietModel.setRowCount(0);
            loadtblHD();
            loadTenNhanVienToComboBox();
            loadcboMaVe();
            txtMaHoaDon.setEditable(true);
            cboTenNhanVien.setEnabled(true);
            dcNgayMua.setEnabled(true);
            cboMaVe.setEnabled(true);
            txtSoLuong.setEditable(true);
            btnThem.setEnabled(true);
            btnLuu.setEnabled(true);
            btnCapNhap.setEnabled(false);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnLuu.setEnabled(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCapNhapActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        int count = tblChiTietHoaDon.getSelectedRow();
        if (count >= 0) {
            btnCapNhap.setEnabled(true);
            btnLuu.setEnabled(false);
        }
        if (count == -1) {
            btnCapNhap.setEnabled(false);
            btnLuu.setEnabled(true);
        }
        txtMaHoaDon.setEditable(false);
        cboTenNhanVien.setEnabled(false);
        dcNgayMua.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        String maHoaDon = txtMaHoaDon.getText();
        String tenVe = cboMaVe.getSelectedItem().toString();
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaHoaDon.requestFocus();
            return;
        }
        if (txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        model.addRow(new Object[]{maHoaDon, tenVe, soLuong});
        txtSoLuong.setText("");
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhap;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboMaVe;
    private javax.swing.JComboBox<String> cboTenNhanVien;
    private com.toedter.calendar.JDateChooser dcNgayMua;
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
