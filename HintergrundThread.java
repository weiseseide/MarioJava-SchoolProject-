import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Toolkit;
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 15.01.2015
  * @author Michael Trubizkij & Denis Vukomanovic
  */

public class HintergrundThread extends Thread {
    
    // Anfang Attribute
    Dimension screenSize           = Toolkit.getDefaultToolkit().getScreenSize();
    private int frameWidth         = (int)screenSize.getWidth();
    private int frameHeight        = (int)screenSize.getHeight();
    private int picPositionWidth1  = 0;
    private int picPositionWidth2  = frameWidth;
    Image hintergrund1             = Toolkit.getDefaultToolkit().getImage("Graphics//Backgrounds//background.png"); 
    Image hintergrund2             = Toolkit.getDefaultToolkit().getImage("Graphics//Backgrounds//background.png");
    Image overlayGameBG    = Toolkit.getDefaultToolkit().getImage("Graphics//Backgrounds//BgTextureStandart.png");
    Image lavaBackGround           = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//lava2.gif");  
    Image lavaGround1              = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//lava2.gif"); 
    Image lavaGround2              = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//lava2.gif"); 
    Image lavaGround3              = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//lava2.gif"); 
    Image lavaGround4              = Toolkit.getDefaultToolkit().getImage("Graphics//Ground//lava2.gif"); 
    private int pixelRunSpeed = 1;
    
    // Ende Attribute
    
    // Anfang Methoden
    
    public void drawBackground(Graphics g){
        //try{Thread.sleep(5);}catch(InterruptedException e){System.out.println(e);}  
        g.drawImage(lavaBackGround , 0,0,frameWidth, frameHeight, null);
        // g.drawImage(overlayGameBG, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(hintergrund1, picPositionWidth1, 0             , frameWidth, frameHeight, null);
        g.drawImage(hintergrund2, picPositionWidth2, 0             , frameWidth, frameHeight, null);
        
        g.drawImage(lavaGround1,  picPositionWidth1, frameHeight-70, frameWidth/2, 214, null);     
        g.drawImage(lavaGround2,  picPositionWidth1 + frameWidth/2, frameHeight-70, frameWidth/2, 214, null);
        
        g.drawImage(lavaGround1,  picPositionWidth2, frameHeight-70, frameWidth/2, 214, null);     
        g.drawImage(lavaGround2,  picPositionWidth2 + frameWidth/2, frameHeight-70, frameWidth/2, 214, null);
        
        changePosPictures();
    }
    
    private void changePosPictures(){
        if (picPositionWidth1 > (-1)*(frameWidth)) {
            picPositionWidth1  = picPositionWidth1  - pixelRunSpeed;
        }
        if(picPositionWidth2 > (-1)*(frameWidth)){
            picPositionWidth2  = picPositionWidth2  - pixelRunSpeed;
        }
        
        if (picPositionWidth1 == (-frameWidth)) {
            picPositionWidth1  = frameWidth;
        }
        if(picPositionWidth2 == (-frameWidth)){
            picPositionWidth2  = frameWidth;
        }
    }
    
    public void setPixelRunSpeed(int pixelRunSpeed) {
        this.pixelRunSpeed = pixelRunSpeed;
    }
    
    // Ende Methoden
} // end of HintergrundThread
