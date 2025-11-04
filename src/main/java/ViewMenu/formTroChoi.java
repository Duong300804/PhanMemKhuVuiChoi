/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import Model.troChoi;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
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
public class formTroChoi extends javax.swing.JPanel {

    /**
     * Creates new form formTroChoi
     */
    public formTroChoi() {
        initComponents();
        model = (DefaultTableModel) tblThongTinTroChoi.getModel();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
        loadTenPhanKhuToComboBox();
    }

    DefaultTableModel model;
    private List<troChoi> danhsachtrochoi = new ArrayList<>();

    public void loadDatatoArrayList() {
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "SELECT tc.maTroChoi, tc.maPhanKhu, pk.tenPhanKhu, tc.tenTroChoi, tc.moTa "
                    + "FROM troChoi tc JOIN phanKhu pk ON tc.maPhanKhu = pk.maPhanKhu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            danhsachtrochoi.clear();

            while (rs.next()) {
                String maTroChoi = rs.getString("maTroChoi");
                String maPhanKhu = rs.getString("maPhanKhu");
                String tenPhanKhu = rs.getString("tenPhanKhu");
                String tenTroChoi = rs.getString("tenTroChoi");
                String moTa = rs.getString("moTa");

                troChoi tc = new troChoi(maTroChoi, maPhanKhu, tenPhanKhu, tenTroChoi, moTa);
                danhsachtrochoi.add(tc);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database");
        }
    }

    public void loadDataArrayListtoTable() {
        model.setRowCount(0);
        for (troChoi tc : danhsachtrochoi) {
            model.addRow(new Object[]{
                tc.getMaTroChoi(),
                tc.getTenPhanKhu(),
                tc.getTenTroChoi(),
                tc.getMoTa()
            });
        }
    }

    public void loadTenPhanKhuToComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT tenPhanKhu FROM phanKhu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboTenPhanKhu.removeAllItems();
            cboTenPhanKhu.addItem("---Chọn tên phân khu---");

