import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Toolkit;  
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 08.12.2014
  * @Michail Trubizkij & Denis Vukomanovic  
  */

public class SpielGUI extends JFrame {
    // Anfang Attribute
    Brett brett = new Brett();
    private static String gameName = "Italo-Runner";
    // Ende Attribute
    
    public SpielGUI(String title) { 
        // Frame-Initialisierung
        super(title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(brett.getFrameWidth(), brett.getFrameHeight());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0, 0);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        Container cp = getContentPane();
        cp.add(brett);
        
        setVisible(true);
        
        // Anfang Komponenten
        
        // Ende Komponenten
        
    } // end of public SpielGUI
    
    // Anfang Methoden
    
    
    // Ende Methoden
    
    public static void main(String[] args) {
        new SpielGUI(gameName);
    } // end of main
    
} // end of class SpielGUI
