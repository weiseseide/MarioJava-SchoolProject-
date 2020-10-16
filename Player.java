import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 08.12.2014
  * @Michail Trubizkij & Denis Vukomanovic  
  */

public class Player extends Thread {
    
    // Anfang Attribute
    private Image playerPic; 
    private Image playerFallingPic; 
    Dimension screenSize        = Toolkit.getDefaultToolkit().getScreenSize();
    private int frameWidth      = (int)screenSize.getWidth();
    private int frameHeight     = (int)screenSize.getHeight();
    private int gravity         = 1;
    private int jumpHeight      = -80;
    private boolean pWatchRight = true;
    private boolean wPressed    = false;// w = Up
    private boolean isInAir     = true;
    private int playerView      = 53;
    private int xPos;
    private int yPos;
    private int horizontalSpeed;
    private int verticalSpeed;
    private int speed;
    private int leftSpeed;
    private Platform lastPlatTouched; 
    private int playerID;
    private int j = 0;
    // Ende Attribute
    
    public Player(int playerID) {
        this.horizontalSpeed = 0;
        this.verticalSpeed   = 1;
        this.speed           = 3;
        this.leftSpeed       = 1;
        this.playerID        = playerID;
        this.yPos            = (frameHeight / 2) - 150;
        if (playerID == 1) {
            this.xPos        = (frameWidth  / 2) - 150;
            playerPic        = Toolkit.getDefaultToolkit().getImage("Graphics//Player//mario//mario1.gif");
            playerFallingPic = Toolkit.getDefaultToolkit().getImage("Graphics//Player//mario//marioDeath.gif");
        } // end of if
        else if(playerID == 2) {
            this.xPos        = (frameWidth  / 2) - 250;
            playerPic        = Toolkit.getDefaultToolkit().getImage("Graphics//Player//luigi//luigi1.gif");
            playerFallingPic = Toolkit.getDefaultToolkit().getImage("Graphics//Player//luigi//luigi_death.gif");
        } // end of if-else
    }
    
    // Anfang Methoden
    public void collisionDetection(Platform[] plat){
        for(int i = 0; i < plat.length; i++){ 
            if(plat[i].getYPos() < (this.yPos + 99) && plat[i].getYPos() > (this.yPos + 96) ){
                if (lastPlatTouched == null) {
                    lastPlatTouched = plat[i];
                } // end of if
                if (plat[i].getXPos() < (this.xPos + 40) && this.xPos < (plat[i].getXPos() + 160)) {
                    isInAir = false;
                    verticalSpeed = 0;
                    lastPlatTouched = plat[i];
                    if (i < plat.length-1) {
                        j = i + 1;
                    } // end of if
                    else{
                        j = 0;
                    }
                } // end of if
                else if ((this.xPos + 40) < lastPlatTouched.getXPos() || this.xPos > (lastPlatTouched.getXPos() + 160) ) {
                    isInAir = true;
                    verticalSpeed = gravity;
                } // end of if  
            } 
            
            else /*if(plat[i].getYPos() > (this.yPos + 99) || plat[i].getYPos() < (this.yPos + 96) )*/{
                verticalSpeed = gravity;
                isInAir = true;
            } // end of if-else
        }
    }  
    
    public void drawPlayer(Graphics g){
        if (yPos < (frameHeight / 2)+ 30) {
            if (pWatchRight == true) {  
                playerView = 53;
                g.drawImage(playerPic, xPos, yPos, playerView, 99, null);
            } 
            else{   
                playerView = -53;                                            
                g.drawImage(playerPic, xPos + 53, yPos, playerView, 99, null);  
            }
        } // end of if
        else {
            g.drawImage(playerFallingPic, xPos, yPos, 73, 99, null);
            if (isDead()) {
                gravity = 0;
            } // end of if-else
            else{
                gravity = 3;
            }
        }
    }
    
    public boolean keyPressed(KeyEvent ke){
        int taste = ke.getKeyCode();
        if (playerID == 1) {
            if(taste == KeyEvent.VK_LEFT){
                horizontalSpeed = -speed - leftSpeed;//damit er schneller nach links läuft
                pWatchRight = false;
            }
            if(taste == KeyEvent.VK_RIGHT){
                horizontalSpeed = speed;
                pWatchRight = true;
            }
            if(taste == KeyEvent.VK_UP){
                if (!wPressed) {
                    if (!isInAir) {
                        wPressed = true;
                        verticalSpeed = jumpHeight;
                    } // end of if
                } // end of if
            } 
            return true;
        } // end of if
        else{
            if(taste == KeyEvent.VK_A){
                horizontalSpeed = -speed - leftSpeed;//damit er schneller nach links läuft
                pWatchRight = false;
            }
            if(taste == KeyEvent.VK_D){
                horizontalSpeed = speed;
                pWatchRight = true;
            }
            if(taste == KeyEvent.VK_W){
                if (!wPressed) {
                    if (!isInAir) {
                        wPressed = true;
                        verticalSpeed = jumpHeight;
                    } // end of if
                } // end of if
            } 
            return true;
        }
    }
    
    public void keyReleased(KeyEvent ke){ 
        int taste = ke.getKeyCode();
        if (playerID == 1) {
            if(taste == KeyEvent.VK_LEFT){
                horizontalSpeed = 0;
            }
            else if(taste == KeyEvent.VK_RIGHT){
                horizontalSpeed = 0;
            }
            else if(taste == KeyEvent.VK_UP){
                if (wPressed) {
                    verticalSpeed = gravity;
                    horizontalSpeed = 0; 
                    wPressed = false;
                } // end of if            
            }           
        }
        else{
            if(taste == KeyEvent.VK_A){
                horizontalSpeed = 0;
            }
            else if(taste == KeyEvent.VK_D){
                horizontalSpeed = 0;
            }
            else if(taste == KeyEvent.VK_W){
                if (wPressed) {
                    verticalSpeed = gravity;
                    horizontalSpeed = 0; 
                    wPressed = false;
                } // end of if            
            }    
        }
    }
    
    public boolean isDead(){
        if (yPos > frameHeight-99) {
            return true;
        } // end of if
        else {
            return false;
        } // end of if-else
    }
    
    public void move(){
        xPos = xPos + horizontalSpeed;
        yPos = yPos + verticalSpeed;
    }
    
    public int getXPos(){
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
    
    
    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }
    
    
    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }
    
    
    public int getVerticalSpeed() {
        return verticalSpeed;
    }
    
    
    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setLeftSpeed(int leftSpeed) {
        this.leftSpeed = leftSpeed;
    }
    
    public void setLastPlatTouched(Platform lastPlatTouched) {
        this.lastPlatTouched = lastPlatTouched;
    }
    
    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
    
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    
    // Ende Methoden
} // end of Flugzeug