            while (rs.next()) {
                String tenPhanKhu = rs.getString("tenPhanKhu");
                cboTenPhanKhu.addItem(tenPhanKhu);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm tên phân khu vào combo box");
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    public String getMaPhanKhuFromTenPhanKhu(String tenPhanKhu) {
        String maPhanKhu = "";
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maPhanKhu FROM phanKhu WHERE tenPhanKhu = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tenPhanKhu);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maPhanKhu = rs.getString("maPhanKhu");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã phân khu: " + e.getMessage());
        }
        return maPhanKhu;
    }

    public boolean validateInput() {
        String maTroChoi = txtMaTroChoi.getText().trim();
        String tenPhanKhu = cboTenPhanKhu.getSelectedItem().toString();
        String tenTroChoi = txtTenTroChoi.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (maTroChoi.isEmpty() || tenPhanKhu.isEmpty() || tenTroChoi.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin trò chơi.");
            return false;
        }

        for (troChoi tc : danhsachtrochoi) {
            if (tc.getMaTroChoi().equals(maTroChoi)) {
                JOptionPane.showMessageDialog(null, "Mã trò chơi đã tồn tại!");
                txtMaTroChoi.requestFocus();
                return false;
            }
        }

        if (tenPhanKhu.equals("---Chọn tên phân khu---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tên phân khu hợp lệ!");
            cboTenPhanKhu.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateInputSUA() {
        String maTroChoi = txtMaTroChoi.getText().trim();
        String tenPhanKhu = cboTenPhanKhu.getSelectedItem().toString();
        String tenTroChoi = txtTenTroChoi.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (maTroChoi.isEmpty() || tenPhanKhu.isEmpty() || tenTroChoi.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin trò chơi.");
            return false;
        }

        if (tenPhanKhu.equals("---Chọn tên phân khu---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tên phân khu hợp lệ!");
            cboTenPhanKhu.requestFocus();
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtMaTroChoi.setText("");
        cboTenPhanKhu.setSelectedIndex(0);
        txtTenTroChoi.setText("");
        txtMoTa.setText("");
        txtTimKiem.setText("");
        txtMaTroChoi.setEnabled(true);
    }

    //    private void foreignKeyError(SQLException e) {
//    String errorMessage = e.getMessage();
//    if (errorMessage != null && errorMessage.toLowerCase().contains("foreign key")) {
//        JOptionPane.showMessageDialog(this, "Không thể xóa vì trò chơi này đang được sử dụng trong bảng thiết bị!");
//    } else {
//        JOptionPane.showMessageDialog(this, "Lỗi khi xóa trò chơi!");
//        JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//    }
//}
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
        labelMaTrochoi = new javax.swing.JLabel();
        labelMota = new javax.swing.JLabel();
        txtMaTroChoi = new javax.swing.JTextField();
        txtTenTroChoi = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelTenTrochoi = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        cboTenPhanKhu = new javax.swing.JComboBox<>();
        labelTenPK = new javax.swing.JLabel();
        lblTenPhanKhu = new javax.swing.JLabel();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinTroChoi = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ TRÒ CHƠI");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/videogame.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(222, 222, 222))
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

        labelMaTrochoi.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaTrochoi.setText("Mã trò chơi: ");

        labelMota.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMota.setText("Mô tả:");

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

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/board-game.png"))); // NOI18N

        labelTenTrochoi.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenTrochoi.setText("Tên trò chơi:");

        txtMoTa.setColumns(20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        txtMoTa.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtMoTa);

        cboTenPhanKhu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenPhanKhuItemStateChanged(evt);
            }
        });

        labelTenPK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenPK.setText("Tên phân khu:");

        lblTenPhanKhu.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(labelMaTrochoi, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(lblTenPhanKhu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelMota)
                                    .addComponent(labelTenTrochoi))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenTroChoi)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(labelTenPK, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboTenPhanKhu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addGap(70, 70, 70)
                .addComponent(labelIMG)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMaTrochoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTenPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTenPK, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTenTrochoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMota, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa)
                        .addComponent(btnLamMoi)
                        .addComponent(btnSua)))
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDuLieu)
                    .addComponent(btnTimKiem))
                .addGap(48, 48, 48))
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinTroChoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã trò chơi", "Tên phân khu", "Tên trò chơi", "Mô tả"
            }
        ));
        tblThongTinTroChoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinTroChoiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinTroChoi);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabelPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
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
        int selectedRow = tblThongTinTroChoi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trò chơi để sửa thông tin!");
            return;
        }
        if (!validateInputSUA()) {
            return; // Dừng lại nếu không hợp lệ
        }
        try {
            String tenPhanKhu = cboTenPhanKhu.getSelectedItem().toString();
            String maPhanKhu = getMaPhanKhuFromTenPhanKhu(tenPhanKhu);

            Connection conn = ConnectDB.getConnection();
            String SQL = "UPDATE troChoi SET maPhanKhu = ?, tenTroChoi = ?, moTa = ? WHERE maTroChoi = ?";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, maPhanKhu);
            pstmt.setString(2, txtTenTroChoi.getText());
            pstmt.setString(3, txtMoTa.getText());
            pstmt.setString(4, txtMaTroChoi.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cập nhật thông tin trò chơi thành công!");
            clearForm();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trò chơi!");
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
            return;
        }
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
        }
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("DanhSachTroChoi");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã Trò Chơi", "Tên Phân Khu", "Tên Trò Chơi", "Mô Tả"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (int i = 0; i < danhsachtrochoi.size(); i++) {
                troChoi tc = danhsachtrochoi.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(tc.getMaTroChoi());
                row.createCell(1).setCellValue(tc.getTenPhanKhu());
                row.createCell(2).setCellValue(tc.getTenTroChoi());
                row.createCell(3).setCellValue(tc.getMoTa());
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
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
        int selectedRow = tblThongTinTroChoi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trò chơi để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa trò chơi này không?",
                "Xác nhận xóa!", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConnectDB.getConnection();
                String SQL = "DELETE FROM troChoi WHERE maTroChoi = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, txtMaTroChoi.getText());
                pstmt.execute();
                JOptionPane.showMessageDialog(this, "Xóa trò chơi thành công!");
                clearForm();
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Không thể xóa vì trò chơi này đang được sử dụng trong bảng thiết bị!");
                //            foreignKeyError(e);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa trò chơi!");
                JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
            }
            loadDatatoArrayList();
            loadDataArrayListtoTable();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
