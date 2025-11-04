/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import static Database.ConnectDB.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class formGuiDo extends javax.swing.JPanel {

        Connection con;
    DefaultTableModel model;
    Map<String, String> khachhangMap = new HashMap<>();
    /**
     * Creates new form formGuiDo
     */
    public formGuiDo() {
        initComponents();
        //load_cboMaKhachHang();
        loadmaKhachHangToComboBox();
        load_ds();
        // Khóa tất cả các ô khác, chỉ cho chỉnh sửa thời gian gửi
        txtMaGuiDo.setEnabled(false);
        txtMaTuDo.setEnabled(false);
        cboMaKhachHang.setEnabled(false);
        ThoiGianNhan.setEnabled(false);
        ThoiGianGui.setEnabled(false); // Cho phép chỉnh sửa
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
    }
    
    private boolean ChecktrungMaTD(String maTuDo) {
        Boolean kq = false;
        try {
            con = getConnection();
            // b2 : tao đối tượng preparestatement 
            String sql = "Select * from guiDo Where maTuDo=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maTuDo);
            //tao doi tương resultset de lay ket qua tu ps 
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                kq = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kq;
    }
    
    public boolean khongDeTrong_GuiDo() {

        if (txtMaTuDo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã tủ đồ không được để trống!");
            return false;
        }

        if (cboMaKhachHang.getSelectedItem() == "---Chọn mã khách hàng---") {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
            return false;
        }

        if (ThoiGianGui.getDateTimeStrict() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian gửi!");
            return false;
        }

        return true;
    }
    
    public void loadmaKhachHangToComboBox() {
        try {
            Connection con = getConnection();
            String sql = "SELECT maKhachHang FROM khachHang;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboMaKhachHang.removeAllItems(); // Xóa các mục cũ
            cboMaKhachHang.addItem("---Chọn mã khách hàng---");

            while (rs.next()) {
                String maKhachHang = rs.getString("maKhachHang");
                cboMaKhachHang.addItem(maKhachHang);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm mã phân khu vào combo box");
            JOptionPane.showMessageDialog(this, "Lỗi" + e.getMessage());
        }
    }

    private void hienThitenKhachHang(String maKhachHang) {
        try {
            Connection con = getConnection();
            String sql = "SELECT hoTen FROM khachHang WHERE maKhachHang = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maKhachHang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String hoTen = rs.getString("hoTen");
                lbTenKhachHang.setText(hoTen);  // Gán lên label
            } else {
                lbTenKhachHang.setText("");  // Không tìm thấy thì để trống
            }
            //        conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            lbTenKhachHang.setText("Lỗi DB");
        }
    }

    private void load_ds() {
        model = (DefaultTableModel) tblThongTinGuiDo.getModel();
        model.setRowCount(0);
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guiDo");

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("maGuiDo"),
                    rs.getString("maTuDo"),
                    rs.getString("maKhachHang"),
                    rs.getTimestamp("thoiGianGui"),
                    rs.getTimestamp("thoiGianNhan")
                };
                model.addRow(row);
            }

            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        labelMaTD = new javax.swing.JLabel();
        labelTGGui = new javax.swing.JLabel();
        txtMaGuiDo = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelMaKH = new javax.swing.JLabel();
        labelMaGD = new javax.swing.JLabel();
        txtMaTuDo = new javax.swing.JTextField();
        ThoiGianGui = new com.github.lgooddatepicker.components.DateTimePicker();
        cboMaKhachHang = new javax.swing.JComboBox<>();
        labelMaKH1 = new javax.swing.JLabel();
        lbTenKhachHang = new javax.swing.JLabel();
        labelTGGui1 = new javax.swing.JLabel();
        ThoiGianNhan = new com.github.lgooddatepicker.components.DateTimePicker();
        btnThemMoi = new javax.swing.JButton();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinGuiDo = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setText("QUẢN LÝ GỬI ĐỒ");
        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/safe.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
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

        labelMaTD.setText("Mã tủ đồ:");
        labelMaTD.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        labelTGGui.setText("Thời gian gửi:");
        labelTGGui.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-add-25.png"))); // NOI18N
        btnThem.setText("THÊM");
        btnThem.setBackground(new java.awt.Color(76, 175, 80));
        btnThem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-pencil-25.png"))); // NOI18N
        btnSua.setText("SỬA");
        btnSua.setBackground(new java.awt.Color(76, 175, 80));
        btnSua.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-xls-export-25.png"))); // NOI18N
        btnXuatDuLieu.setText("XUẤT DỮ LIỆU");
        btnXuatDuLieu.setBackground(new java.awt.Color(76, 175, 80));
        btnXuatDuLieu.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXuatDuLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-update-25.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBackground(new java.awt.Color(76, 175, 80));
        btnLamMoi.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        labelTimKiem.setText("Tìm kiếm:");
        labelTimKiem.setBackground(new java.awt.Color(51, 153, 255));
        labelTimKiem.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        labelTimKiem.setForeground(new java.awt.Color(51, 153, 255));

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-delete-25.png"))); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.setBackground(new java.awt.Color(76, 175, 80));
        btnXoa.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-search-25.png"))); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.setBackground(new java.awt.Color(76, 175, 80));
        btnTimKiem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/deposit.png"))); // NOI18N

        labelMaKH.setText("Mã khách hàng:");
        labelMaKH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        labelMaGD.setText("Mã gửi đồ:");
        labelMaGD.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        cboMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaKhachHangActionPerformed(evt);
            }
        });

        labelMaKH1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaKH1.setText("Tên khách hàng:");

        lbTenKhachHang.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N

        labelTGGui1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTGGui1.setText("Thời gian nhận:");

        btnThemMoi.setBackground(new java.awt.Color(76, 175, 80));
        btnThemMoi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnThemMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-add-25.png"))); // NOI18N
        btnThemMoi.setText(" THÊM MỚI");
        btnThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiActionPerformed(evt);
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
                        .addComponent(labelMaKH1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(labelTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                                .addComponent(labelIMG)
                                .addGap(38, 38, 38)))
                        .addGap(7, 7, 7))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, informationPanelLayout.createSequentialGroup()
                                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThemMoi))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, informationPanelLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, informationPanelLayout.createSequentialGroup()
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelMaTD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelTGGui)
                                        .addComponent(labelMaKH)
                                        .addComponent(labelMaGD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(labelTGGui1))
                                .addGap(18, 18, 18)
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMaGuiDo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                    .addComponent(txtMaTuDo, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ThoiGianGui, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboMaKhachHang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ThoiGianNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(labelIMG)
                        .addGap(18, 29, Short.MAX_VALUE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaGuiDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMaGD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaTD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaTuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addComponent(labelMaKH1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelTGGui, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ThoiGianGui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelTGGui1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ThoiGianNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa)
                        .addComponent(btnLamMoi)
                        .addComponent(btnSua)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDuLieu)
                    .addComponent(btnTimKiem)
                    .addComponent(btnThemMoi))
                .addContainerGap())
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinGuiDo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã gửi đồ", "Mã tủ đồ", "Mã khách hàng", "Thời gian gửi", "Thời gian nhận"
            }
        ));
        tblThongTinGuiDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinGuiDoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinGuiDo);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabelPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
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
        
        try {
            String maGuiDo = txtMaGuiDo.getText().trim();
            String maTuDo = txtMaTuDo.getText().trim();
            String maKhachHang = cboMaKhachHang.getSelectedItem().toString();
            LocalDateTime thoiGianGui = ThoiGianGui.getDateTimeStrict();
            LocalDateTime thoiGianNhan = ThoiGianNhan.getDateTimeStrict();

            // Kiểm tra dữ liệu rỗng
            if (!khongDeTrong_GuiDo()) {
                return;
            }

            // Kiểm tra thời gian nhận phải sau thời gian gửi
            if (thoiGianNhan != null && !thoiGianNhan.isAfter(thoiGianGui)) {
                JOptionPane.showMessageDialog(this, "Thời gian nhận phải lớn hơn thời gian gửi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            con = getConnection();

            String sql = "UPDATE guiDo SET maTuDo = ?, maKhachHang = ?, thoiGianGui = ?, thoiGianNhan = ? WHERE maGuiDo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maTuDo);
            ps.setString(2, maKhachHang);
            ps.setTimestamp(3, Timestamp.valueOf(thoiGianGui));

            if (thoiGianNhan != null) {
                ps.setTimestamp(4, Timestamp.valueOf(thoiGianNhan));
            } else {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }

            ps.setString(5, maGuiDo);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            con.close();
            load_ds();

            // Reset form
            txtMaGuiDo.setText("");
            txtMaTuDo.setText("");
            cboMaKhachHang.setSelectedIndex(0);
            ThoiGianGui.clear();
            ThoiGianNhan.clear();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        btnThem.setEnabled(true);
        btnThemMoi.setEnabled(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        String maGuiDo = txtMaGuiDo.getText().trim();

        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa gửi đồ có mã: " + maGuiDo + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                con = getConnection();
                String sql = "DELETE FROM guiDo WHERE maGuiDo = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, maGuiDo);

                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");

                    // Reset form
                    txtMaGuiDo.setText("");
                    txtMaTuDo.setText("");
                    cboMaKhachHang.setSelectedIndex(0);
                    ThoiGianGui.clear();
                    ThoiGianNhan.clear();

                    load_ds(); // Tải lại bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã gửi đồ để xóa.");
                }

                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa gửi đồ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        btnThem.setEnabled(true);
        btnThemMoi.setEnabled(true);
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiActionPerformed
        // TODO add your handling code here:
        LocalDateTime now = LocalDateTime.now();
        ThoiGianGui.setDateTimeStrict(now);

        txtMaTuDo.setEnabled(true);
        cboMaKhachHang.setEnabled(true);
        ThoiGianNhan.setEnabled(true);
        ThoiGianGui.setEnabled(true);
    }//GEN-LAST:event_btnThemMoiActionPerformed

    private void tblThongTinGuiDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinGuiDoMouseClicked
        // TODO add your handling code here:
        int i = tblThongTinGuiDo.getSelectedRow();
        DefaultTableModel tb = (DefaultTableModel) tblThongTinGuiDo.getModel();

        txtMaGuiDo.setText(tb.getValueAt(i, 0).toString());
        txtMaTuDo.setText(tb.getValueAt(i, 1).toString());
        cboMaKhachHang.setSelectedItem(tb.getValueAt(i, 2).toString());

        ThoiGianGui.setDateTimeStrict(((Timestamp) tb.getValueAt(i, 3)).toLocalDateTime());

        Object tgNhan = tb.getValueAt(i, 4); // Cột 4 là thời gian nhận

        if (tgNhan != null) {
            // Nếu có dữ liệu thì ép kiểu và gán
            LocalDateTime thoiGianNhan = ((Timestamp) tgNhan).toLocalDateTime();
            ThoiGianNhan.setDateTimeStrict(thoiGianNhan);
        } else {
            // Nếu null thì truyền chuỗi rỗng hoặc xóa dữ liệu
            ThoiGianNhan.clear();
        }
        txtMaGuiDo.setEnabled(false);
        txtMaTuDo.setEnabled(false);
        cboMaKhachHang.setEnabled(false);
        ThoiGianGui.setEnabled(false);
        ThoiGianNhan.setEnabled(true);
        btnXoa.setEnabled(true);
        btnSua.setEnabled(true);
        btnThem.setEnabled(false);
        btnThemMoi.setEnabled(false);

    }//GEN-LAST:event_tblThongTinGuiDoMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        // Lấy dữ liệu 
        LocalDateTime thoiGianGui = LocalDateTime.now();

        String maTuDo = txtMaTuDo.getText().trim();
        String maKhachHang = cboMaKhachHang.getSelectedItem().toString();
        LocalDateTime thoiGianNhan = ThoiGianNhan.getDateTimeStrict();

        if (!ChecktrungMaTD(maTuDo)) {
            JOptionPane.showMessageDialog(this, "Trùng mã loại");
            return;
        }
// Kiểm tra dữ liệu rỗng
        if (!khongDeTrong_GuiDo()) {
            return;
        }

// Nếu có chọn thời gian nhận, thì kiểm tra phải sau thời gian gửi
        if (thoiGianNhan != null && !thoiGianNhan.isAfter(thoiGianGui)) {
            JOptionPane.showMessageDialog(this, "Thời gian nhận phải lớn hơn thời gian gửi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            con = getConnection();

            // KHÔNG chèn cột maGuiDo (vì tự tăng)
            String sql = "INSERT INTO guiDo (maTuDo, maKhachHang, thoiGianGui, thoiGianNhan) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maTuDo);
            st.setString(2, maKhachHang);
            st.setTimestamp(3, Timestamp.valueOf(thoiGianGui));
            if (thoiGianNhan != null) {
                st.setTimestamp(4, Timestamp.valueOf(thoiGianNhan));
            } else {
                st.setNull(4, java.sql.Types.TIMESTAMP);
            }

            st.executeUpdate();

            // Reset form
            txtMaGuiDo.setText("");
            txtMaTuDo.setText("");
            cboMaKhachHang.setSelectedIndex(0);
            ThoiGianGui.clear();
            ThoiGianNhan.clear();

            con.close();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            load_ds();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
                txtMaGuiDo.setText("");
        txtMaTuDo.setText("");
        cboMaKhachHang.setSelectedIndex(0);
        ThoiGianGui.clear();
        ThoiGianNhan.clear();

        // Reset trạng thái nút
        btnThem.setEnabled(true);
        btnThemMoi.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        load_ds();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cboMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaKhachHangActionPerformed
        // TODO add your handling code here:
                String maKhachHang = (String) cboMaKhachHang.getSelectedItem();
        if (maKhachHang != null && !maKhachHang.equals("---Chọn mã khách hàng---")) {
            hienThitenKhachHang(maKhachHang);  // Gọi hàm để hiển thị tên nhân viên ra label
        } else {
            lbTenKhachHang.setText("");  // Nếu là dòng mặc định thì xóa label
        }
    }//GEN-LAST:event_cboMaKhachHangActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
         String timkiem = txtTimKiem.getText().trim();
        try {
            con = getConnection();

            String sql = "SELECT * FROM guiDo WHERE maKhachHang LIKE ? OR maTuDo LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            String keyword = "%" + timkiem + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);

            ResultSet rs = ps.executeQuery();

            // Cập nhật tiêu đề bảng đúng với bảng guiDo
            String[] td = {"Mã Gửi Đồ", "Mã Tủ Đồ", "Mã Khách Hàng", "Thời Gian Gửi", "Thời Gian Nhận"};
            DefaultTableModel tb = new DefaultTableModel(td, 0);

            while (rs.next()) {
                Vector vt = new Vector();
                vt.add(rs.getString("maGuiDo"));
                vt.add(rs.getString("maTuDo"));
                vt.add(rs.getString("maKhachHang"));
                vt.add(rs.getTimestamp("thoiGianGui"));
                vt.add(rs.getTimestamp("thoiGianNhan"));  // có thể null
                tb.addRow(vt);
            }

            tblThongTinGuiDo.setModel(tb);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage());
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DateTimePicker ThoiGianGui;
    private com.github.lgooddatepicker.components.DateTimePicker ThoiGianNhan;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemMoi;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboMaKhachHang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaGD;
    private javax.swing.JLabel labelMaKH;
    private javax.swing.JLabel labelMaKH1;
    private javax.swing.JLabel labelMaTD;
    private javax.swing.JLabel labelTGGui;
    private javax.swing.JLabel labelTGGui1;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JLabel lbTenKhachHang;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinGuiDo;
    private javax.swing.JTextField txtMaGuiDo;
    private javax.swing.JTextField txtMaTuDo;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
