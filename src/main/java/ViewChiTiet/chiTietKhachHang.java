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
import java.time.LocalDate;
import java.time.Period;
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
public class chiTietKhachHang extends javax.swing.JFrame {

    /**
     * Creates new form chiTietKhachHang
     */
    public chiTietKhachHang() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        loadGioiTinhComboBox();
        loadKhachHangCards();
    }

    private JPanel createKhachHangCard(String ma, String hoTen, String ngaySinh, String sdt, String gioiTinh) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(new Color(242, 242, 242));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel lblHoTen = new JLabel("Họ tên: " + hoTen);
        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblNgaySinh = new JLabel("Ngày sinh: " + ngaySinh);
        JLabel lblSDT = new JLabel("SĐT: " + sdt);
        JLabel lblGioiTinh = new JLabel("Giới tính: " + gioiTinh);

        lblHoTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        content.add(lblHoTen);
        content.add(Box.createVerticalStrut(5));
        content.add(lblMa);
        content.add(Box.createVerticalStrut(5));
        content.add(lblNgaySinh);
        content.add(Box.createVerticalStrut(5));
        content.add(lblSDT);
        content.add(Box.createVerticalStrut(5));
        content.add(lblGioiTinh);

        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        return card;
    }

    public void loadKhachHangCards() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT maKhachHang, hoTen, ngaySinh, SDT, gioiTinh FROM khachHang";
            ResultSet rs = stmt.executeQuery(sql);

            pnlDanhSachKhachHang.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String ngaySinh = rs.getString("ngaySinh");
                String sdt = rs.getString("SDT");
                String gioiTinh = rs.getString("gioiTinh");

                JPanel card = createKhachHangCard(ma, hoTen, ngaySinh, sdt, gioiTinh);
                pnlDanhSachKhachHang.add(card);
            }

            lblTongKhachHang.setText(String.valueOf(count));
            pnlDanhSachKhachHang.revalidate();
            pnlDanhSachKhachHang.repaint();
            // conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng!");
        }
    }

    private void loadGioiTinhComboBox() {
        try {
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT gioiTinh FROM khachHang";
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

    private int calculateAge(String ngaySinh) {
        try {
            LocalDate birthDate = LocalDate.parse(ngaySinh);
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean validateInput() {
        String tuoiTruocStr = txtTuoiTruoc.getText().trim();
        String tuoiSauStr = txtTuoiSau.getText().trim();

        // Check if age fields are filled when used
        if (!tuoiTruocStr.isEmpty() || !tuoiSauStr.isEmpty()) {
            if (tuoiTruocStr.isEmpty() || tuoiSauStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ khoảng độ tuổi.");
                return false;
            }

            // Validate number format
            int tuoiTruoc, tuoiSau;
            try {
                tuoiTruoc = Integer.parseInt(tuoiTruocStr);
                tuoiSau = Integer.parseInt(tuoiSauStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho độ tuổi.");
                txtTuoiTruoc.requestFocus();
                return false;
            }

            // Validate positive numbers and logical range
            if (tuoiTruoc < 0 || tuoiSau < 0) {
                JOptionPane.showMessageDialog(this, "Độ tuổi phải là số không âm.");
                txtTuoiTruoc.requestFocus();
                return false;
            }

            if (tuoiSau <= tuoiTruoc) {
                JOptionPane.showMessageDialog(this, "Độ tuổi sau phải lớn hơn độ tuổi trước.");
                txtTuoiSau.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void refreshData() {
        txtTuoiTruoc.setText("");
        txtTuoiSau.setText("");
        cboGioiTinh.setSelectedItem("Chọn giới tính");
        loadKhachHangCards();
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
        lblTongKhachHang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDanhSachKhachHang = new javax.swing.JPanel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        txtTuoiTruoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTuoiSau = new javax.swing.JTextField();
        btnApDung = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel.setText("THỐNG KÊ KHÁCH HÀNG");

        headerImgae1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/rating.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae1)
                .addGap(133, 133, 133))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae1)
                    .addComponent(headerLabel))
                .addGap(10, 10, 10))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số khách hàng:");

        lblTongKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        lblTongKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongKhachHang.setForeground(new java.awt.Color(0, 153, 255));

        pnlDanhSachKhachHang.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        jScrollPane1.setViewportView(pnlDanhSachKhachHang);

        cboGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboGioiTinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboGioiTinhItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("->>");

        btnApDung.setText("Áp dụng");
        btnApDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApDungActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Độ tuổi:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTongKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTuoiTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTuoiSau, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnApDung)
                    .addComponent(btnLamMoi))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblTongKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnApDung))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTuoiTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txtTuoiSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi)
                            .addComponent(jLabel3))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboGioiTinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboGioiTinhItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioiTinhItemStateChanged

    private void btnApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }

        try {
            Connection conn = ConnectDB.getConnection();
            String sql = "SELECT * FROM khachHang";  //câu truy vấn ở đây
            //Kiểm tra xem có chọn giới tính hay nhập khoảng độ tuổi không
            boolean hasGioiTinhFilter = !cboGioiTinh.getSelectedItem().equals("Chọn giới tính");
            boolean hasTuoiFilter = !txtTuoiTruoc.getText().trim().isEmpty() && !txtTuoiSau.getText().trim().isEmpty();

            if (hasGioiTinhFilter || hasTuoiFilter) {
                sql += " WHERE ";   //Nếu có chọn giới tính hoặc độ tuổi thì thêm where vào câu truy vấn
                if (hasGioiTinhFilter) {
                    sql += "gioiTinh = ?";  //thêm điều kiện giới tính vào câu sql
                    if (hasTuoiFilter) {
                        sql += " AND ";  //nếu có cả 2 thêm điều kiện end
                    }
                }
                if (hasTuoiFilter) {
                    sql += "DATEDIFF(YEAR, ngaySinh, GETDATE()) BETWEEN ? AND ?"; //lọc độ tuổi
                }
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;      //gán giá trị cho dấu ? trong câu lệnh sql bắt đầu từ vị trí 1
            if (hasGioiTinhFilter) {
                pstmt.setString(paramIndex++, cboGioiTinh.getSelectedItem().toString());
            }
            if (hasTuoiFilter) {
                pstmt.setInt(paramIndex++, Integer.parseInt(txtTuoiTruoc.getText().trim()));
                pstmt.setInt(paramIndex, Integer.parseInt(txtTuoiSau.getText().trim()));
            }

            ResultSet rs = pstmt.executeQuery();
            pnlDanhSachKhachHang.removeAll();
            int count = 0;

            while (rs.next()) {
                count++;
                String ma = rs.getString("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String ngaySinh = rs.getString("ngaySinh");
                String sdt = rs.getString("SDT");
                String gioiTinh = rs.getString("gioiTinh");

                JPanel card = createKhachHangCard(ma, hoTen, ngaySinh, sdt, gioiTinh);
                pnlDanhSachKhachHang.add(card);
            }

            lblTongKhachHang.setText(String.valueOf(count));
            pnlDanhSachKhachHang.revalidate();
            pnlDanhSachKhachHang.repaint();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc danh sách khách hàng!");
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
            java.util.logging.Logger.getLogger(chiTietKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chiTietKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chiTietKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chiTietKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chiTietKhachHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApDung;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel headerImgae1;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongKhachHang;
    private javax.swing.JPanel pnlDanhSachKhachHang;
    private javax.swing.JTextField txtTuoiSau;
    private javax.swing.JTextField txtTuoiTruoc;
    // End of variables declaration//GEN-END:variables
}
