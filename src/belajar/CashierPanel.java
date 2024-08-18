/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.sql.*;
import javax.swing.JOptionPane;
import java.time.LocalDate;

/**
 *
 *
 * @author ASUS
 */
public class CashierPanel extends javax.swing.JFrame {

    /**
     * Creates new form CashierPanel
     */
    Integer IDBarang = null;
    String IDEmployee = null;
    String sql;
    Integer row;
    HashMap<String, Integer> categoryMap = new HashMap<String, Integer>();
    String productName;
    private static DefaultTableModel tabelModel;
    Integer productPrice;
    LocalDate currentDate = LocalDate.now();
    HashMap<String, Integer> productQuantities = new HashMap<>();

    public CashierPanel() {
        initComponents();
        setComboBox();
        AutoCompleteDecorator.decorate(productlist);
    }

    private void search(String val) {
        sql = "SELECT * FROM product WHERE ProductName LIKE ? LIMIT 1;";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + val + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                valharga.setText(resultSet.getString("Price"));
                productName = resultSet.getString("ProductName");
                productPrice = resultSet.getInt("Price");
                productQuantities.put(productName, resultSet.getInt("Quantity"));
            }
        } catch (Exception e) {

        }
    }

    private void updateProductQuantity() {
        sql = "UPDATE product SET Quantity = ? WHERE ProductName = ?";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,  productQuantities.get(productName));
            preparedStatement.setString(2, productName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void newrecord() {
        String Name = productlist.getSelectedItem().toString();
        int harga = Integer.parseInt(valharga.getText());
        int jumlah = Integer.parseInt(valjumlah.getText());
        int total = jumlah * productPrice;
        if (!productQuantities.containsKey(Name) || jumlah > productQuantities.get(Name)) {
            JOptionPane.showMessageDialog(null, "Jumlah barang yang diminta melebihi stok!");
            return;
        }
        DefaultTableModel tavel = (DefaultTableModel) tbl.getModel();

        tavel.addRow(new Object[]{
            Name,
            harga,
            jumlah,
            total
        });
        Integer totalHarga = 0;
        for (int i = 0; i < tbl.getRowCount(); i++) {
            totalHarga += Integer.parseInt(tbl.getValueAt(i, 3).toString());
        }
        ttlcuy.setText(totalHarga.toString());
//        productQuantities.put(Name, productQuantities.get(Name) - jumlah);
//        updateProductQuantity(Name);
    }
//    private void updateProductQuantity(String productName) {
//        sql = "UPDATE product SET Quantity = ? WHERE ProductName = ?";
//
//        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, productQuantities.get(productName));
//            preparedStatement.setString(2, productName);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.toString());
//        }
//    }
    private void updatePrice() {
        int row = tbl.getRowCount();
        Integer totalHarga = 0;
        for (int i = 0; i < tbl.getRowCount(); i++) {
            totalHarga += Integer.parseInt(tbl.getValueAt(i, 3).toString());
        }
        ttlcuy.setText(totalHarga.toString());
    }

    public void setComboBox() {
//        productlist.removeAllItems();
        productlist.addItem("Cari disini");
        productlist.setSelectedItem("Cari disini");
        String[] kolom = {"Nama", "Harga", "Jumlah", "Total"};
        DefaultTableModel tabelModel = new DefaultTableModel(null, kolom);
        sql = "SELECT * FROM product";
        try (Connection connection = DB.connectdb(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                productlist.addItem(resultSet.getString("ProductName"));
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

        productlist = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        valjumlah = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        valharga = new javax.swing.JTextField();
        addorder = new javax.swing.JButton();
        ttlcuy = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        byrya = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        bayarbtn = new javax.swing.JButton();
        cngres = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        clrbtn = new javax.swing.JButton();
        delbtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        productlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productlistActionPerformed(evt);
            }
        });

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Nama", "Harga ", "Jumlah", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setResizable(false);
            tbl.getColumnModel().getColumn(0).setHeaderValue("Nama");
            tbl.getColumnModel().getColumn(1).setResizable(false);
            tbl.getColumnModel().getColumn(1).setHeaderValue("Harga ");
            tbl.getColumnModel().getColumn(2).setResizable(false);
            tbl.getColumnModel().getColumn(2).setHeaderValue("Jumlah");
            tbl.getColumnModel().getColumn(3).setResizable(false);
            tbl.getColumnModel().getColumn(3).setHeaderValue("Total Harga");
        }

        jLabel1.setText("Jumlah");

        jLabel2.setText("Harga");

        addorder.setText("Tambah");
        addorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addorderActionPerformed(evt);
            }
        });

        jLabel3.setText("Total");

        jLabel4.setText("Rp. ");

        jLabel5.setText("Bayar");

        jLabel6.setText("Rp. ");

        bayarbtn.setText("Bayar");
        bayarbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bayarbtnMouseClicked(evt);
            }
        });

        jLabel7.setText("Kembali");

        jLabel8.setText("Rp. ");

        clrbtn.setText("clear");
        clrbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clrbtnActionPerformed(evt);
            }
        });

        delbtn.setText("Delete");
        delbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delbtnActionPerformed(evt);
            }
        });

        jButton1.setText("<< Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productlist, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(byrya))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ttlcuy))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(valjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valharga, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cngres))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(145, 145, 145))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bayarbtn)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(clrbtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(delbtn))
                                    .addComponent(addorder))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addorder)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clrbtn)
                            .addComponent(delbtn))
                        .addGap(82, 82, 82)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ttlcuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(byrya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cngres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addComponent(bayarbtn)))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void productlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productlistActionPerformed
        // TODO add your handling code here:
        search(productlist.getSelectedItem().toString());
