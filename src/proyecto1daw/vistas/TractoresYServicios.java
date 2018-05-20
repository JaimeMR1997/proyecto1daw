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
public class TractoresYServicios extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public TractoresYServicios() {
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

        jPanelTop = new javax.swing.JPanel();
        listaFincas = new javax.swing.JLabel();
        jLabelGestionar1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        imgTractores = new javax.swing.JLabel();
        imgServicios = new javax.swing.JLabel();
        jScrollPaneServicios = new javax.swing.JScrollPane();
        tablaServicios = new javax.swing.JTable();
        jScrollPaneItv = new javax.swing.JScrollPane();
        tablaItv1 = new javax.swing.JTable();
        verTractores = new javax.swing.JButton();
        verServicios = new javax.swing.JButton();
        jLabelItv = new javax.swing.JLabel();
        jLabelItv1 = new javax.swing.JLabel();
        jPanelBot = new javax.swing.JPanel();
        jLabelGestionar = new javax.swing.JLabel();
        jLabelAdd = new javax.swing.JLabel();
        jLabelMod = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jLabelEliminar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(174, 207, 182));

        jPanelTop.setBackground(new java.awt.Color(119, 182, 134));
        jPanelTop.setToolTipText("");

        listaFincas.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        listaFincas.setForeground(new java.awt.Color(255, 255, 255));
        listaFincas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        listaFincas.setText("Tractores y Servicios");

        jLabelGestionar1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelGestionar1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGestionar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_back.png"))); // NOI18N
        jLabelGestionar1.setText("Volver");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabelGestionar1)
                .addGap(220, 220, 220)
                .addComponent(listaFincas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(listaFincas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelGestionar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        imgTractores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/tsTractores.jpg"))); // NOI18N

        imgServicios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/tsServicios.jpg"))); // NOI18N

        tablaServicios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneServicios.setViewportView(tablaServicios);

        tablaItv1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneItv.setViewportView(tablaItv1);

        verTractores.setText("Ver tractores");
        verTractores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verTractoresActionPerformed(evt);
            }
        });

        verServicios.setText("Ver Servicios");
        verServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verServiciosActionPerformed(evt);
            }
        });

        jLabelItv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelItv.setText("Próximas ITV");

        jLabelItv1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelItv1.setText("Servicios sin terminar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPaneServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(imgServicios))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(imgTractores)
                                        .addGap(209, 209, 209))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(170, 170, 170)
                                        .addComponent(jLabelItv1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(verTractores)
                                .addGap(188, 188, 188))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(verServicios)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelItv)
                .addGap(203, 203, 203))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(523, Short.MAX_VALUE)
                    .addComponent(jScrollPaneItv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(imgTractores)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelItv1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabelItv)
                        .addGap(221, 221, 221)
                        .addComponent(verTractores)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgServicios)
                    .addComponent(jScrollPaneServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verServicios)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jScrollPaneItv, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(275, Short.MAX_VALUE)))
        );

        jPanelBot.setBackground(new java.awt.Color(119, 182, 134));
        jPanelBot.setToolTipText("");

        jLabelGestionar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelGestionar.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGestionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_gest.png"))); // NOI18N
        jLabelGestionar.setText("Gestionar");

        jLabelAdd.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelAdd.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_add.png"))); // NOI18N
        jLabelAdd.setText("Añadir");

        jLabelMod.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelMod.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_edit.png"))); // NOI18N
        jLabelMod.setText("Modificar");

        jLabelInfo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelInfo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_info.png"))); // NOI18N
        jLabelInfo.setText("Informacion");

        jLabelEliminar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabelEliminar.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_borrar.png"))); // NOI18N
        jLabelEliminar.setText("Eliminar");

        javax.swing.GroupLayout jPanelBotLayout = new javax.swing.GroupLayout(jPanelBot);
        jPanelBot.setLayout(jPanelBotLayout);
        jPanelBotLayout.setHorizontalGroup(
            jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabelGestionar)
                .addGap(64, 64, 64)
                .addComponent(jLabelAdd)
                .addGap(64, 64, 64)
                .addComponent(jLabelMod)
                .addGap(64, 64, 64)
                .addComponent(jLabelInfo)
                .addGap(64, 64, 64)
                .addComponent(jLabelEliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelBotLayout.setVerticalGroup(
            jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGestionar)
                    .addComponent(jLabelAdd)
                    .addComponent(jLabelMod)
                    .addComponent(jLabelInfo)
                    .addComponent(jLabelEliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelBot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verServiciosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verServiciosActionPerformed

    private void verTractoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verTractoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verTractoresActionPerformed

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
            java.util.logging.Logger.getLogger(TractoresYServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TractoresYServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TractoresYServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TractoresYServicios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imgServicios;
    private javax.swing.JLabel imgTractores;
    private javax.swing.JLabel jLabelAdd;
    private javax.swing.JLabel jLabelEliminar;
    private javax.swing.JLabel jLabelGestionar;
    private javax.swing.JLabel jLabelGestionar1;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelItv;
    private javax.swing.JLabel jLabelItv1;
    private javax.swing.JLabel jLabelMod;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelBot;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPaneItv;
    private javax.swing.JScrollPane jScrollPaneServicios;
    private javax.swing.JLabel listaFincas;
    private javax.swing.JTable tablaItv1;
    private javax.swing.JTable tablaServicios;
    private javax.swing.JButton verServicios;
    private javax.swing.JButton verTractores;
    // End of variables declaration//GEN-END:variables
}
