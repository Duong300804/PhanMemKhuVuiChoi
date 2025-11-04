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
public class chiTietTaiKhoan extends javax.swing.JFrame {

    /**
     * Creates new form chiTietTaiKhoan
     */
    public chiTietTaiKhoan() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        loadLoaiTaiKhoanComboBox();
        loadTaiKhoanCards();
    }

    private JPanel createTaiKhoanCard(int maTaiKhoan, String tenDangNhap, String loaiTaiKhoan, String tenNhanVien) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblMaTaiKhoan = new JLabel("Mã tài khoản: " + maTaiKhoan);
        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập: " + tenDangNhap);
        JLabel lblLoaiTaiKhoan = new JLabel("Loại tài khoản: " + loaiTaiKhoan);
        JLabel lblTenNhanVien = new JLabel("Tên nhân viên: " + (tenNhanVien != null ? tenNhanVien : "Không liên kết"));

        lblMaTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblLoaiTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblMaTaiKhoan);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTenDangNhap);
        content.add(Box.createVerticalStrut(5));
        content.add(lblLoaiTaiKhoan);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTenNhanVien);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    private void loadTaiKhoanCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.loaiTaiKhoan, nv.tenNhanVien "
                    + "FROM taiKhoan tk "
                    + "LEFT JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachTaiKhoan.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                int maTaiKhoan = rs.getInt("maTaiKhoan");
                String tenDangNhap = rs.getString("tenDangNhap");
                String loaiTaiKhoan = rs.getString("loaiTaiKhoan");
                String tenNhanVien = rs.getString("tenNhanVien");

                JPanel card = createTaiKhoanCard(maTaiKhoan, tenDangNhap, loaiTaiKhoan, tenNhanVien);
                pnlDanhSachTaiKhoan.add(card);
            }

            lblTongTaiKhoan.setText(String.valueOf(count));
            pnlDanhSachTaiKhoan.revalidate();
            pnlDanhSachTaiKhoan.repaint();
//            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tài khoản!");
        }
    }

    private void loadLoaiTaiKhoanComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT loaiTaiKhoan FROM taiKhoan";
            ResultSet rs = stmt.executeQuery(sql);

            cboLoaiTaiKhoan.addItem("Chọn loại tài khoản");
            while (rs.next()) {
                cboLoaiTaiKhoan.addItem(rs.getString("loaiTaiKhoan"));
            }
//            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách loại tài khoản!");
        }
    }

    private void loadTaiKhoanCardsByLoaiTaiKhoan(String loaiTaiKhoan) {
        try {
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt;
            String sql = "SELECT tk.maTaiKhoan, tk.tenDangNhap, tk.loaiTaiKhoan, nv.tenNhanVien "
                    + "FROM taiKhoan tk "
                    + "LEFT JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien";
            if (loaiTaiKhoan != null && !loaiTaiKhoan.equals("Chọn loại tài khoản")) {
                sql += " WHERE tk.loaiTaiKhoan = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, loaiTaiKhoan);
            } else {
                pstmt = conn.prepareStatement(sql);
            }
            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachTaiKhoan.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                int maTaiKhoan = rs.getInt("maTaiKhoan");
                String tenDangNhap = rs.getString("tenDangNhap");
                String loaiTK = rs.getString("loaiTaiKhoan");
                String tenNhanVien = rs.getString("tenNhanVien");

                JPanel card = createTaiKhoanCard(maTaiKhoan, tenDangNhap, loaiTK, tenNhanVien);
                pnlDanhSachTaiKhoan.add(card);
            }

            lblTongTaiKhoan.setText(String.valueOf(count));
            pnlDanhSachTaiKhoan.revalidate();
            pnlDanhSachTaiKhoan.repaint();
//            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách tài khoản!");
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
        lblTongTaiKhoan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachTaiKhoan = new javax.swing.JPanel();
        cboLoaiTaiKhoan = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ TÀI KHOẢN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/verified-account.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerImgae)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae)
                    .addComponent(headerLabel))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số tài khoản:");

        lblTongTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        lblTongTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongTaiKhoan.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachTaiKhoan.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachTaiKhoan);

        cboLoaiTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLoaiTaiKhoan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiTaiKhoanItemStateChanged(evt);
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
                .addComponent(lblTongTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(cboLoaiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblTongTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboLoaiTaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboLoaiTaiKhoanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiTaiKhoanItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selected = cboLoaiTaiKhoan.getSelectedItem().toString();
            loadTaiKhoanCardsByLoaiTaiKhoan(selected);
        }
    }//GEN-LAST:event_cboLoaiTaiKhoanItemStateChanged

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
            java.util.logging.Logger.getLogger(chiTietTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietTaiKhoan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboLoaiTaiKhoan;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongTaiKhoan;
    private javax.swing.JPanel pnlDanhSachTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
