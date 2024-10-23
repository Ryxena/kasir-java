/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package belajar;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.HashMap;
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
public class Barang extends javax.swing.JFrame {

    /**
     * Creates new form Barang
     */
    Integer ID = null;
    String sql;
    HashMap<String, Integer> categoryMap = new HashMap<String, Integer>();

    public Barang() {
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

    private void delete() {
        if (ID != -1) {
            String query = "DELETE FROM product WHERE ProductID = ?";
            try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ID);
                int hasil = preparedStatement.executeUpdate();

                if (hasil > 0) {
                    JOptionPane.showMessageDialog(null, "Baris dengan ID " + ID + " berhasil dihapus dari Database");
                } else {
                    JOptionPane.showMessageDialog(null, "Baris dengan ID " + ID + " tidak ditemukan dalam Database");
                }
                ID = -1;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih untuk dihapus.");
        }
    }

    private void update() {
        String productName = ItemID.getText();
        int quantity = Integer.parseInt(ItemQuantity.getText());
        double price = Double.parseDouble(ItemPrice.getText());
        int categoryID = categoryMap.get(ItemCategory.getSelectedItem().toString());

        sql = "UPDATE product SET ProductName = ?, Quantity = ?, Price = ?, CategoryID = ? WHERE ProductID = ?";

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, categoryID);
            preparedStatement.setInt(5, ID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data item berhasil ditambahkan");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui item dengan ID " + ID + " di Database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void setComboBox() {
        sql = "SELECT * FROM category";
        ItemCategory.addItem("Category");
        ItemCategory.setSelectedItem("Category");
        try (Connection connection = DB.connectdb(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");
                ItemCategory.addItem(resultSet.getString("CategoryName"));
                categoryMap.put(categoryName, categoryID);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void showData(String cari) {
        sql = "SELECT p.ProductID, p.ProductName, p.Quantity, p.Price, c.CategoryName "
                + "FROM product p "
                + "INNER JOIN category c ON p.CategoryID = c.CategoryID ";

        if (cari != null && !cari.isEmpty()) {
            sql += "WHERE p.ProductName LIKE ? OR p.Quantity LIKE ? OR p.Price LIKE ? OR c.CategoryName LIKE ?";
        }

        String[] kolom = {"ID", "Nama", "Stock", "Harga", "Category"};
        DefaultTableModel tabelModel = new DefaultTableModel(null, kolom);

        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (cari != null && !cari.isEmpty()) {
                String cariString = "%" + cari + "%";
                for (int i = 1; i <= 4; i++) {
                    preparedStatement.setString(i, cariString);
                }
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("ProductID");
                    String nama = resultSet.getString("ProductName");
                    String quantity = resultSet.getString("Quantity");
                    String price = resultSet.getString("Price");
                    String category = resultSet.getString("CategoryName");
                    Object[] baris = {id, nama, quantity, price, category};
                    tabelModel.addRow(baris);
                }
            }

            itemtabel.setModel(tabelModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void tambah() {
        String nama = ItemName.getText();
        Integer quantity = Integer.valueOf(ItemQuantity.getText());
        Integer price = Integer.valueOf(ItemPrice.getText());
        Integer id = Integer.valueOf(ItemID.getText());
        Integer categoryid = categoryMap.get(ItemCategory.getSelectedItem().toString());
        sql = "INSERT INTO product (ProductID, ProductName, Quantity, Price, CategoryID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB.connectdb(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nama);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setInt(5, categoryid);

            int hasil = preparedStatement.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(null, "Item Berhasil ditambahkan");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan item ke Database");
            }
            ItemID.setText("");
            ItemName.setText("");
            ItemQuantity.setText("");
            ItemPrice.setText("");
            ItemCategory.setSelectedItem("Category");
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
        itemtabel = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        SearchBox = new javax.swing.JTextField();
        ItemID = new javax.swing.JTextField();
        ItemQuantity = new javax.swing.JTextField();
        ItemPrice = new javax.swing.JTextField();
        ItemCategory = new javax.swing.JComboBox<>();
        ButtonBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        CreateButton = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ItemName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        itemtabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemtabel.getTableHeader().setReorderingAllowed(false);
        itemtabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemtabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(itemtabel);
        if (itemtabel.getColumnModel().getColumnCount() > 0) {
            itemtabel.getColumnModel().getColumn(0).setResizable(false);
            itemtabel.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            itemtabel.getColumnModel().getColumn(1).setResizable(false);
            itemtabel.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            itemtabel.getColumnModel().getColumn(2).setResizable(false);
            itemtabel.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            itemtabel.getColumnModel().getColumn(3).setResizable(false);
            itemtabel.getColumnModel().getColumn(3).setHeaderValue("Title 4");
            itemtabel.getColumnModel().getColumn(4).setResizable(false);
            itemtabel.getColumnModel().getColumn(4).setHeaderValue("null");
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 85, 829, 545));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Data Barang");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(604, 0, 355, 80));

        SearchBox.setToolTipText("");
        SearchBox.setName(""); // NOI18N
        SearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SearchBoxKeyTyped(evt);
            }
        });
        getContentPane().add(SearchBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 117, 149, -1));

        ItemID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemIDActionPerformed(evt);
            }
        });
        getContentPane().add(ItemID, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 191, 149, -1));
        getContentPane().add(ItemQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 383, 95, -1));

        ItemPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemPriceActionPerformed(evt);
            }
        });
        getContentPane().add(ItemPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 433, 95, -1));

        ItemCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        getContentPane().add(ItemCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 321, 149, -1));

        ButtonBack.setText("<< Kembali");
        ButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBackActionPerformed(evt);
            }
        });
        getContentPane().add(ButtonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 46, -1, -1));

        jLabel2.setText("Cari Disini");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 94, 68, -1));

        jLabel3.setText("ID Barang");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 157, 86, -1));

        jLabel4.setText("Price");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 411, 86, -1));

        jLabel5.setText("Quantity");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 361, 86, -1));

        jLabel6.setText("Category");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 293, 86, -1));

        CreateButton.setText("Create");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        getContentPane().add(CreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 490, -1, -1));

        UpdateButton.setText("Update");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });
        getContentPane().add(UpdateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 531, -1, -1));

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        getContentPane().add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 572, -1, -1));

        jLabel7.setText("Nama Barang");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 231, 86, -1));
        getContentPane().add(ItemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 253, 149, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBackActionPerformed
        // TODO add your handling code here:
        dispose();
        AdminPanel.main(null);
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        // TODO add your handling code here:
        update();
        showData(null);
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void itemtabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemtabelMouseClicked
        // TODO add your handling code here:
        int baris = itemtabel.getSelectedRow();
        ID = Integer.valueOf(itemtabel.getModel().getValueAt(baris, 0).toString());
        ItemID.setText(itemtabel.getModel().getValueAt(baris, 0).toString());
        ItemName.setText(itemtabel.getModel().getValueAt(baris, 1).toString());
        ItemQuantity.setText(itemtabel.getModel().getValueAt(baris, 2).toString());
        ItemPrice.setText(itemtabel.getModel().getValueAt(baris, 3).toString());
        ItemCategory.setSelectedItem(itemtabel.getModel().getValueAt(baris, 4).toString());
    }//GEN-LAST:event_itemtabelMouseClicked

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        // TODO add your handling code here:
        tambah();
        showData(null);
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        // TODO add your handling code here:
        delete();
        showData(null);
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void SearchBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchBoxKeyTyped
        // TODO add your handling code here:
        showData(SearchBox.getText().trim());
    }//GEN-LAST:event_SearchBoxKeyTyped

    private void ItemIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemIDActionPerformed

    private void ItemPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemPriceActionPerformed

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
            java.util.logging.Logger.getLogger(Barang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Barang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Barang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Barang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton CreateButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JComboBox<String> ItemCategory;
    private javax.swing.JTextField ItemID;
    private javax.swing.JTextField ItemName;
    private javax.swing.JTextField ItemPrice;
    private javax.swing.JTextField ItemQuantity;
    private javax.swing.JTextField SearchBox;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JTable itemtabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
