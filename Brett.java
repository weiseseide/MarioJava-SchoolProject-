import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 08.12.2014
  * @Michail Trubizkij & Denis Vukomanovic 
  */

public class Brett extends JPanel implements ActionListener{
    
    // Anfang Attribute
    private FileWriter writer;
    private File saves;
    private Timer timer;
    private Player mario;
    private Player luigi;
    private HintergrundThread background;
    Dimension screenSize          = Toolkit.getDefaultToolkit().getScreenSize();
    private int frameWidth        = (int)screenSize.getWidth();
    private int frameHeight       = (int)screenSize.getHeight();
    private boolean ingame        = false;
    private boolean menue         = true;
    private int message           = 0;
    private Image title           = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//title2.png");
    private Image pauseGameBG     = Toolkit.getDefaultToolkit().getImage("Graphics//Backgrounds//BgTexturePause.png");
    private Image overlayGameBG   = Toolkit.getDefaultToolkit().getImage("Graphics//Backgrounds//BgTextureStandart.png");
    private Image highscorePic    = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//Scoreboard.png"); 
    private Image gameOverPic     = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gameOver.png");
    private Image marioWinsPic    = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//mario_win.png");
    private Image luigiWinsPic    = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//luigi_win.png");
    private int lastPlatformY     = frameHeight / 2;
    private int maxPlatforms      = ((frameWidth + 320) / 160) / 2;
    private int noPlatformsPlace  = ((maxPlatforms * 160) - frameWidth) / maxPlatforms ;
    int platformRandDistance      = 0;
    private Platform[] startPlat  = new Platform[(int)(frameWidth/160)];
    private Platform[] platforms  = new Platform[maxPlatforms];
    private int distances         = 0;
    private int highscore         = 0;
    private int level             = 0;
    private boolean firstStart    = true;
    private boolean startStopGame = true;
    private boolean showOptions   = false;
    private boolean onePlayer     = true;
    private boolean musicOn       = true;
    private boolean marioWins     = true;
    private boolean gameOver      = false;
    private Clip bgMusic;
    private int lastPlatSpeed     = -2;
    
    /*
    Das Prinzip von Messages ist Simpel. Dies habe ich mir etwas bei Microsoft abgeschaut.
    Jede Zahl wird ihre eigene Bedeutung haben und auf etwas eine Auswirkung haben.
    Folgende Liste zeigt die Zahl + Bedeutung
    999 = Exit Game
    1 = StartGame
    2 = Pause / Fortsetzen
    3 = Optionen
    4 = Musik an/aus
    5 = Spieler 1/2
    6 = Zurück ins Hauptmenü
    */
    /***** Buttons ink. Bild *****/
    private Button startGameBt;
    private Image startGameBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//startBt.png");
    
    private Button exitGameBt;
    private Image exitGameBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//exitBt.png");
    
    private Button pauseOrContinueGameBt;
    private Image pauseGameBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//pauseBt.png");
    private Image continueGameBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//continueBt.png");
    
    private Button optionsGameBt;
    private Image optionsGameBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//optionsBt.png");
    
    private Button musicOnOffBt;
    private Image musicOnBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//musicOnBt.png");
    private Image musicOffBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//musicOffBt.png");
    
    private Button playersCountBt;
    private Image onePlayerBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//singlePlayerBt.png");
    private Image twoPlayerBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//doublePlayerBt.png");
    
    private Button backToMenueBt;
    private Image backToMenueBtPic = Toolkit.getDefaultToolkit().getImage("Graphics//Menu//gamebt//mainMenuBt.png");
    
