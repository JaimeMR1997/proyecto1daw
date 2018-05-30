/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.vistas;

/**
 *
 * @author Jaime-torre
 */
public class JFVentaAdd extends javax.swing.JFrame {

    /**
     * Creates new form SelectFincaAdd
     */
    public JFVentaAdd() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupTipoEncargado = new javax.swing.ButtonGroup();
        jPanelIzq = new javax.swing.JPanel();
        jLabelFPlant = new javax.swing.JLabel();
        jLabelKg = new javax.swing.JLabel();
        jLabelColor = new javax.swing.JLabel();
        jLabelTamanio = new javax.swing.JLabel();
        jLabelIdVenta = new javax.swing.JLabel();
        jLabelIdPlant = new javax.swing.JLabel();
        jLabelPrecio = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        campoColor = new javax.swing.JTextField();
        campoFecha = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        jSpinnerKg = new javax.swing.JSpinner();
        jComboTam = new javax.swing.JComboBox<>();
        campoPrecio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Añadir Finca");

        jPanelIzq.setBackground(new java.awt.Color(119, 182, 134));

        jLabelFPlant.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFPlant.setText("Fecha");

        jLabelKg.setForeground(new java.awt.Color(255, 255, 255));
        jLabelKg.setText("Cantidad (KG)");

        jLabelColor.setForeground(new java.awt.Color(255, 255, 255));
        jLabelColor.setText("Color");

        jLabelTamanio.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTamanio.setText("Tamaño");

        jLabelIdVenta.setForeground(new java.awt.Color(255, 255, 255));

        jLabelIdPlant.setForeground(new java.awt.Color(255, 255, 255));

        jLabelPrecio.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPrecio.setText("Precio");

        javax.swing.GroupLayout jPanelIzqLayout = new javax.swing.GroupLayout(jPanelIzq);
        jPanelIzq.setLayout(jPanelIzqLayout);
        jPanelIzqLayout.setHorizontalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrecio)
                    .addComponent(jLabelTamanio)
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addComponent(jLabelKg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIdVenta))
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addComponent(jLabelColor)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelIdPlant))
                    .addComponent(jLabelFPlant))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanelIzqLayout.setVerticalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKg)
                    .addComponent(jLabelIdVenta))
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabelColor))
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabelIdPlant)))
                .addGap(18, 18, 18)
                .addComponent(jLabelTamanio)
                .addGap(27, 27, 27)
                .addComponent(jLabelFPlant)
                .addGap(30, 30, 30)
                .addComponent(jLabelPrecio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        campoColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoColorActionPerformed(evt);
            }
        });

        campoFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFechaActionPerformed(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");

        jComboTam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        campoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(botonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboTam, javax.swing.GroupLayout.Alignment.LEADING, 0, 169, Short.MAX_VALUE)
                            .addComponent(jSpinnerKg, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoColor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoFecha, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSpinnerKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboTam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void campoFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFechaActionPerformed

    private void campoColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoColorActionPerformed

    private void campoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoPrecioActionPerformed

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
            java.util.logging.Logger.getLogger(JFVentaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFVentaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFVentaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFVentaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFVentaAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAceptar;
    public javax.swing.JButton botonCancelar;
    private javax.swing.ButtonGroup buttonGroupTipoEncargado;
    public javax.swing.JTextField campoColor;
    public javax.swing.JTextField campoFecha;
    public javax.swing.JTextField campoPrecio;
    public javax.swing.JComboBox<String> jComboTam;
    private javax.swing.JLabel jLabelColor;
    private javax.swing.JLabel jLabelFPlant;
    public javax.swing.JLabel jLabelIdPlant;
    public javax.swing.JLabel jLabelIdVenta;
    private javax.swing.JLabel jLabelKg;
    private javax.swing.JLabel jLabelPrecio;
    private javax.swing.JLabel jLabelTamanio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelIzq;
    public javax.swing.JSpinner jSpinnerKg;
    // End of variables declaration//GEN-END:variables
}