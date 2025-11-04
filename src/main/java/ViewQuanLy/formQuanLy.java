/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewQuanLy;

import Model.taiKhoan;
import ViewLogin.formLogin;
import ViewMenu.formDoAn;
import ViewMenu.formDoLuuNiem;
import ViewMenu.formGuiDo;
import ViewMenu.formHoaDon;
import ViewMenu.formHoaDonDoAn;
import ViewMenu.formKhachHang;
import ViewMenu.formNhanVien;
import ViewMenu.formTaiKhoan;
import ViewMenu.formPhanKhu;
import ViewMenu.formSuKien;
import ViewMenu.formThietBi;
import ViewMenu.formTrangChu;
import ViewMenu.formTroChoi;
import ViewMenu.formVe;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class formQuanLy extends javax.swing.JFrame {

    private formTrangChu panelformTrangChu;
    private formPhanKhu panelformPhanKhu;
    private formTaiKhoan panelformTaiKhoan;
    private formTroChoi panelformTroChoi;
    private formThietBi panelformThietBi;
    private formSuKien panelformSuKien;
    private formNhanVien panelformNhanVien;
    private formKhachHang panelformKhachHang;
    private formGuiDo panelformGuiDo;
    private formVe panelformVe;
    private formHoaDon panelformHoaDon;
    private formDoAn panelformDoAn;
    private formDoLuuNiem panelformDoLuuNiem;
    private formHoaDonDoAn panelformHoaDonDoAn;
    private String loaiTaiKhoan;
    private taiKhoan taiKhoanDangNhap;

    /**
     * Creates new form formQuanLy
     */
    public formQuanLy(taiKhoan taiKhoanDangNhap) {
    this.taiKhoanDangNhap = taiKhoanDangNhap;
    this.loaiTaiKhoan = taiKhoanDangNhap.getLoaiTaiKhoan(); // lấy role
        initComponents();
//    setExtendedState(JFrame.MAXIMIZED_BOTH); // Full màn hình
        setSize(1024, 768); // ví dụ: rộng 1024px, cao 768px
        setLocationRelativeTo(null); // để cửa sổ hiển thị giữa màn hình

        //Nếu không phải admin thì ẩn đi
        if (!loaiTaiKhoan.equalsIgnoreCase("Admin")) {
            btnTaiKhoan.setEnabled(false);
            btnNhanVien.setEnabled(false);
        }

        //Khởi tạo các Menu: các chức năng quản lý 
        panelformTrangChu = new formTrangChu();
        panelformTaiKhoan = new formTaiKhoan();
        panelformPhanKhu = new formPhanKhu();
        panelformTroChoi = new formTroChoi();
        panelformThietBi = new formThietBi();
        panelformSuKien = new formSuKien();
        panelformNhanVien = new formNhanVien();
        panelformKhachHang = new formKhachHang();
        panelformGuiDo = new formGuiDo();
        panelformVe = new formVe();
        panelformHoaDon = new formHoaDon(taiKhoanDangNhap);
        panelformDoAn = new formDoAn();
        panelformDoLuuNiem = new formDoLuuNiem();
        panelformHoaDonDoAn = new formHoaDonDoAn(taiKhoanDangNhap);
        // Thêm vào jLayeredPane1
        chuyenLayeredPanel.add(panelformTrangChu, "Trang Chủ");
        chuyenLayeredPanel.add(panelformTaiKhoan, "Tài Khoản");
        chuyenLayeredPanel.add(panelformPhanKhu, "Phân Khu");
        chuyenLayeredPanel.add(panelformTroChoi, "Trò Chơi");
        chuyenLayeredPanel.add(panelformThietBi, "Thiết Bị");
        chuyenLayeredPanel.add(panelformSuKien, "Sự Kiện");
        chuyenLayeredPanel.add(panelformNhanVien, "Nhân Viên");
        chuyenLayeredPanel.add(panelformKhachHang, "Khách Hàng");
        chuyenLayeredPanel.add(panelformGuiDo, "Gửi Đồ");
        chuyenLayeredPanel.add(panelformVe, "Vé");
        chuyenLayeredPanel.add(panelformHoaDon, "Hóa Đơn");
        chuyenLayeredPanel.add(panelformDoAn, "Đồ Ăn");
        chuyenLayeredPanel.add(panelformDoLuuNiem, "Đồ Lưu Niệm");
        chuyenLayeredPanel.add(panelformHoaDonDoAn, "Hóa Đơn Đồ Ăn");

        // Hiển thị menu1 mặc định
        showPanel(panelformTrangChu);
        setActiveButton(btnTrangChu);
    }
    //Hàm để hiển thị panel

    private void showPanel(JPanel panel) {
        chuyenLayeredPanel.removeAll();
        chuyenLayeredPanel.add(panel);
        chuyenLayeredPanel.repaint();
        chuyenLayeredPanel.revalidate();
    }
    //Hàm để đặt màu cho nút khi chuyển trang

    private void setActiveButton(JButton activeButton) {
        Color defaultColor = new Color(51, 153, 255);
        // Đặt lại màu cho tất cả các nút về mặc định
        btnTrangChu.setBackground(defaultColor);
        btnTaiKhoan.setBackground(defaultColor);
        btnPhanKhu.setBackground(defaultColor);
        btnTroChoi.setBackground(defaultColor);
        btnThietBi.setBackground(defaultColor);
        btnSuKien.setBackground(defaultColor);
        btnNhanVien.setBackground(defaultColor);
        btnKhachHang.setBackground(defaultColor);
        btnGuiDo.setBackground(defaultColor);
        btnVe.setBackground(defaultColor);
        btnHoaDon.setBackground(defaultColor);
        btnDoAn.setBackground(defaultColor);
        btnDoLuuNiem.setBackground(defaultColor);
        btnHoaDonDoAn.setBackground(defaultColor);
        // Đặt màu xanh cho nút đang được chọn
        activeButton.setBackground(new Color(51, 51, 255));
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
        headerLable1 = new javax.swing.JLabel();
        headerLable2 = new javax.swing.JLabel();
        menuPanel = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnPhanKhu = new javax.swing.JButton();
        btnTroChoi = new javax.swing.JButton();
        btnThietBi = new javax.swing.JButton();
        btnSuKien = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnGuiDo = new javax.swing.JButton();
        btnVe = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnDoAn = new javax.swing.JButton();
        btnHoaDonDoAn = new javax.swing.JButton();
        btnDoLuuNiem = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        chuyenLayeredPanel = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        headerPanel.setBackground(new java.awt.Color(232, 64, 60));
        headerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        headerLable1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        headerLable1.setForeground(new java.awt.Color(255, 255, 255));
        headerLable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-manage-40.png"))); // NOI18N
        headerLable1.setText("QUẢN LÝ ");

        headerLable2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        headerLable2.setForeground(new java.awt.Color(255, 255, 255));
        headerLable2.setText("KHU VUI CHƠI GIẢI TRÍ");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(headerLable2))
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(headerLable1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerLable1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerLable2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuPanel.setBackground(new java.awt.Color(82, 83, 81));
        menuPanel.setLayout(new java.awt.GridLayout(0, 1));

        btnTrangChu.setBackground(new java.awt.Color(51, 153, 255));
        btnTrangChu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-home-25.png"))); // NOI18N
        btnTrangChu.setText("Trang Chủ ");
        btnTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        menuPanel.add(btnTrangChu);

        btnTaiKhoan.setBackground(new java.awt.Color(51, 153, 255));
        btnTaiKhoan.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-account-25.png"))); // NOI18N
        btnTaiKhoan.setText("Tài Khoản");
        btnTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });
        menuPanel.add(btnTaiKhoan);

        btnPhanKhu.setBackground(new java.awt.Color(51, 153, 255));
        btnPhanKhu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnPhanKhu.setForeground(new java.awt.Color(255, 255, 255));
        btnPhanKhu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-house-25.png"))); // NOI18N
        btnPhanKhu.setText("Phân Khu");
        btnPhanKhu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPhanKhu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanKhuActionPerformed(evt);
            }
        });
        menuPanel.add(btnPhanKhu);

        btnTroChoi.setBackground(new java.awt.Color(51, 153, 255));
        btnTroChoi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnTroChoi.setForeground(new java.awt.Color(255, 255, 255));
        btnTroChoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-toys-25.png"))); // NOI18N
        btnTroChoi.setText("Trò Chơi");
        btnTroChoi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTroChoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroChoiActionPerformed(evt);
            }
        });
        menuPanel.add(btnTroChoi);

        btnThietBi.setBackground(new java.awt.Color(51, 153, 255));
        btnThietBi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnThietBi.setForeground(new java.awt.Color(255, 255, 255));
        btnThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-machine-25.png"))); // NOI18N
        btnThietBi.setText("Thiết bị ");
        btnThietBi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThietBiActionPerformed(evt);
            }
        });
        menuPanel.add(btnThietBi);

        btnSuKien.setBackground(new java.awt.Color(51, 153, 255));
        btnSuKien.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSuKien.setForeground(new java.awt.Color(255, 255, 255));
        btnSuKien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-event-25.png"))); // NOI18N
        btnSuKien.setText("Sự kiện");
        btnSuKien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSuKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuKienActionPerformed(evt);
            }
        });
        menuPanel.add(btnSuKien);

        btnNhanVien.setBackground(new java.awt.Color(51, 153, 255));
        btnNhanVien.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-employee-25.png"))); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        menuPanel.add(btnNhanVien);

        btnKhachHang.setBackground(new java.awt.Color(51, 153, 255));
        btnKhachHang.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-customer-25.png"))); // NOI18N
        btnKhachHang.setText("Khách Hàng");
        btnKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });
        menuPanel.add(btnKhachHang);

        btnGuiDo.setBackground(new java.awt.Color(51, 153, 255));
        btnGuiDo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnGuiDo.setForeground(new java.awt.Color(255, 255, 255));
        btnGuiDo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-bag-25.png"))); // NOI18N
        btnGuiDo.setText("Gửi Đồ");
        btnGuiDo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuiDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiDoActionPerformed(evt);
            }
        });
        menuPanel.add(btnGuiDo);

        btnVe.setBackground(new java.awt.Color(51, 153, 255));
        btnVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnVe.setForeground(new java.awt.Color(255, 255, 255));
        btnVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-ticket-25.png"))); // NOI18N
        btnVe.setText("Vé");
        btnVe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVeActionPerformed(evt);
            }
        });
        menuPanel.add(btnVe);

        btnHoaDon.setBackground(new java.awt.Color(51, 153, 255));
        btnHoaDon.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-bill-25.png"))); // NOI18N
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });
        menuPanel.add(btnHoaDon);

        btnDoAn.setBackground(new java.awt.Color(51, 153, 255));
        btnDoAn.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDoAn.setForeground(new java.awt.Color(255, 255, 255));
        btnDoAn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-ingredients-25.png"))); // NOI18N
        btnDoAn.setText("Đồ Ăn");
        btnDoAn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDoAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoAnActionPerformed(evt);
            }
        });
        menuPanel.add(btnDoAn);

        btnHoaDonDoAn.setBackground(new java.awt.Color(51, 153, 255));
        btnHoaDonDoAn.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnHoaDonDoAn.setForeground(new java.awt.Color(255, 255, 255));
        btnHoaDonDoAn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-bill-25.png"))); // NOI18N
        btnHoaDonDoAn.setText("Hóa Đơn Đồ Ăn");
        btnHoaDonDoAn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHoaDonDoAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonDoAnActionPerformed(evt);
            }
        });
        menuPanel.add(btnHoaDonDoAn);

        btnDoLuuNiem.setBackground(new java.awt.Color(51, 153, 255));
        btnDoLuuNiem.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDoLuuNiem.setForeground(new java.awt.Color(255, 255, 255));
        btnDoLuuNiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-collectibles-25.png"))); // NOI18N
        btnDoLuuNiem.setText("Đồ Lưu Niệm");
        btnDoLuuNiem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDoLuuNiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoLuuNiemActionPerformed(evt);
            }
        });
        menuPanel.add(btnDoLuuNiem);

        btnDangXuat.setBackground(new java.awt.Color(204, 51, 0));
        btnDangXuat.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        menuPanel.add(btnDangXuat);

        chuyenLayeredPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        chuyenLayeredPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chuyenLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chuyenLayeredPanel)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        // TODO add your handling code here:
        panelformTrangChu.loadSoLuongTaiKhoan();
        panelformTrangChu.loadSoLuongPhanKhu();
        panelformTrangChu.loadSoLuongTroChoi();
        panelformTrangChu.loadSoLuongThietBi();
        panelformTrangChu.loadSoLuongDoAn();
        panelformTrangChu.loadSoLuongDoLuuNiem();
        panelformTrangChu.loadSoLuongSuKien();
        panelformTrangChu.loadSoLuongNhanVien();
        panelformTrangChu.loadSoLuongVe();
        panelformTrangChu.loadSoLuongHoaDon();
        panelformTrangChu.loadSoLuongKhachHang();
        panelformTrangChu.loadSoLuongGuiDo();
        panelformTrangChu.loadDoanhThuVe();
        panelformTrangChu.loadDoanhThuDoAn();
        panelformTrangChu.loadTongDoanhThu();
        panelformTrangChu.loadSoLuongHoaDonDoAn();
        showPanel(panelformTrangChu);
        setActiveButton(btnTrangChu);
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
        showPanel(panelformTaiKhoan);
        setActiveButton(btnTaiKhoan);
        panelformTaiKhoan.loadTenNhanVienToComboBox();
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnPhanKhuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanKhuActionPerformed
        // TODO add your handling code here:
        showPanel(panelformPhanKhu);
        setActiveButton(btnPhanKhu);
    }//GEN-LAST:event_btnPhanKhuActionPerformed

    private void btnTroChoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroChoiActionPerformed
        // TODO add your handling code here:
        showPanel(panelformTroChoi);
        setActiveButton(btnTroChoi);
        panelformTroChoi.loadTenPhanKhuToComboBox();
    }//GEN-LAST:event_btnTroChoiActionPerformed

    private void btnThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThietBiActionPerformed
        // TODO add your handling code here:
        showPanel(panelformThietBi);
        setActiveButton(btnThietBi);
        panelformThietBi.loadTenTroChoiToComboBox();
