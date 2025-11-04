/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewChiTiet;

import Database.ConnectDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class chiTietNhanVien extends javax.swing.JFrame {

    /**
     * Creates new form chiTietNhanVien
     */
    public chiTietNhanVien() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        loadChucVuComboBox();
        loadGioiTinhComboBox();
        loadNhanVienCards();
    }

    private JPanel createNhanVienCard(String ma, String ten, String ngaySinh, String sdt, String chucVu, String gioiTinh) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblTen = new JLabel("Tên nhân viên: " + ten);
        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblNgaySinh = new JLabel("Ngày sinh: " + ngaySinh);
        JLabel lblSDT = new JLabel("SĐT: " + sdt);
        JLabel lblChucVu = new JLabel("Chức vụ: " + chucVu);
        JLabel lblGioiTinh = new JLabel("Giới tính: " + gioiTinh);

        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMa);
        content.add(Box.createVerticalStrut(5));
        content.add(lblNgaySinh);
        content.add(Box.createVerticalStrut(5));
        content.add(lblSDT);
        content.add(Box.createVerticalStrut(5));
        content.add(lblChucVu);
        content.add(Box.createVerticalStrut(5));
        content.add(lblGioiTinh);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    public void loadNhanVienCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT maNhanVien, tenNhanVien, ngaySinh, SDT, chucVu, gioiTinh FROM NhanVien";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachNhanVien.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maNhanVien");
                String ten = rs.getString("tenNhanVien");
                String ngaySinh = rs.getString("ngaySinh");
                String sdt = rs.getString("SDT");
                String chucVu = rs.getString("chucVu");
                String gioiTinh = rs.getString("gioiTinh");

                JPanel card = createNhanVienCard(ma, ten, ngaySinh, sdt, chucVu, gioiTinh);
                pnlDanhSachNhanVien.add(card);
            }

            lblTongNhanVien.setText(String.valueOf(count));
            pnlDanhSachNhanVien.revalidate();
            pnlDanhSachNhanVien.repaint();
            // conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhân viên!");
        }
    }

    public void loadChucVuComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT chucVu FROM NhanVien";
            ResultSet rs = stmt.executeQuery(sql);

            cboChucVu.addItem("Chọn chức vụ");
            while (rs.next()) {
                cboChucVu.addItem(rs.getString("chucVu"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách chức vụ!");
        }
    }

    public void loadGioiTinhComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT gioiTinh FROM NhanVien";
            ResultSet rs = stmt.executeQuery(sql);

            cboGioiTinh.addItem("Chọn giới tính");
            while (rs.next()) {
                cboGioiTinh.addItem(rs.getString("gioiTinh"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách giới tính!");
        }
    }

    public void loadNhanVienCardsByFilters(String chucVu, String gioiTinh) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maNhanVien, tenNhanVien, ngaySinh, SDT, chucVu, gioiTinh FROM NhanVien";
            boolean hasChucVuFilter = chucVu != null && !chucVu.equals("Chọn chức vụ");
            boolean hasGioiTinhFilter = gioiTinh != null && !gioiTinh.equals("Chọn giới tính");

            if (hasChucVuFilter || hasGioiTinhFilter) {
                sql += " WHERE ";
                if (hasChucVuFilter) {
                    sql += "chucVu = ?";
                    if (hasGioiTinhFilter) {
                        sql += " AND ";
                    }
                }
                if (hasGioiTinhFilter) {
                    sql += "gioiTinh = ?";
                }
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (hasChucVuFilter) {
                pstmt.setString(paramIndex++, chucVu);
            }
            if (hasGioiTinhFilter) {
                pstmt.setString(paramIndex, gioiTinh);
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachNhanVien.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maNhanVien");
                String ten = rs.getString("tenNhanVien");
                String ngaySinh = rs.getString("ngaySinh");
                String sdt = rs.getString("SDT");
                String cv = rs.getString("chucVu");
                String gt = rs.getString("gioiTinh");

                JPanel card = createNhanVienCard(ma, ten, ngaySinh, sdt, cv, gt);
                pnlDanhSachNhanVien.add(card);
            }

            lblTongNhanVien.setText(String.valueOf(count));
            pnlDanhSachNhanVien.revalidate();
            pnlDanhSachNhanVien.repaint();
            // conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách nhân viên!");
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
        jLabel1 = new javax.swing.JLabel();
        lblTongNhanVien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachNhanVien = new javax.swing.JPanel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        cboChucVu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ NHÂN VIÊN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/grouping.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(139, 139, 139))
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
        jLabel1.setText("Tổng số nhân viên:");

        lblTongNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        lblTongNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongNhanVien.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachNhanVien.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachNhanVien);

        cboGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboGioiTinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboGioiTinhItemStateChanged(evt);
            }
        });

        cboChucVu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboChucVu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboChucVuItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboChucVu, 0, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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
                            .addComponent(lblTongNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboGioiTinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboGioiTinhItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object selectedChucVu = cboChucVu.getSelectedItem();
            Object selectedGioiTinh = cboGioiTinh.getSelectedItem();
            String chucVu = selectedChucVu != null ? selectedChucVu.toString() : "Chọn chức vụ";
            String gioiTinh = selectedGioiTinh != null ? selectedGioiTinh.toString() : "Chọn giới tính";
            loadNhanVienCardsByFilters(chucVu, gioiTinh);
        }
    }//GEN-LAST:event_cboGioiTinhItemStateChanged

    private void cboChucVuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboChucVuItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object selectedChucVu = cboChucVu.getSelectedItem();
            Object selectedGioiTinh = cboGioiTinh.getSelectedItem();
            String chucVu = selectedChucVu != null ? selectedChucVu.toString() : "Chọn chức vụ";
            String gioiTinh = selectedGioiTinh != null ? selectedGioiTinh.toString() : "Chọn giới tính";
            loadNhanVienCardsByFilters(chucVu, gioiTinh);
        }
    }//GEN-LAST:event_cboChucVuItemStateChanged

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
            java.util.logging.Logger.getLogger(chiTietNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongNhanVien;
    private javax.swing.JPanel pnlDanhSachNhanVien;
    // End of variables declaration//GEN-END:variables
}




//SELECT * 
//FROM NhanVien
//WHERE MONTH(ngaySinh) = 5 AND YEAR(ngaySinh) = 2000;
//
//SELECT * 
//FROM NhanVien 
//WHERE ngaySinh < '2000-01-01';

