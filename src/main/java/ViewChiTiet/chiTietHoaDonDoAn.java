/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewChiTiet;

import Database.ConnectDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class chiTietHoaDonDoAn extends javax.swing.JFrame {

    /**
     * Creates new form chiTietHoaDonDoAn
     */
    public chiTietHoaDonDoAn() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        loadThangComboBox();
        loadNamComboBox();
        loadTenNhanVienComboBox();
        loadHoaDonCards();
    }

    private JPanel createHoaDonCard(String maHoaDon, String ngayMua, String tenNhanVien, List<String> chiTietDoAn, int tongTien) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblMaHoaDon = new JLabel("Mã hóa đơn: " + maHoaDon);
        JLabel lblNgayMua = new JLabel("Ngày mua: " + ngayMua);
        JLabel lblTenNV = new JLabel("Tên nhân viên: " + tenNhanVien);
        JLabel lblChiTietTitle = new JLabel("Chi tiết hóa đơn:");

        lblMaHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNgayMua.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenNV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblChiTietTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblMaHoaDon);
        content.add(Box.createVerticalStrut(5));
        content.add(lblNgayMua);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTenNV);
        content.add(Box.createVerticalStrut(5));
        content.add(lblChiTietTitle);

        for (String detail : chiTietDoAn) {
            JLabel lblChiTiet = new JLabel(detail);
            lblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            content.add(lblChiTiet);
            content.add(Box.createVerticalStrut(3));
        }

        JLabel lblTongTien = new JLabel("Tổng tiền: " + tongTien + " VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 13));
        content.add(Box.createVerticalStrut(5));
        content.add(lblTongTien);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return card;
    }

    private void loadHoaDonCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT hd.maHoaDon, hd.ngayMua, nv.tenNhanVien, hd.tongTien "
                    + "FROM hoaDonDoAn hd JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            pnlDanhSachHoaDon.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maHD = rs.getString("maHoaDon");
                String ngayMua = rs.getString("ngayMua");
                String tenNV = rs.getString("tenNhanVien");
                int tongTien = rs.getInt("tongTien");

                // Lấy chi tiết hóa đơn
                List<String> chiTietDoAn = new ArrayList<>();
                String chiTietSql = "SELECT da.tenDoAn, cthd.soLuong "
                        + "FROM chiTietHoaDonDoAn cthd JOIN banDoAn da ON cthd.maDoAn = da.maDoAn "
                        + "WHERE cthd.maHoaDon = ?";
                PreparedStatement pstmtChiTiet = conn.prepareStatement(chiTietSql);
                pstmtChiTiet.setString(1, maHD);
                ResultSet rsChiTiet = pstmtChiTiet.executeQuery();

                while (rsChiTiet.next()) {
                    String tenDoAn = rsChiTiet.getString("tenDoAn");
                    int soLuong = rsChiTiet.getInt("soLuong");
                    chiTietDoAn.add("    Đồ ăn: " + tenDoAn + " - Số lượng: " + soLuong);
                }

                JPanel card = createHoaDonCard(maHD, ngayMua, tenNV, chiTietDoAn, tongTien);
                pnlDanhSachHoaDon.add(card);
            }

            lblTongHoaDon.setText(String.valueOf(count));
            pnlDanhSachHoaDon.revalidate();
            pnlDanhSachHoaDon.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn!");
        }
    }

    private void loadThangComboBox() {
        cboThang.addItem("Chọn tháng");
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(String.valueOf(i));
        }
    }

    public void loadNamComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT YEAR(ngayMua) AS nam FROM hoaDonDoAn ORDER BY nam";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboNam.addItem("Chọn năm");
            while (rs.next()) {
                cboNam.addItem(rs.getString("nam"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách năm!");
        }
    }

    public void loadTenNhanVienComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT tenNhanVien FROM NhanVien WHERE chucVu = N'Nhân viên bán hàng' ORDER BY tenNhanVien";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboNhanVien.addItem("Chọn nhân viên");
            while (rs.next()) {
                cboNhanVien.addItem(rs.getString("tenNhanVien"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhân viên!");
        }
    }

    private boolean validateInput() {
        if (!cboThang.getSelectedItem().equals("Chọn tháng") && cboNam.getSelectedItem().equals("Chọn năm")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm khi đã chọn tháng.");
            return false;
        }
        return true;
    }

    private void refreshData() {
        cboNhanVien.setSelectedItem("Chọn nhân viên");
        cboThang.setSelectedItem("Chọn tháng");
        cboNam.setSelectedItem("Chọn năm");
        loadHoaDonCards();
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
        jLabel1 = new javax.swing.JLabel();
        lblTongHoaDon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachHoaDon = new javax.swing.JPanel();
        cboNam = new javax.swing.JComboBox<>();
        cboThang = new javax.swing.JComboBox<>();
        cboNhanVien = new javax.swing.JComboBox<>();
        btnLamMoi = new javax.swing.JButton();
        btnApDung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ HÓA ĐƠN ĐỒ ĂN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/bill.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerImgae)
                .addGap(122, 122, 122))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae)
                    .addComponent(headerLabel))
                .addGap(10, 10, 10))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số hóa đơn:");

        lblTongHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        lblTongHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongHoaDon.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachHoaDon.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachHoaDon);

        cboNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });

        cboThang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThangItemStateChanged(evt);
            }
        });

        cboNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboNhanVien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNhanVienItemStateChanged(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnApDung.setText("Áp dụng");
        btnApDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApDungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboNhanVien, 0, 146, Short.MAX_VALUE)
                    .addComponent(cboThang, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnApDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLamMoi)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblTongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboNam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLamMoi)
                            .addComponent(btnApDung))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNamItemStateChanged

    private void cboThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThangItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThangItemStateChanged

    private void cboNhanVienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNhanVienItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNhanVienItemStateChanged

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        refreshData();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }

        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT hd.maHoaDon, hd.ngayMua, nv.tenNhanVien, hd.tongTien "
                    + "FROM hoaDonDoAn hd JOIN NhanVien nv ON hd.maNhanVien = nv.maNhanVien";

            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (!cboNhanVien.getSelectedItem().equals("Chọn nhân viên")) {
                conditions.add("nv.tenNhanVien = ?");
                params.add(cboNhanVien.getSelectedItem().toString());
            }

            if (!cboThang.getSelectedItem().equals("Chọn tháng")
                    && !cboNam.getSelectedItem().equals("Chọn năm")) {
                conditions.add("MONTH(hd.ngayMua) = ? AND YEAR(hd.ngayMua) = ?");
                params.add(Integer.parseInt(cboThang.getSelectedItem().toString()));
                params.add(Integer.parseInt(cboNam.getSelectedItem().toString()));
            } else if (!cboNam.getSelectedItem().equals("Chọn năm")) {
                conditions.add("YEAR(hd.ngayMua) = ?");
                params.add(Integer.parseInt(cboNam.getSelectedItem().toString()));
            }

            if (!conditions.isEmpty()) {
                sql += " WHERE " + String.join(" AND ", conditions);
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachHoaDon.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maHD = rs.getString("maHoaDon");
                String ngayMua = rs.getString("ngayMua");
                String tenNV = rs.getString("tenNhanVien");
                int tongTien = rs.getInt("tongTien");

                List<String> chiTietDoAn = new ArrayList<>();
                String detailSql = "SELECT da.tenDoAn, cthd.soLuong FROM chiTietHoaDonDoAn cthd JOIN banDoAn da ON cthd.maDoAn = da.maDoAn WHERE maHoaDon = ?";
                PreparedStatement detailStmt = conn.prepareStatement(detailSql);
                detailStmt.setString(1, maHD);
                ResultSet rsDetail = detailStmt.executeQuery();

                while (rsDetail.next()) {
                    chiTietDoAn.add("    Đồ ăn: " + rsDetail.getString("tenDoAn")
                            + " - Số lượng: " + rsDetail.getInt("soLuong"));
                }

                JPanel card = createHoaDonCard(maHD, ngayMua, tenNV, chiTietDoAn, tongTien);
                pnlDanhSachHoaDon.add(card);
            }

            lblTongHoaDon.setText(String.valueOf(count));
            pnlDanhSachHoaDon.revalidate();
            pnlDanhSachHoaDon.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách hóa đơn!");
        }
    }//GEN-LAST:event_btnApDungActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chiTietHoaDonDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietHoaDonDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietHoaDonDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietHoaDonDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietHoaDonDoAn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongHoaDon;
    private javax.swing.JPanel pnlDanhSachHoaDon;
    // End of variables declaration//GEN-END:variables
}