//    System.out.println("belajar.CashierPanel.productlistActionPerformed() " + productlist.getSelectedItem().toString());
    }//GEN-LAST:event_productlistActionPerformed

    private void addorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addorderActionPerformed
        // TODO add your handling code here:
        newrecord();
        System.out.println(productQuantities);
    }//GEN-LAST:event_addorderActionPerformed

    private void bayarbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bayarbtnMouseClicked
        // TODO add your handling code here:
        Integer TotalHarga = Integer.parseInt(ttlcuy.getText());
        Integer TotalBayar = Integer.parseInt(byrya.getText());
        Integer result = TotalBayar - TotalHarga;
        cngres.setText(result.toString());

        sql = "INSERT INTO orders (date, total_price) VALUES (?, ?)";
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentDate.toString());
            preparedStatement.setInt(2, TotalHarga);

            int hasil = preparedStatement.executeUpdate();
            if (hasil > 0) {
                DefaultTableModel dm = (DefaultTableModel) tbl.getModel();
                productPrice = 0;
                productlist.setSelectedItem("Cari disini");
                valjumlah.setText("");
                ttlcuy.setText("");
                valharga.setText("");
                dm.setNumRows(0);
                JOptionPane.showMessageDialog(null, "Pesanan berhasil ditambahkan");

            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan pesanan ke Database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

    }//GEN-LAST:event_bayarbtnMouseClicked

    private void clrbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clrbtnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dm = (DefaultTableModel) tbl.getModel();
        productPrice = 0;
        productlist.setSelectedItem("Cari disini");
        valjumlah.setText("");
        ttlcuy.setText("");
        valharga.setText("");
        dm.setNumRows(0);


    }//GEN-LAST:event_clrbtnActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
        row = tbl.getSelectedRow();
        valharga.setText(productPrice.toString());
    }//GEN-LAST:event_tblMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
        LoginPage.main(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void delbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delbtnActionPerformed

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
            java.util.logging.Logger.getLogger(CashierPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashierPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashierPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashierPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashierPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addorder;
    private javax.swing.JButton bayarbtn;
    private javax.swing.JTextField byrya;
    private javax.swing.JButton clrbtn;
    private javax.swing.JTextField cngres;
    private javax.swing.JButton delbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> productlist;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField ttlcuy;
    private javax.swing.JTextField valharga;
    private javax.swing.JTextField valjumlah;
    // End of variables declaration//GEN-END:variables
}
