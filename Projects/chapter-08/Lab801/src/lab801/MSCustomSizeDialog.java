/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab801;

import javax.swing.SpinnerNumberModel;

/**
 *
 * @author David Rogers
 */
public class MSCustomSizeDialog extends javax.swing.JDialog
{
  private int exitCode;
  
  /** Creates new form CustomSize */
  public MSCustomSizeDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
    super.setTitle("Create a custom mine field:");
    super.setIconImage(MSResources.BOMB.getImage());
    super.setResizable(false);
    exitCode = -1;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jSpinner1 = new javax.swing.JSpinner();
    jSpinner2 = new javax.swing.JSpinner();
    jLabel3 = new javax.swing.JLabel();
    jSpinner3 = new javax.swing.JSpinner();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();

    setBackground(new java.awt.Color(240,240,240));
    setResizable(false);

    jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText("Height:");
    jLabel1.setMaximumSize(new java.awt.Dimension(60, 24));
    jLabel1.setMinimumSize(new java.awt.Dimension(60, 24));
    jLabel1.setPreferredSize(new java.awt.Dimension(60, 24));

    jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setText("Width:");
    jLabel2.setMaximumSize(new java.awt.Dimension(60, 24));
    jLabel2.setMinimumSize(new java.awt.Dimension(60, 24));
    jLabel2.setName(""); // NOI18N
    jLabel2.setPreferredSize(new java.awt.Dimension(60, 24));

    jSpinner1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jSpinner1.setModel(new javax.swing.SpinnerNumberModel(9, 9, 24, 1));
    jSpinner1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 128, 128), 2));
    jSpinner1.setMaximumSize(new java.awt.Dimension(50, 30));
    jSpinner1.setMinimumSize(new java.awt.Dimension(50, 30));
    jSpinner1.setPreferredSize(new java.awt.Dimension(50, 30));
    jSpinner1.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        jSpinner1StateChanged(evt);
      }
    });

    jSpinner2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jSpinner2.setModel(new javax.swing.SpinnerNumberModel(18, 9, 30, 1));
    jSpinner2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 128, 128), 2));
    jSpinner2.setMaximumSize(new java.awt.Dimension(50, 30));
    jSpinner2.setMinimumSize(new java.awt.Dimension(50, 30));
    jSpinner2.setPreferredSize(new java.awt.Dimension(50, 30));
    jSpinner2.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        jSpinner2StateChanged(evt);
      }
    });

    jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel3.setText("Mines:");
    jLabel3.setMaximumSize(new java.awt.Dimension(60, 24));
    jLabel3.setMinimumSize(new java.awt.Dimension(60, 24));
    jLabel3.setName(""); // NOI18N
    jLabel3.setPreferredSize(new java.awt.Dimension(60, 24));

    jSpinner3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jSpinner3.setModel(new javax.swing.SpinnerNumberModel(10, 10, 145, 1));
    jSpinner3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(128, 128, 128), 2));
    jSpinner3.setMaximumSize(new java.awt.Dimension(50, 30));
    jSpinner3.setMinimumSize(new java.awt.Dimension(50, 30));
    jSpinner3.setPreferredSize(new java.awt.Dimension(50, 30));

    jButton1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton1ActionPerformed(evt);
      }
    });

    jButton2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    jButton2.setText("Cancel");
    jButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton2ActionPerformed(evt);
      }
    });

    jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel4.setText("(9 - 24)");

    jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel5.setText("(9 - 30)");

    jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel6.setText("(10 - 145)");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(6, 6, 6)
        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
            .addContainerGap())))
      .addGroup(layout.createSequentialGroup()
        .addGap(125, 125, 125)
        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel6)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton1)
          .addComponent(jButton2))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
  public int showDialog()
  {
    exitCode = -1;
    setVisible(true);
    return exitCode;
  }
  
  
  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
  {//GEN-HEADEREND:event_jButton1ActionPerformed
    // TODO add your handling code here:
    super.setVisible(false);
    exitCode = 1;
    super.dispose();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void updateMinesSpinner()
  {
    int r = Integer.parseInt(""+jSpinner1.getValue());
    int c = Integer.parseInt(""+jSpinner2.getValue());
    int val = Integer.parseInt(""+jSpinner3.getValue());
    int max = r * c * 9 / 10;
    SpinnerNumberModel s = (SpinnerNumberModel)jSpinner3.getModel();
    s.setMaximum(max);
    jLabel6.setText("(10 - "+max+")");
    if (val > max) s.setValue(max);
  }
  
  private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSpinner1StateChanged
  {//GEN-HEADEREND:event_jSpinner1StateChanged
    // TODO add your handling code here:
    updateMinesSpinner();
  }//GEN-LAST:event_jSpinner1StateChanged

  private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSpinner2StateChanged
  {//GEN-HEADEREND:event_jSpinner2StateChanged
    // TODO add your handling code here:
    updateMinesSpinner();
  }//GEN-LAST:event_jSpinner2StateChanged

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
  {//GEN-HEADEREND:event_jButton2ActionPerformed
    // TODO add your handling code here:
    super.setVisible(false);
    exitCode = -1;
    super.dispose();
  }//GEN-LAST:event_jButton2ActionPerformed

  public int getRows()
  {
    return Integer.parseInt(""+jSpinner1.getValue()); 
  }
  public int getCols()
  {
    return Integer.parseInt(""+jSpinner2.getValue()); 
  }
  public int getMines()
  {
    return Integer.parseInt(""+jSpinner3.getValue()); 
  }
  

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JSpinner jSpinner1;
  private javax.swing.JSpinner jSpinner2;
  private javax.swing.JSpinner jSpinner3;
  // End of variables declaration//GEN-END:variables
}