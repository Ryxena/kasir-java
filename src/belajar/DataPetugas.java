/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class DataPetugas extends javax.swing.JFrame {

    /**
     * Creates new form DataPetugas
     */
    String ID;
    private static DefaultTableModel tabelModel;
    String sql;
    HashMap<String, Integer> roleMap = new HashMap<String, Integer>();

    public DataPetugas() {
        initComponents();
        FullScreen();
        setComboBox();
        showData(null);

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

    private void setComboBox() {
        Role.addItem("Role");
        Role.setSelectedItem("Role");
        sql = "SELECT * FROM role";
        try (Connection connection = DB.connectdb(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                roleMap.put(resultSet.getString("role"), resultSet.getInt("id"));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void delete() {
        if (ID != null) {
            String query = "DELETE FROM employee WHERE id = ?";
            try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, ID);
                int hasil = preparedStatement.executeUpdate();

                if (hasil > 0) {
                    JOptionPane.showMessageDialog(null, "Baris dengan ID " + ID + " berhasil dihapus dari Database");
                } else {
                    JOptionPane.showMessageDialog(null, "Baris dengan ID " + ID + " tidak ditemukan dalam Database");
                }
                ID = null;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih untuk dihapus.");
        }
    }

    private void showData(String cari) {
        sql = "SELECT e.*, r.* FROM employee e INNER JOIN role r ON e.role_id = r.id ";

        if (cari != null && !cari.isEmpty()) {
            sql += "WHERE e.Name LIKE ? OR r.role LIKE ?";
        }

        String[] kolom = {"ID", "Name", "Role", "Password"};
        tabelModel = new DefaultTableModel(null, kolom);
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (cari != null && !cari.isEmpty()) {
                String cariString = "%" + cari + "%";
                for (int i = 1; i <= 2; i++) {
                    preparedStatement.setString(i, cariString);
                }
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String nama = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    String pw = resultSet.getString("password");
                    Object[] baris = {id, nama, role, pw};
                    tabelModel.addRow(baris);
                }

                tabelkar.setModel(tabelModel);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void update() {
        String Id = idkar.getText();
        String EmployeeName = Name.getText();
        String password = Passwordkar.getText();
        Integer role_id = roleMap.get(Role.getSelectedItem().toString());
        sql = "UPDATE employee SET id = ?, name = ?, role_id = ?, password = ? WHERE id = ?;";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Id);
            preparedStatement.setString(2, EmployeeName);
            preparedStatement.setInt(3, role_id);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, ID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data item berhasil diperbarui");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui item dengan ID " + ID + " di Database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelkar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Cari = new javax.swing.JTextField();
        idkar = new javax.swing.JTextField();
        Passwordkar = new javax.swing.JTextField();
        Role = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Name = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelkar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelkar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelkar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 76, 690, 510));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Data Petugas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 0, 301, -1));

        Cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CariKeyTyped(evt);
            }
        });
        getContentPane().add(Cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 76, 130, -1));

        idkar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idkarActionPerformed(evt);
            }
        });
        getContentPane().add(idkar, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 136, 130, -1));
        getContentPane().add(Passwordkar, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 266, 130, -1));

        Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Cashier" }));
        getContentPane().add(Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 338, 130, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Cari Disini");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 36, 70, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nama Petugas");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 170, 102, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Password");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 232, 70, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Role");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 306, 70, -1));

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 389, -1, -1));

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 430, -1, -1));
        getContentPane().add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 202, 164, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("ID");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 110, 102, -1));

        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 389, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idkarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idkarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idkarActionPerformed

    private void tabelkarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkarMouseClicked
        // TODO add your handling code here:
        Integer baris = tabelkar.getSelectedRow();
        ID = tabelkar.getModel().getValueAt(baris, 0).toString();
        Name.setText(tabelkar.getModel().getValueAt(baris, 1).toString());
        idkar.setText(tabelkar.getModel().getValueAt(baris, 0).toString());
        Passwordkar.setText(tabelkar.getModel().getValueAt(baris, 3).toString());
        Role.setSelectedItem(tabelkar.getModel().getValueAt(baris, 2).toString());
        System.out.println("e" + ID);
    }//GEN-LAST:event_tabelkarMouseClicked

    private void CariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariKeyTyped
        // TODO add your handling code here:
        showData(Cari.getText().trim());

    }//GEN-LAST:event_CariKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        delete();
        showData(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        update();
        showData(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
        AdminPanel.main(null);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(DataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPetugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Cari;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Passwordkar;
    private javax.swing.JComboBox<String> Role;
    private javax.swing.JTextField idkar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelkar;
    // End of variables declaration//GEN-END:variables
}
