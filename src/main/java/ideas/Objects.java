package ideas;

import main.WindowPanel;

import java.awt.*;
import java.util.Map;

public class Objects {

    WindowPanel wp;

    //PHYSIC PART
    public double posX, posY;
    public double x_v0, y_v0;
    public double x_velocity, y_velocity;
    public int mass, time;
    public double pathTaken;

    public final double gravity = 9.81;

    public int collCounter = 0;

    //ENTITY PART
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    Map<String, Integer> RGB;

    public Objects(WindowPanel wp){
        this.wp = wp;
    }

    public void setDefault() {}

    public void update() {}

    public void draw(Graphics2D g2) {}
}
