/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import Model.thietBi;
import Model.troChoi;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LENOVO
 */
public class formThietBi extends javax.swing.JPanel {

    /**
     * Creates new form formThietBi
     */
    public formThietBi() {
        initComponents();
        model = (DefaultTableModel) tblThongTinThietBi.getModel();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
        loadTenTroChoiToComboBox();
        loadTinhTrangtoCombobox();
    }

    DefaultTableModel model;
    private List<thietBi> danhsachthietbi = new ArrayList<>();

    public void loadDatatoArrayList() {
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "SELECT tb.maThietBi, tb.maTroChoi, tc.tenTroChoi, tb.tenThietBi, tb.tinhTrang "
                    + "FROM thietBi tb JOIN troChoi tc ON tc.maTroChoi = tb.maTroChoi";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            danhsachthietbi.clear();

            while (rs.next()) {
                String maThietBi = rs.getString("maThietBi");
                String maTroChoi = rs.getString("maTroChoi");
                String tenTroChoi = rs.getString("tenTroChoi");
                String tenThietBi = rs.getString("tenThietBi");
                String tinhTrang = rs.getString("tinhTrang");

                thietBi tb = new thietBi(maThietBi, maTroChoi, tenTroChoi, tenThietBi, tinhTrang);
                danhsachthietbi.add(tb);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database");
        }
    }

    public void loadDataArrayListtoTable() {
        model.setRowCount(0);
        for (thietBi tb : danhsachthietbi) {
            model.addRow(new Object[]{
                tb.getMaThietBi(), tb.getTenTroChoi(), tb.getTenThietBi(), tb.getTinhTrang()});
        }
    }

    public void loadTenTroChoiToComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT tenTroChoi FROM troChoi";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboTenTroChoi.removeAllItems(); // Xóa các mục cũ
            cboTenTroChoi.addItem("---Chọn tên trò chơi---");

