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
public class chiTietDoAn extends javax.swing.JFrame {

    /**
     * Creates new form chiTietDoAn
     */
    public chiTietDoAn() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ
        loadDoAnCards();
        loadTinhTrangToComboBox();
    }

    private JPanel createDoAnCard(String maDoAn, String tenDoAn, double giaDoAn, int soLuong) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblTenDoAn = new JLabel("Tên đồ ăn: " + tenDoAn);
        JLabel lblMaDoAn = new JLabel("Mã: " + maDoAn);
        JLabel lblGiaDoAn = new JLabel("Giá: " + giaDoAn);
        JLabel lblSoLuong = new JLabel("Số lượng: " + soLuong);

        lblTenDoAn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMaDoAn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGiaDoAn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblTenDoAn);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMaDoAn);
        content.add(Box.createVerticalStrut(5));
        content.add(lblGiaDoAn);
        content.add(Box.createVerticalStrut(5));
        content.add(lblSoLuong);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    private void loadDoAnCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT maDoAn, tenDoAn, giaDoAn, soLuong FROM banDoAn";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachDoAn.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maDoAn = rs.getString("maDoAn");
                String tenDoAn = rs.getString("tenDoAn");
                double giaDoAn = rs.getDouble("giaDoAn");
                int soLuong = rs.getInt("soLuong");

                JPanel card = createDoAnCard(maDoAn, tenDoAn, giaDoAn, soLuong);
                pnlDanhSachDoAn.add(card);
            }

            lblTongDoAn.setText(String.valueOf(count));
            pnlDanhSachDoAn.revalidate();
            pnlDanhSachDoAn.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách đồ ăn!");
        }
    }

    private void loadTinhTrangToComboBox() {
        cboTinhTrang.removeAllItems(); // Xóa các mục hiện tại
        cboTinhTrang.addItem("Tất cả");
        cboTinhTrang.addItem("Còn hàng");
        cboTinhTrang.addItem("Hết hàng");
        cboTinhTrang.setSelectedIndex(0); // Mặc định chọn "Tất cả"
    }

    private boolean validateInput() {
        String giaTruocStr = txtGiaTruoc.getText().trim();
        String giaSauStr = txtGiaSau.getText().trim();

        // Kiểm tra nếu nhập khoảng giá thì phải nhập cả hai
        if (!giaTruocStr.isEmpty() || !giaSauStr.isEmpty()) {
            if (giaTruocStr.isEmpty() || giaSauStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ khoảng giá đồ ăn.");
                return false;
            }

            // Kiểm tra định dạng số
            double giaTruoc, giaSau;
            try {
                giaTruoc = Double.parseDouble(giaTruocStr);
                giaSau = Double.parseDouble(giaSauStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho giá đồ ăn.");
                txtGiaTruoc.requestFocus();
                return false;
            }

            // Kiểm tra giá trị dương và khoảng hợp lý
            if (giaTruoc < 0 || giaSau < 0) {
                JOptionPane.showMessageDialog(this, "Giá đồ ăn phải là số không âm.");
                txtGiaTruoc.requestFocus();
                return false;
            }

            if (giaSau <= giaTruoc) {
                JOptionPane.showMessageDialog(this, "Giá sau phải lớn hơn giá trước.");
                txtGiaSau.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void refreshData() {
        txtGiaTruoc.setText("");
        txtGiaSau.setText("");
        cboTinhTrang.setSelectedIndex(0); // Chọn "Tất cả"
        loadDoAnCards();
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
        lblTongDoAn = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachDoAn = new javax.swing.JPanel();
        txtGiaTruoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtGiaSau = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnApDung = new javax.swing.JButton();
        cboTinhTrang = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ ĐỒ ĂN");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/fast-food.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        jLabel1.setText("Tổng số loại đồ ăn:");

        lblTongDoAn.setBackground(new java.awt.Color(255, 255, 255));
        lblTongDoAn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongDoAn.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachDoAn.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachDoAn);

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
                .addComponent(lblTongDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaSau, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cboTinhTrang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnApDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoi)))
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
                        .addComponent(lblTongDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGiaTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtGiaSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLamMoi)
                        .addComponent(btnApDung))
                    .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            String sql = "SELECT maDoAn, tenDoAn, giaDoAn, soLuong FROM banDoAn";
            boolean hasGiaFilter = !txtGiaTruoc.getText().trim().isEmpty() && !txtGiaSau.getText().trim().isEmpty();
            String tinhTrang = cboTinhTrang.getSelectedItem().toString();
            boolean hasTinhTrangFilter = !tinhTrang.equals("Tất cả");

            // Xây dựng điều kiện WHERE
            String whereClause = "";
            if (hasGiaFilter || hasTinhTrangFilter) {
                whereClause = " WHERE ";
                if (hasGiaFilter) {
                    whereClause += "giaDoAn BETWEEN ? AND ?";
                }
                if (hasTinhTrangFilter) {
                    if (hasGiaFilter) {
                        whereClause += " AND ";
                    }
                    whereClause += "soLuong " + (tinhTrang.equals("Còn hàng") ? "> 0" : "= 0");
                }
            }

            sql += whereClause;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (hasGiaFilter) {
                pstmt.setDouble(1, Double.parseDouble(txtGiaTruoc.getText().trim()));
                pstmt.setDouble(2, Double.parseDouble(txtGiaSau.getText().trim()));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachDoAn.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maDoAn = rs.getString("maDoAn");
                String tenDoAn = rs.getString("tenDoAn");
                double giaDoAn = rs.getDouble("giaDoAn");
                int soLuong = rs.getInt("soLuong");

                JPanel card = createDoAnCard(maDoAn, tenDoAn, giaDoAn, soLuong);
                pnlDanhSachDoAn.add(card);
            }

            lblTongDoAn.setText(String.valueOf(count));
            pnlDanhSachDoAn.revalidate();
            pnlDanhSachDoAn.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách đồ ăn!");
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
            java.util.logging.Logger.getLogger(chiTietDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietDoAn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietDoAn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongDoAn;
    private javax.swing.JPanel pnlDanhSachDoAn;
    private javax.swing.JTextField txtGiaSau;
    private javax.swing.JTextField txtGiaTruoc;
    // End of variables declaration//GEN-END:variables
}
