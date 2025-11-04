/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import Database.ConnectDB;
import Model.phanKhu;
import Model.taiKhoan;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class formPhanKhu extends javax.swing.JPanel {

    /**
     * Creates new form formPhanKhu
     */
    public formPhanKhu() {
        initComponents();
        model = (DefaultTableModel) tblThongTinPhanKhu.getModel();
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }

    DefaultTableModel model;
    private List<phanKhu> danhsachphankhu = new ArrayList<>();
    
    public void loadDatatoArrayList(){
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String SQL = "SELECT * FROM phanKhu";
            ResultSet rs = stmt.executeQuery(SQL);
            danhsachphankhu.clear();
            
            while(rs.next()){
                String maPhanKhu = rs.getString("maPhanKhu");
                String tenPhanKhu = rs.getString("tenPhanKhu");
                String moTa = rs.getString("moTa");
                String viTri = rs.getString("viTri");
                float dientich = rs.getFloat("dienTich");
            
                phanKhu pk = new phanKhu(maPhanKhu, tenPhanKhu, moTa, viTri, dientich);
                danhsachphankhu.add(pk);
            }
            conn.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "lỗi khi tải dữ liệu từ database!");
        }
    }
    
    public void loadDataArrayListtoTable(){
        model.setRowCount(0);
//      model = (DefaultTableModel) tblThongTinNhanVien.getModel();
        for(phanKhu pk : danhsachphankhu){
            model.addRow(new Object[]{ 
            pk.getMaPhanKhu(), pk.getTenPhanKhu(), pk.getMoTa(), pk.getViTri(), pk.getDienTich()});
        }
    }
    
    private void clearForm(){
        txtMaPhanKhu.setText("");
        txtTenPhanKhu.setText("");
        txtMoTa.setText("");
        txtViTri.setText("");
        txtDienTich.setText("");
        txtTimKiem.setText("");
        txtMaPhanKhu.setEnabled(true);
        txtTenPhanKhu.setEnabled(true);
    }
    
    public boolean validateInput() {
    String maPhanKhu = txtMaPhanKhu.getText().trim();
    String tenPhanKhu = txtTenPhanKhu.getText().trim();
    String moTa = txtMoTa.getText().trim();
    String viTri = txtViTri.getText().trim();
    String dienTichStr = txtDienTich.getText().trim();

    // Kiểm tra rỗng
    if (maPhanKhu.isEmpty() || tenPhanKhu.isEmpty() || moTa.isEmpty() || viTri.isEmpty() || dienTichStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phân khu.");
        return false;
    }

    // Kiểm tra định dạng diện tích
    float dienTich;
    try {
        dienTich = Float.parseFloat(dienTichStr);
        if (dienTich <= 0) {
            JOptionPane.showMessageDialog(null, "Diện tích phải lớn hơn 0.");
            txtDienTich.requestFocus();
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số cho diện tích.");
        txtDienTich.requestFocus();
        return false;
    }

    // Kiểm tra trùng mã và tên phân khu
    for (phanKhu pk : danhsachphankhu) {
        if (pk.getMaPhanKhu().equals(maPhanKhu)) {
            JOptionPane.showMessageDialog(null, "Mã phân khu đã tồn tại!");
            txtMaPhanKhu.requestFocus();
            return false;
        }
        if (pk.getTenPhanKhu().equals(tenPhanKhu)) {
            JOptionPane.showMessageDialog(null, "Tên phân khu đã tồn tại!");
            txtTenPhanKhu.requestFocus();
            return false;
        }
    }

    return true; // hợp lệ
}
    
    public boolean validateInputSUA() {
    String maPhanKhu = txtMaPhanKhu.getText().trim();
    String tenPhanKhu = txtTenPhanKhu.getText().trim();
    String moTa = txtMoTa.getText().trim();
    String viTri = txtViTri.getText().trim();
    String dienTichStr = txtDienTich.getText().trim();

    // Kiểm tra rỗng
    if (maPhanKhu.isEmpty() || tenPhanKhu.isEmpty() || moTa.isEmpty() || viTri.isEmpty() || dienTichStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phân khu.");
        return false;
    }

    // Kiểm tra định dạng diện tích
    float dienTich;
    try {
        dienTich = Float.parseFloat(dienTichStr);
        if (dienTich <= 0) {
            JOptionPane.showMessageDialog(null, "Diện tích phải lớn hơn 0.");
            txtDienTich.requestFocus();
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số cho diện tích.");
        txtDienTich.requestFocus();
        return false;
    }
    return true; // hợp lệ
}
    
//    private void foreignKeyError(SQLException e) {
//    String errorMessage = e.getMessage();
//    if (errorMessage != null && errorMessage.toLowerCase().contains("foreign key")) {
//        JOptionPane.showMessageDialog(this, "Không thể xóa vì phân khu này đang được sử dụng trong bảng trò chơi!");
//    } else {
//        JOptionPane.showMessageDialog(this, "Lỗi khi xóa phân khu!");
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
        labelMaPK = new javax.swing.JLabel();
        labelTenPK = new javax.swing.JLabel();
        labelMota = new javax.swing.JLabel();
        txtMaPhanKhu = new javax.swing.JTextField();
        txtTenPhanKhu = new javax.swing.JTextField();
        txtDienTich = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelVitri = new javax.swing.JLabel();
        labelDienTich = new javax.swing.JLabel();
        txtViTri = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinPhanKhu = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("QUẢN LÝ PHÂN KHU");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/area-graph.png"))); // NOI18N

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

        labelMaPK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelMaPK.setText("Mã phân khu: ");

        labelTenPK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelTenPK.setText("Tên phân khu: ");

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

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
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

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/area-chart.png"))); // NOI18N

        labelVitri.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelVitri.setText("Vị trí:");

        labelDienTich.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelDienTich.setText("Diện tích:");

        txtMoTa.setColumns(20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        txtMoTa.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtMoTa);

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDienTich)
                    .addComponent(labelMaPK, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTenPK)
                    .addComponent(labelVitri)
                    .addComponent(labelMota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtMaPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2)
                    .addComponent(txtViTri, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(labelTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addComponent(labelIMG)
                        .addGap(38, 38, 38)))
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(btnTimKiem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(labelIMG)
                        .addGap(27, 27, 27))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMaPK, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTenPK, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMota, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelVitri, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
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
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinPhanKhu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phân khu", "Tên phân khu", "Mô tả", "Vị trí", "Diện tích"
            }
        ));
        tblThongTinPhanKhu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinPhanKhuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinPhanKhu);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabelPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
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
        int selectedRow = tblThongTinPhanKhu.getSelectedRow();
        if(selectedRow == -1){
        JOptionPane.showMessageDialog(this,"Vui lòng chọn phân khu để sửa thông tin!");
        return;
        }
     // Kiểm tra 
        if(! validateInputSUA()){
            return; // dừng lại nếu không hợp lệ
        }
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "UPDATE phanKhu SET moTa = ?, viTri = ?, dienTich = ? WHERE maPhanKhu = ?";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, txtMoTa.getText());
            pstmt.setString(2, txtViTri.getText());
            pstmt.setFloat(3, Float.parseFloat(txtDienTich.getText()));
            pstmt.setString(4, txtMaPhanKhu.getText());
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Đổi thông tin phân khu thành công!");
            clearForm();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Lỗi khi đổi thông tin phân khu!");
            JOptionPane.showMessageDialog(this,"Chi tiết lỗi: " + e.getMessage());
        }
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        try {
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM phanKhu";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        // Tạo workbook Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách Phân Khu");
        // Tạo dòng tiêu đề
        String[] headers = {"Mã phân khu", "Tên phân khu", "Mô tả", "Vị trí", "Diện tích"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        // Ghi dữ liệu từng dòng
        int rowIndex = 1;
        while (rs.next()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(rs.getString("maPhanKhu"));
            row.createCell(1).setCellValue(rs.getString("tenPhanKhu"));
            row.createCell(2).setCellValue(rs.getString("moTa"));
            row.createCell(3).setCellValue(rs.getString("viTri"));
            row.createCell(4).setCellValue(rs.getFloat("dienTich"));
        }
        // Tự động resize các cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // Mở hộp thoại để chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();            
            // Đảm bảo tên file có đuôi .xlsx
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            // Ghi file ra đĩa
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
        int selectedRow = tblThongTinPhanKhu.getSelectedRow();
        if(selectedRow == -1){
        JOptionPane.showMessageDialog(this,"Vui lòng chọn phân khu để xóa!");
        return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phân khu này không?",
                "Xác nhận xóa!", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "DELETE FROM phanKhu WHERE maPhanKhu = ?";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, txtMaPhanKhu.getText());
            pstmt.execute();
            JOptionPane.showMessageDialog(this, "Xóa phân khu thành công!");
            clearForm();
            conn.close();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, "Không thể xóa vì phân khu này đang được sử dụng trong bảng trò chơi!");