            while (rs.next()) {
                String tenTroChoi = rs.getString("tenTroChoi");
                cboTenTroChoi.addItem(tenTroChoi);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm tên trò chơi vào combo box");
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    // Hàm lấy mã trò chơi từ tên trò chơi
    private String getMaTroChoiFromTenTroChoi(String tenTroChoi) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maTroChoi FROM troChoi WHERE tenTroChoi = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenTroChoi);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String maTroChoi = rs.getString("maTroChoi");
                conn.close();
                return maTroChoi;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã trò chơi: " + e.getMessage());
        }
        return null;
    }

    public void loadTinhTrangtoCombobox() {
        cboTinhTrang.removeAllItems(); // Xóa các mục cũ
        cboTinhTrang.addItem("---Chọn tình trạng thiết bị---");
        cboTinhTrang.addItem("Đang hoạt động");
        cboTinhTrang.addItem("Đang bảo trì");
    }

    public boolean validateInput() {
        String maThietBi = txtMaThietBi.getText().trim();
        String tenTroChoi = cboTenTroChoi.getSelectedItem().toString();
        String tenThietBi = txtTenThietBi.getText().trim();
        String tinhTrang = cboTinhTrang.getSelectedItem().toString();

        // Kiểm tra rỗng
        if (maThietBi.isEmpty() || tenTroChoi.isEmpty() || tenThietBi.isEmpty() || tinhTrang.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin thiết bị.");
            return false;
        }
        // Kiểm tra trùng
        for (thietBi tb : danhsachthietbi) {
            if (tb.getMaThietBi().equals(maThietBi)) {
                JOptionPane.showMessageDialog(null, "Mã thiết bị đã tồn tại!");
                txtMaThietBi.requestFocus();
                return false;
            }
        }
        // Kiểm tra chọn combobox
        if (tenTroChoi.equals("---Chọn tên trò chơi---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tên trò chơi hợp lệ!");
            cboTenTroChoi.requestFocus();
            return false;
        }
        if (tinhTrang.equals("---Chọn tình trạng thiết bị---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tình trạng thiết bị hợp lệ!");
            cboTinhTrang.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateInputSUA() {
        String maThietBi = txtMaThietBi.getText().trim();
        String tenTroChoi = cboTenTroChoi.getSelectedItem().toString();
        String tenThietBi = txtTenThietBi.getText().trim();
        String tinhTrang = cboTinhTrang.getSelectedItem().toString();

        // Kiểm tra rỗng
        if (maThietBi.isEmpty() || tenTroChoi.isEmpty() || tenThietBi.isEmpty() || tinhTrang.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin thiết bị.");
            return false;
        }
        // Kiểm tra chọn combobox
        if (tenTroChoi.equals("---Chọn tên trò chơi---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tên trò chơi hợp lệ!");
            cboTenTroChoi.requestFocus();
            return false;
        }
        if (tinhTrang.equals("---Chọn tình trạng thiết bị---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tình trạng thiết bị hợp lệ!");
            cboTinhTrang.requestFocus();
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtMaThietBi.setText("");
        cboTenTroChoi.setSelectedIndex(0);
        txtTenThietBi.setText("");
        cboTinhTrang.setSelectedIndex(0);
        txtTimKiem.setText("");
        txtMaThietBi.setEnabled(true);
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
        labelMaTB = new javax.swing.JLabel();
        labelTinhtrang = new javax.swing.JLabel();
        txtMaThietBi = new javax.swing.JTextField();
        txtTenThietBi = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelTenThietBi = new javax.swing.JLabel();
        cboTenTroChoi = new javax.swing.JComboBox<>();
        cboTinhTrang = new javax.swing.JComboBox<>();
        labelTenThietBi1 = new javax.swing.JLabel();
        lblTenTroChoi = new javax.swing.JLabel();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinThietBi = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ THIẾT BỊ");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/wall.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        labelMaTB.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaTB.setText("Mã thiết bị:");

        labelTinhtrang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTinhtrang.setText("Tình trạng:");

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

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/playground.png"))); // NOI18N

        labelTenThietBi.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenThietBi.setText("Tên thiết bị:");

        labelTenThietBi1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenThietBi1.setText("Tên trò chơi:");

        lblTenTroChoi.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTenThietBi1)
                            .addComponent(labelTenThietBi)
                            .addComponent(labelTinhtrang))
                        .addGap(34, 34, 34)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboTenTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTenTroChoi))
                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(labelMaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(informationPanelLayout.createSequentialGroup()
                            .addGap(235, 235, 235)
                            .addComponent(btnTimKiem))
                        .addGroup(informationPanelLayout.createSequentialGroup()
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                    .addComponent(btnThem)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(labelTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addComponent(labelIMG)
                        .addGap(38, 38, 38)))
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
                            .addComponent(labelMaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTenTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTenThietBi1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTinhtrang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem)
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnXoa)
                                .addComponent(btnLamMoi)
                                .addComponent(btnSua)))
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXuatDuLieu)
                            .addComponent(btnTimKiem))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinThietBi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thiết bị", "Tên trò chơi", "Tên thiết bị", "Tình trạng"
            }
        ));
        tblThongTinThietBi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinThietBiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinThietBi);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
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
                .addComponent(tabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblThongTinThietBi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thiết bị để sửa thông tin!");
            return;
        }
        if (!validateInputSUA()) {
            return; // Dừng lại nếu không hợp lệ
        }
        try {
            String tenTroChoi = cboTenTroChoi.getSelectedItem().toString();
            String maTroChoi = getMaTroChoiFromTenTroChoi(tenTroChoi);
            if (maTroChoi == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã trò chơi cho tên trò chơi đã chọn!");
                return;
            }

            Connection conn = ConnectDB.getConnection();
            String SQL = "UPDATE thietBi SET maTroChoi = ?, tenThietBi = ?, tinhTrang = ? WHERE maThietBi = ?";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, maTroChoi);
            pstmt.setString(2, txtTenThietBi.getText());
            pstmt.setString(3, cboTinhTrang.getSelectedItem().toString());
            pstmt.setString(4, txtMaThietBi.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thiết bị thành công!");
            clearForm();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thiết bị!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }

        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // Người dùng hủy
        }
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
        }
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("DanhSachThietBi");
            // Tạo dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã Thiết Bị", "Tên Trò Chơi", "Tên Thiết Bị", "Tình Trạng"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            // Ghi dữ liệu từ danh sách
            for (int i = 0; i < danhsachthietbi.size(); i++) {
                thietBi tb = danhsachthietbi.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(tb.getMaThietBi());
                row.createCell(1).setCellValue(tb.getTenTroChoi());
                row.createCell(2).setCellValue(tb.getTenThietBi());
                row.createCell(3).setCellValue(tb.getTinhTrang());
            }
            // Auto resize cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Ghi file
            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                workbook.write(fos);
            }
            JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công tới:\n" + fileToSave.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất dữ liệu: " + e.getMessage());
        }
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblThongTinThietBi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thiết bị để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thiết bị này không?",
                "Xác nhận xóa!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConnectDB.getConnection();
                String SQL = "DELETE FROM thietBi WHERE maThietBi = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, txtMaThietBi.getText());
                pstmt.execute();
                JOptionPane.showMessageDialog(this, "Xóa thiết bị thành công!");
                clearForm();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa thiết bị!");
                JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
            }
            loadDatatoArrayList();
            loadDataArrayListtoTable();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
