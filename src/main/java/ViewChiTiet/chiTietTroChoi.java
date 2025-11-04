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
public class chiTietTroChoi extends javax.swing.JFrame {

    /**
     * Creates new form chiTietTroChoi
     */
    public chiTietTroChoi() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // hiển thị giữa màn hình
        loadPhanKhuComboBox();
        loadTroChoiCards();
    }

    private JPanel createTroChoiCard(String ma, String ten, String tenPhanKhu, String moTa) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblTen = new JLabel("Tên trò chơi: " + ten);
        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblTenPhanKhu = new JLabel("Tên phân khu: " + tenPhanKhu);
        JLabel lblMota = new JLabel("<html><div style='width:190px'>Mô tả: " + moTa + "</div></html>");

        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenPhanKhu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMota.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMa);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTenPhanKhu);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMota);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    public void loadTroChoiCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT tc.maTroChoi, tc.tenTroChoi, tc.moTa, pk.tenPhanKhu "
                    + "FROM TroChoi tc "
                    + "JOIN phanKhu pk ON tc.maPhanKhu = pk.maPhanKhu";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachTroChoi.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maTroChoi");
                String ten = rs.getString("tenTroChoi");
                String tenPhanKhu = rs.getString("tenPhanKhu");
                String moTa = rs.getString("moTa");

                JPanel card = createTroChoiCard(ma, ten, tenPhanKhu, moTa);
                pnlDanhSachTroChoi.add(card);
            }

            lblTongTroChoi.setText(String.valueOf(count));
            pnlDanhSachTroChoi.revalidate();
            pnlDanhSachTroChoi.repaint();
//            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải trò chơi!");
        }
    }

    private void loadPhanKhuComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT tenPhanKhu FROM phanKhu";
            ResultSet rs = stmt.executeQuery(sql);

            cboTenPhanKhu.addItem("Chọn phân khu");
            while (rs.next()) {
                cboTenPhanKhu.addItem(rs.getString("tenPhanKhu"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phân khu!");
        }
    }

    public void loadTroChoiCardsByPhanKhu(String tenPhanKhu) {
        try {
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt;
            String sql = "SELECT tc.maTroChoi, tc.tenTroChoi, tc.moTa, pk.tenPhanKhu "
                    + "FROM TroChoi tc "
                    + "JOIN phanKhu pk ON tc.maPhanKhu = pk.maPhanKhu";
            if (tenPhanKhu != null && !tenPhanKhu.equals("Chọn phân khu")) {
                sql += " WHERE pk.tenPhanKhu = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tenPhanKhu);
            } else {
                pstmt = conn.prepareStatement(sql);
            }
            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachTroChoi.removeAll();
            int count = 0;
            while (rs.next()) {
                count++;
                String ma = rs.getString("maTroChoi");
                String ten = rs.getString("tenTroChoi");
                String tenPK = rs.getString("tenPhanKhu");
                String moTa = rs.getString("moTa");
                JPanel card = createTroChoiCard(ma, ten, tenPK, moTa);
                pnlDanhSachTroChoi.add(card);
            }
            lblTongTroChoi.setText(String.valueOf(count));
            pnlDanhSachTroChoi.revalidate();
            pnlDanhSachTroChoi.repaint();
//            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải trò chơi!");
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
        headerImgae1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTongTroChoi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachTroChoi = new javax.swing.JPanel();
        cboTenPhanKhu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ TRÒ CHƠI");

        headerImgae1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/videogame.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae1)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae1)
                    .addComponent(headerLabel))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số trò chơi:");

        lblTongTroChoi.setBackground(new java.awt.Color(255, 255, 255));
        lblTongTroChoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongTroChoi.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachTroChoi.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachTroChoi);

        cboTenPhanKhu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTenPhanKhu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenPhanKhuItemStateChanged(evt);
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
                .addComponent(lblTongTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboTenPhanKhu, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(lblTongTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboTenPhanKhu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTenPhanKhuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenPhanKhuItemStateChanged
        // TODO add your handling code here: 
        if (evt.getStateChange() == ItemEvent.SELECTED) {
        String selected = cboTenPhanKhu.getSelectedItem().toString();
        loadTroChoiCardsByPhanKhu(selected);
        }
    }//GEN-LAST:event_cboTenPhanKhuItemStateChanged

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
            java.util.logging.Logger.getLogger(chiTietTroChoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietTroChoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietTroChoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietTroChoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietTroChoi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboTenPhanKhu;
    private javax.swing.JLabel headerImgae1;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongTroChoi;
    private javax.swing.JPanel pnlDanhSachTroChoi;
    // End of variables declaration//GEN-END:variables
}
