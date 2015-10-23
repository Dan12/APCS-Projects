package survive;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LabeledTextField {
    private int colums = 8;
    private int width = 70;
    private int height = 26;
    private String label;
    private TextField textField;
    private Font font;
    private int fontSize = 12;
    private boolean visible;
    
    //Constructor: label text, xPos, yPos, DrawPanel context, initial string
    public LabeledTextField(String l, int x, int y, DrawPanel dp, String s){
        label = l;
        textField = new TextField(s, colums);
        textField.setBounds(x, y, width, height);
        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                dp.getLevelMaker().textFieldUpdated(LabeledTextField.this);
            }
        });
        font = new Font("Arial",Font.PLAIN,fontSize);
        dp.add(textField);
        textField.setVisible(false);
        visible = false;
    }
    
    public void drawLabel(Graphics g){
        if(visible){
            g.setFont(font);
            g.drawString(label, textField.getX(), textField.getY()-4);
        }
    }
    
    public void setText(String s){
        textField.setText(s);
    }
    
    public String getText(){
        return textField.getText();
    }
    
    public void setVisibility(boolean v){
        visible = v;
        textField.setVisible(v);
    }
}
