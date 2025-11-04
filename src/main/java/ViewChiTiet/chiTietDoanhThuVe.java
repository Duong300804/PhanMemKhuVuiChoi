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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class chiTietDoanhThuVe extends javax.swing.JFrame {

    /**
     * Creates new form chiTietDoanhThuVe
     */
    public chiTietDoanhThuVe() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa cửa sổ
        loadDoanhThuCards();
        loadThongKe();
    }

    private JPanel createDoanhThuCard(String loaiVe, int soLuong, int doanhThu) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblLoaiVe = new JLabel("Loại vé: " + loaiVe);
        JLabel lblDaBan = new JLabel("Đã bán: " + soLuong);
        JLabel lblDoanhThu = new JLabel("Doanh thu: " + String.format("%,d VND", doanhThu));

        lblLoaiVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDaBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblLoaiVe);
        content.add(Box.createVerticalStrut(5));
        content.add(lblDaBan);
        content.add(Box.createVerticalStrut(5));
        content.add(lblDoanhThu);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    private void loadThongKe() {
        try {
            Connection conn = ConnectDB.getConnection();
            // Tổng doanh thu
            String sqlTongDoanhThu = "SELECT SUM(tongTien) as tongDoanhThu FROM HoaDon";
            PreparedStatement pstmtTongDoanhThu = conn.prepareStatement(sqlTongDoanhThu);
            ResultSet rsTongDoanhThu = pstmtTongDoanhThu.executeQuery();
            int tongDoanhThu = rsTongDoanhThu.next() ? rsTongDoanhThu.getInt("tongDoanhThu") : 0;
            lblTongDoanhThu.setText(String.format("%,d VND", tongDoanhThu));

            // Tổng số lượng vé bán ra
            String sqlTongVe = "SELECT SUM(soLuong) as tongVe FROM ChiTietHoaDon";
            PreparedStatement pstmtTongVe = conn.prepareStatement(sqlTongVe);
            ResultSet rsTongVe = pstmtTongVe.executeQuery();
            int tongVe = rsTongVe.next() ? rsTongVe.getInt("tongVe") : 0;
            lblTongVe.setText(String.valueOf(tongVe));

            // Loại vé bán được nhiều nhất
            String sqlVeNhieuNhat = "SELECT TOP 1 v.loaiVe, SUM(ct.soLuong) as soLuong "
                    + "FROM ChiTietHoaDon ct JOIN Ve v ON ct.maVe = v.maVe "
                    + "GROUP BY v.loaiVe ORDER BY soLuong DESC";
            PreparedStatement pstmtVeNhieuNhat = conn.prepareStatement(sqlVeNhieuNhat);
            ResultSet rsVeNhieuNhat = pstmtVeNhieuNhat.executeQuery();
            String veNhieuNhat = rsVeNhieuNhat.next() ? rsVeNhieuNhat.getString("loaiVe") : "Không có";
            lblVeNhieuNhat.setText(veNhieuNhat);

            // Tháng có doanh thu cao nhất
            String sqlThangCaoNhat = "SELECT TOP 1 MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM HoaDon GROUP BY MONTH(ngayMua), YEAR(ngayMua) ORDER BY doanhThu DESC";
            PreparedStatement pstmtThangCaoNhat = conn.prepareStatement(sqlThangCaoNhat);
            ResultSet rsThangCaoNhat = pstmtThangCaoNhat.executeQuery();
            String thangCaoNhat = rsThangCaoNhat.next() ? rsThangCaoNhat.getInt("thang") + "/" + rsThangCaoNhat.getInt("nam") : "Không có";
            lblThangCaoNhat.setText(thangCaoNhat);

            // Doanh thu trung bình mỗi hóa đơn
            String sqlDoanhThuTB = "SELECT AVG(tongTien) as doanhThuTB FROM HoaDon";
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

    public void loadDoanhThuCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT v.loaiVe, SUM(ct.soLuong) as soLuong, SUM(ct.soLuong * v.giaVe) as doanhThu "
                    + "FROM ChiTietHoaDon ct JOIN Ve v ON ct.maVe = v.maVe "
                    + "GROUP BY v.loaiVe";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            pnlDanhSachDoanhThu.removeAll();

            while (rs.next()) {
                String loaiVe = rs.getString("loaiVe");
                int soLuong = rs.getInt("soLuong");
                int doanhThu = rs.getInt("doanhThu");

                JPanel card = createDoanhThuCard(loaiVe, soLuong, doanhThu);
                pnlDanhSachDoanhThu.add(card);
            }

            pnlDanhSachDoanhThu.revalidate();
            pnlDanhSachDoanhThu.repaint();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách doanh thu!");
        }
    }

    public void loadDoanhThuCardsByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT v.loaiVe, SUM(ct.soLuong) as soLuong, SUM(ct.soLuong * v.giaVe) as doanhThu "
                    + "FROM ChiTietHoaDon ct JOIN Ve v ON ct.maVe = v.maVe "
                    + "JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon ";
            if (startDate != null && endDate != null) {
                sql += "WHERE hd.ngayMua BETWEEN ? AND ? ";
            }
            sql += "GROUP BY v.loaiVe";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (startDate != null && endDate != null) {
                pstmt.setDate(1, Date.valueOf(startDate));
                pstmt.setDate(2, Date.valueOf(endDate));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachDoanhThu.removeAll();

            while (rs.next()) {
                String loaiVe = rs.getString("loaiVe");
                int soLuong = rs.getInt("soLuong");
                int doanhThu = rs.getInt("doanhThu");

                JPanel card = createDoanhThuCard(loaiVe, soLuong, doanhThu);
                pnlDanhSachDoanhThu.add(card);
            }

            // Cập nhật lại các nhãn thống kê 
            String sqlTongDoanhThu = "SELECT SUM(tongTien) as tongDoanhThu FROM HoaDon";
            String sqlTongVe = "SELECT SUM(soLuong) as tongVe FROM ChiTietHoaDon ct JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon";
            String sqlVeNhieuNhat = "SELECT TOP 1 v.loaiVe, SUM(ct.soLuong) as soLuong "
                    + "FROM ChiTietHoaDon ct JOIN Ve v ON ct.maVe = v.maVe "
                    + "JOIN HoaDon hd ON ct.maHoaDon = hd.maHoaDon ";
            String sqlDoanhThuTB = "SELECT AVG(tongTien) as doanhThuTB FROM HoaDon";
            if (startDate != null && endDate != null) {
                sqlTongDoanhThu += " WHERE ngayMua BETWEEN ? AND ?";
                sqlTongVe += " WHERE hd.ngayMua BETWEEN ? AND ?";
                sqlVeNhieuNhat += " WHERE hd.ngayMua BETWEEN ? AND ? GROUP BY v.loaiVe ORDER BY soLuong DESC";
                sqlDoanhThuTB += " WHERE ngayMua BETWEEN ? AND ?";
            }

            // Tổng doanh thu
            PreparedStatement pstmtTongDoanhThu = conn.prepareStatement(sqlTongDoanhThu);
            if (startDate != null && endDate != null) {
                pstmtTongDoanhThu.setDate(1, Date.valueOf(startDate));
                pstmtTongDoanhThu.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsTongDoanhThu = pstmtTongDoanhThu.executeQuery();
            int tongDoanhThu = rsTongDoanhThu.next() ? rsTongDoanhThu.getInt("tongDoanhThu") : 0;
            lblTongDoanhThu.setText(String.format("%,d VND", tongDoanhThu));

            // Tổng số lượng vé bán ra
            PreparedStatement pstmtTongVe = conn.prepareStatement(sqlTongVe);
            if (startDate != null && endDate != null) {
                pstmtTongVe.setDate(1, Date.valueOf(startDate));
                pstmtTongVe.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsTongVe = pstmtTongVe.executeQuery();
            int tongVe = rsTongVe.next() ? rsTongVe.getInt("tongVe") : 0;
            lblTongVe.setText(String.valueOf(tongVe));

            // Loại vé bán được nhiều nhất
            PreparedStatement pstmtVeNhieuNhat = conn.prepareStatement(sqlVeNhieuNhat);
            if (startDate != null && endDate != null) {
                pstmtVeNhieuNhat.setDate(1, Date.valueOf(startDate));
                pstmtVeNhieuNhat.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsVeNhieuNhat = pstmtVeNhieuNhat.executeQuery();
            String veNhieuNhat = rsVeNhieuNhat.next() ? rsVeNhieuNhat.getString("loaiVe") : "Không có";
            lblVeNhieuNhat.setText(veNhieuNhat);

            // Doanh thu trung bình mỗi hóa đơn
            PreparedStatement pstmtDoanhThuTB = conn.prepareStatement(sqlDoanhThuTB);
            if (startDate != null && endDate != null) {
                pstmtDoanhThuTB.setDate(1, Date.valueOf(startDate));
                pstmtDoanhThuTB.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsDoanhThuTB = pstmtDoanhThuTB.executeQuery();
            double doanhThuTB = rsDoanhThuTB.next() ? rsDoanhThuTB.getDouble("doanhThuTB") : 0;
            lblDoanhThuTrungBinh.setText(String.format("%,.2f VND", doanhThuTB));

            pnlDanhSachDoanhThu.revalidate();
            pnlDanhSachDoanhThu.repaint();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách doanh thu: " + e.getMessage());
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
        lblTongVe = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachDoanhThu = new javax.swing.JPanel();
        btnLamMoi = new javax.swing.JButton();
        btnApDung = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dpNgayTruoc = new com.github.lgooddatepicker.components.DatePicker();
        dpNgaySau = new com.github.lgooddatepicker.components.DatePicker();
        jLabel3 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        lblThangCaoNhat = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblVeNhieuNhat = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDoanhThuTrungBinh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setText("THỐNG KÊ DOANH THU VÉ");
        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/bill.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(120, 120, 120))
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

        jLabel1.setText("Tổng doanh thu:");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTongVe.setBackground(new java.awt.Color(240, 240, 240));
        lblTongVe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTongVe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongVe.setForeground(new java.awt.Color(0, 153, 255));
        lblTongVe.setOpaque(true);

        pnlDanhSachDoanhThu.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachDoanhThu);

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

        jLabel2.setText("Tổng số lượng vé bán ra:");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel3.setText("Loại vé bán được nhiều nhất:");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTongDoanhThu.setBackground(new java.awt.Color(240, 240, 240));
        lblTongDoanhThu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(0, 153, 255));
        lblTongDoanhThu.setOpaque(true);

        lblThangCaoNhat.setBackground(new java.awt.Color(240, 240, 240));
        lblThangCaoNhat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblThangCaoNhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThangCaoNhat.setForeground(new java.awt.Color(0, 153, 255));
        lblThangCaoNhat.setOpaque(true);

        jLabel4.setText("Tháng có doanh thu cao nhất:");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblVeNhieuNhat.setBackground(new java.awt.Color(240, 240, 240));
        lblVeNhieuNhat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblVeNhieuNhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblVeNhieuNhat.setForeground(new java.awt.Color(0, 153, 255));
        lblVeNhieuNhat.setOpaque(true);

        jLabel5.setText("Doanh thu trung bình mỗi hóa đơn:");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblDoanhThuTrungBinh.setBackground(new java.awt.Color(240, 240, 240));
        lblDoanhThuTrungBinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDoanhThuTrungBinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDoanhThuTrungBinh.setForeground(new java.awt.Color(0, 153, 255));
        lblDoanhThuTrungBinh.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDoanhThuTrungBinh, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTongVe, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThangCaoNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblVeNhieuNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dpNgayTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpNgaySau, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnApDung)
                            .addComponent(btnLamMoi))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(lblTongVe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblVeNhieuNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(lblThangCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lblDoanhThuTrungBinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dpNgayTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnApDung)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi)
                    .addComponent(dpNgaySau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        dpNgayTruoc.clear();
        dpNgaySau.clear();
        loadDoanhThuCards();
        loadThongKe();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        LocalDate startDate = dpNgayTruoc.getDate();
        LocalDate endDate = dpNgaySau.getDate();

        if (startDate != null && endDate != null) {
            if (!endDate.isBefore(startDate)) {
                loadDoanhThuCardsByDateRange(startDate, endDate);
            } else {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu!");
                loadDoanhThuCards();
                loadThongKe();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!");
            loadDoanhThuCards();
            loadThongKe();
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
            java.util.logging.Logger.getLogger(chiTietDoanhThuVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietDoanhThuVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietDoanhThuVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietDoanhThuVe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietDoanhThuVe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private com.github.lgooddatepicker.components.DatePicker dpNgaySau;
    private com.github.lgooddatepicker.components.DatePicker dpNgayTruoc;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDoanhThuTrungBinh;
    private javax.swing.JLabel lblThangCaoNhat;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JLabel lblTongVe;
    private javax.swing.JLabel lblVeNhieuNhat;
    private javax.swing.JPanel pnlDanhSachDoanhThu;
    // End of variables declaration//GEN-END:variables
}
