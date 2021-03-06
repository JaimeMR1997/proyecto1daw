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
public class JFInicio extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public JFInicio() {
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

        jPanelOpcs = new javax.swing.JPanel();
        botonConfig = new javax.swing.JButton();
        listaFincas = new javax.swing.JLabel();
        jPanelBotones = new javax.swing.JPanel();
        botonTractores = new javax.swing.JButton();
        botonFincas = new javax.swing.JButton();
        botonEstadisticas = new javax.swing.JButton();
        botonTrabajadores = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio");

        jPanelOpcs.setBackground(new java.awt.Color(119, 182, 134));
        jPanelOpcs.setForeground(new java.awt.Color(255, 255, 255));

        botonConfig.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        botonConfig.setForeground(new java.awt.Color(255, 255, 255));
        botonConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_engranaje.png"))); // NOI18N
        botonConfig.setText("Configuración");
        botonConfig.setBorderPainted(false);
        botonConfig.setContentAreaFilled(false);
        botonConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConfigActionPerformed(evt);
            }
        });

        listaFincas.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        listaFincas.setForeground(new java.awt.Color(255, 255, 255));
        listaFincas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        listaFincas.setText("Ramillete ERP");

        javax.swing.GroupLayout jPanelOpcsLayout = new javax.swing.GroupLayout(jPanelOpcs);
        jPanelOpcs.setLayout(jPanelOpcsLayout);
        jPanelOpcsLayout.setHorizontalGroup(
            jPanelOpcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOpcsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(listaFincas)
                .addGap(110, 110, 110)
                .addComponent(botonConfig))
        );
        jPanelOpcsLayout.setVerticalGroup(
            jPanelOpcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOpcsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelOpcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listaFincas)
                    .addComponent(botonConfig))
                .addContainerGap())
        );

        jPanelBotones.setLayout(new java.awt.GridLayout(1, 0));

        botonTractores.setBackground(new java.awt.Color(119, 182, 134));
        botonTractores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/tractores.jpg"))); // NOI18N
        botonTractores.setPreferredSize(new java.awt.Dimension(200, 338));
        botonTractores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTractoresActionPerformed(evt);
            }
        });
        jPanelBotones.add(botonTractores);

        botonFincas.setBackground(new java.awt.Color(119, 182, 134));
        botonFincas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/Finca.jpg"))); // NOI18N
        botonFincas.setPreferredSize(new java.awt.Dimension(200, 338));
        jPanelBotones.add(botonFincas);

        botonEstadisticas.setBackground(new java.awt.Color(119, 182, 134));
        botonEstadisticas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/estadisticas.jpg"))); // NOI18N
        botonEstadisticas.setPreferredSize(new java.awt.Dimension(200, 338));
        jPanelBotones.add(botonEstadisticas);

        botonTrabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/empleados.jpg"))); // NOI18N
        botonTrabajadores.setPreferredSize(new java.awt.Dimension(200, 338));
        botonTrabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTrabajadoresActionPerformed(evt);
            }
        });
        jPanelBotones.add(botonTrabajadores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBotones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelOpcs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanelOpcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonTractoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTractoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonTractoresActionPerformed

    private void botonTrabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTrabajadoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonTrabajadoresActionPerformed

    private void botonConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonConfigActionPerformed

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
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonConfig;
    public javax.swing.JButton botonEstadisticas;
    public javax.swing.JButton botonFincas;
    public javax.swing.JButton botonTrabajadores;
    public javax.swing.JButton botonTractores;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelOpcs;
    private javax.swing.JLabel listaFincas;
    // End of variables declaration//GEN-END:variables
}
