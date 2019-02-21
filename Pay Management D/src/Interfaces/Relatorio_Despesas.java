
package Interfaces;

import Model.Usuario;
import java.sql.*;
import Dao.Conexao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.scripts.JO;
import net.proteanit.sql.DbUtils;


public class Relatorio_Despesas extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    CallableStatement cs = null;
    Usuario u = Usuario.getInstance();
    
    public Relatorio_Despesas() throws ClassNotFoundException, SQLException {
        initComponents();
        con = Conexao.conexao();
        listarDespesas();
        CalculaDespesas();
        CalculaSaldo();
    }

    
    public void listarDespesas() throws SQLException{
        try{
        String sql = "{call relatorio_despesas(?)}";
        cs = con.prepareCall(sql);
        cs.setString(1, u.getCpf());
        cs.execute();
        rs = cs.getResultSet();
        jTableDespesas.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void CalculaDespesas() throws SQLException{
        try{
        String sql = "{call Despesa_Total(?)}";
        cs = con.prepareCall(sql);
        cs.setString(1, u.getCpf());
        cs.execute();
        rs = cs.getResultSet();

        while(rs.next()){
            jVtotalText.setText(rs.getString(1));
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void CalculaSaldo() throws SQLException{
        try{
        String sql2 = "select salario from usuario where cpf = ?";
        pst = con.prepareStatement(sql2);
        pst.setString(1, u.getCpf());
        rs = pst.executeQuery();
        
        while(rs.next()){
            u.setSalario(rs.getFloat(1)); 
        }
        jSalText.setText(String.valueOf(u.getSalario()));
        String sql = "{call saldo(?, ?)}";
        cs = con.prepareCall(sql);
        cs.setString(1, u.getCpf());
        cs.setFloat(2,u.getSalario());
        cs.execute();
        rs = cs.getResultSet();
        
        while(rs.next()){
            jSaldoText.setText(rs.getString(1));
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void preencherDespesas() throws SQLException{
        int selecionado = jTableDespesas.getSelectedRow();
        
        jIdText.setText((String.valueOf(jTableDespesas.getModel().getValueAt(selecionado, 0))));
        jDescricaoText.setText((String)jTableDespesas.getModel().getValueAt(selecionado, 1));
        jValorText.setText((String.valueOf(jTableDespesas.getModel().getValueAt(selecionado, 2))));
        jCategoriaText.setText((String) jTableDespesas.getModel().getValueAt(selecionado, 3));
        jDataText.setText((String.valueOf(jTableDespesas.getModel().getValueAt(selecionado, 4))));
        
    }
    
    public void alterardespesas(){
        String sql = "update despesas set descricao = ?, valor = ?, categoria = ?, data_insercao = ? where id = ?";
        
        try{
            if(jDescricaoText.getText().isEmpty()||jValorText.getText().isEmpty()||jCategoriaText.getText().isEmpty()||jDataText.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Há campos vazios, favor preenche-los");
            }else{
                pst = con.prepareStatement(sql);
                pst.setString(1, jDescricaoText.getText());
                pst.setFloat(2, Float.parseFloat(jValorText.getText()));
                pst.setString(3, jCategoriaText.getText());
                pst.setDate(4, Date.valueOf(jDataText.getText()));
                pst.setInt(5, Integer.parseInt(jIdText.getText()));
                pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alteração bem sucedida");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void excluirDespesas(){
        String sql = "delete from despesas where id = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(jIdText.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Exclusão bem sucedida");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDespesas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jVtotalText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSalText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSaldoText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDescricaoText = new javax.swing.JTextField();
        jValorText = new javax.swing.JTextField();
        jCategoriaText = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jIdText = new javax.swing.JTextField();
        jDataText = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5"
            }
        ));
        jTableDespesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDespesasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDespesas);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Icones/arrow_rotate_clockwise.png"))); // NOI18N
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Valor total de despesas:");

        jVtotalText.setEditable(false);
        jVtotalText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jVtotalTextActionPerformed(evt);
            }
        });

        jLabel3.setText("Salário:");

        jSalText.setEditable(false);
        jSalText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSalTextActionPerformed(evt);
            }
        });

        jLabel4.setText("Saldo:");

        jSaldoText.setEditable(false);
        jSaldoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaldoTextActionPerformed(evt);
            }
        });

        jLabel1.setText("Descrição:");

        jLabel5.setText("Valor:");

        jLabel6.setText("Categoria:");

        jLabel7.setText("Data_inserção:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Icones/pencil.png"))); // NOI18N
        jButton2.setText("Alterar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Icones/pencil_delete.png"))); // NOI18N
        jButton3.setText("Excluir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("ID:");

        jIdText.setEditable(false);

        try {
            jDataText.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(118, 118, 118)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jSalText, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jSaldoText, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jVtotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jDataText, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jDescricaoText, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jValorText, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCategoriaText, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDescricaoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jValorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8)
                    .addComponent(jIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jCategoriaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jDataText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jVtotalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSaldoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jVtotalTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jVtotalTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jVtotalTextActionPerformed

    private void jSalTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSalTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSalTextActionPerformed

    private void jSaldoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaldoTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSaldoTextActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        excluirDespesas();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTableDespesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDespesasMouseClicked

        try {
            preencherDespesas();
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio_Despesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jTableDespesasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        alterardespesas();

    }//GEN-LAST:event_jButton2ActionPerformed


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
            java.util.logging.Logger.getLogger(Relatorio_Despesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatorio_Despesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatorio_Despesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorio_Despesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Relatorio_Despesas().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Relatorio_Despesas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Relatorio_Despesas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JTextField jCategoriaText;
    private javax.swing.JFormattedTextField jDataText;
    private javax.swing.JTextField jDescricaoText;
    private javax.swing.JTextField jIdText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jSalText;
    private javax.swing.JTextField jSaldoText;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDespesas;
    private javax.swing.JTextField jValorText;
    private javax.swing.JTextField jVtotalText;
    // End of variables declaration//GEN-END:variables
}