//    String tuKhoa = txtTimKiem.getText().trim();
//    if (tuKhoa.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thiết bị hoặc mã thiết bị để tìm kiếm.");
//        return;
//    }
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String SQL = "SELECT tb.maThietBi, tb.maTroChoi, tc.tenTroChoi, tb.tenThietBi, tb.tinhTrang "
//                   + "FROM thietBi tb JOIN troChoi tc ON tb.maTroChoi = tc.maTroChoi "
//                   + "WHERE tb.maThietBi LIKE N'%" + tuKhoa + "%' "
//                   + "OR tb.tenThietBi LIKE N'%" + tuKhoa + "%' "
//                   + "OR tb.tinhTrang LIKE N'%" + tuKhoa + "%' "
//                   + "OR tc.tenTroChoi LIKE N'%" + tuKhoa + "%'";
    ////        System.out.println(SQL);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(SQL);
//        danhsachthietbi.clear();
//        model.setRowCount(0);
//        while(rs.next()){
//                String maThietBi = rs.getString("maThietBi");
//                String maTroChoi = rs.getString("maTroChoi");
//                String tenTroChoi = rs.getString("tenTroChoi");
//                String tenThietBi = rs.getString("tenThietBi");
//                String tinhTrang = rs.getString("tinhTrang");
//                
//                thietBi tb = new thietBi(maThietBi, maTroChoi, tenTroChoi, tenThietBi, tinhTrang);
//                danhsachthietbi.add(tb);
//        }
//        if (danhsachthietbi.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy thiết bị phù hợp.");
//        }
//        conn.close();
//        loadDataArrayListtoTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//              JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm thiết bị!");
//              JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        tblThongTinThietBi.clearSelection();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return; // Nếu dữ liệu không hợp lệ thì dừng
        }
        try {
            String tenTroChoi = cboTenTroChoi.getSelectedItem().toString();
            String maTroChoi = getMaTroChoiFromTenTroChoi(tenTroChoi);
            if (maTroChoi == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã trò chơi cho tên trò chơi đã chọn!");
                return;
            }

            Connection conn = ConnectDB.getConnection();
            String SQL = "INSERT INTO thietBi (maThietBi, maTroChoi, tenThietBi, tinhTrang) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, txtMaThietBi.getText().trim());
            pstmt.setString(2, maTroChoi);
            pstmt.setString(3, txtTenThietBi.getText().trim());
            pstmt.setString(4, cboTinhTrang.getSelectedItem().toString());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm thiết bị thành công!");
            clearForm();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm thiết bị!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }

        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblThongTinThietBiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinThietBiMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblThongTinThietBi.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy dữ liệu từ bảng
            String maThietBi = model.getValueAt(selectedRow, 0).toString();
            String tenTroChoi = model.getValueAt(selectedRow, 1).toString();
            String tenThietBi = model.getValueAt(selectedRow, 2).toString();
            String tinhTrang = model.getValueAt(selectedRow, 3).toString();

            // Đổ dữ liệu lên form
            txtMaThietBi.setText(maThietBi);
            txtMaThietBi.setEnabled(false);
            cboTenTroChoi.setSelectedItem(tenTroChoi);
            txtTenThietBi.setText(tenThietBi);
            cboTinhTrang.setSelectedItem(tinhTrang);
        }
    }//GEN-LAST:event_tblThongTinThietBiMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String tuKhoa = txtTimKiem.getText().trim();
        danhsachthietbi.clear();
