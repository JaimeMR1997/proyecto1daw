/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.vistas;

import java.awt.Toolkit;

/**
 *
 * @author Jaime-torre
 */
public class JFExplotacionAdd extends javax.swing.JFrame {

    /**
     * Creates new form SelectFincaAdd
     */
    public JFExplotacionAdd() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/proyecto1daw/img/icono_logo.png")));
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
        jLabelSuperficie = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jLabelTipo = new javax.swing.JLabel();
        jLabelFC = new javax.swing.JLabel();
        jLabelFC1 = new javax.swing.JLabel();
        jLabelFC2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        campoId = new javax.swing.JTextField();
        campoFechaC = new javax.swing.JTextField();
        campoSuperficie = new javax.swing.JTextField();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jComboBoxSubtipo = new javax.swing.JComboBox<>();
        campoIdFinca = new javax.swing.JTextField();
        errTipo = new javax.swing.JLabel();
        errSuperficie = new javax.swing.JLabel();
        errFCreacion = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        campoAlias = new javax.swing.JTextField();
        errAlias = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Añadir Explotación");

        jPanelIzq.setBackground(new java.awt.Color(119, 182, 134));

        jLabelSuperficie.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSuperficie.setText("Superficie");

        jLabelId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelId.setText("ID");

        jLabelTipo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTipo.setText("Tipo");

        jLabelFC.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFC.setText("F.Creacion");

        jLabelFC1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFC1.setText("Finca");

        jLabelFC2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFC2.setText("Alias");

        javax.swing.GroupLayout jPanelIzqLayout = new javax.swing.GroupLayout(jPanelIzq);
        jPanelIzq.setLayout(jPanelIzqLayout);
        jPanelIzqLayout.setHorizontalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelId)
                    .addComponent(jLabelTipo)
                    .addComponent(jLabelSuperficie)
                    .addComponent(jLabelFC)
                    .addComponent(jLabelFC1)
                    .addComponent(jLabelFC2))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanelIzqLayout.setVerticalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabelId)
                .addGap(21, 21, 21)
                .addComponent(jLabelTipo)
                .addGap(48, 48, 48)
                .addComponent(jLabelSuperficie)
                .addGap(30, 30, 30)
                .addComponent(jLabelFC)
                .addGap(33, 33, 33)
                .addComponent(jLabelFC1)
                .addGap(28, 28, 28)
                .addComponent(jLabelFC2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        campoSuperficie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSuperficieActionPerformed(evt);
            }
        });

        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxSubtipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        errTipo.setForeground(new java.awt.Color(255, 0, 0));
        errTipo.setText(" ");

        errSuperficie.setForeground(new java.awt.Color(255, 0, 0));
        errSuperficie.setText(" ");

        errFCreacion.setForeground(new java.awt.Color(255, 0, 0));
        errFCreacion.setText(" ");

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");

        campoAlias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoAliasActionPerformed(evt);
            }
        });

        errAlias.setForeground(new java.awt.Color(255, 0, 0));
        errAlias.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(errAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(campoIdFinca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(errSuperficie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoSuperficie, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoId, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBoxSubtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(errFCreacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoFechaC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(botonAceptar)
                            .addGap(18, 18, 18)
                            .addComponent(botonCancelar))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(errTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSubtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(errTipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addComponent(campoSuperficie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(errSuperficie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoFechaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(errFCreacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoIdFinca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(errAlias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonCancelar)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void campoSuperficieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSuperficieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoSuperficieActionPerformed

    private void campoAliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoAliasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoAliasActionPerformed

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
            java.util.logging.Logger.getLogger(JFExplotacionAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFExplotacionAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFExplotacionAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFExplotacionAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFExplotacionAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAceptar;
    public javax.swing.JButton botonCancelar;
    private javax.swing.ButtonGroup buttonGroupTipoEncargado;
    public javax.swing.JTextField campoAlias;
    public javax.swing.JTextField campoFechaC;
    public javax.swing.JTextField campoId;
    public javax.swing.JTextField campoIdFinca;
    public javax.swing.JTextField campoSuperficie;
    public javax.swing.JLabel errAlias;
    public javax.swing.JLabel errFCreacion;
    public javax.swing.JLabel errSuperficie;
    public javax.swing.JLabel errTipo;
    public javax.swing.JComboBox<String> jComboBoxSubtipo;
    public javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JLabel jLabelFC;
    private javax.swing.JLabel jLabelFC1;
    private javax.swing.JLabel jLabelFC2;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelSuperficie;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelIzq;
    // End of variables declaration//GEN-END:variables
}
