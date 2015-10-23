/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab701;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author
 */
public class GamePanel extends javax.swing.JPanel implements ActionListener
{
  private Image bgImage, cardBack;
  
  private ArrayList<Card> hCard = new ArrayList<Card>();
  private ArrayList<Card> cCard = new ArrayList<Card>();
  private ArrayList<Card> hWarCard = new ArrayList<Card>();
  private ArrayList<Card> cWarCard = new ArrayList<Card>();

  private ArrayList<Card> deck = new ArrayList<Card>();
  
  private boolean debug = false;
  
  private boolean firstMove = true;
  
  int turns = 0;

   /** Creates new form GamePanel */
  public GamePanel()
  {
    initComponents();
    bgImage = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("images/bg.png"));
    cardBack = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("images/back.png"));
    this.getCards();
    this.newGame();
    System.out.println(hCard.size());
    System.out.println(cCard.size());
  }

  private void getCards()
  {
     for(int i = 0; i < 52; i++){
       String s = "";
       if(i/13 == 0)
         s = "c";
       if(i/13 == 1)
         s = "s";
       if(i/13 == 2)
         s = "d";
       if(i/13 == 3)
         s = "c";
       deck.add(new Card("images/"+s+""+((i%13)+1)+".png", s, (i%13)+1));
       System.out.println("images/"+s+""+((i%13)+1)+".png");
     }
  }

  private void newGame()
  {
    for(int i = 0; i < 52; i++){
      Random r = new Random();
      int n = r.nextInt(52-i);
      if(i%2 == 0)
        hCard.add(deck.get(n));
      else
        cCard.add(deck.get(n));
      deck.remove(n);
    }
    Timer t = new Timer(1, this);
    t.start();
  }


