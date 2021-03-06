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
public class JFFincaAdd extends javax.swing.JFrame {

    /**
     * Creates new form SelectFincaAdd
     */
    public JFFincaAdd() {
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
        jLabelLocalidad = new javax.swing.JLabel();
        jLabelFC = new javax.swing.JLabel();
        etiquetaId = new javax.swing.JLabel();
        jCheckBoxFFin = new javax.swing.JCheckBox();
        jLabelAlias = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        campoId = new javax.swing.JTextField();
        campoFechaC = new javax.swing.JTextField();
        campoLocalidad = new javax.swing.JTextField();
        campoSuperficie = new javax.swing.JTextField();
        campoFechaFin = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        errId = new javax.swing.JLabel();
        errLocalidad = new javax.swing.JLabel();
        errSuperficie = new javax.swing.JLabel();
        errFInicio = new javax.swing.JLabel();
        errFFin = new javax.swing.JLabel();
        campoAlias = new javax.swing.JTextField();
        errAlias = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Añadir Finca");

        jPanelIzq.setBackground(new java.awt.Color(119, 182, 134));

        jLabelSuperficie.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSuperficie.setText("Superficie");

        jLabelId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelId.setText("ID");

        jLabelLocalidad.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLocalidad.setText("Localidad");

        jLabelFC.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFC.setText("F.Creacion");

        etiquetaId.setForeground(new java.awt.Color(255, 255, 255));

        jCheckBoxFFin.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxFFin.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxFFin.setText("F.Fin");
        jCheckBoxFFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxFFinActionPerformed(evt);
            }
        });

        jLabelAlias.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAlias.setText("Alias");

        javax.swing.GroupLayout jPanelIzqLayout = new javax.swing.GroupLayout(jPanelIzq);
        jPanelIzq.setLayout(jPanelIzqLayout);
        jPanelIzqLayout.setHorizontalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addComponent(jLabelId)
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelIzqLayout.createSequentialGroup()
                        .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLocalidad)
                            .addComponent(jLabelSuperficie)
                            .addComponent(jLabelFC)
                            .addComponent(jCheckBoxFFin)
                            .addComponent(jLabelAlias))
                        .addGap(0, 49, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelIzqLayout.setVerticalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etiquetaId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jLabelLocalidad)
                .addGap(27, 27, 27)
                .addComponent(jLabelSuperficie)
                .addGap(30, 30, 30)
                .addComponent(jLabelFC)
                .addGap(27, 27, 27)
                .addComponent(jCheckBoxFFin)
                .addGap(30, 30, 30)
                .addComponent(jLabelAlias)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        campoLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoLocalidadActionPerformed(evt);
            }
        });

        campoSuperficie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSuperficieActionPerformed(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");

        errId.setForeground(java.awt.Color.red);
        errId.setText(" ");

        errLocalidad.setForeground(java.awt.Color.red);
        errLocalidad.setText(" ");

        errSuperficie.setForeground(java.awt.Color.red);
        errSuperficie.setText(" ");

        errFInicio.setForeground(java.awt.Color.red);
        errFInicio.setText(" ");

        errFFin.setForeground(java.awt.Color.red);
        errFFin.setText(" ");

        campoAlias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoAliasActionPerformed(evt);
            }
        });

        errAlias.setForeground(java.awt.Color.red);
        errAlias.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(botonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(botonCancelar)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(errAlias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoAlias)
                            .addComponent(errFFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errFInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errSuperficie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errLocalidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoFechaFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(campoFechaC, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(campoSuperficie, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoLocalidad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(errId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errLocalidad)
                .addGap(4, 4, 4)
                .addComponent(campoSuperficie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(errSuperficie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoFechaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(errFInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(errFFin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errAlias)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addGap(20, 20, 20))
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

    private void campoLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoLocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoLocalidadActionPerformed

    private void jCheckBoxFFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFFinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxFFinActionPerformed

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
            java.util.logging.Logger.getLogger(JFFincaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFFincaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFFincaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFFincaAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFFincaAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAceptar;
    public javax.swing.JButton botonCancelar;
    private javax.swing.ButtonGroup buttonGroupTipoEncargado;
    public javax.swing.JTextField campoAlias;
    public javax.swing.JTextField campoFechaC;
    public javax.swing.JTextField campoFechaFin;
    public javax.swing.JTextField campoId;
    public javax.swing.JTextField campoLocalidad;
    public javax.swing.JTextField campoSuperficie;
    public javax.swing.JLabel errAlias;
    public javax.swing.JLabel errFFin;
    public javax.swing.JLabel errFInicio;
    public javax.swing.JLabel errId;
    public javax.swing.JLabel errLocalidad;
    public javax.swing.JLabel errSuperficie;
    public javax.swing.JLabel etiquetaId;
    public javax.swing.JCheckBox jCheckBoxFFin;
    private javax.swing.JLabel jLabelAlias;
    private javax.swing.JLabel jLabelFC;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelLocalidad;
    private javax.swing.JLabel jLabelSuperficie;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelIzq;
    // End of variables declaration//GEN-END:variables
}