//        float so =0;  //tìm kiếm mà có số
        try {
            Connection conn = ConnectDB.getConnection();
            //if(txtTimKiem.getText().isEmpty())
            //so = 0;
            //else if (txtTimkiem.getText().chars().allMatch(Character:isDigit))
            //so = Float.parseFloat(txtTimkiem.getText());
            // or Price = " + so + "   viết ở câu lệnh truy vấn 
            String SQL = "SELECT tb.maThietBi, tb.maTroChoi, tc.tenTroChoi, tb.tenThietBi, tb.tinhTrang "
                    + "FROM thietBi tb JOIN troChoi tc ON tb.maTroChoi = tc.maTroChoi "
                    + "WHERE tb.maThietBi LIKE N'%" + tuKhoa + "%' "
                    + "OR tb.tenThietBi LIKE N'%" + tuKhoa + "%' "
                    + "OR tb.tinhTrang LIKE N'%" + tuKhoa + "%' "
                    + "OR tb.maTroChoi LIKE N'%" + tuKhoa + "%' "
                    + "OR tc.tenTroChoi LIKE N'%" + tuKhoa + "%'";
//        System.out.println(SQL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String maThietBi = rs.getString("maThietBi");
                String maTroChoi = rs.getString("maTroChoi");
                String tenTroChoi = rs.getString("tenTroChoi");
                String tenThietBi = rs.getString("tenThietBi");
                String tinhTrang = rs.getString("tinhTrang");

                thietBi tb = new thietBi(maThietBi, maTroChoi, tenTroChoi, tenThietBi, tinhTrang);
                danhsachthietbi.add(tb);
            }
            conn.close();
            loadDataArrayListtoTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm thiết bị!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    private void cboTenTroChoiItemStateChanged(java.awt.event.ItemEvent evt) {
//        // TODO add your handling code here:
//        if (evt.getStateChange() == ItemEvent.SELECTED) {
//            Object selectedTenTroChoi = cboTenTroChoi.getSelectedItem();
//            Object selectedTinhTrang = cboTinhTrang.getSelectedItem();
//            String tenTroChoi = selectedTenTroChoi != null ? selectedTenTroChoi.toString() : null;
//            String tinhTrang = selectedTinhTrang != null ? selectedTinhTrang.toString() : null;
//            loadDataTheoLoc(tenTroChoi, tinhTrang);
//        }
//    }
//
//    private void cboTinhTrangItemStateChanged(java.awt.event.ItemEvent evt) {
//        // TODO add your handling code here:
//        if (evt.getStateChange() == ItemEvent.SELECTED) {
//            Object selectedTenTroChoi = cboTenTroChoi.getSelectedItem();
//            Object selectedTinhTrang = cboTinhTrang.getSelectedItem();
//            String tenTroChoi = selectedTenTroChoi != null ? selectedTenTroChoi.toString() : null;
//            String tinhTrang = selectedTinhTrang != null ? selectedTinhTrang.toString() : null;
//            loadDataTheoLoc(tenTroChoi, tinhTrang);
//        }
//    }
//
//    // Hàm lọc dữ liệu theo tên trò chơi và tình trạng
//    public void loadDataTheoLoc(String tenTroChoi, String tinhTrang) {
//        model.setRowCount(0);
//        for (thietBi tb : danhsachthietbi) {
//            boolean matchTenTroChoi = tenTroChoi == null || tenTroChoi.equals("---Chọn tên trò chơi---") || tb.getTenTroChoi().equals(tenTroChoi);
//            boolean matchTinhTrang = tinhTrang == null || tinhTrang.equals("---Chọn tình trạng thiết bị---") || tb.getTinhTrang().equals(tinhTrang);
//            if (matchTenTroChoi && matchTinhTrang) {
//                model.addRow(new Object[]{
//                    tb.getMaThietBi(),
//                    tb.getTenTroChoi(),
//                    tb.getTenThietBi(),
//                    tb.getTinhTrang()
//                });
//            }
//        }
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboTenTroChoi;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaTB;
    private javax.swing.JLabel labelTenThietBi;
    private javax.swing.JLabel labelTenThietBi1;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JLabel labelTinhtrang;
    private javax.swing.JLabel lblTenTroChoi;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinThietBi;
    private javax.swing.JTextField txtMaThietBi;
    private javax.swing.JTextField txtTenThietBi;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
