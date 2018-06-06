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
public class JFEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public JFEmpleados() {
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
        botonVolver = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelCuadrilla = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCuadrilla = new javax.swing.JTable();
        jPanelBot2 = new javax.swing.JPanel();
        botonGestionarCuad = new javax.swing.JButton();
        botonAddCuad = new javax.swing.JButton();
        botonModCuad = new javax.swing.JButton();
        botonInfoCuad = new javax.swing.JButton();
        botonEliminarCuad = new javax.swing.JButton();
        jPanelEmpleados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmple = new javax.swing.JTable();
        jPanelBot = new javax.swing.JPanel();
        botonGestionarEmp = new javax.swing.JButton();
        botonAddEmp = new javax.swing.JButton();
        botonModEmp = new javax.swing.JButton();
        botonInfoEmp = new javax.swing.JButton();
        botonEliminarEmp = new javax.swing.JButton();
        jCheckBoxEmple = new javax.swing.JCheckBox();
        jCheckBoxTract = new javax.swing.JCheckBox();
        jCheckBoxEnc = new javax.swing.JCheckBox();
        jCheckBoxFinalizado = new javax.swing.JCheckBox();
        jPanelTrabajos = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableTrab = new javax.swing.JTable();
        jPanelBot4 = new javax.swing.JPanel();
        botonAddTrab = new javax.swing.JButton();
        botonModTrab = new javax.swing.JButton();
        botonEliminarTrab = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi programa - Fincas");
        setBackground(new java.awt.Color(174, 207, 182));

        jPanelTop.setBackground(new java.awt.Color(119, 182, 134));
        jPanelTop.setToolTipText("");

        listaFincas.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        listaFincas.setForeground(new java.awt.Color(255, 255, 255));
        listaFincas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        listaFincas.setText("Cuadrillas y Empleados");