//            String tuKhoa = txtTimKiem.getText().trim();
//    if (tuKhoa.isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên trò chơi hoặc mã trò chơi để tìm kiếm.");
//        return;
//    }
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String SQL = "SELECT tc.maTroChoi, tc.maPhanKhu, pk.tenPhanKhu, tc.tenTroChoi, tc.moTa "
//           + "FROM troChoi tc JOIN phanKhu pk ON tc.maPhanKhu = pk.maPhanKhu "
//           + "WHERE tc.maTroChoi LIKE N'%" + tuKhoa + "%' OR tc.tenTroChoi LIKE N'%" + tuKhoa + "%'";
    ////        System.out.println(SQL);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(SQL);
//        danhsachtrochoi.clear();
//        model.setRowCount(0);
//        while(rs.next()){
//                String maTroChoi = rs.getString("maTroChoi");
//                String maPhanKhu = rs.getString("maPhanKhu");
//                String tenPhanKhu = rs.getString("tenPhanKhu");
//                String tenTroChoi = rs.getString("tenTroChoi");
//                String moTa = rs.getString("moTa");
//                
//                troChoi tc = new troChoi(maTroChoi, maPhanKhu, tenPhanKhu, tenTroChoi, moTa);
//                danhsachtrochoi.add(tc);
//        }
//        if (danhsachtrochoi.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy trò chơi phù hợp.");
//        }
//        conn.close();
//        loadDataArrayListtoTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//              JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm trò chơi!");
//              JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        tblThongTinTroChoi.clearSelection();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }
        try {
            String tenPhanKhu = cboTenPhanKhu.getSelectedItem().toString();
            String maPhanKhu = getMaPhanKhuFromTenPhanKhu(tenPhanKhu);

            Connection conn = ConnectDB.getConnection();
            String SQL = "INSERT INTO troChoi (maTroChoi, maPhanKhu, tenTroChoi, moTa) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, txtMaTroChoi.getText().trim());
            pstmt.setString(2, maPhanKhu);
            pstmt.setString(3, txtTenTroChoi.getText().trim());
            pstmt.setString(4, txtMoTa.getText().trim());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm trò chơi thành công!");
            clearForm();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm trò chơi!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }

        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblThongTinTroChoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinTroChoiMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblThongTinTroChoi.getSelectedRow();
        if (selectedRow != -1) {
            String maTroChoi = model.getValueAt(selectedRow, 0).toString();
            String tenPhanKhu = model.getValueAt(selectedRow, 1).toString();
            String tenTroChoi = model.getValueAt(selectedRow, 2).toString();
            String moTa = model.getValueAt(selectedRow, 3).toString();

            txtMaTroChoi.setText(maTroChoi);
            txtMaTroChoi.setEnabled(false);
            cboTenPhanKhu.setSelectedItem(tenPhanKhu);
            txtTenTroChoi.setText(tenTroChoi);
            txtMoTa.setText(moTa);
        }
    }//GEN-LAST:event_tblThongTinTroChoiMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        String tuKhoa = txtTimKiem.getText().trim();
        danhsachtrochoi.clear();
//        float so =0;  //tìm kiếm mà có số
        try {
            Connection conn = ConnectDB.getConnection();
            //if(txtTimKiem.getText().isEmpty())
            //so = 0;
            //else if (txtTimkiem.getText().chars().allMatch(Character:isDigit))
            //so = Float.parseFloat(txtTimkiem.getText());
            // or Price = " + so + "   viết ở câu lệnh truy vấn 
            String SQL = "SELECT tc.maTroChoi, tc.maPhanKhu, pk.tenPhanKhu, tc.tenTroChoi, tc.moTa "
                    + "FROM troChoi tc JOIN phanKhu pk ON tc.maPhanKhu = pk.maPhanKhu "
                    + "WHERE tc.maTroChoi LIKE N'%" + tuKhoa + "%'"
                    + "OR tc.tenTroChoi LIKE N'%" + tuKhoa + "%'"
                    + "OR tc.maPhanKhu LIKE N'%" + tuKhoa + "%'"
                    + "OR tc.moTa LIKE N'%" + tuKhoa + "%'"
                    + "OR pk.tenPhanKhu LIKE N'%" + tuKhoa + "%'";
//        System.out.println(SQL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String maTroChoi = rs.getString("maTroChoi");
                String maPhanKhu = rs.getString("maPhanKhu");
                String tenPhanKhu = rs.getString("tenPhanKhu");
                String tenTroChoi = rs.getString("tenTroChoi");
                String moTa = rs.getString("moTa");

                troChoi tc = new troChoi(maTroChoi, maPhanKhu, tenPhanKhu, tenTroChoi, moTa);
                danhsachtrochoi.add(tc);
            }
            conn.close();
            loadDataArrayListtoTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm trò chơi!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cboTenPhanKhuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenPhanKhuItemStateChanged
        // TODO add your handling code here:
