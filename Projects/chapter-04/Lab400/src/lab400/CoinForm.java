/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab400;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 166348
 */
public class CoinForm extends javax.swing.JPanel
{

  int total = 0;
  int qTot = 0;
  int dTot = 0;
  int nTot = 0;
  int pTot = 0;
    
  Image pennyI;
  Image dimeI;
  Image nickelI;
  Image quarterI;
  /** Creates new form CoinForm */
  public CoinForm()
  {
    initComponents();
    pennyI = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("penny.png"));
    dimeI = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("dime.png"));
    nickelI = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("nickel.png"));
    quarterI = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("quarter.png"));
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

    pBut = new javax.swing.JButton();
    nBut = new javax.swing.JButton();
    dBut = new javax.swing.JButton();
    qBut = new javax.swing.JButton();
    rBut = new javax.swing.JButton();
    tLabel = new javax.swing.JLabel();
    eLabel = new javax.swing.JLabel();

    pBut.setText("Penny");
    pBut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        pButActionPerformed(evt);
      }
    });

    nBut.setText("Nickel");
    nBut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        nButActionPerformed(evt);
      }
    });

    dBut.setText("Dime");
    dBut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        dButActionPerformed(evt);
      }
    });

    qBut.setText("Quarter");
    qBut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        qButActionPerformed(evt);
      }
    });

    rBut.setText("Reset");
    rBut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        rButActionPerformed(evt);
      }
    });

    tLabel.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
    tLabel.setText("0 Cents");

    eLabel.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
    eLabel.setText("Most efficient:    0 Q    0 D    0 N    0 P");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(55, 55, 55)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(eLabel)
          .addComponent(tLabel)
          .addGroup(layout.createSequentialGroup()
            .addComponent(pBut)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(nBut)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(dBut)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(qBut)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rBut)))
        .addContainerGap(62, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(pBut)
          .addComponent(nBut)
          .addComponent(dBut)
          .addComponent(qBut)
          .addComponent(rBut))
        .addGap(18, 18, 18)
        .addComponent(tLabel)
        .addGap(18, 18, 18)
        .addComponent(eLabel)
        .addContainerGap(32, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void pButActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pButActionPerformed
  {//GEN-HEADEREND:event_pButActionPerformed
    // TODO add your handling code here:
    total += 1;
    updateText();
  }//GEN-LAST:event_pButActionPerformed

  private void nButActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nButActionPerformed
  {//GEN-HEADEREND:event_nButActionPerformed
    // TODO add your handling code here:
    total += 5;
    updateText();
  }//GEN-LAST:event_nButActionPerformed

  private void dButActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_dButActionPerformed
  {//GEN-HEADEREND:event_dButActionPerformed
    // TODO add your handling code here:
    total += 10;
    updateText();
  }//GEN-LAST:event_dButActionPerformed

  private void qButActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_qButActionPerformed
  {//GEN-HEADEREND:event_qButActionPerformed
    // TODO add your handling code here:
    total += 25;
    updateText();
  }//GEN-LAST:event_qButActionPerformed

  private void updateText(){
    tLabel.setText(total+" Cents");
    int totalTest = 0;
    qTot = 0;
    dTot = 0;
    nTot = 0;
    pTot = 0;
    for (int i = 0; i < 4; i++){
      if (totalTest == total){
        continue;
      }
      while(true){
        switch(i){
          case 0:
            totalTest += 25;
            qTot++;
            break;
          case 1:
            totalTest += 10;
            dTot++;
            break;
          case 2:
            totalTest += 5;
            nTot++;
            break;
          case 3:
            totalTest += 1;
            pTot++;
            break;
        }
        if (totalTest > total){
          switch(i){
            case 0:
              totalTest -= 25;
              qTot--;
              break;
            case 1:
              totalTest -= 10;
              dTot--;
              break;
            case 2:
              totalTest -= 5;
              nTot--;
              break;
            case 3:
              totalTest -= 1;
              pTot--;
              break;
          }
          break;
        }
      }
    }
    eLabel.setText("Most efficient:    "+qTot+" Q    "+dTot+" D    "+nTot+" N    "+pTot+" P");
    super.repaint();
  }
  
  public void paintComponent(Graphics g){
    g.clearRect(-10, -10, 500, 200);
    for (int i = 0; i < 4; i++){
      switch(i){
        case 0:
          for (int ii = 0; ii < pTot; ii++){
            Random random1 = new Random();
            int x = random1.nextInt(401);
            int y = random1.nextInt(101);
            g.drawImage(pennyI, x, y, 50, 50, this);
          }
          break;
        case 1:
          for (int ii = 0; ii < qTot; ii++){
            Random random1 = new Random();
            int x = random1.nextInt(401);
            int y = random1.nextInt(101);
            g.drawImage(quarterI, x, y, 50, 50, this);
          }
          break;
        case 2:
          for (int ii = 0; ii < dTot; ii++){
            Random random1 = new Random();
            int x = random1.nextInt(401);
            int y = random1.nextInt(101);
            g.drawImage(dimeI, x, y, 50, 50, this);
          }
          break;
        case 3:
          for (int ii = 0; ii < nTot; ii++){
            Random random1 = new Random();
            int x = random1.nextInt(401);
            int y = random1.nextInt(101);
            g.drawImage(nickelI, x, y, 50, 50, this);
          }
          break;
      }
    }
    g.setColor(new Color(100,100,100,100));
    g.fillRect(-10, -10, 500, 200);
  }
  
  private void rButActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rButActionPerformed
  {//GEN-HEADEREND:event_rButActionPerformed
    // TODO add your handling code here:
    total = 0;
    updateText();
  }//GEN-LAST:event_rButActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton dBut;
  private javax.swing.JLabel eLabel;
  private javax.swing.JButton nBut;
  private javax.swing.JButton pBut;
  private javax.swing.JButton qBut;
  private javax.swing.JButton rBut;
  private javax.swing.JLabel tLabel;
  // End of variables declaration//GEN-END:variables
}
