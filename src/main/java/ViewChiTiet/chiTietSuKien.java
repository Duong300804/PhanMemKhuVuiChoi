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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class chiTietSuKien extends javax.swing.JFrame {

    /**
     * Creates new form chiTietSuKien
     */
    public chiTietSuKien() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ
        loadSuKienCards();
    }

    private JPanel createSuKienCard(String ma, String ten, String thoiGianBatDau, String thoiGianKetThuc, String moTa) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblTen = new JLabel("Tên sự kiện: " + ten);
        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblThoiGianBatDau = new JLabel("Thời gian bắt đầu: " + thoiGianBatDau);
        JLabel lblThoiGianKetThuc = new JLabel("Thời gian kết thúc: " + thoiGianKetThuc);
        JLabel lblMoTa = new JLabel("<html><div style='width:190px'>Mô tả: " + moTa + "</div></html>");

        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblThoiGianBatDau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblThoiGianKetThuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMa);
        content.add(Box.createVerticalStrut(5));
        content.add(lblThoiGianBatDau);
        content.add(Box.createVerticalStrut(5));
        content.add(lblThoiGianKetThuc);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMoTa);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    public void loadSuKienCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maSuKien, tenSuKien, thoiGianBatDau, thoiGianKetThuc, moTa FROM suKien";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            pnlDanhSachSuKien.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maSuKien");
                String ten = rs.getString("tenSuKien");
                String thoiGianBatDau = rs.getString("thoiGianBatDau");
                String thoiGianKetThuc = rs.getString("thoiGianKetThuc");
                String moTa = rs.getString("moTa");

                JPanel card = createSuKienCard(ma, ten, thoiGianBatDau, thoiGianKetThuc, moTa);
                pnlDanhSachSuKien.add(card);
            }

            lblTongSuKien.setText(String.valueOf(count));
            pnlDanhSachSuKien.revalidate();
            pnlDanhSachSuKien.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sự kiện!");
        }
    }

    public void loadSuKienCardsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT maSuKien, tenSuKien, thoiGianBatDau, thoiGianKetThuc, moTa FROM suKien";
            if (startDateTime != null && endDateTime != null) {
                sql += " WHERE thoiGianBatDau >= ? AND thoiGianKetThuc <= ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (startDateTime != null && endDateTime != null) {
                pstmt.setTimestamp(1, Timestamp.valueOf(startDateTime));
                pstmt.setTimestamp(2, Timestamp.valueOf(endDateTime));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachSuKien.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maSuKien");
                String ten = rs.getString("tenSuKien");
                String thoiGianBatDau = rs.getString("thoiGianBatDau");
                String thoiGianKetThuc = rs.getString("thoiGianKetThuc");
                String moTa = rs.getString("moTa");

                JPanel card = createSuKienCard(ma, ten, thoiGianBatDau, thoiGianKetThuc, moTa);
                pnlDanhSachSuKien.add(card);
            }

            lblTongSuKien.setText(String.valueOf(count));
            pnlDanhSachSuKien.revalidate();
            pnlDanhSachSuKien.repaint();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sự kiện: " + e.getMessage());
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
        lblTongSuKien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachSuKien = new javax.swing.JPanel();
        dtpThoiGianDau = new com.github.lgooddatepicker.components.DateTimePicker();
        dtpThoiGianSau = new com.github.lgooddatepicker.components.DateTimePicker();
        btnApDung = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setText("THỐNG KÊ SỰ KIỆN");
        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/calendar.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        jLabel1.setText("Tổng số sự kiện:");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTongSuKien.setBackground(new java.awt.Color(255, 255, 255));
        lblTongSuKien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongSuKien.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachSuKien.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachSuKien);

        btnApDung.setText("Áp dụng");
        btnApDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApDungActionPerformed(evt);
            }
        });

        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                .addComponent(lblTongSuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dtpThoiGianSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dtpThoiGianDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnApDung)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblTongSuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dtpThoiGianDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnApDung))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dtpThoiGianSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        LocalDateTime startDateTime = dtpThoiGianDau.getDateTimeStrict();
        LocalDateTime endDateTime = dtpThoiGianSau.getDateTimeStrict();

        if (startDateTime != null && endDateTime != null) {
            if (!endDateTime.isBefore(startDateTime)) {
                loadSuKienCardsByDateRange(startDateTime, endDateTime);
            } else {
                JOptionPane.showMessageDialog(this, "Thời gian kết thúc không được trước thời gian bắt đầu!");
                loadSuKienCards();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ thời gian bắt đầu và thời gian kết thúc!");
            loadSuKienCards();
        }
    }//GEN-LAST:event_btnApDungActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dtpThoiGianDau.clear();
        dtpThoiGianSau.clear();
        loadSuKienCards();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(chiTietSuKien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietSuKien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietSuKien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietSuKien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietSuKien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private com.github.lgooddatepicker.components.DateTimePicker dtpThoiGianDau;
    private com.github.lgooddatepicker.components.DateTimePicker dtpThoiGianSau;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongSuKien;
    private javax.swing.JPanel pnlDanhSachSuKien;
    // End of variables declaration//GEN-END:variables
}