//            foreignKeyError(e);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa phân khu!");
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
//        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phân khu hoặc mã phân khu để tìm kiếm.");
//        return;
//    }
//    try {
//        Connection conn = ConnectDB.getConnection();
//        String SQL = "SELECT * FROM phanKhu WHERE maPhanKhu LIKE N'%" + txtTimKiem.getText().trim() +"%' "
//                + "OR tenPhanKhu LIKE N'%" + txtTimKiem.getText().trim() +"%'  ";
////        System.out.println(SQL);
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(SQL);
//        danhsachphankhu.clear();
//        model.setRowCount(0);
//        while(rs.next()){
//                String maPhanKhu = rs.getString("maPhanKhu");
//                String tenPhanKhu = rs.getString("tenPhanKhu");
//                String moTa = rs.getString("moTa");
//                String viTri = rs.getString("viTri");
//                float dientich = rs.getFloat("dienTich");
//            
//                phanKhu pk = new phanKhu(maPhanKhu, tenPhanKhu, moTa, viTri, dientich);
//                danhsachphankhu.add(pk);
//        }
//        if (danhsachphankhu.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy phân khu phù hợp.");
//        }
//        conn.close();
//        loadDataArrayListtoTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//              JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm phân khu!");
//              JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
//        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(! validateInput()){
            return; // dừng lại nếu không hợp lệ
        }
        try {
            Connection conn = ConnectDB.getConnection();
            String SQL = "INSERT INTO phanKhu (maPhanKhu, tenPhanKhu, moTa, viTri, dienTich) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, txtMaPhanKhu.getText());
            pstmt.setString(2, txtTenPhanKhu.getText());
            pstmt.setString(3, txtMoTa.getText());
            pstmt.setString(4, txtViTri.getText());
            pstmt.setFloat(5, Float.parseFloat(txtDienTich.getText()));
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this,"Thêm phân khu thành công!");
            clearForm();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Lỗi khi thêm phân khu!");
            JOptionPane.showMessageDialog(this, "Chi tiết lỗi" + e.getMessage());
        }
        loadDatatoArrayList();
        loadDataArrayListtoTable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblThongTinPhanKhuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinPhanKhuMouseClicked
        // TODO add your handling code here:
        int SelectedRow = tblThongTinPhanKhu.getSelectedRow();
        if(SelectedRow != -1){
            //Lấy dữ liệu từ bảng
            String maPhanKhu = model.getValueAt(SelectedRow, 0).toString();
            String tenPhanKhu = model.getValueAt(SelectedRow, 1).toString();
            String moTa = model.getValueAt(SelectedRow, 2).toString();
            String viTri = model.getValueAt(SelectedRow, 3).toString();
            float dienTich = Float.parseFloat(model.getValueAt(SelectedRow, 4).toString());
            
            //Đổ dữ liệu lại lên form
            txtMaPhanKhu.setText(maPhanKhu);
            txtMaPhanKhu.setEnabled(false);
            txtTenPhanKhu.setText(tenPhanKhu);
            txtTenPhanKhu.setEnabled(false);
            txtMoTa.setText(moTa);
            txtViTri.setText(viTri);
            txtDienTich.setText(String.valueOf(dienTich));
        }
    }//GEN-LAST:event_tblThongTinPhanKhuMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        tblThongTinPhanKhu.clearSelection();
        loadDatatoArrayList();
        loadDataArrayListtoTable(); 
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        danhsachphankhu.clear();
//        float so =0;  //tìm kiếm mà có số
        try {
        Connection conn = ConnectDB.getConnection();
        //if(txtTimKiem.getText().isEmpty())
        //so = 0;
        //else if (txtTimkiem.getText().chars().allMatch(Character:isDigit))
        //so = Float.parseFloat(txtTimkiem.getText());
        // or Price = " + so + "   viết ở câu lệnh truy vấn 
        String SQL = "SELECT * FROM phanKhu WHERE maPhanKhu LIKE N'%" + txtTimKiem.getText().trim() +"%' "
                + "OR tenPhanKhu LIKE N'%" + txtTimKiem.getText().trim() +"%' "
                + "OR viTri LIKE N'%" + txtTimKiem.getText().trim() +"%' "
                + "OR dienTich LIKE N'%" + txtTimKiem.getText().trim() +"%'";
//        System.out.println(SQL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while(rs.next()){
                String maPhanKhu = rs.getString("maPhanKhu");
                String tenPhanKhu = rs.getString("tenPhanKhu");
                String moTa = rs.getString("moTa");
                String viTri = rs.getString("viTri");
                float dientich = rs.getFloat("dienTich");
            
                phanKhu pk = new phanKhu(maPhanKhu, tenPhanKhu, moTa, viTri, dientich);
                danhsachphankhu.add(pk);
        }
        conn.close();
        loadDataArrayListtoTable();
        } catch (Exception e) {
            e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm tài khoản!");
              JOptionPane.showMessageDialog(this, "Chi tiết lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelDienTich;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaPK;
    private javax.swing.JLabel labelMota;
    private javax.swing.JLabel labelTenPK;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JLabel labelVitri;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinPhanKhu;
    private javax.swing.JTextField txtDienTich;
    private javax.swing.JTextField txtMaPhanKhu;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenPhanKhu;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
}