        botonVolver.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        botonVolver.setForeground(new java.awt.Color(255, 255, 255));
        botonVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_back.png"))); // NOI18N
        botonVolver.setText("Volver");
        botonVolver.setBorderPainted(false);
        botonVolver.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(botonVolver)
                .addGap(137, 137, 137)
                .addComponent(listaFincas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTopLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(listaFincas))
                    .addGroup(jPanelTopLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(botonVolver)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane.setBackground(new java.awt.Color(119, 182, 134));

        jTableCuadrilla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Encargado", "F Inicio", "Ult Trabajo"
            }
        ));
        jScrollPane3.setViewportView(jTableCuadrilla);

        jPanelBot2.setBackground(new java.awt.Color(119, 182, 134));
        jPanelBot2.setToolTipText("");

        botonGestionarCuad.setForeground(new java.awt.Color(255, 255, 255));
        botonGestionarCuad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_gest.png"))); // NOI18N
        botonGestionarCuad.setText("Añadir trabajo");
        botonGestionarCuad.setBorderPainted(false);
        botonGestionarCuad.setContentAreaFilled(false);
        botonGestionarCuad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGestionarCuadActionPerformed(evt);
            }
        });

        botonAddCuad.setForeground(new java.awt.Color(255, 255, 255));
        botonAddCuad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_add.png"))); // NOI18N
        botonAddCuad.setText("Añadir");
        botonAddCuad.setBorderPainted(false);
        botonAddCuad.setContentAreaFilled(false);
        botonAddCuad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddCuadActionPerformed(evt);
            }
        });

        botonModCuad.setForeground(new java.awt.Color(255, 255, 255));
        botonModCuad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_edit.png"))); // NOI18N
        botonModCuad.setText("Modificar");
        botonModCuad.setBorderPainted(false);
        botonModCuad.setContentAreaFilled(false);

        botonInfoCuad.setForeground(new java.awt.Color(255, 255, 255));
        botonInfoCuad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_info.png"))); // NOI18N
        botonInfoCuad.setText("Información");
        botonInfoCuad.setBorderPainted(false);
        botonInfoCuad.setContentAreaFilled(false);

        botonEliminarCuad.setForeground(new java.awt.Color(255, 255, 255));
        botonEliminarCuad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_borrar.png"))); // NOI18N
        botonEliminarCuad.setText("Eliminar");
        botonEliminarCuad.setBorderPainted(false);
        botonEliminarCuad.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanelBot2Layout = new javax.swing.GroupLayout(jPanelBot2);
        jPanelBot2.setLayout(jPanelBot2Layout);
        jPanelBot2Layout.setHorizontalGroup(
            jPanelBot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBot2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(botonGestionarCuad)
                .addGap(70, 70, 70)
                .addComponent(botonAddCuad)
                .addGap(71, 71, 71)
                .addComponent(botonModCuad)
                .addGap(70, 70, 70)
                .addComponent(botonInfoCuad)
                .addGap(18, 69, Short.MAX_VALUE)
                .addComponent(botonEliminarCuad)
                .addGap(89, 89, 89))
        );
        jPanelBot2Layout.setVerticalGroup(
            jPanelBot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBot2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelBot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGestionarCuad)
                    .addComponent(botonAddCuad)
                    .addComponent(botonModCuad)
                    .addComponent(botonInfoCuad)
                    .addComponent(botonEliminarCuad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCuadrillaLayout = new javax.swing.GroupLayout(jPanelCuadrilla);
        jPanelCuadrilla.setLayout(jPanelCuadrillaLayout);
        jPanelCuadrillaLayout.setHorizontalGroup(
            jPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCuadrillaLayout.createSequentialGroup()
                .addGroup(jPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBot2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCuadrillaLayout.setVerticalGroup(
            jPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCuadrillaLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanelBot2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane.addTab("Cuadrillas", jPanelCuadrilla);

        jPanelEmpleados.setBackground(new java.awt.Color(119, 182, 134));

        jTableEmple.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "DNI", "Nombre ", "Apellidos", "Tlf", "F Inicio", "F Fin"
            }
        ));
        jScrollPane1.setViewportView(jTableEmple);

        jPanelBot.setBackground(new java.awt.Color(119, 182, 134));
        jPanelBot.setToolTipText("");

        botonGestionarEmp.setForeground(new java.awt.Color(255, 255, 255));
        botonGestionarEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_gest.png"))); // NOI18N
        botonGestionarEmp.setText("Ascender");
        botonGestionarEmp.setBorderPainted(false);
        botonGestionarEmp.setContentAreaFilled(false);
        botonGestionarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGestionarEmpActionPerformed(evt);
            }
        });

        botonAddEmp.setForeground(new java.awt.Color(255, 255, 255));
        botonAddEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_add.png"))); // NOI18N
        botonAddEmp.setText("Añadir");
        botonAddEmp.setBorderPainted(false);
        botonAddEmp.setContentAreaFilled(false);
        botonAddEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddEmpActionPerformed(evt);
            }
        });

        botonModEmp.setForeground(new java.awt.Color(255, 255, 255));
        botonModEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_edit.png"))); // NOI18N
        botonModEmp.setText("Modificar");
        botonModEmp.setBorderPainted(false);
        botonModEmp.setContentAreaFilled(false);

        botonInfoEmp.setForeground(new java.awt.Color(255, 255, 255));
        botonInfoEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_info.png"))); // NOI18N
        botonInfoEmp.setText("Información");
        botonInfoEmp.setBorderPainted(false);
        botonInfoEmp.setContentAreaFilled(false);

        botonEliminarEmp.setForeground(new java.awt.Color(255, 255, 255));
        botonEliminarEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_borrar.png"))); // NOI18N
        botonEliminarEmp.setText("Eliminar");
        botonEliminarEmp.setBorderPainted(false);
        botonEliminarEmp.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanelBotLayout = new javax.swing.GroupLayout(jPanelBot);
        jPanelBot.setLayout(jPanelBotLayout);
        jPanelBotLayout.setHorizontalGroup(
            jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(botonGestionarEmp)
                .addGap(70, 70, 70)
                .addComponent(botonAddEmp)
                .addGap(68, 68, 68)
                .addComponent(botonModEmp)
                .addGap(70, 70, 70)
                .addComponent(botonInfoEmp)
                .addGap(18, 69, Short.MAX_VALUE)
                .addComponent(botonEliminarEmp)
                .addGap(89, 89, 89))
        );
        jPanelBotLayout.setVerticalGroup(
            jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGestionarEmp)
                    .addComponent(botonAddEmp)
                    .addComponent(botonModEmp)
                    .addComponent(botonInfoEmp)
                    .addComponent(botonEliminarEmp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBoxEmple.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxEmple.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxEmple.setText("Empleado");

        jCheckBoxTract.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxTract.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxTract.setText("Tractorista");

        jCheckBoxEnc.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxEnc.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxEnc.setText("Encargado");

        jCheckBoxFinalizado.setBackground(new java.awt.Color(119, 182, 134));
        jCheckBoxFinalizado.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxFinalizado.setText("Contrato finalizado");

        javax.swing.GroupLayout jPanelEmpleadosLayout = new javax.swing.GroupLayout(jPanelEmpleados);
        jPanelEmpleados.setLayout(jPanelEmpleadosLayout);
        jPanelEmpleadosLayout.setHorizontalGroup(
            jPanelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpleadosLayout.createSequentialGroup()
                .addGroup(jPanelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 948, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelEmpleadosLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jCheckBoxEmple)
                .addGap(110, 110, 110)
                .addComponent(jCheckBoxTract)
                .addGap(78, 78, 78)
                .addComponent(jCheckBoxEnc)
                .addGap(98, 98, 98)
                .addComponent(jCheckBoxFinalizado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEmpleadosLayout.setVerticalGroup(
            jPanelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpleadosLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxEmple)
                    .addComponent(jCheckBoxTract)
                    .addComponent(jCheckBoxEnc)
                    .addComponent(jCheckBoxFinalizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelBot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane.addTab("Empleados", jPanelEmpleados);

        jTableTrab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cuadrilla", "Fecha", "Horas", "Tipo", "Explotacion"
            }
        ));
        jScrollPane5.setViewportView(jTableTrab);

        jPanelBot4.setBackground(new java.awt.Color(119, 182, 134));
        jPanelBot4.setToolTipText("");

        botonAddTrab.setForeground(new java.awt.Color(255, 255, 255));
        botonAddTrab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_add.png"))); // NOI18N
        botonAddTrab.setText("Añadir");
        botonAddTrab.setBorderPainted(false);
        botonAddTrab.setContentAreaFilled(false);
        botonAddTrab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddTrabActionPerformed(evt);
            }
        });

        botonModTrab.setForeground(new java.awt.Color(255, 255, 255));
        botonModTrab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_edit.png"))); // NOI18N
        botonModTrab.setText("Modificar");
        botonModTrab.setBorderPainted(false);
        botonModTrab.setContentAreaFilled(false);

        botonEliminarTrab.setForeground(new java.awt.Color(255, 255, 255));
        botonEliminarTrab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto1daw/img/ico_borrar.png"))); // NOI18N
        botonEliminarTrab.setText("Eliminar");
        botonEliminarTrab.setBorderPainted(false);
        botonEliminarTrab.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanelBot4Layout = new javax.swing.GroupLayout(jPanelBot4);
        jPanelBot4.setLayout(jPanelBot4Layout);
        jPanelBot4Layout.setHorizontalGroup(
            jPanelBot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBot4Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(botonAddTrab)
                .addGap(162, 162, 162)
                .addComponent(botonModTrab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                .addComponent(botonEliminarTrab)
                .addGap(158, 158, 158))
        );
        jPanelBot4Layout.setVerticalGroup(
            jPanelBot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBot4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelBot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAddTrab)
                    .addComponent(botonModTrab)
                    .addComponent(botonEliminarTrab))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelTrabajosLayout = new javax.swing.GroupLayout(jPanelTrabajos);
        jPanelTrabajos.setLayout(jPanelTrabajosLayout);
        jPanelTrabajosLayout.setHorizontalGroup(
            jPanelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addComponent(jPanelBot4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelTrabajosLayout.setVerticalGroup(
            jPanelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTrabajosLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanelBot4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane.addTab("Trabajos", jPanelTrabajos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAddEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAddEmpActionPerformed

    private void botonGestionarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGestionarEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonGestionarEmpActionPerformed

    private void botonGestionarCuadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGestionarCuadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonGestionarCuadActionPerformed

    private void botonAddCuadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddCuadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAddCuadActionPerformed

    private void botonAddTrabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddTrabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAddTrabActionPerformed

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
            java.util.logging.Logger.getLogger(JFEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new JFEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAddCuad;
    public javax.swing.JButton botonAddEmp;
    public javax.swing.JButton botonAddTrab;
    public javax.swing.JButton botonEliminarCuad;
    public javax.swing.JButton botonEliminarEmp;
    public javax.swing.JButton botonEliminarTrab;
    public javax.swing.JButton botonGestionarCuad;
    public javax.swing.JButton botonGestionarEmp;
    public javax.swing.JButton botonInfoCuad;
    public javax.swing.JButton botonInfoEmp;
    public javax.swing.JButton botonModCuad;
    public javax.swing.JButton botonModEmp;
    public javax.swing.JButton botonModTrab;
    public javax.swing.JButton botonVolver;
    public javax.swing.JCheckBox jCheckBoxEmple;
    public javax.swing.JCheckBox jCheckBoxEnc;
    public javax.swing.JCheckBox jCheckBoxFinalizado;
    public javax.swing.JCheckBox jCheckBoxTract;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelBot;
    private javax.swing.JPanel jPanelBot2;
    private javax.swing.JPanel jPanelBot4;
    private javax.swing.JPanel jPanelCuadrilla;
    private javax.swing.JPanel jPanelEmpleados;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPanel jPanelTrabajos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTabbedPane jTabbedPane;
    public javax.swing.JTable jTableCuadrilla;
    public javax.swing.JTable jTableEmple;
    public javax.swing.JTable jTableTrab;
    private javax.swing.JLabel listaFincas;
    // End of variables declaration//GEN-END:variables
}
