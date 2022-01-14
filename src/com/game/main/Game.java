package com.game.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = -501003198964753032L;
    //1440x900 or 1280*720 or 1366x768

    public static final Dimension defaultSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static Dimension preferredSize = defaultSize;

    public Thread thread;
    private boolean running = false;

    private final MainMenu menu;
    private final SettingsMenu settings;
    private final HelpMenu help;
    private final MapGame map;
    private final PauseMenu pause;
    public static GameDialog gameDialog;
    public static Cutscene cutscene;
    private final Popup popup;

    Handler handler;

    private final BufferedImage image1, image2, image3, image4;

    public static Window win;

    public enum STATE {
        MainMenu,
        Settings,
        Help,
        MyMap,
        PauseMenu,
        Dialog,
        Cutscene,
        Popup
    }

    public STATE gameState = STATE.MainMenu;

    public Game() throws IOException {
        handler = new Handler(this, preferredSize);

        menu = new MainMenu(this, preferredSize);
        this.addMouseListener(menu);

        settings = new SettingsMenu(this, preferredSize);
        this.addMouseListener(settings);

        help = new HelpMenu(this, preferredSize);
        this.addMouseListener(help);

        map = new MapGame(this, preferredSize);
        this.addMouseListener(map);

        pause = new PauseMenu(this, preferredSize);
        this.addMouseListener(pause);

        gameDialog = new GameDialog(this, preferredSize);
        this.addMouseListener(gameDialog);

        cutscene = new Cutscene(this, preferredSize);
        this.addMouseListener(cutscene);

        popup = new Popup(this, preferredSize);
        this.addMouseListener(popup);

        File imgFile = new File("Images/Backgrounds/MainMenuBackground.jpg");
        image1 = ImageIO.read(imgFile);
        imgFile = new File("Images/Backgrounds/GameBackground.jpg");
        image2 = ImageIO.read(imgFile);
        imgFile = new File("Images/Backgrounds/SettingsMenu.jpg");
        image3 = ImageIO.read(imgFile);
        imgFile = new File("Images/Backgrounds/wew.jpg");
        image4 = ImageIO.read(imgFile);

        win = new Window(preferredSize, "Bürgermeister", this);
    }

    public boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x &&  mx < x + width){
            return my > y && my < y + height;
        }
        return false;
    }

    private void render() throws IOException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            //Recommended: 2
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        //Render default backgrounds
        if(gameState == STATE.MainMenu) {
            ImageDrawer.drawScaledImage(this.image1, win.getFrame(), g);
        }
        else if(gameState == STATE.MyMap){
            ImageDrawer.drawScaledImage(this.image2, win.getFrame(), g);
        }
        else if(gameState == STATE.Settings || gameState == STATE.Help){
            ImageDrawer.drawScaledImage(this.image3, win.getFrame(), g);
        }
        else if(gameState == STATE.Cutscene){
            cutscene.render(g);
        }

        //Render elements
        if(gameState == STATE.MainMenu){
            menu.render(g);
        }
        else if(gameState == STATE.Settings){
            settings.render(g);
        }
        else if(gameState == STATE.Help){
            help.render(g);
        }
        else if(gameState == STATE.MyMap){
            map.render(g);
        }
        else if(gameState == STATE.PauseMenu){
            pause.render(g);
        }
        else if(gameState == STATE.Dialog){
            gameDialog.render(g);
        }
        if(gameState == STATE.Cutscene){
            cutscene.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                delta--;
            }
            if(running) {
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public static int strLength(Graphics g, String str){
        return g.getFontMetrics().stringWidth(str);
    }

    public static int strHeight(Graphics g){
        return g.getFontMetrics().getHeight();
    }
}
