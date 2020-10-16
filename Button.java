import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Font;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 16.01.2015
  * @author Michael Trubizkij & Denis Vukomanovic
  */

public class Button extends Thread {
    
    // Anfang Attribute
    private int x;
    private int y;
    private int height;
    private int width;
    private Color fillColor;
    private Color borderColor;
    private String text;
    private int fontSize;
    private Image image;
    private boolean hide = false;
    // Ende Attribute
    
    public Button(int x, int y, int height, int width, Color fillColor, Color borderColor, String text, int fontSize) {  //überladener Konstruktor
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        this.text = text;
        this.fontSize = fontSize;
    }
    
    public Button(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.height = 56;
        this.width = 208;
        this.image = image;
    }
    
    // Anfang Methoden
    public void drawButton(Graphics g){
        g.setColor(borderColor);
        g.fillRect(x-3,y-3,width+6,height+6);
        g.setColor(fillColor);
        g.fillRect(x,y,width,height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Impact", Font.PLAIN, fontSize));
        g.drawString(text, x, y+fontSize);
    }
    
    public void drawButtonImage(Graphics g){
        if (!hide) {
            g.drawImage(image, x, y, width, height, null);
        } // end of if
        else{
            g.drawImage(image, -x, -y, width, height, null);
        }
    }
    
    public boolean checkButtonClicked(int xC, int yC){
        if (!hide) {
            int x2 = width + x;
            int y2 = height + y;
            if (xC > x && yC > y && xC < x2 && yC < y2) {
                System.out.println("LogSystem - Button");
                return true;
            } // end of if
            else{
                System.out.println("LogSystem - NoButton");
                return false;
            }
        } // end of if
        else{
            return false;
        }
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setHide(boolean hide) {
        this.hide = hide;
    }
    
    // Ende Methoden
} // end of Button
