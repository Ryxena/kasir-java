/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import javax.swing.SwingUtilities;

/**
 *
 *
 * @author ASUS
 */
public class CashierPanel extends javax.swing.JFrame {

    /**
     * Creates new form CashierPanel
     */
    // kode barang xx-
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
        FullScreen();
        productlist.requestFocusInWindow();

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

    private void updateProductQuantity(String productName) {
        sql = "UPDATE product SET Quantity = ? WHERE ProductName = ?";
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int currentQuantity = productQuantities.get(productName);
            int quantityInTable = 0;

            for (int i = 0; i < tbl.getRowCount(); i++) {
                if (tbl.getValueAt(i, 0).toString().equals(productName)) {
                    quantityInTable = Integer.parseInt(tbl.getValueAt(i, 2).toString());
                    break;
                }
            }

            // Update quantity in the database
            preparedStatement.setInt(1, currentQuantity - quantityInTable);
            preparedStatement.setString(2, productName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void addProductToTable(String productId) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl.getModel();

        sql = "SELECT * FROM product WHERE ProductID = ? LIMIT 1;";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                int price = resultSet.getInt("Price");
                int availableQuantity = resultSet.getInt("Quantity");  // Kuantitas yang tersedia di database
                productQuantities.put(productName, resultSet.getInt("Quantity"));

                int quantityInTable = 0;
                // Cek apakah produk sudah ada di tabel sementara
                boolean productExists = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(productName)) {
                        quantityInTable = (int) tableModel.getValueAt(i, 2);
                        if (quantityInTable + 1 > availableQuantity) {
                            JOptionPane.showMessageDialog(null, "Kuantitas melebihi stok yang tersedia");
                            return;  // Jika kuantitas melebihi stok, hentikan proses
                        }
                        tableModel.setValueAt(quantityInTable + 1, i, 2);  // Update quantity
                        tableModel.setValueAt((quantityInTable + 1) * price, i, 3);  // Update total price
                        productExists = true;
                        System.out.println("belajar.CashierPanel.addProductToTable() " + productExists);
                        break;
                    }
                }

                // Jika produk belum ada di tabel, tambahkan sebagai baris baru
                if (!productExists) {
                    if (availableQuantity < 1) {
                        JOptionPane.showMessageDialog(null, "Stok produk habis");
                        return;  // Jika stok kurang, hentikan proses
                    }
                    tableModel.addRow(new Object[]{productName, price, 1, price});
                }

                // Update total harga
                updatePrice();

            } else {
                JOptionPane.showMessageDialog(null, "Produk tidak ditemukan");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void updatePrice() {
        int rowCount = tbl.getRowCount();
        Integer totalHarga = 0;
        for (int i = 0; i < rowCount; i++) {
            totalHarga += Integer.parseInt(tbl.getValueAt(i, 3).toString());
        }
        ttlcuy.setText(totalHarga.toString());
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
        tbl = new javax.swing.JTable();
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
        jButton1 = new javax.swing.JButton();
        productlist = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        bayarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarbtnActionPerformed(evt);
            }
        });

        jLabel7.setText("Kembali");

        jLabel8.setText("Rp. ");

        jButton1.setText("<< Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        productlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productlistActionPerformed(evt);
            }
        });

        jLabel9.setText("Inpu ID barang");

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
                            .addComponent(productlist, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(byrya, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ttlcuy))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cngres))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(145, 145, 145))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bayarbtn))
                                .addContainerGap(215, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
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

    private void bayarbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bayarbtnMouseClicked
        Integer TotalHarga = Integer.parseInt(ttlcuy.getText());
        Integer TotalBayar = Integer.parseInt(byrya.getText());
        Integer result = TotalBayar - TotalHarga;
        cngres.setText(result.toString());

        sql = "INSERT INTO orders (date, total_price) VALUES (?, ?)";
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currentDate.toString());
            preparedStatement.setInt(2, TotalHarga);

            int hasil = preparedStatement.executeUpdate();

            if (hasil > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                Integer orderId = null;

                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }

                if (orderId != null) {
                    DefaultTableModel dm = (DefaultTableModel) tbl.getModel();

                    // Insert each item from the table into orders_detail
                    sql = "INSERT INTO orders_detail (order_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement detailStatement = connection.prepareStatement(sql)) {
                        for (int i = 0; i < dm.getRowCount(); i++) {
                            String productName = dm.getValueAt(i, 0).toString();
                            int quantity = Integer.parseInt(dm.getValueAt(i, 2).toString());
                            int price = Integer.parseInt(dm.getValueAt(i, 3).toString());

                            detailStatement.setInt(1, orderId);
                            detailStatement.setString(2, productName);
                            detailStatement.setInt(3, quantity);
                            detailStatement.setInt(4, price);

                            detailStatement.executeUpdate();
                            updateProductQuantity(productName);

                        }
                    }

                    productPrice = 0;
                    ttlcuy.setText("");
                    dm.setNumRows(0);

                    JOptionPane.showMessageDialog(null, "Pesanan berhasil ditambahkan");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mendapatkan Order ID");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan pesanan ke Database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

    }//GEN-LAST:event_bayarbtnMouseClicked

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
        row = tbl.getSelectedRow();
    }//GEN-LAST:event_tblMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
        LoginPage.main(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void productlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productlistActionPerformed
        String productId = productlist.getText();
        addProductToTable(productId);
        productlist.setText("");
        productlist.requestFocusInWindow();
    }//GEN-LAST:event_productlistActionPerformed

    private void bayarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bayarbtnActionPerformed

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
    private javax.swing.JButton bayarbtn;
    private javax.swing.JTextField byrya;
    private javax.swing.JTextField cngres;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField productlist;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField ttlcuy;
    // End of variables declaration//GEN-END:variables
}