//        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
//            String tenPhanKhu = cboTenPhanKhu.getSelectedItem().toString();
//            loadDataTheoPhanKhu(tenPhanKhu);
//        }
    }//GEN-LAST:event_cboTenPhanKhuItemStateChanged

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void loadDataTheoPhanKhu(String tenPhanKhu) {
        model.setRowCount(0);
        for (troChoi tc : danhsachtrochoi) {
            if (tenPhanKhu.equals("---Chọn tên phân khu---") || tc.getTenPhanKhu().equals(tenPhanKhu)) {
                model.addRow(new Object[]{
                    tc.getMaTroChoi(),
                    tc.getTenPhanKhu(),
                    tc.getTenTroChoi(),
                    tc.getMoTa()
                });
            }
        }
    }

//    public void loadTenPhanKhuToComboBox() {
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String SQL = "SELECT tenPhanKhu FROM phanKhu";
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(SQL);
//        cboMaPhanKhu.removeAllItems();
//        cboMaPhanKhu.addItem("--CHỌN TÊN PHÂN KHU--"); // Thêm dòng mặc định
//        while (rs.next()) {
//            cboMaPhanKhu.addItem(rs.getString("tenPhanKhu"));
//        }
//        conn.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tên phân khu");
//    }
//}
//    
//    public String getMaPhanKhuFromTenPhanKhu(String tenPhanKhu) {
//    String maPhanKhu = "";
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String sql = "SELECT maPhanKhu FROM phanKhu WHERE tenPhanKhu = ?";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, tenPhanKhu);
//        ResultSet rs = pstmt.executeQuery();
//        if (rs.next()) {
//            maPhanKhu = rs.getString("maPhanKhu");
//        }
//        conn.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã phân khu: " + e.getMessage());
//    }
//    return maPhanKhu;
//}
//    String tenPhanKhu = (String) cboMaPhanKhu.getSelectedItem();
//      if (tenPhanKhu.equals("--CHỌN TÊN PHÂN KHU--")) {
//    JOptionPane.showMessageDialog(this, "Vui lòng chọn tên phân khu hợp lệ!");
//    return;
//}
//String maPhanKhu = getMaPhanKhuFromTenPhanKhu(tenPhanKhu);
//        if (!validateInput()) {
//        return; // Nếu dữ liệu không hợp lệ thì dừng
//    }
//        try {
//        Connection conn = ConnectDB.getConnection();
//        String sql = "INSERT INTO troChoi (maTroChoi, maPhanKhu, tenTroChoi, moTa) VALUES (?, ?, ?, ?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, txtMaTroChoi.getText());
//        pstmt.setString(2, maPhanKhu);
//        pstmt.setString(3, txtTenTroChoi.getText());
//        pstmt.setString(4, txtMoTa.getText());
//        pstmt.executeUpdate();
//
//        JOptionPane.showMessageDialog(this, "Thêm trò chơi thành công!");
//        clearForm();
//        conn.close();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Lỗi khi thêm trò chơi!");
//        JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//    }
//
//    loadDatatoArrayList();
//    loadDataArrayListtoTable();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboTenPhanKhu;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaTrochoi;
    private javax.swing.JLabel labelMota;
    private javax.swing.JLabel labelTenPK;
    private javax.swing.JLabel labelTenTrochoi;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JLabel lblTenPhanKhu;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinTroChoi;
    private javax.swing.JTextField txtMaTroChoi;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenTroChoi;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