    // Ende Attribute
    public Brett(){
        this.setBackground(Color.RED);
        timer = new Timer(10, this);
        
        background = new HintergrundThread();
        background.start();
        
        startGameBt = new Button((frameWidth / 2)-220, 475, startGameBtPic); 
        startGameBt.start();
        
        exitGameBt = new Button((frameWidth / 2) + 20,475, exitGameBtPic); 
        exitGameBt.start();
        
        pauseOrContinueGameBt = new Button(frameWidth -220, 30, pauseGameBtPic);
        pauseOrContinueGameBt.start();
        
        optionsGameBt = new Button((frameWidth / 2)-220, 475+80, optionsGameBtPic);
        optionsGameBt.start();
        /*Unter Options*/
        
        musicOnOffBt = new Button((frameWidth / 2)-220, 475, musicOnBtPic);
        musicOnOffBt.start();
        
        playersCountBt = new Button((frameWidth / 2) + 20,475, onePlayerBtPic);
        playersCountBt.start();
        
        backToMenueBt = new Button((frameWidth / 2)-220, 475 + 80, backToMenueBtPic);
        backToMenueBt.start();
        
        try {
            saves = new File("Saves//saves.txt");
            if (saves.exists()) {
                writer = new FileWriter(saves ,true);
            }
            else{
                writer = new FileWriter(saves ,false);
                writer.write("/Saves - You should not see this :P");
            }
        } catch(Exception e) {
            System.out.println("LogSystem - Error creating Saves File");
            e.printStackTrace();
        }
        
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Graphics//audio//mario_loop.wav"));
            bgMusic = AudioSystem.getClip();
            bgMusic.open(inputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        
        
        
        this.addKeyListener(new TastaturLauscher());
        this.addMouseListener(new MausLauscher());
        this.setFocusable(true); 
        timer.start();
    }
    
    // Anfang Methoden
    public void paintComponent(Graphics g){ //ToDo verteilung auf menüMethode
        super.paintComponent(g);
        background.drawBackground(g);    
        if (menue == true) {   
            g.drawImage(title, (frameWidth / 2)- 351 , 175 , null);
            backToMenueBt.setY(475 + 80);
            startGameBt.setHide(false);
            exitGameBt.setHide(false);
            optionsGameBt.setHide(false);
            pauseOrContinueGameBt.setHide(true);
            musicOnOffBt.setHide(true);
            playersCountBt.setHide(true);
            backToMenueBt.setHide(true); 
        } // end of if
        if (showOptions == true) { 
            startGameBt.setHide(true);
            exitGameBt.setHide(true);
            optionsGameBt.setHide(true);
            pauseOrContinueGameBt.setHide(true);
            optionsGameBt.setHide(true);
            g.drawImage(title, (frameWidth / 2)- 351 , 175 , null);
            musicOnOffBt.setHide(false);
            playersCountBt.setHide(false);
            backToMenueBt.setHide(false); 
            backToMenueBt.setY(475 + 80);
            if(onePlayer == true){
                playersCountBt.setImage(onePlayerBtPic);
            }
            else{
                playersCountBt.setImage(twoPlayerBtPic);
            }
            if(musicOn == true){
                musicOnOffBt.setImage(musicOnBtPic);
            }
            else{
                musicOnOffBt.setImage(musicOffBtPic);
            }
        } // end of if
        if (ingame == true) {
            mario.drawPlayer(g);
            if (!onePlayer) {
                luigi.drawPlayer(g);
            } // end of if
            g.setColor(Color.BLACK);
            g.drawImage(highscorePic,85, 30, 108, 78, null);
            g.setFont(new Font("Impact", Font.PLAIN, 70));
            g.drawString(""+ highscore, 105,98);
            backToMenueBt.setY(475);
            generatePlatforms(g);   
            if (startStopGame == false) {
                g.drawImage(pauseGameBG, 0, 0, frameWidth, frameHeight, null);
                pauseOrContinueGameBt.setImage(continueGameBtPic); 
                exitGameBt.setHide(false);
                backToMenueBt.setHide(false);              
            }
            else {
                pauseOrContinueGameBt.setImage(pauseGameBtPic);
                exitGameBt.setHide(true);
                backToMenueBt.setHide(true);
            }
            if (gameOver == true) {
                g.drawImage(pauseGameBG, 0, 0, frameWidth, frameHeight, null);
                g.drawImage(gameOverPic, (frameWidth/2)-(640/2), (frameHeight/2)-480, 640, 480, null);  
                if (onePlayer) {
                    g.setColor(Color.RED);
                    g.drawString("Highscore: " + highscore, (frameWidth/2)-(640/2) + 123, (frameHeight/2)-97);
                    g.setColor(Color.BLACK);
                    g.drawString("Highscore: " + highscore, (frameWidth/2)-(640/2) + 120, (frameHeight/2)-100);
                } // end of if
                else{
                    if (marioWins) {
                        g.drawImage(marioWinsPic, (frameWidth/2)-(640/2) + 90, (frameHeight/2)-300, null); 
                    } // end of if
                    else{
                        g.drawImage(luigiWinsPic, (frameWidth/2)-(640/2) + 90, (frameHeight/2)-300, null); 
                    }
                }
                pauseOrContinueGameBt.setHide(true);
                backToMenueBt.setHide(false);
                exitGameBt.setHide(false);
            } // end of if
            else{
                pauseOrContinueGameBt.setHide(false);
            }
            optionsGameBt.setHide(true);
            startGameBt.setHide(true);
            musicOnOffBt.setHide(true);
            playersCountBt.setHide(true);
        } // end of if-else
        /*Draw All Buttons*/
        startGameBt.drawButtonImage(g); 
        exitGameBt.drawButtonImage(g);
        pauseOrContinueGameBt.drawButtonImage(g);
        optionsGameBt.drawButtonImage(g);
        musicOnOffBt.drawButtonImage(g);
        playersCountBt.drawButtonImage(g);
        backToMenueBt.drawButtonImage(g);
    } // end of if
    
    
    public void actionPerformed(ActionEvent e){   
        if (ingame == true) {
            mario.move();
            mario.collisionDetection(platforms);
            if (!onePlayer) {
                luigi.move();
                luigi.collisionDetection(platforms);
            } // end of if
            if (firstStart == true) {
                mario.collisionDetection(startPlat);
                if (!onePlayer) {
                    luigi.collisionDetection(startPlat);
                } // end of if 
            } 
            if (onePlayer) {
                if(mario.isDead()){
                    try {
                        writer.write(System.getProperty("line.separator"));
                        writer.write("h"+ highscore);
                    } catch(Exception e2) {
                        System.out.println("LogSystem - Error save HighScore in Saves File");
                        e2.printStackTrace();
                    }
                    stopGame();
                    gameOver = true;
                } 
            } 
            else{
                if(mario.isDead() && gameOver == false){ //ToDo Winner Anzeigen
                    marioWins = false;
                    try {
                        writer.write(System.getProperty("line.separator"));
                        writer.write("Luigi Wins");
                    } catch(Exception e2) {
                        System.out.println("LogSystem - Error save HighScore in Saves File");
                        e2.printStackTrace();
                    }
                    stopGame();
                    gameOver = true;
                } 
                if(luigi.isDead() && gameOver == false){
                    marioWins = true;
                    try {
                        writer.write(System.getProperty("line.separator"));
                        writer.write("Mario Wins");
                    } catch(Exception e2) {
                        System.out.println("LogSystem - Error save HighScore in Saves File");
                        e2.printStackTrace();
                    }
                    stopGame();
                    gameOver = true;
                } 
            }
            if (message == 2) {
                if (startStopGame == true) {
                    startStopGame = false;
                    stopGame();
                } // end of if
                else{
                    startStopGame = true;
                    continueGame();
                }
                resetMessage();
            } // end of if
        } // end of if
        
        if (musicOn) {
            if (!bgMusic.isRunning())
            {
                bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } 
        } // end of if
        else if(!musicOn){
            bgMusic.stop();
        } // end of if-else
        
        if (message == 999) {
            try {
                writer.flush();
                writer.close();
            } catch(Exception e2) {
                System.out.println("LogSystem - Error closing Saves File");
                e2.printStackTrace();
            }
            resetMessage();
            System.exit(0);
        } // end of if
        
        if (message == 1) {
            menue = false;
            ingame = true;
            startGame();
            resetMessage();
        } // end of if
        
        if (message == 3) {
            menue = false;
            showOptions = true;
            resetMessage();
        } // end of if
        
        if (message == 4) {
            musicOn = !musicOn;
            resetMessage();
        } // end of if
        
        if (message == 5) {
            onePlayer = !onePlayer;
            resetMessage();
        } // end of if 
        
        if (message == 6) {
            menue = true;
            showOptions = false;
            if (ingame == true) {
                deleteGame();
            } // end of if
            resetMessage();
        } // end of if  
        
        repaint();
    }
    
    public void startGame(){
        mario      = new Player(1);
        firstStart = true;
        gameOver   = false;
        lastPlatSpeed = -2;
        mario.start();
        if (!onePlayer) {
            luigi = new Player(2);
            luigi.start();
        } // end of if
        
        for(int i = 0;i < (int)(frameWidth/160); i++){
            startPlat[i]         = new Platform(lastPlatformY, distances-frameWidth);
            startPlat[i].start(); 
            distances = distances + 160;
            //lastPlatformY = lastPlatformY + 10;
        }
        
        distances = 0;
        
        for(int i = 0;i < maxPlatforms; i++){
            platformRandDistance = (int)(Math.random() * 80 + 1);
            platforms[i]         = new Platform(lastPlatformY, distances + platformRandDistance);
            platforms[i].start();
            distances = distances + 320;
            //lastPlatformY = lastPlatformY + 10;
        }
        continueGame();
    }
    
    public void deleteGame(){
        mario         = null;
        luigi         = null;
        menue         = true;
        ingame        = false;
        showOptions   = false;
        startStopGame = true;
        distances     = 0;
        firstStart    = false;
        highscore     = 0;
        lastPlatformY = frameHeight / 2; 
        level         = 0; 
        
        for(int i = 0;i < (int)(frameWidth/160); i++){
            startPlat[i] = null;
        }
        
        for(int i = 0;i < maxPlatforms; i++){
            platforms[i] = null;
        }
    }
    
    public void stopGame(){
        mario.setSpeed(0);
        mario.setLeftSpeed(0);
        mario.setJumpHeight(0);
        mario.setGravity(0);
        lastPlatSpeed = platforms[1].getSpeed();
        if (!onePlayer) {
            luigi.setSpeed(0);
            luigi.setLeftSpeed(0);
            luigi.setJumpHeight(0);
            luigi.setGravity(0);
        } // end of if
        
        background.setPixelRunSpeed(0);
        
        for(int i = 0;i < maxPlatforms; i++){
            platforms[i].setspeed(0);
        }
        
        for(int i = 0;i < (int)(frameWidth/160); i++){
            if (startPlat[i] != null) {
                startPlat[i].setspeed(0);
            } // end of if
        }
    }
    
    public void continueGame(){
        mario.setSpeed(3);
        mario.setLeftSpeed(1);
        mario.setJumpHeight(-80);
        mario.setGravity(1);
        
        if (!onePlayer) {
            luigi.setSpeed(3);
            luigi.setLeftSpeed(1);
            luigi.setJumpHeight(-80);
            luigi.setGravity(1);
        } // end of if
        
        background.setPixelRunSpeed(1);
        
        for(int i = 0;i < maxPlatforms; i++){
            platforms[i].setspeed(lastPlatSpeed);
        }
        
        for(int i = 0;i < (int)(frameWidth/160); i++){
            if (startPlat[i] != null) {
                startPlat[i].setspeed(-2);
            } // end of if
        }
    }
    
    void generatePlatforms(Graphics g){
        for(Platform p: startPlat ){
            if (p != null) {
                p.drawPlatform(g);
                if (p.getXPos() < -160) {
                    p = null;               
                } // end of if
            } // end of if
        } 
        for(Platform p: platforms ){
            if (p != null) { //ToDo for schleife soll array von platformen malen. anzahl plattformen(array) = screenwith / längeplatform. dürfen nicht um playersize an rand von bilschirm generiert werden
                p.drawPlatform(g);
                if (p.getXPos() < -160) {
                    firstStart = false;
                    p.setXPos(lastPlatformY);//ToDo + Zufallszahl zwischen 50  wenn gesamte höhe player über wert von playersize - screenheigh dann neu generieren
                    
                    //lastPlatformY = lastPlatformY - 10;
                    
                    p.setYPos(lastPlatformY);
                    p.reset();
                    highscore++; 
                    
                    if (highscore > 10*level ) {
                        for(Platform p2: platforms ){
                            int speed = p2.getSpeed() - 1;
                            p2.setspeed(speed);
                        }
                    } // end of if    
                    
                    if (highscore < 10) {
                        level = 1;
                    } // end of if
                    else if(highscore < 20 && highscore > 10){
                        level = 2;
                    } // end of if-else
                    else if(highscore < 30 && highscore > 20){
                        level = 3;
                    } // end of if-else
                    else if(highscore < 40 && highscore > 30){
                        level = 4;
                    } // end of if-else
                    
                } // end of if
            } // end of if
        }  
        
    }
    
    public int getFrameWidth() {
        return frameWidth;
    }
    
    public int getFrameHeight() {
        return frameHeight;
    }
    
    public void resetMessage(){
        message = 0;
    }
    
    // Ende Methoden
    /*************************** Innere Klasse Tastaturlauscher ***************************/
    private class TastaturLauscher implements KeyListener{
        //Anfang Attribute
        
        //Ende Attribute
        //Anfang Methoden
        public void  keyReleased(KeyEvent ke){
            if (mario != null) {
                mario.keyReleased(ke);
            } // end of if
            if (luigi != null) {
                luigi.keyReleased(ke);
            } // end of if
        }
        
        public void  keyPressed(KeyEvent ke){
            if (mario != null) {
                mario.keyPressed(ke);
            } // end of if
            if (luigi != null) {
                luigi.keyPressed(ke);
            } // end of if
        }
        
        public void  keyTyped(KeyEvent ke){
            
        }
        //Ende Methoden
    } 
    
    
    private class MausLauscher implements MouseListener{
        //Anfang Attribute
        
        //Ende Attribute
        //Anfang Methoden
        public void mouseClicked(MouseEvent e){
            System.out.println("LogSystem - Mouse Clicked");
            if (e.getID() == e.MOUSE_CLICKED) {
                if (startGameBt.checkButtonClicked(e.getX(), e.getY()) == true) {
                    message = 1;
                    System.out.println("LogSystem - StartGame Message");
                } // end of if
                
                if (exitGameBt.checkButtonClicked(e.getX(), e.getY()) == true) {
                    message = 999;
                    System.out.println("LogSystem - ExitGame Message");
                } // end of if
                
                if (pauseOrContinueGameBt.checkButtonClicked(e.getX(), e.getY())) {
                    message = 2;
                    System.out.println("LogSystem - Pause/ContinueGame Message");
                } // end of if
                
                if (optionsGameBt.checkButtonClicked(e.getX(), e.getY())) {
                    message = 3;
                    System.out.println("LogSystem - OptionsGame Message");
                } // end of if
                
                if (musicOnOffBt.checkButtonClicked(e.getX(), e.getY())) {
                    message = 4;
                    System.out.println("LogSystem - MusicFX On/Off Message");
                } // end of if
                
                if (playersCountBt.checkButtonClicked(e.getX(), e.getY())) {
                    message = 5;
                    System.out.println("LogSystem - Players 1/2 Message");
                } // end of if
                
                if (backToMenueBt.checkButtonClicked(e.getX(), e.getY())) {
                    message = 6;
                    System.out.println("LogSystem - Back to Menue Message");
                } // end of if
            } // end of if  
        } 
        
        public void mouseExited(MouseEvent e) {
            
        }  
        public void mouseEntered(MouseEvent e) {
            
        }   
        public void mouseReleased(MouseEvent e) {
            
        }  
        public void mousePressed(MouseEvent e) {
            
        }    
        //Ende Methoden
    } 
    
}