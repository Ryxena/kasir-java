/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class AdminPanel extends javax.swing.JFrame {

    /**
     * Creates new form adminpanel
     */
    public AdminPanel() {
        initComponents();
        FullScreen();
    }
    
    private void FullScreen() {
        getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        pack();
        setResizable(false);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Point p = new Point(0, 0);
                SwingUtilities.convertPointToScreen(p, getContentPane());
                Point l = getLocation();
                l.x -= p.x;
                l.y -= p.y;
                setLocation(l);
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barangbutton = new javax.swing.JButton();
        petugasbutton = new javax.swing.JButton();
        riwayatbutton = new javax.swing.JButton();
        logoutbutton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barangbutton.setText("Data Barang");
        barangbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barangbuttonMouseClicked(evt);
            }
        });
        barangbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(barangbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 243, 98, 41));

        petugasbutton.setText("Data petugas");
        petugasbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                petugasbuttonMouseClicked(evt);
            }
        });
        petugasbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                petugasbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(petugasbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 243, -1, 41));

        riwayatbutton.setText("Riwayat");
        riwayatbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riwayatbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(riwayatbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 243, 98, 41));

        logoutbutton.setText("Logout");
        logoutbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(logoutbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 243, 98, 41));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin Panel");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 117, 319, -1));

        jButton1.setText("Supplier");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 110, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void petugasbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_petugasbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_petugasbuttonActionPerformed

    private void barangbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangbuttonActionPerformed

        dispose();
        Barang.main(null);
    }//GEN-LAST:event_barangbuttonActionPerformed

    private void barangbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barangbuttonMouseClicked

    }//GEN-LAST:event_barangbuttonMouseClicked

    private void logoutbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbuttonActionPerformed
        dispose();
        LoginPage.main(null);
    }//GEN-LAST:event_logoutbuttonActionPerformed

    private void riwayatbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayatbuttonActionPerformed
        // TODO add your handling code here:
        dispose();
        Riwayat.main(null);
    }//GEN-LAST:event_riwayatbuttonActionPerformed

    private void petugasbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_petugasbuttonMouseClicked
        // TODO add your handling code here:
        dispose();
        DataPetugas.main(null);
    }//GEN-LAST:event_petugasbuttonMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
        Supplier.main(null);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barangbutton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutbutton;
    private javax.swing.JButton petugasbutton;
    private javax.swing.JButton riwayatbutton;
    // End of variables declaration//GEN-END:variables
}
