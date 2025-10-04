package com.portfolio;
import javax.swing.*;
import java.sql.*;

public class AddFundsPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AddFundsPage.class.getName());

    private String userName;
    public AddFundsPage(String userName) {
        this.userName = userName;
        initComponents();
        jButton1.addActionListener(e -> goBack());
        jButton2.addActionListener(e -> addFunds());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        jColorChooser2 = new javax.swing.JColorChooser();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Back");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Name Of Fund:");

        jLabel2.setText("Amount Invested:");

        jButton2.setText("Add Funds");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jTextField2))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(119, 119, 119))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JColorChooser jColorChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
private void goBack() {
    this.dispose(); 
    new MainPage().setVisible(true); 
}

private void addFunds() {
    String fundName = jTextField1.getText().trim();
    String amountText = jTextField2.getText().trim();

    if(fundName.isEmpty() || amountText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    double amount;
    try {
        amount = Double.parseDouble(amountText);
        if(amount <= 0) {
            JOptionPane.showMessageDialog(this, "Amount must be > 0");
            return;
        }
    } 
    catch(NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Enter valid number for amount");
        return;
    }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/portfolio_db","root","")) {

            String insertUser = "INSERT IGNORE INTO users(name) VALUES(?)";
            try (PreparedStatement pstUser = conn.prepareStatement(insertUser)) {
                pstUser.setString(1, userName);
                pstUser.executeUpdate();
            }

            int userId = -1;
            String getUserId = "SELECT id FROM users WHERE name=?";
            try (PreparedStatement pstId = conn.prepareStatement(getUserId)) {
                pstId.setString(1, userName);
                ResultSet rs = pstId.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }

            if (userId == -1) {
                JOptionPane.showMessageDialog(this, "Could not fetch user ID!");
                return;
            }

            String insertInv = "INSERT INTO investments(user_id,fund_name,amount) VALUES(?,?,?)";
            try (PreparedStatement pstInv = conn.prepareStatement(insertInv)) {
                pstInv.setInt(1, userId);
                pstInv.setString(2, fundName);
                pstInv.setDouble(3, amount);
                pstInv.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Funds added successfully!");
            jTextField1.setText("");
            jTextField2.setText("");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
            }
    }
}

