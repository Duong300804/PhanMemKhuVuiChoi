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
public class chiTietThietBi extends javax.swing.JFrame {

    /**
     * Creates new form chiTietThietBi
     */
    public chiTietThietBi() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        loadTroChoiComboBox();
        loadTinhTrangComboBox();
        loadThietBiCards();
    }

    private JPanel createThietBiCard(String ma, String ten, String tenTroChoi, String tinhTrang) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblTen = new JLabel("Tên thiết bị: " + ten);
        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblTenTroChoi = new JLabel("Tên trò chơi: " + tenTroChoi);
        JLabel lblTinhTrang = new JLabel("Tình trạng: " + tinhTrang);

        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenTroChoi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTinhTrang.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMa);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTenTroChoi);
        content.add(Box.createVerticalStrut(5));
        content.add(lblTinhTrang);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    public void loadThietBiCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT tb.maThietBi, tb.tenThietBi, tb.tinhTrang, tc.tenTroChoi "
                    + "FROM thietBi tb "
                    + "JOIN TroChoi tc ON tb.maTroChoi = tc.maTroChoi";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachThietBi.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maThietBi");
                String ten = rs.getString("tenThietBi");
                String tenTroChoi = rs.getString("tenTroChoi");
                String tinhTrang = rs.getString("tinhTrang");

                JPanel card = createThietBiCard(ma, ten, tenTroChoi, tinhTrang);
                pnlDanhSachThietBi.add(card);
            }

            lblTongThietBi.setText(String.valueOf(count));
            pnlDanhSachThietBi.revalidate();
            pnlDanhSachThietBi.repaint();
            // conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách thiết bị!");
        }
    }

    public void loadTroChoiComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT tenTroChoi FROM TroChoi";
            ResultSet rs = stmt.executeQuery(sql);

            cboTenTroChoi.addItem("Chọn trò chơi");
            while (rs.next()) {
                cboTenTroChoi.addItem(rs.getString("tenTroChoi"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách trò chơi!");
        }
    }

    public void loadTinhTrangComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT tinhTrang FROM thietBi";
            ResultSet rs = stmt.executeQuery(sql);

            cboTinhTrang.addItem("Chọn tình trạng");
            while (rs.next()) {
                cboTinhTrang.addItem(rs.getString("tinhTrang"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tình trạng!");
        }
    }

    public void loadThietBiCardsByFilters(String tenTroChoi, String tinhTrang) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT tb.maThietBi, tb.tenThietBi, tb.tinhTrang, tc.tenTroChoi "
                    + "FROM thietBi tb "
                    + "JOIN TroChoi tc ON tb.maTroChoi = tc.maTroChoi";
            boolean hasTroChoiFilter = tenTroChoi != null && !tenTroChoi.equals("Chọn trò chơi");
            boolean hasTinhTrangFilter = tinhTrang != null && !tinhTrang.equals("Chọn tình trạng");

            if (hasTroChoiFilter || hasTinhTrangFilter) {
                sql += " WHERE ";
                if (hasTroChoiFilter) {
                    sql += "tc.tenTroChoi = ?";
                    if (hasTinhTrangFilter) {
                        sql += " AND ";
                    }
                }
                if (hasTinhTrangFilter) {
                    sql += "tb.tinhTrang = ?";
                }
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (hasTroChoiFilter) {
                pstmt.setString(paramIndex++, tenTroChoi);
            }
            if (hasTinhTrangFilter) {
                pstmt.setString(paramIndex, tinhTrang);
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachThietBi.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maThietBi");
                String ten = rs.getString("tenThietBi");
                String tenTC = rs.getString("tenTroChoi");
                String tTrang = rs.getString("tinhTrang");

                JPanel card = createThietBiCard(ma, ten, tenTC, tTrang);
                pnlDanhSachThietBi.add(card);
            }

            lblTongThietBi.setText(String.valueOf(count));
            pnlDanhSachThietBi.revalidate();
            pnlDanhSachThietBi.repaint();
            // conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách thiết bị!");
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
        lblTongThietBi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachThietBi = new javax.swing.JPanel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        cboTenTroChoi = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ THIẾT BỊ");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/wall.png"))); // NOI18N

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số thiết bị:");

        lblTongThietBi.setBackground(new java.awt.Color(255, 255, 255));
        lblTongThietBi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongThietBi.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachThietBi.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachThietBi);

        cboTinhTrang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTinhTrang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTinhTrangItemStateChanged(evt);
            }
        });

        cboTenTroChoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTenTroChoi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenTroChoiItemStateChanged(evt);
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
                .addComponent(lblTongThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboTenTroChoi, 0, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(lblTongThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboTinhTrang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboTenTroChoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTinhTrangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTinhTrangItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object selectedTroChoi = cboTenTroChoi.getSelectedItem();
            Object selectedTinhTrang = cboTinhTrang.getSelectedItem();
            String tenTroChoi = selectedTroChoi != null ? selectedTroChoi.toString() : "Chọn trò chơi";
            String tinhTrang = selectedTinhTrang != null ? selectedTinhTrang.toString() : "Chọn tình trạng";
            loadThietBiCardsByFilters(tenTroChoi, tinhTrang);
        }
    }//GEN-LAST:event_cboTinhTrangItemStateChanged

    private void cboTenTroChoiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenTroChoiItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Object selectedTroChoi = cboTenTroChoi.getSelectedItem();
            Object selectedTinhTrang = cboTinhTrang.getSelectedItem();
            String tenTroChoi = selectedTroChoi != null ? selectedTroChoi.toString() : "Chọn trò chơi";
            String tinhTrang = selectedTinhTrang != null ? selectedTinhTrang.toString() : "Chọn tình trạng";
            loadThietBiCardsByFilters(tenTroChoi, tinhTrang);
        }
    }//GEN-LAST:event_cboTenTroChoiItemStateChanged

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
            java.util.logging.Logger.getLogger(chiTietThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietThietBi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboTenTroChoi;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongThietBi;
    private javax.swing.JPanel pnlDanhSachThietBi;
    // End of variables declaration//GEN-END:variables
}
