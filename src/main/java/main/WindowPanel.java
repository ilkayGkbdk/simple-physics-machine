package main;

import ideas.Acceleration1;
import ideas.Circle_A;
import ideas.Circle_B;
import ideas.Objects;

import javax.swing.*;
import java.awt.*;

public class WindowPanel extends JPanel implements Runnable{

    private final int originalTileSize = 15;
    private final int scale = 5;

    public final int tileSize = scale * originalTileSize;
    public final int maxCol = 16;
    public final int maxRow = 9;

    public final int screenWidth = tileSize * maxCol;
    public final int screenHeight = tileSize * maxRow;

    //FPS
    public final int FPS = 60;
    public float currentFPS = 0;

    //STATES
    public int state;
    public final int selectionState = 1;
    public final int rectangleState = 2;
    public final int circleCollState = 3;


    //SYSTEM
    public boolean runEngine = false;
    public boolean freeze = true;

    // SYSTEM
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread engineThread;

    //CLASSES
    public Objects[] obj = new Objects[10];
    public Acceleration1 acc1 = new Acceleration1(this);


    public WindowPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(keyH);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void setupEngine(){
        state = selectionState;
        aSetter.setCircles();
    }

    public void reset(){
        aSetter.setCircles();
        for (Objects objects : obj) {
            if (objects != null) {
                objects.setDefault();
            }
        }
    }

    public void startEngine(){
        engineThread = new Thread(this);
        engineThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        float frames = 0;

        while (engineThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }

            if (timer >= 1000000000) {
                currentFPS = frames;
                timer = 0;
                frames = 0;
            }
        }
    }

    public void update(){
        if (runEngine && !freeze){
            // rectangle acceleration state
            if (state == rectangleState){
                acc1.update();
            }

            // circle collision state
            if (state == circleCollState){
                for (int i = 0; i < obj.length; i++){
                    if (obj[i] != null){
                        obj[i].update();
                    }
                }
            }

        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (runEngine){
            if (state == rectangleState){
                acc1.draw(g2);
            }

            if (state == circleCollState){
                for (int i = 0; i < obj.length; i++){
                    if (obj[i] != null){
                        obj[i].draw(g2);
                    }
                }
            }
        }
        else {
            acc1.draw(g2);
            for (int i = 0; i < obj.length; i++){
                if (obj[i] != null){
                    obj[i].draw(g2);
                }
            }
        }

        ui.draw(g2);

        g2.dispose();
    }
}