// Perform any custom painting (if necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, this);
    for(int i = 0; i < hCard.size(); i++){
      if(debug)
        g.drawImage(hCard.get(i).cImage, i*10+10, 420, this);
      else
        g.drawImage(cardBack, i*10+10, 420, this);
    }
    
    for(int i = 0; i < cCard.size(); i++){
      if(debug)
        g.drawImage(cCard.get(i).cImage, i*10+10, 50, this);
      else
        g.drawImage(cardBack, i*10+10, 50, this);
    }
    
    for(int i = 0; i < cWarCard.size(); i++){
      if(debug || i%3 ==0)
        g.drawImage(cWarCard.get(i).cImage, i*10+460, 200, this);
      else if(i%3 != 0)
        g.drawImage(cardBack, i*10+440, 200, this);
    }
    
    for(int i = 0; i < hWarCard.size(); i++){
      if(debug || i%3 ==0)
        g.drawImage(hWarCard.get(i).cImage, i*10+460, 240, this);
      else if(i%3 != 0)
        g.drawImage(cardBack, i*10+440, 240, this);
    }
  }
  
  private void distOneCard(){
    if((!hWarCard.isEmpty() && !cWarCard.isEmpty()) || firstMove){
      if(!firstMove){
        if(hWarCard.get(hWarCard.size()-1).val > cWarCard.get(cWarCard.size()-1).val){
          for(int i = 0; i < hWarCard.size(); i++){
            hCard.add(0, hWarCard.get(i));
          }
          for(int i = 0; i < cWarCard.size(); i++){
            hCard.add(0, cWarCard.get(i));
          }
          hWarCard.clear();
          cWarCard.clear();
        }
        else if(hWarCard.get(hWarCard.size()-1).val < cWarCard.get(cWarCard.size()-1).val){
          for(int i = 0; i < hWarCard.size(); i++){
            cCard.add(0, hWarCard.get(i));
          }
          for(int i = 0; i < cWarCard.size(); i++){
            cCard.add(0, cWarCard.get(i));
          }
          hWarCard.clear();
          cWarCard.clear();
        }
        else if(hWarCard.get(hWarCard.size()-1).val == cWarCard.get(cWarCard.size()-1).val){
          if(hCard.size() >= 3 && cCard.size() >= 3){
            hWarCard.add(hCard.get(hCard.size()-1));
            hCard.remove(hCard.size()-1);
            cWarCard.add(cCard.get(cCard.size()-1));
            cCard.remove(cCard.size()-1);
            hWarCard.add(hCard.get(hCard.size()-1));
            hCard.remove(hCard.size()-1);
            cWarCard.add(cCard.get(cCard.size()-1));
            cCard.remove(cCard.size()-1);
          }
          else{
            messageLabel.setText("Took "+turns+" turns");
            playButton.setEnabled(false);          
            return;
          }
        }
      }
      
      if((!hCard.isEmpty() && !cCard.isEmpty())){
        hWarCard.add(hCard.get(hCard.size()-1));
        hCard.remove(hCard.size()-1);
        cWarCard.add(cCard.get(cCard.size()-1));
        cCard.remove(cCard.size()-1);
        if(hWarCard.get(hWarCard.size()-1).val > cWarCard.get(cWarCard.size()-1).val)
          messageLabel.setText("Human Wins");
        else if(hWarCard.get(hWarCard.size()-1).val < cWarCard.get(cWarCard.size()-1).val)
          messageLabel.setText("Computer Wins");
        else if(hWarCard.get(hWarCard.size()-1).val == cWarCard.get(cWarCard.size()-1).val)
          messageLabel.setText("War");
      }
      else{
        messageLabel.setText("Took "+turns+" turns");
        playButton.setEnabled(false);
      }
      firstMove = false;
    }
    else{
      messageLabel.setText("Took "+turns+" turns");
      playButton.setEnabled(false);
    }
    
    turns++;
     
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        humanLabel = new javax.swing.JLabel();
        computerLabel = new javax.swing.JLabel();
        debugCheckbox = new javax.swing.JCheckBox();
        playButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();

        humanLabel.setBackground(new java.awt.Color(0, 0, 0));
        humanLabel.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        humanLabel.setForeground(new java.awt.Color(255, 255, 255));
        humanLabel.setText("Human");

        computerLabel.setBackground(new java.awt.Color(0, 0, 0));
        computerLabel.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        computerLabel.setForeground(new java.awt.Color(255, 255, 255));
        computerLabel.setText("Computer");

        debugCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        debugCheckbox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        debugCheckbox.setText("Debug Mode");
        debugCheckbox.setBorder(null);
        debugCheckbox.setOpaque(false);
        debugCheckbox.setSelected(false);
        debugCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugCheckboxActionPerformed(evt);
            }
        });

        playButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        playButton.setText("Play...");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        messageLabel.setBackground(new java.awt.Color(0, 0, 0));
        messageLabel.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(255, 255, 255));
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        messageLabel.setText("Fight!  ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(debugCheckbox)
                .addGap(32, 32, 32)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(computerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(humanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 372, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(computerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(messageLabel)
                .addGap(74, 74, 74)
                .addComponent(humanLabel)
                .addGap(138, 138, 138)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(debugCheckbox, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

  private void debugCheckboxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_debugCheckboxActionPerformed
  {//GEN-HEADEREND:event_debugCheckboxActionPerformed
    // TODO add your handling code here:
    super.repaint();
    debug = !debug;
  }//GEN-LAST:event_debugCheckboxActionPerformed

  private void playButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_playButtonActionPerformed
  {//GEN-HEADEREND:event_playButtonActionPerformed
    // TODO add your handling code here:
    distOneCard();
    super.repaint();
  }//GEN-LAST:event_playButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel computerLabel;
    private javax.swing.JCheckBox debugCheckbox;
    private javax.swing.JLabel humanLabel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton playButton;
    // End of variables declaration//GEN-END:variables

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if(playButton.isEnabled())
      this.playButtonActionPerformed(null);
  }
}
