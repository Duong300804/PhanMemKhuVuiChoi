/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewChiTiet;

import Database.ConnectDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author LENOVO
 */
public class bieuDoChiTietDoanhThu extends javax.swing.JFrame {

    /**
     * Creates new form bieuDoChiTietDoanhThu
     */
    public bieuDoChiTietDoanhThu(LocalDate startDate, LocalDate endDate) {
//        initComponents();

        setTitle("Biểu đồ chi tiết doanh thu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tạo dataset cho biểu đồ cột
        DefaultCategoryDataset barDataset = createBarDataset(startDate, endDate);
        JFreeChart barChart = createBarChart(barDataset);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

        // Tạo dataset cho biểu đồ tròn
        DefaultPieDataset pieDataset = createPieDataset(startDate, endDate);
        JFreeChart pieChart = createPieChart(pieDataset);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

        // Thêm các biểu đồ vào panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.add(barChartPanel, BorderLayout.NORTH);
        chartPanel.add(pieChartPanel, BorderLayout.CENTER);

        //JPanel chartPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 hàng, 2 cột, cách nhau 10px ngang
        //chartPanel.add(barChartPanel);
        //chartPanel.add(pieChartPanel);
        //
        //barChartPanel.setPreferredSize(null); // bỏ kích thước cố định
        //pieChartPanel.setPreferredSize(null);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }

    private DefaultCategoryDataset createBarDataset(LocalDate startDate, LocalDate endDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection conn = ConnectDB.getConnection();

            // Query doanh thu vé theo tháng
            String sqlVe = "SELECT MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM HoaDon ";
            if (startDate != null && endDate != null) {
                sqlVe += "WHERE ngayMua BETWEEN ? AND ? ";
            }
            sqlVe += "GROUP BY MONTH(ngayMua), YEAR(ngayMua)";
            PreparedStatement pstmtVe = conn.prepareStatement(sqlVe);
            if (startDate != null && endDate != null) {
                pstmtVe.setDate(1, Date.valueOf(startDate));
                pstmtVe.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsVe = pstmtVe.executeQuery();
            while (rsVe.next()) {
                String thangNam = rsVe.getInt("thang") + "/" + rsVe.getInt("nam");
                dataset.addValue(rsVe.getDouble("doanhThu"), "Vé", thangNam);
            }

            // Query doanh thu đồ ăn theo tháng
            String sqlDoAn = "SELECT MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM hoaDonDoAn ";
            if (startDate != null && endDate != null) {
                sqlDoAn += "WHERE ngayMua BETWEEN ? AND ? ";
            }
            sqlDoAn += "GROUP BY MONTH(ngayMua), YEAR(ngayMua)";
            PreparedStatement pstmtDoAn = conn.prepareStatement(sqlDoAn);
            if (startDate != null && endDate != null) {
                pstmtDoAn.setDate(1, Date.valueOf(startDate));
                pstmtDoAn.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsDoAn = pstmtDoAn.executeQuery();
            while (rsDoAn.next()) {
                String thangNam = rsDoAn.getInt("thang") + "/" + rsDoAn.getInt("nam");
                dataset.addValue(rsDoAn.getDouble("doanhThu"), "Đồ ăn", thangNam);
            }

            // Query tổng doanh thu theo tháng
            String sqlTong = "SELECT MONTH(ngayMua) as thang, YEAR(ngayMua) as nam, SUM(tongTien) as doanhThu "
                    + "FROM (SELECT ngayMua, tongTien FROM HoaDon ";
            if (startDate != null && endDate != null) {
                sqlTong += "WHERE ngayMua BETWEEN ? AND ? ";
            }
            sqlTong += "UNION ALL SELECT ngayMua, tongTien FROM hoaDonDoAn ";
            if (startDate != null && endDate != null) {
                sqlTong += "WHERE ngayMua BETWEEN ? AND ? ";
            }
            sqlTong += ") as combined GROUP BY MONTH(ngayMua), YEAR(ngayMua)";
            PreparedStatement pstmtTong = conn.prepareStatement(sqlTong);
            if (startDate != null && endDate != null) {
                pstmtTong.setDate(1, Date.valueOf(startDate));
                pstmtTong.setDate(2, Date.valueOf(endDate));
                pstmtTong.setDate(3, Date.valueOf(startDate));
                pstmtTong.setDate(4, Date.valueOf(endDate));
            }
            ResultSet rsTong = pstmtTong.executeQuery();
            while (rsTong.next()) {
                String thangNam = rsTong.getInt("thang") + "/" + rsTong.getInt("nam");
                dataset.addValue(rsTong.getDouble("doanhThu"), "Tổng", thangNam);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo tháng",
                "Tháng/Năm",
                "Doanh thu (VND)",
                dataset
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(51, 153, 255));   // Vé
        renderer.setSeriesPaint(1, new Color(102, 204, 255));  // Đồ ăn
        renderer.setSeriesPaint(2, new Color(51, 102, 153));   // Tổng

        return chart;
    }

    private DefaultPieDataset createPieDataset(LocalDate startDate, LocalDate endDate) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Connection conn = ConnectDB.getConnection();

            // Query tổng doanh thu vé
            String sqlVe = "SELECT SUM(tongTien) as doanhThu FROM HoaDon";
            if (startDate != null && endDate != null) {
                sqlVe += " WHERE ngayMua BETWEEN ? AND ?";
            }
            PreparedStatement pstmtVe = conn.prepareStatement(sqlVe);
            if (startDate != null && endDate != null) {
                pstmtVe.setDate(1, Date.valueOf(startDate));
                pstmtVe.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsVe = pstmtVe.executeQuery();
            double doanhThuVe = rsVe.next() ? rsVe.getDouble("doanhThu") : 0;
            dataset.setValue("Vé", doanhThuVe);

            // Query tổng doanh thu đồ ăn
            String sqlDoAn = "SELECT SUM(tongTien) as doanhThu FROM hoaDonDoAn";
            if (startDate != null && endDate != null) {
                sqlDoAn += " WHERE ngayMua BETWEEN ? AND ?";
            }
            PreparedStatement pstmtDoAn = conn.prepareStatement(sqlDoAn);
            if (startDate != null && endDate != null) {
                pstmtDoAn.setDate(1, Date.valueOf(startDate));
                pstmtDoAn.setDate(2, Date.valueOf(endDate));
            }
            ResultSet rsDoAn = pstmtDoAn.executeQuery();
            double doanhThuDoAn = rsDoAn.next() ? rsDoAn.getDouble("doanhThu") : 0;
            dataset.setValue("Đồ ăn", doanhThuDoAn);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private JFreeChart createPieChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Tỷ lệ doanh thu",
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Vé", new Color(51, 153, 255));
        plot.setSectionPaint("Đồ ăn", new Color(102, 204, 255));

        return chart;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(bieuDoChiTietDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bieuDoChiTietDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bieuDoChiTietDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bieuDoChiTietDoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bieuDoChiTietDoanhThu(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