//        panelformThietBi.loadTinhTrangtoCombobox();
    }//GEN-LAST:event_btnThietBiActionPerformed

    private void btnSuKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuKienActionPerformed
        // TODO add your handling code here:
        showPanel(panelformSuKien);
        setActiveButton(btnSuKien);
    }//GEN-LAST:event_btnSuKienActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // TODO add your handling code here:
        showPanel(panelformNhanVien);
        setActiveButton(btnNhanVien);
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        showPanel(panelformKhachHang);
        setActiveButton(btnKhachHang);
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnGuiDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiDoActionPerformed
        // TODO add your handling code here:
        showPanel(panelformGuiDo);
        setActiveButton(btnGuiDo);
        panelformGuiDo.loadmaKhachHangToComboBox();
    }//GEN-LAST:event_btnGuiDoActionPerformed

    private void btnVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVeActionPerformed
        // TODO add your handling code here:
        showPanel(panelformVe);
        setActiveButton(btnVe);
    }//GEN-LAST:event_btnVeActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        showPanel(panelformHoaDon);
        setActiveButton(btnHoaDon);
        panelformHoaDon.loadTenNhanVienToComboBox();
        panelformHoaDon.loadcboMaVe();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnDoAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoAnActionPerformed
        // TODO add your handling code here:
        showPanel(panelformDoAn);
        setActiveButton(btnDoAn);
        panelformDoAn.LoadDuLieuBang();
    }//GEN-LAST:event_btnDoAnActionPerformed

    private void btnDoLuuNiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoLuuNiemActionPerformed
        // TODO add your handling code here:
        showPanel(panelformDoLuuNiem);
        setActiveButton(btnDoLuuNiem);
    }//GEN-LAST:event_btnDoLuuNiemActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        formLogin formLogin = new formLogin();
        formLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnHoaDonDoAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonDoAnActionPerformed
        // TODO add your handling code here:
        showPanel(panelformHoaDonDoAn);
        setActiveButton(btnHoaDonDoAn);
        panelformHoaDonDoAn.loadCboMaDoAn();
        panelformHoaDonDoAn.loadCboNhanVien();
    }//GEN-LAST:event_btnHoaDonDoAnActionPerformed

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
            java.util.logging.Logger.getLogger(formQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new formQuanLy(taiKhoanDangNhap).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoAn;
    private javax.swing.JButton btnDoLuuNiem;
    private javax.swing.JButton btnGuiDo;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnHoaDonDoAn;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPhanKhu;
    private javax.swing.JButton btnSuKien;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThietBi;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTroChoi;
    private javax.swing.JButton btnVe;
    private javax.swing.JLayeredPane chuyenLayeredPanel;
    private javax.swing.JLabel headerLable1;
    private javax.swing.JLabel headerLable2;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel menuPanel;
    // End of variables declaration//GEN-END:variables
}
