import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 12.01.2015
  * @Michail Trubizkij & Denis Vukomanovic 
  */

public class Platform extends Thread{     
    
    // Anfang Attribute
    private int xPos;
    private int yPos;
    private Image platformPic = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//platform_long.png");
    private int speed;
    Dimension screenSize      = Toolkit.getDefaultToolkit().getScreenSize();
    private int frameWidth    = (int)screenSize.getWidth();
    // Ende Attribute
    
    public Platform(int yPos, int xPos) {
        this.xPos = frameWidth + xPos;
        this.yPos = yPos;
        this.speed = -2;
    }
    
    
    // Anfang Methoden
    public void drawPlatform(Graphics g){
        g.drawImage(platformPic, xPos, yPos, 160, 32, null);
        move();
    }
    
    public void move(){
        xPos = xPos + speed;
    } 
    
    public void reset(){
        setXPos(frameWidth + 100);
    }
    
    public int getXPos() {
        return xPos;
    }
    
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    
    public int getYPos() {
        return yPos;
    }
    
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setspeed(int speed) {
        this.speed = speed;
    }
    
    
    // Ende Methoden
} // end of Roehre
