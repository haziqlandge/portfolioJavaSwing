package com.portfolio;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.sql.*;
import java.text.NumberFormat;
import javax.swing.*; 

public class ViewFundsPage extends javax.swing.JFrame {   
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ViewFundsPage.class.getName());

    private String userName;
    public ViewFundsPage(String userName) {
        this.userName = userName;
        initComponents();
        populatePieChart();
    }

    private void populatePieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        double totalAmount = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio_db","root","")) {
            String sql = "SELECT i.fund_name, i.amount " + "FROM investments i " + "JOIN users u ON i.user_id = u.id " +
                 "WHERE u.name = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String fundName = rs.getString("fund_name");
                double amount = rs.getDouble("amount");
                dataset.setValue(fundName, amount);
                totalAmount += amount;
            }
        } 
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
            return;
        }

        JFreeChart chart = ChartFactory.createPieChart( "Portfolio - " + userName, dataset,true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", 
                NumberFormat.getCurrencyInstance(), NumberFormat.getPercentInstance()));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(jPanel1.getSize());
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(chartPanel, java.awt.BorderLayout.CENTER);
        jPanel1.validate();
        jLabel1.setText("Total Invested: $" + String.format("%.2f", totalAmount));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(483, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           this.dispose();
            new MainPage().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}



