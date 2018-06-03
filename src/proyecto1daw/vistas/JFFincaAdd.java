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
public class JFFincaAdd extends javax.swing.JFrame {

    /**
     * Creates new form SelectFincaAdd
     */
    public JFFincaAdd() {
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
        jLabelSuperficie = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jLabelLocalidad = new javax.swing.JLabel();
        jRadioButtonEmp = new javax.swing.JRadioButton();
        jRadioButtonTract = new javax.swing.JRadioButton();
        jLabelFC = new javax.swing.JLabel();
        jRadioButtonEnc = new javax.swing.JRadioButton();
        jLabelEncargado = new javax.swing.JLabel();
        etiquetaId = new javax.swing.JLabel();
        jCheckBoxFFin = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListEmpleados = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        campoId = new javax.swing.JTextField();
        campoFechaC = new javax.swing.JTextField();
        campoLocalidad = new javax.swing.JTextField();
        campoSuperficie = new javax.swing.JTextField();
        campoFechaFin = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Añadir Finca");

        jPanelIzq.setBackground(new java.awt.Color(119, 182, 134));

        jLabelSuperficie.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSuperficie.setText("Superficie");

        jLabelId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelId.setText("ID");

        jLabelLocalidad.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLocalidad.setText("Localidad");

        jRadioButtonEmp.setBackground(new java.awt.Color(119, 182, 134));
        buttonGroupTipoEncargado.add(jRadioButtonEmp);
        jRadioButtonEmp.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonEmp.setText("Empleado");
        jRadioButtonEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEmpActionPerformed(evt);
            }
        });

        jRadioButtonTract.setBackground(new java.awt.Color(119, 182, 134));
        buttonGroupTipoEncargado.add(jRadioButtonTract);
        jRadioButtonTract.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonTract.setText("Tractorista");
        jRadioButtonTract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTractActionPerformed(evt);
            }
        });

        jLabelFC.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFC.setText("F.Creacion");

        jRadioButtonEnc.setBackground(new java.awt.Color(119, 182, 134));
        buttonGroupTipoEncargado.add(jRadioButtonEnc);
        jRadioButtonEnc.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonEnc.setText("Encargado");
        jRadioButtonEnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEncActionPerformed(evt);
            }
        });

        jLabelEncargado.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEncargado.setText("Encargado");

        etiquetaId.setForeground(new java.awt.Color(255, 255, 255));

        jCheckBoxFFin.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxFFin.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxFFin.setText("F.Fin");
        jCheckBoxFFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxFFinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelIzqLayout = new javax.swing.GroupLayout(jPanelIzq);
        jPanelIzq.setLayout(jPanelIzqLayout);
        jPanelIzqLayout.setHorizontalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLocalidad)
                    .addComponent(jLabelSuperficie)
                    .addComponent(jLabelFC)
                    .addComponent(jLabelEncargado)
                    .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelIzqLayout.createSequentialGroup()
                            .addComponent(jLabelId)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(etiquetaId))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelIzqLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jRadioButtonEmp)
                                .addComponent(jRadioButtonEnc)
                                .addComponent(jRadioButtonTract))))
                    .addComponent(jCheckBoxFFin))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanelIzqLayout.setVerticalGroup(
            jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIzqLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(etiquetaId))
                .addGap(21, 21, 21)
                .addComponent(jLabelLocalidad)
                .addGap(21, 21, 21)
                .addComponent(jLabelSuperficie)
                .addGap(21, 21, 21)
                .addComponent(jLabelFC)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxFFin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelEncargado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonEnc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonTract)
                .addGap(63, 63, 63))
        );

        jListEmpleados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListEmpleados);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(campoFechaC, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(campoSuperficie, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(campoLocalidad, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(campoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoSuperficie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoFechaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(botonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelar)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap())
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

    private void jRadioButtonEncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEncActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonEncActionPerformed

    private void jRadioButtonTractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTractActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonTractActionPerformed

    private void jRadioButtonEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonEmpActionPerformed

    private void jCheckBoxFFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFFinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxFFinActionPerformed

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
    public javax.swing.JTextField campoFechaC;
    public javax.swing.JTextField campoFechaFin;
    public javax.swing.JTextField campoId;
    public javax.swing.JTextField campoLocalidad;
    public javax.swing.JTextField campoSuperficie;
    public javax.swing.JLabel etiquetaId;
    public javax.swing.JCheckBox jCheckBoxFFin;
    private javax.swing.JLabel jLabelEncargado;
    private javax.swing.JLabel jLabelFC;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelLocalidad;
    private javax.swing.JLabel jLabelSuperficie;
    public javax.swing.JList<String> jListEmpleados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelIzq;
    public javax.swing.JRadioButton jRadioButtonEmp;
    public javax.swing.JRadioButton jRadioButtonEnc;
    public javax.swing.JRadioButton jRadioButtonTract;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
