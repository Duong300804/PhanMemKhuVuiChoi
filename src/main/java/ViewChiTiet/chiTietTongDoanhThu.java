/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewChiTiet;

import Database.ConnectDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class chiTietTongDoanhThu extends javax.swing.JFrame {

    /**
     * Creates new form chiTietTongDoanhThu
     */
    public chiTietTongDoanhThu() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ
        loadThongKe();
    }

    private void loadThongKe() {
        try {
            Connection conn = ConnectDB.getConnection();

            // Tổng doanh thu (vé + đồ ăn)
            String sqlTongDoanhThu = "SELECT SUM(tongTien) as tongDoanhThu FROM (SELECT tongTien FROM HoaDon UNION ALL SELECT tongTien FROM hoaDonDoAn) as combined";
            PreparedStatement pstmtTongDoanhThu = conn.prepareStatement(sqlTongDoanhThu);
            ResultSet rsTongDoanhThu = pstmtTongDoanhThu.executeQuery();
            double tongDoanhThu = rsTongDoanhThu.next() ? rsTongDoanhThu.getDouble("tongDoanhThu") : 0;
            lblTongDoanhThu.setText(String.format("%,.0f VND", tongDoanhThu));

            // Tổng số hóa đơn (vé + đồ ăn)
            String sqlTongHoaDon = "SELECT COUNT(*) as tongHoaDon FROM (SELECT maHoaDon FROM HoaDon UNION ALL SELECT maHoaDon FROM hoaDonDoAn) as combined";
            PreparedStatement pstmtTongHoaDon = conn.prepareStatement(sqlTongHoaDon);
            ResultSet rsTongHoaDon = pstmtTongHoaDon.executeQuery();
            int tongHoaDon = rsTongHoaDon.next() ? rsTongHoaDon.getInt("tongHoaDon") : 0;
            lblTongHoaDon.setText(String.valueOf(tongHoaDon));

            // Tháng có doanh thu cao nhất (vé + đồ ăn)
            String sqlThangCaoNhat = "SELECT TOP 1 MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM (SELECT ngayMua, tongTien FROM HoaDon UNION ALL SELECT ngayMua, tongTien FROM hoaDonDoAn) as combined "
                    + "GROUP BY MONTH(ngayMua), YEAR(ngayMua) ORDER BY doanhThu DESC";
            PreparedStatement pstmtThangCaoNhat = conn.prepareStatement(sqlThangCaoNhat);
            ResultSet rsThangCaoNhat = pstmtThangCaoNhat.executeQuery();
            String thangCaoNhat = rsThangCaoNhat.next() ? rsThangCaoNhat.getInt("thang") + "/" + rsThangCaoNhat.getInt("nam") : "Không có";
            lblThangCaoNhat.setText(thangCaoNhat);

            // Doanh thu trung bình mỗi hóa đơn (vé + đồ ăn)
            String sqlDoanhThuTB = "SELECT AVG(tongTien) as doanhThuTB FROM (SELECT tongTien FROM HoaDon UNION ALL SELECT tongTien FROM hoaDonDoAn) as combined";
            PreparedStatement pstmtDoanhThuTB = conn.prepareStatement(sqlDoanhThuTB);
            ResultSet rsDoanhThuTB = pstmtDoanhThuTB.executeQuery();
            double doanhThuTB = rsDoanhThuTB.next() ? rsDoanhThuTB.getDouble("doanhThuTB") : 0;
            lblDoanhThuTrungBinh.setText(String.format("%,.2f VND", doanhThuTB));

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin thống kê: " + e.getMessage());
        }
    }

    private void loadThongKeByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            Connection conn = ConnectDB.getConnection();

            // Tổng doanh thu (vé + đồ ăn)
            String sqlTongDoanhThu = "SELECT SUM(tongTien) as tongDoanhThu FROM (SELECT tongTien FROM HoaDon UNION ALL SELECT tongTien FROM hoaDonDoAn) as combined";
            if (startDate != null && endDate != null) {
                sqlTongDoanhThu = "SELECT SUM(tongTien) as tongDoanhThu FROM (SELECT tongTien FROM HoaDon WHERE ngayMua BETWEEN ? AND ? UNION ALL SELECT tongTien FROM hoaDonDoAn WHERE ngayMua BETWEEN ? AND ?) as combined";
            }
            PreparedStatement pstmtTongDoanhThu = conn.prepareStatement(sqlTongDoanhThu);
            if (startDate != null && endDate != null) {
                pstmtTongDoanhThu.setDate(1, Date.valueOf(startDate));
                pstmtTongDoanhThu.setDate(2, Date.valueOf(endDate));
                pstmtTongDoanhThu.setDate(3, Date.valueOf(startDate));
                pstmtTongDoanhThu.setDate(4, Date.valueOf(endDate));
            }
            ResultSet rsTongDoanhThu = pstmtTongDoanhThu.executeQuery();
            double tongDoanhThu = rsTongDoanhThu.next() ? rsTongDoanhThu.getDouble("tongDoanhThu") : 0;
            lblTongDoanhThu.setText(String.format("%,.0f VND", tongDoanhThu));

            // Tổng số hóa đơn (vé + đồ ăn)
            String sqlTongHoaDon = "SELECT COUNT(*) as tongHoaDon FROM (SELECT maHoaDon FROM HoaDon UNION ALL SELECT maHoaDon FROM hoaDonDoAn) as combined";
            if (startDate != null && endDate != null) {
                sqlTongHoaDon = "SELECT COUNT(*) as tongHoaDon FROM (SELECT maHoaDon FROM HoaDon WHERE ngayMua BETWEEN ? AND ? UNION ALL SELECT maHoaDon FROM hoaDonDoAn WHERE ngayMua BETWEEN ? AND ?) as combined";
            }
            PreparedStatement pstmtTongHoaDon = conn.prepareStatement(sqlTongHoaDon);
            if (startDate != null && endDate != null) {
                pstmtTongHoaDon.setDate(1, Date.valueOf(startDate));
                pstmtTongHoaDon.setDate(2, Date.valueOf(endDate));
                pstmtTongHoaDon.setDate(3, Date.valueOf(startDate));
                pstmtTongHoaDon.setDate(4, Date.valueOf(endDate));
            }
            ResultSet rsTongHoaDon = pstmtTongHoaDon.executeQuery();
            int tongHoaDon = rsTongHoaDon.next() ? rsTongHoaDon.getInt("tongHoaDon") : 0;
            lblTongHoaDon.setText(String.valueOf(tongHoaDon));

            // Tháng có doanh thu cao nhất (vé + đồ ăn)
            String sqlThangCaoNhat = "SELECT TOP 1 MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM (SELECT ngayMua, tongTien FROM HoaDon UNION ALL SELECT ngayMua, tongTien FROM hoaDonDoAn) as combined ";
            if (startDate != null && endDate != null) {
                sqlThangCaoNhat = "SELECT TOP 1 MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                        + "FROM (SELECT ngayMua, tongTien FROM HoaDon WHERE ngayMua BETWEEN ? AND ? UNION ALL SELECT ngayMua, tongTien FROM hoaDonDoAn WHERE ngayMua BETWEEN ? AND ?) as combined ";
            }
            sqlThangCaoNhat += "GROUP BY MONTH(ngayMua), YEAR(ngayMua) ORDER BY doanhThu DESC";
            PreparedStatement pstmtThangCaoNhat = conn.prepareStatement(sqlThangCaoNhat);
            if (startDate != null && endDate != null) {
                pstmtThangCaoNhat.setDate(1, Date.valueOf(startDate));
                pstmtThangCaoNhat.setDate(2, Date.valueOf(endDate));
                pstmtThangCaoNhat.setDate(3, Date.valueOf(startDate));
                pstmtThangCaoNhat.setDate(4, Date.valueOf(endDate));
            }
            ResultSet rsThangCaoNhat = pstmtThangCaoNhat.executeQuery();
            String thangCaoNhat = rsThangCaoNhat.next() ? rsThangCaoNhat.getInt("thang") + "/" + rsThangCaoNhat.getInt("nam") : "Không có";
            lblThangCaoNhat.setText(thangCaoNhat);

            // Doanh thu trung bình mỗi hóa đơn (vé + đồ ăn)
            String sqlDoanhThuTB = "SELECT AVG(tongTien) as doanhThuTB FROM (SELECT tongTien FROM HoaDon UNION ALL SELECT tongTien FROM hoaDonDoAn) as combined";
            if (startDate != null && endDate != null) {
                sqlDoanhThuTB = "SELECT AVG(tongTien) as doanhThuTB FROM (SELECT tongTien FROM HoaDon WHERE ngayMua BETWEEN ? AND ? UNION ALL SELECT tongTien FROM hoaDonDoAn WHERE ngayMua BETWEEN ? AND ?) as combined";
            }
            PreparedStatement pstmtDoanhThuTB = conn.prepareStatement(sqlDoanhThuTB);
            if (startDate != null && endDate != null) {
                pstmtDoanhThuTB.setDate(1, Date.valueOf(startDate));
                pstmtDoanhThuTB.setDate(2, Date.valueOf(endDate));
                pstmtDoanhThuTB.setDate(3, Date.valueOf(startDate));
                pstmtDoanhThuTB.setDate(4, Date.valueOf(endDate));
            }
            ResultSet rsDoanhThuTB = pstmtDoanhThuTB.executeQuery();
            double doanhThuTB = rsDoanhThuTB.next() ? rsDoanhThuTB.getDouble("doanhThuTB") : 0;
            lblDoanhThuTrungBinh.setText(String.format("%,.2f VND", doanhThuTB));

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin thống kê: " + e.getMessage());
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
        lblTongHoaDon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnApDung = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dpNgayTruoc = new com.github.lgooddatepicker.components.DatePicker();
        dpNgaySau = new com.github.lgooddatepicker.components.DatePicker();
        lblTongDoanhThu = new javax.swing.JLabel();
        lblThangCaoNhat = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDoanhThuTrungBinh = new javax.swing.JLabel();
        btnXemBieuDo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ TỔNG DOANH THU ");

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
                .addGap(93, 93, 93))
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

        lblTongHoaDon.setBackground(new java.awt.Color(240, 240, 240));
        lblTongHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongHoaDon.setForeground(new java.awt.Color(0, 153, 255));
        lblTongHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTongHoaDon.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng doanh thu:");

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tổng số hóa đơn:");

        lblTongDoanhThu.setBackground(new java.awt.Color(240, 240, 240));
        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(0, 153, 255));
        lblTongDoanhThu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTongDoanhThu.setOpaque(true);

        lblThangCaoNhat.setBackground(new java.awt.Color(240, 240, 240));
        lblThangCaoNhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThangCaoNhat.setForeground(new java.awt.Color(0, 153, 255));
        lblThangCaoNhat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblThangCaoNhat.setOpaque(true);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Tháng có doanh thu cao nhất:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Doanh thu trung bình mỗi hóa đơn:");

        lblDoanhThuTrungBinh.setBackground(new java.awt.Color(240, 240, 240));
        lblDoanhThuTrungBinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDoanhThuTrungBinh.setForeground(new java.awt.Color(0, 153, 255));
        lblDoanhThuTrungBinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDoanhThuTrungBinh.setOpaque(true);

        btnXemBieuDo.setText("Xem Biểu Đồ");
        btnXemBieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemBieuDoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dpNgayTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dpNgaySau, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnApDung)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnLamMoi)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnXemBieuDo))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblDoanhThuTrungBinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(53, 53, 53)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblThangCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(15, 15, 15))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 394, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(lblThangCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lblDoanhThuTrungBinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dpNgayTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnApDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(dpNgaySau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXemBieuDo))
                .addGap(22, 22, 22))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2)
                    .addContainerGap(188, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        dpNgayTruoc.clear();
        dpNgaySau.clear();
        loadThongKe();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        LocalDate startDate = dpNgayTruoc.getDate();
        LocalDate endDate = dpNgaySau.getDate();

        if (startDate != null && endDate != null) {
            if (!endDate.isBefore(startDate)) {
                loadThongKeByDateRange(startDate, endDate);
            } else {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu!");
                loadThongKe();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!");
            loadThongKe();
        }
    }//GEN-LAST:event_btnApDungActionPerformed

    private void btnXemBieuDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemBieuDoActionPerformed
        // TODO add your handling code here:
        LocalDate startDate = dpNgayTruoc.getDate();
        LocalDate endDate = dpNgaySau.getDate();

        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu!");
                return;
            }
        }
        new bieuDoChiTietDoanhThu(startDate, endDate).setVisible(true);
    }//GEN-LAST:event_btnXemBieuDoActionPerformed

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
            java.util.logging.Logger.getLogger(chiTietTongDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietTongDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietTongDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietTongDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietTongDoanhThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnXemBieuDo;
    private com.github.lgooddatepicker.components.DatePicker dpNgaySau;
    private com.github.lgooddatepicker.components.DatePicker dpNgayTruoc;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblDoanhThuTrungBinh;
    private javax.swing.JLabel lblThangCaoNhat;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTongHoaDon;
    // End of variables declaration//GEN-END:variables
}
