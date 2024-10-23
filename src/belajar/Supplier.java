/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class Supplier extends javax.swing.JFrame {

    Integer ID;
    String sql;

    public Supplier() {
        initComponents();
        FullScreen();
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

    private void showData(String cari) {
        sql = "SELECT * FROM supplier";

        if (cari != null && !cari.isEmpty()) {
            sql += " WHERE kode_supplier LIKE ? OR nama_supplier LIKE ? OR alamat LIKE ? OR kota LIKE ? OR negara LIKE ? OR telepon LIKE ? OR email LIKE ? OR provinsi LIKE ?";
        }
        String[] kolom = {"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA", "NEGARA", "TELEPON", "EMAIL", "NO REKENING", "NPWP", "PROVINSI", "KODEPOS", "FAXIMILE"};
        DefaultTableModel tabelModel = new DefaultTableModel(null, kolom);

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (cari != null && !cari.isEmpty()) {
                String cariString = "%" + cari + "%";
                for (int i = 1; i <= 12; i++) {
                    preparedStatement.setString(i, cariString);
                }
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String kodeSupplier = resultSet.getString("kode_supplier");
                    String namaSupplier = resultSet.getString("nama_supplier");
                    String alamat = resultSet.getString("alamat");
                    String kota = resultSet.getString("kota");
                    String negara = resultSet.getString("negara");
                    String telepon = resultSet.getString("telepon");
                    String email = resultSet.getString("email");
                    String noRekening = resultSet.getString("no_rekening");
                    String npwp = resultSet.getString("npwp");
                    String propinsi = resultSet.getString("provinsi");
                    String kodepos = resultSet.getString("kodepos");
                    String faximile = resultSet.getString("faximile");

                    Object[] baris = {kodeSupplier, namaSupplier, alamat, kota, negara, telepon, email, noRekening, npwp, propinsi, kodepos, faximile};
                    tabelModel.addRow(baris);
                }
            }

            itemtabel.setModel(tabelModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void deleteSupplier() {
        String kodeSupplier = kodesup.getText();
        if (!kodeSupplier.isEmpty()) {
            String query = "DELETE FROM supplier WHERE kode_supplier = ?";
            try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, kodeSupplier);
                int hasil = preparedStatement.executeUpdate();

                if (hasil > 0) {
                    kodesup.setText("");
                    namasup.setText("");
                    alamatsup.setText("");
                    telsup.setText("");
                    kotasup.setText("");
                    negarasup.setText("");
                    emailsup.setText("");
                    noreksup.setText("");
                    npwpsup.setText("");
                    provinsisup.setText("");
                    kodepossup.setText("");
                    faxsup.setText("");
                    JOptionPane.showMessageDialog(null, "Supplier dengan kode " + kodeSupplier + " berhasil dihapus dari Database");
                } else {
                    JOptionPane.showMessageDialog(null, "Supplier dengan kode " + kodeSupplier + " tidak ditemukan dalam Database");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kode Supplier belum diisi.");
        }
    }

    private void updateSupplier() {
        String kodeSupplier = kodesup.getText();
        String namaSupplier = namasup.getText();
        String alamat = alamatsup.getText();
        String kota = kotasup.getText();
        String negara = negarasup.getText();
        String telepon = telsup.getText();
        String email = emailsup.getText();
        String noRekening = noreksup.getText();
        String npwp = npwpsup.getText();
        String propinsi = provinsisup.getText();
        String kodepos = kodepossup.getText();
        String faximile = faxsup.getText();

        sql = "UPDATE supplier SET nama_supplier = ?, alamat = ?, kota = ?, negara = ?, telepon = ?, email = ?, no_rekening = ?, npwp = ?, provinsi = ?, kodepos = ?, faximile = ? WHERE kode_supplier = ?";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, namaSupplier);
            preparedStatement.setString(2, alamat);
            preparedStatement.setString(3, kota);
            preparedStatement.setString(4, negara);
            preparedStatement.setString(5, telepon);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, noRekening);
            preparedStatement.setString(8, npwp);
            preparedStatement.setString(9, propinsi);
            preparedStatement.setString(10, kodepos);
            preparedStatement.setString(11, faximile);
            preparedStatement.setString(12, kodeSupplier);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data supplier berhasil diperbarui");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui supplier dengan kode " + kodeSupplier);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void addSupplier() {
        String kodeSupplier = kodesup.getText();
        String namaSupplier = namasup.getText();
        String alamat = alamatsup.getText();
        String kota = kotasup.getText();
        String negara = negarasup.getText();
        String telepon = telsup.getText();
        String email = emailsup.getText();
        String noRekening = noreksup.getText();
        String npwp = npwpsup.getText();
        String propinsi = provinsisup.getText();
        String kodepos = kodepossup.getText();
        String faximile = faxsup.getText();

        sql = "INSERT INTO supplier (kode_supplier, nama_supplier, alamat, kota, negara, telepon, email, no_rekening, npwp, provinsi, kodepos, faximile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, kodeSupplier);
            preparedStatement.setString(2, namaSupplier);
            preparedStatement.setString(3, alamat);
            preparedStatement.setString(4, kota);
            preparedStatement.setString(5, negara);
            preparedStatement.setString(6, telepon);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, noRekening);
            preparedStatement.setString(9, npwp);
            preparedStatement.setString(10, propinsi);
            preparedStatement.setString(11, kodepos);
            preparedStatement.setString(12, faximile);

            int hasil = preparedStatement.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(null, "Supplier berhasil ditambahkan");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan supplier ke Database");
            }
            // Reset form fields
            kodesup.setText("");
            namasup.setText("");
            alamatsup.setText("");
            telsup.setText("");
            kotasup.setText("");
            negarasup.setText("");
            emailsup.setText("");
            noreksup.setText("");
            npwpsup.setText("");
            provinsisup.setText("");
            kodepossup.setText("");
            faxsup.setText("");
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        kodesup = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        namasup = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        alamatsup = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        itemtabel = new javax.swing.JTable();
        kotasup = new javax.swing.JTextField();
        negarasup = new javax.swing.JTextField();
        telsup = new javax.swing.JTextField();
        emailsup = new javax.swing.JTextField();
        noreksup = new javax.swing.JTextField();
        npwpsup = new javax.swing.JTextField();
        faxsup = new javax.swing.JTextField();
        provinsisup = new javax.swing.JTextField();
        kodepossup = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("jButton5");

        jScrollPane1.setViewportView(kodesup);

        jScrollPane2.setViewportView(namasup);

        jScrollPane3.setViewportView(alamatsup);

        jLabel1.setText("Kode Supplier");

        jLabel2.setText("Nama");

        jLabel3.setText("Alamat");

        jLabel4.setText("Kota");

        jLabel5.setText("Negara");

        jLabel6.setText("Telepon");

        jLabel7.setText("Email");

        jLabel8.setText("No Rekening");

        jLabel9.setText("NPWP");

        jLabel10.setText("Provinsi");

        jLabel11.setText("Kode Pos");

        jLabel12.setText("Fax");

        itemtabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Kode", "Nama", "Alamat", "Kota", "Negara", "Telepon", "Email" , "Norek", "NPWP", "Province", "Kode POS", "FAX"

            }
        ));
        itemtabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemtabelMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(itemtabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addGap(75, 75, 75))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(111, 111, 111)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kotasup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(emailsup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(telsup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kodepossup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(provinsisup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(faxsup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(npwpsup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(noreksup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(negarasup, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(329, 329, 329))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jButton5)
                        .addGap(96, 96, 96)
                        .addComponent(jButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jButton2)
                        .addGap(41, 41, 41)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(kotasup, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(negarasup, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(telsup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailsup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(noreksup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(npwpsup, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(faxsup, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(provinsisup, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kodepossup, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(27, 27, 27)))
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemtabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemtabelMouseClicked
        // TODO add your handling code here:
        int baris = itemtabel.getSelectedRow();
        kodesup.setText(itemtabel.getModel().getValueAt(baris, 0).toString());
        namasup.setText(itemtabel.getModel().getValueAt(baris, 1).toString());
        alamatsup.setText(itemtabel.getModel().getValueAt(baris, 2).toString());
        kotasup.setText(itemtabel.getModel().getValueAt(baris, 3).toString());
        negarasup.setText(itemtabel.getModel().getValueAt(baris, 4).toString());
        telsup.setText(itemtabel.getModel().getValueAt(baris, 5).toString());
        emailsup.setText(itemtabel.getModel().getValueAt(baris, 6).toString());
        noreksup.setText(itemtabel.getModel().getValueAt(baris, 7).toString());
        npwpsup.setText(itemtabel.getModel().getValueAt(baris, 8).toString());
        provinsisup.setText(itemtabel.getModel().getValueAt(baris, 9).toString());
        kodepossup.setText(itemtabel.getModel().getValueAt(baris, 10).toString());
        faxsup.setText(itemtabel.getModel().getValueAt(baris, 11).toString());
    }//GEN-LAST:event_itemtabelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addSupplier();
        showData(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deleteSupplier();
        showData(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        updateSupplier();
        showData(null);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Supplier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Supplier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Supplier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Supplier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane alamatsup;
    private javax.swing.JTextField emailsup;
    private javax.swing.JTextField faxsup;
    private javax.swing.JTable itemtabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField kodepossup;
    private javax.swing.JTextPane kodesup;
    private javax.swing.JTextField kotasup;
    private javax.swing.JTextPane namasup;
    private javax.swing.JTextField negarasup;
    private javax.swing.JTextField noreksup;
    private javax.swing.JTextField npwpsup;
    private javax.swing.JTextField provinsisup;
    private javax.swing.JTextField telsup;
    // End of variables declaration//GEN-END:variables
}
