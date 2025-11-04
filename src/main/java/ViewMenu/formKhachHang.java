/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewMenu;

import static Database.ConnectDB.getConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LENOVO
 */
public class formKhachHang extends javax.swing.JPanel {

        DefaultTableModel model;
    Connection con;
    /**
     * Creates new form formKhachHang
     */
    public formKhachHang() {
        initComponents();
                load_ds();
    }

    
    private void ThemKhachHang(String maKH, String tenKH, Date ngaySinh, String SDT, String gioiTinh) {
        //throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            // kết nối database
            Connection con = getConnection();
            String sql = "insert into khachHang values(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKH);
            ps.setString(2, tenKH);
            ps.setDate(3, ngaySinh);
            ps.setString(4, SDT);
            ps.setString(5, gioiTinh);

            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
        }
        System.out.println("Đang thêm tác giả: " + maKH + ", " + tenKH);
    }

    private void ReadExcel(String tenfilepath) {
        try {
            FileInputStream fis = new FileInputStream(tenfilepath);
            //Tạo đối tượng Excel
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0); //Lấy sheet đầu tiên của file
            //Lấy ra các dòng bảng bảng
            Iterator<Row> itr = sheet.iterator();
            //Đọc dữ liệu
            itr.next();
            while (itr.hasNext()) {//Lặp đến hết các dòng trong excel
                Row row = itr.next();//Lấy dòng tiếp theo
                String maKH, tenKH, SDT, gioiTinh;
                maKH = row.getCell(0).getStringCellValue();
                tenKH = row.getCell(1).getStringCellValue();
                Date ngaySinh = new Date(row.getCell(2).getDateCellValue().getTime());
                SDT = row.getCell(3).getStringCellValue();
                gioiTinh = row.getCell(4).getStringCellValue();

                ThemKhachHang(maKH, tenKH, ngaySinh, SDT, gioiTinh);
            }
        } catch (Exception e) {

        }
    }
    
    private boolean ChecktrungMaKH(String maKH) {
        Boolean kq = false;
        try {
            con = getConnection();
            // b2 : tao đối tượng preparestatement 
            String sql = "Select * from khachHang Where maKhachHang=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKH);
            //tao doi tương resultset de lay ket qua tu ps 
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                kq = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kq;
    }

    public boolean KhongDeTrong() {
        if (txtMaKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống!");
            return false;
        }

        if (txtTenKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!");
            return false;
        }

        if (txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!");
            return false;
        }

        if (cboGioiTinh.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return false;
        }

        return true;
    }
    
    private void load_ds() {
        model = (DefaultTableModel) tblThongTinKhachHang.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM khachHang");

            while (rs.next()) {
                Object[] row = {
                    rs.getString("maKhachHang"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh"),
                    rs.getString("SDT"),
                    rs.getString("gioiTinh")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        informationPanel = new javax.swing.JPanel();
        labelTenKH = new javax.swing.JLabel();
        labelSDT = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        labelTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        labelIMG = new javax.swing.JLabel();
        labelNgaySinh = new javax.swing.JLabel();
        labelMaKH = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        labelGioiTinh = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        cboGioiTinh = new javax.swing.JComboBox<>();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        btnUpLoad = new javax.swing.JButton();
        txtNhapExcel = new javax.swing.JLabel();
        txtTimKiem1 = new javax.swing.JTextField();
        tabelPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongTinKhachHang = new javax.swing.JTable();

        headerPanel.setBackground(new java.awt.Color(51, 153, 255));

        headerLabel.setText("QUẢN LÝ KHÁCH HÀNG");
        headerLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 255, 255));

        headerImgae.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/rating.png"))); // NOI18N

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerImgae)
                .addGap(246, 246, 246))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerImgae)
                    .addComponent(headerLabel))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        informationPanel.setBackground(new java.awt.Color(204, 204, 204));

        labelTenKH.setText("Tên khách hàng:");
        labelTenKH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        labelSDT.setText("Số điện thoại:");
        labelSDT.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-add-25.png"))); // NOI18N
        btnThem.setText("THÊM");
        btnThem.setBackground(new java.awt.Color(76, 175, 80));
        btnThem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-pencil-25.png"))); // NOI18N
        btnSua.setText("SỬA");
        btnSua.setBackground(new java.awt.Color(76, 175, 80));
        btnSua.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-xls-export-25.png"))); // NOI18N
        btnXuatDuLieu.setText("XUẤT DỮ LIỆU");
        btnXuatDuLieu.setBackground(new java.awt.Color(76, 175, 80));
        btnXuatDuLieu.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXuatDuLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-update-25.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBackground(new java.awt.Color(76, 175, 80));
        btnLamMoi.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        labelTimKiem.setBackground(new java.awt.Color(51, 153, 255));
        labelTimKiem.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        labelTimKiem.setForeground(new java.awt.Color(51, 153, 255));
        labelTimKiem.setText("Tìm kiếm:");

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-delete-25.png"))); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.setBackground(new java.awt.Color(76, 175, 80));
        btnXoa.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-search-25.png"))); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.setBackground(new java.awt.Color(76, 175, 80));
        btnTimKiem.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        labelIMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/customer.png"))); // NOI18N

        labelNgaySinh.setText("Ngày sinh:");
        labelNgaySinh.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        labelMaKH.setText("Mã khách hàng:");
        labelMaKH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        labelGioiTinh.setText("Giới tính:");
        labelGioiTinh.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam ", "Nữ" }));

        btnUpLoad.setBackground(new java.awt.Color(76, 175, 80));
        btnUpLoad.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnUpLoad.setForeground(new java.awt.Color(255, 255, 255));
        btnUpLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Res/icons8-xls-export-25.png"))); // NOI18N
        btnUpLoad.setText("UPLOAD");
        btnUpLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpLoadActionPerformed(evt);
            }
        });

        txtNhapExcel.setBackground(new java.awt.Color(51, 153, 255));
        txtNhapExcel.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        txtNhapExcel.setForeground(new java.awt.Color(51, 153, 255));
        txtNhapExcel.setText("Nhập excel:");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelSDT)
                            .addComponent(labelNgaySinh)
                            .addComponent(labelGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaKhachHang)
                            .addComponent(txtTenKhachHang)
                            .addComponent(txtSDT)
                            .addComponent(cboGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                    .addGap(116, 116, 116)
                                    .addComponent(labelIMG)
                                    .addGap(79, 79, 79))
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(txtNhapExcel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtTimKiem1)
                                    .addContainerGap()))
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(labelTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))))))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNhapExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem)
                            .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnXoa)
                                .addComponent(btnLamMoi)
                                .addComponent(btnSua)))
                        .addGap(18, 18, 18))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(labelIMG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXuatDuLieu)
                    .addComponent(btnTimKiem)
                    .addComponent(btnUpLoad))
                .addGap(30, 30, 30))
        );

        tabelPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblThongTinKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "SĐT", "Giới tính"
            }
        ));
        tblThongTinKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongTinKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongTinKhachHang);

        javax.swing.GroupLayout tabelPanelLayout = new javax.swing.GroupLayout(tabelPanel);
        tabelPanel.setLayout(tabelPanelLayout);
        tabelPanelLayout.setHorizontalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tabelPanelLayout.setVerticalGroup(
            tabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabelPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
            String maKhachHang = txtMaKhachHang.getText();
            String tenKhachHang = txtTenKhachHang.getText();
            Date ngaySinh = new Date(txtNgaySinh.getDate().getTime());
            String SDT = txtSDT.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            // Kiem tra du lieu rong 
            if (!KhongDeTrong()) {
                return; // dừng lại nếu có lỗi
            }
            // ket noi db
            con = getConnection();
            String sql = "UPDATE khachHang SET maKhachHang=N'" + maKhachHang + "',hoTen=N'" + tenKhachHang + "',ngaySinh='" + ngaySinh + "',gioiTinh=N'" + gioiTinh + "' Where maKhachHang = '" + maKhachHang + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sửa Thành Công");
            load_ds();
            txtMaKhachHang.setText("");
            txtTenKhachHang.setText("");
            cboGioiTinh.setSelectedItem(0);
            txtNgaySinh.setDate(null);
            txtSDT.setText("");
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        // TODO add your handling code here:
        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("KhachHang");

            // Tiêu đề lớn
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("DANH SÁCH KHÁCH HÀNG");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // Dòng tiêu đề cột
            Row headerRow = sheet.createRow(1);
            headerRow.createCell(0).setCellValue("Mã KH");
            headerRow.createCell(1).setCellValue("Tên KH");
            headerRow.createCell(2).setCellValue("Ngày sinh");
            headerRow.createCell(3).setCellValue("Số điện thoại");
            headerRow.createCell(4).setCellValue("Giới tính");

            // Kết nối DB và lấy dữ liệu từ kết quả tìm kiếm
            String tenKH = txtTimKiem.getText().trim();
            String maKH = txtTimKiem.getText().trim();
            // ví dụ tìm theo tên khách
            Connection con = getConnection();
            String sql = "SELECT * FROM KhachHang WHERE hoTen LIKE ? OR maKhachHang LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + tenKH + "%");
            pst.setString(2, "%" + maKH + "%");
            ResultSet rs = pst.executeQuery();

            int rowIndex = 2;
            while (rs.next()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("maKhachHang"));
                row.createCell(1).setCellValue(rs.getString("hoTen"));
                row.createCell(2).setCellValue(rs.getString("ngaySinh"));
                row.createCell(3).setCellValue(rs.getString("sdt"));
                row.createCell(4).setCellValue(rs.getString("gioiTinh"));
            }

            // Cho người dùng chọn nơi lưu
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file Excel");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx");
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx"; // thêm đuôi nếu người dùng không nhập
                }

                FileOutputStream out = new FileOutputStream(filePath);
                workbook.write(out);
                out.close();
                workbook.close();
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + e.getMessage());
        }
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        String maKhachHang = txtMaKhachHang.getText().trim();

        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                con = getConnection();
                String sql = "DELETE FROM khachHang WHERE maKhachHang = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, maKhachHang);
                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để xóa.");
                }

                con.close();
                load_ds(); // Tải lại danh sách
            } catch (SQLException ex) {
                // Bắt lỗi vi phạm ràng buộc khóa ngoại
                if (ex.getMessage().contains("foreign") || ex.getMessage().contains("FK")) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể xóa vì khách hàng này đang được sử dụng trong bảng Gửi Đồ.",
                            "Lỗi ràng buộc khóa ngoại",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:String timkiem = txtTimKiem.getText().trim();
 String timkiem = txtTimKiem.getText().trim();

        try {
            con = getConnection();
            String sql = "SELECT * FROM khachHang WHERE maKhachHang LIKE ? OR hoTen LIKE ? OR SDT LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            String keyword = "%" + timkiem + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);

            ResultSet rs = ps.executeQuery();
            String[] td = {"Mã khách hàng", "Họ tên", "Ngày sinh", "Điện thoại", "Giới tính"};
            DefaultTableModel tb = new DefaultTableModel(td, 0);

            while (rs.next()) {
                Vector vt = new Vector();
                vt.add(rs.getString("maKhachHang"));
                vt.add(rs.getString("hoTen"));
                vt.add(rs.getDate("ngaySinh")); // nên dùng getDate() để rõ ràng
                vt.add(rs.getString("SDT"));
                vt.add(rs.getString("gioiTinh"));
                tb.addRow(vt);
            }

            tblThongTinKhachHang.setModel(tb);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage());
        }
        
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnUpLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpLoadActionPerformed
        // TODO add your handling code here:
         try {
            JFileChooser fc = new JFileChooser();
            int lc = fc.showOpenDialog(this);
            if (lc == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtNhapExcel.setText(file.getPath());
                String tenfile = file.getName();
                if (tenfile.endsWith(".xlsx")) {    //endsWith chọn file có phần kết thúc ...
                    ReadExcel(file.getPath());
                    JOptionPane.showMessageDialog(this, "Import thành công!");

                    load_ds();
                } else {
                    JOptionPane.showMessageDialog(this, "Phải chọn file excel");
                }

            }
            txtNhapExcel.setText("");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnUpLoadActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        
        String maKhachHang = txtMaKhachHang.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        Date ngaySinh = new Date(txtNgaySinh.getDate().getTime());
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();
        String SDT = txtSDT.getText().trim();

        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

        //check trùng mã 
        if (!ChecktrungMaKH(maKhachHang)) {
            JOptionPane.showMessageDialog(this, "Trùng mã loại");
            return;
        }
        //kiểm tra dữ liệu rỗng 
//        if (!KhongDeTrong()) {
//            return; // dừng lại nếu có lỗi
//        }
        if (txtNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!");
            return;
        }
        // So sánh ngày sinh với ngày hôm nay
        if (!ngaySinh.before(today)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải là ngày trong quá khứ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kiểm tra số điện thoại
        if (!SDT.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
            return;
        }
        // kiem tra nhap email
        try {
            con = getConnection();

            String sql = "INSERT INTO khachHang VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maKhachHang);
            st.setString(2, tenKhachHang);
            st.setDate(3, ngaySinh);
            st.setString(4, SDT);
            st.setString(5, gioiTinh);
            st.executeUpdate();
            txtMaKhachHang.setText("");
            txtTenKhachHang.setText("");
            cboGioiTinh.setSelectedItem(0);
            txtNgaySinh.setDate(null);
            txtSDT.setText("");
            con.close();
            JOptionPane.showMessageDialog(this, "Thêm thành công ");
            load_ds();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblThongTinKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongTinKhachHangMouseClicked
        // TODO add your handling code here:
         int i = tblThongTinKhachHang.getSelectedRow();
        DefaultTableModel tb = (DefaultTableModel) tblThongTinKhachHang.getModel();
        txtMaKhachHang.setText(tb.getValueAt(i, 0).toString());
        txtTenKhachHang.setText(tb.getValueAt(i, 1).toString());
        txtNgaySinh.setDate((java.sql.Date) tb.getValueAt(i, 2));
        txtSDT.setText(tb.getValueAt(i, 3).toString());
        cboGioiTinh.setSelectedItem(tb.getValueAt(i, 4).toString());
        txtMaKhachHang.setEnabled(false);
    }//GEN-LAST:event_tblThongTinKhachHangMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
                txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtSDT.setText("");
        txtNgaySinh.setDate(null);
        load_ds();
    }//GEN-LAST:event_btnLamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpLoad;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel headerImgae;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelGioiTinh;
    private javax.swing.JLabel labelIMG;
    private javax.swing.JLabel labelMaKH;
    private javax.swing.JLabel labelNgaySinh;
    private javax.swing.JLabel labelSDT;
    private javax.swing.JLabel labelTenKH;
    private javax.swing.JLabel labelTimKiem;
    private javax.swing.JPanel tabelPanel;
    private javax.swing.JTable tblThongTinKhachHang;
    private javax.swing.JTextField txtMaKhachHang;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JLabel txtNhapExcel;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiem1;
    // End of variables declaration//GEN-END:variables
}
