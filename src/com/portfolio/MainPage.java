package com.portfolio;
import javax.swing.*;
import java.sql.*;

public class MainPage extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainPage.class.getName());

public MainPage() {
    initComponents();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name:");

        jButton1.setText("Add Funds");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("View Funds");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String userName = jTextField1.getText().trim();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!");
            return;
        }
        this.setVisible(false);
        AddFundsPage addFunds = new AddFundsPage(userName);
        addFunds.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    String userName = jTextField1.getText().trim();
    if (userName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter your name!");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio_db","root","")) {
        String sql = "SELECT 1 FROM users WHERE name = ? LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, userName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            // user exists
        } 
        else {
            // user not exist
            JOptionPane.showMessageDialog(this, "No investment found for this user!");
            return;
        }
    } 
    catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
        return;
    }
    this.setVisible(false);
    ViewFundsPage viewFunds = new ViewFundsPage(userName);
    viewFunds.setVisible(true);
    
    }//GEN-LAST:event_jButton2ActionPerformed
              
    public static void main(String args[]) {
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
                try {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } 
        catch (Exception ex) {
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> new MainPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}


