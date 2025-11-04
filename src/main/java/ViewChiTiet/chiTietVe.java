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
public class chiTietVe extends javax.swing.JFrame {

    /**
     * Creates new form chiTietVe
     */
    public chiTietVe() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        loadVeCards();
    }

    private JPanel createVeCard(String maVe, String loaiVe, int giaVe, String moTa) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblLoaiVe = new JLabel("Loại vé: " + loaiVe);
        JLabel lblMaVe = new JLabel("Mã: " + maVe);
        JLabel lblGiaVe = new JLabel("Giá vé: " + giaVe);
        JLabel lblMoTa = new JLabel("<html><div style='width:190px'>Mô tả: " + moTa + "</div></html>");

        lblLoaiVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMaVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGiaVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblLoaiVe);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMaVe);
        content.add(Box.createVerticalStrut(5));
        content.add(lblGiaVe);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMoTa);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    private void loadVeCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT maVe, loaiVe, giaVe, moTa FROM Ve";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachVe.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maVe = rs.getString("maVe");
                String loaiVe = rs.getString("loaiVe");
                int giaVe = rs.getInt("giaVe");
                String moTa = rs.getString("moTa");

                JPanel card = createVeCard(maVe, loaiVe, giaVe, moTa);
                pnlDanhSachVe.add(card);
            }

            lblTongVe.setText(String.valueOf(count));
            pnlDanhSachVe.revalidate();
            pnlDanhSachVe.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách vé!");
        }
    }

    private boolean validateInput() {
        String giaTruocStr = txtGiaTruoc.getText().trim();
        String giaSauStr = txtGiaSau.getText().trim();

        // Check if price fields are filled when used
        if (!giaTruocStr.isEmpty() || !giaSauStr.isEmpty()) {
            if (giaTruocStr.isEmpty() || giaSauStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ khoảng giá vé.");
                return false;
            }

            // Validate number format
            int giaTruoc, giaSau;
            try {
                giaTruoc = Integer.parseInt(giaTruocStr);
                giaSau = Integer.parseInt(giaSauStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho giá vé.");
                txtGiaTruoc.requestFocus();
                return false;
            }

            // Validate positive numbers and logical range
            if (giaTruoc < 0 || giaSau < 0) {
                JOptionPane.showMessageDialog(this, "Giá vé phải là số không âm.");
                txtGiaTruoc.requestFocus();
                return false;
            }

            if (giaSau <= giaTruoc) {
                JOptionPane.showMessageDialog(this, "Giá vé sau phải lớn hơn giá vé trước.");
                txtGiaSau.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void refreshData() {
        txtGiaTruoc.setText("");
        txtGiaSau.setText("");
        loadVeCards();
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
        lblTongVe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachVe = new javax.swing.JPanel();
        txtGiaTruoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtGiaSau = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnApDung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ VÉ");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/ticket.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerImgae)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel1.setText("Tổng số loại vé:");

        lblTongVe.setBackground(new java.awt.Color(255, 255, 255));
        lblTongVe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongVe.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachVe.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachVe);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("->>");

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Giá:");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongVe, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnApDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGiaTruoc))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaSau, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLamMoi))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblTongVe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGiaTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtGiaSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnApDung)
                    .addComponent(btnLamMoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            String sql = "SELECT maVe, loaiVe, giaVe, moTa FROM Ve";
            boolean hasGiaFilter = !txtGiaTruoc.getText().trim().isEmpty() && !txtGiaSau.getText().trim().isEmpty();

            if (hasGiaFilter) {
                sql += " WHERE giaVe BETWEEN ? AND ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (hasGiaFilter) {
                pstmt.setInt(1, Integer.parseInt(txtGiaTruoc.getText().trim()));
                pstmt.setInt(2, Integer.parseInt(txtGiaSau.getText().trim()));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachVe.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maVe = rs.getString("maVe");
                String loaiVe = rs.getString("loaiVe");
                int giaVe = rs.getInt("giaVe");
                String moTa = rs.getString("moTa");

                JPanel card = createVeCard(maVe, loaiVe, giaVe, moTa);
                pnlDanhSachVe.add(card);
            }

            lblTongVe.setText(String.valueOf(count));
            pnlDanhSachVe.revalidate();
            pnlDanhSachVe.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách vé!");
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
            java.util.logging.Logger.getLogger(chiTietVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietVe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongVe;
    private javax.swing.JPanel pnlDanhSachVe;
    private javax.swing.JTextField txtGiaSau;
    private javax.swing.JTextField txtGiaTruoc;
    // End of variables declaration//GEN-END:variables
}
