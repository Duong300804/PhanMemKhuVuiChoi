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
public class chiTietGuiDo extends javax.swing.JFrame {

    /**
     * Creates new form chiTietGuiDo
     */
    public chiTietGuiDo() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        loadThangComboBox();
        loadNamComboBox();
        loadMaTuDoComboBox();
        loadGuiDoCards();
    }

    private JPanel createGuiDoCard(String maGuiDo, String maTuDo, String hoTen, String thoiGianGui, String thoiGianNhan) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblMaGuiDo = new JLabel("Mã gửi: " + maGuiDo);
        JLabel lblMaTuDo = new JLabel("Tủ đồ: " + maTuDo);
        JLabel lblHoTen = new JLabel("Tên khách hàng: " + hoTen);
        JLabel lblThoiGianGui = new JLabel("Thời gian gửi: " + thoiGianGui);
        JLabel lblThoiGianNhan = new JLabel("Thời gian nhận: " + (thoiGianNhan != null ? thoiGianNhan : "Chưa nhận"));

        lblMaGuiDo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMaTuDo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblThoiGianGui.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblThoiGianNhan.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblMaGuiDo);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMaTuDo);
        content.add(Box.createVerticalStrut(5));
        content.add(lblHoTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblThoiGianGui);
        content.add(Box.createVerticalStrut(5));
        content.add(lblThoiGianNhan);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    private void loadGuiDoCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT gd.maGuiDo, gd.maTuDo, kh.hoTen, gd.thoiGianGui, gd.thoiGianNhan "
                    + "FROM guiDo gd JOIN khachHang kh ON gd.maKhachHang = kh.maKhachHang";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            pnlDanhSachGuiDo.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maGuiDo = rs.getString("maGuiDo");
                String maTuDo = rs.getString("maTuDo");
                String hoTen = rs.getString("hoTen");
                String thoiGianGui = rs.getString("thoiGianGui");
                String thoiGianNhan = rs.getString("thoiGianNhan");

                JPanel card = createGuiDoCard(maGuiDo, maTuDo, hoTen, thoiGianGui, thoiGianNhan);
                pnlDanhSachGuiDo.add(card);
            }

            lblTongGuiDo.setText(String.valueOf(count));
            pnlDanhSachGuiDo.revalidate();
            pnlDanhSachGuiDo.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách gửi đồ!");
        }
    }

    private void loadThangComboBox() {
        try {
            cboThang.addItem("Chọn tháng");
            for (int i = 1; i <= 12; i++) {
                cboThang.addItem(String.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách tháng!");
        }
    }

    public void loadNamComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT YEAR(thoiGianGui) AS nam FROM guiDo ORDER BY nam";
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

    public void loadMaTuDoComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT maTuDo FROM guiDo ORDER BY maTuDo";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            cboMaTuDo.addItem("Chọn mã tủ đồ");
            while (rs.next()) {
                cboMaTuDo.addItem(rs.getString("maTuDo"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã tủ đồ!");
        }
    }

    private boolean validateInput() {
        String thang = cboThang.getSelectedItem().toString();
        String nam = cboNam.getSelectedItem().toString();

        if (!thang.equals("Chọn tháng") && nam.equals("Chọn năm")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm khi đã chọn tháng.");
            cboNam.requestFocus();
            return false;
        }

        return true;
    }

    private void refreshData() {
        cboThang.setSelectedItem("Chọn tháng");
        cboNam.setSelectedItem("Chọn năm");
        cboMaTuDo.setSelectedItem("Chọn mã tủ đồ");
        loadGuiDoCards();
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
        lblTongGuiDo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachGuiDo = new javax.swing.JPanel();
        cboNam = new javax.swing.JComboBox<>();
        cboThang = new javax.swing.JComboBox<>();
        cboMaTuDo = new javax.swing.JComboBox<>();
        btnLamMoi = new javax.swing.JButton();
        btnApDung = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ GỬI ĐỒ");

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/safe.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(173, 173, 173))
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
        jLabel1.setText("Tổng số lượt gửi đồ:");

        lblTongGuiDo.setBackground(new java.awt.Color(255, 255, 255));
        lblTongGuiDo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongGuiDo.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachGuiDo.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachGuiDo);

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

        cboMaTuDo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMaTuDo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMaTuDoItemStateChanged(evt);
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
                .addComponent(lblTongGuiDo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboMaTuDo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboThang, 0, 119, Short.MAX_VALUE))
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
                            .addComponent(lblTongGuiDo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboNam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(cboMaTuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void cboMaTuDoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMaTuDoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaTuDoItemStateChanged

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }

        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT gd.maGuiDo, gd.maTuDo, kh.hoTen, gd.thoiGianGui, gd.thoiGianNhan "
                    + "FROM guiDo gd JOIN khachHang kh ON gd.maKhachHang = kh.maKhachHang";

            boolean hasThangFilter = !cboThang.getSelectedItem().equals("Chọn tháng");
            boolean hasNamFilter = !cboNam.getSelectedItem().equals("Chọn năm");
            boolean hasMaTuDoFilter = !cboMaTuDo.getSelectedItem().equals("Chọn mã tủ đồ");

            List<String> conditions = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            if (hasMaTuDoFilter) {
                conditions.add("gd.maTuDo = ?");
                parameters.add(cboMaTuDo.getSelectedItem().toString());
            }

            if (hasThangFilter && hasNamFilter) {
                conditions.add("MONTH(gd.thoiGianGui) = ? AND YEAR(gd.thoiGianGui) = ?");
                parameters.add(Integer.parseInt(cboThang.getSelectedItem().toString()));
                parameters.add(Integer.parseInt(cboNam.getSelectedItem().toString()));
            } else if (hasNamFilter) {
                conditions.add("YEAR(gd.thoiGianGui) = ?");
                parameters.add(Integer.parseInt(cboNam.getSelectedItem().toString()));
            }

            if (!conditions.isEmpty()) {
                sql += " WHERE " + String.join(" AND ", conditions);
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachGuiDo.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String maGuiDo = rs.getString("maGuiDo");
                String maTuDo = rs.getString("maTuDo");
                String hoTen = rs.getString("hoTen");
                String thoiGianGui = rs.getString("thoiGianGui");
                String thoiGianNhan = rs.getString("thoiGianNhan");

                JPanel card = createGuiDoCard(maGuiDo, maTuDo, hoTen, thoiGianGui, thoiGianNhan);
                pnlDanhSachGuiDo.add(card);
            }

            lblTongGuiDo.setText(String.valueOf(count));
            pnlDanhSachGuiDo.revalidate();
            pnlDanhSachGuiDo.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách gửi đồ!");
        }
    }//GEN-LAST:event_btnApDungActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        refreshData();
    }//GEN-LAST:event_btnLamMoiActionPerformed

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
            java.util.logging.Logger.getLogger(chiTietGuiDo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietGuiDo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietGuiDo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietGuiDo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietGuiDo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JComboBox<String> cboMaTuDo;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongGuiDo;
    private javax.swing.JPanel pnlDanhSachGuiDo;
    // End of variables declaration//GEN-END:variables
}
