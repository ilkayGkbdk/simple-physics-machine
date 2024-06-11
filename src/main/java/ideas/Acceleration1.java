package ideas;

import main.WindowPanel;

import java.awt.*;

public class Acceleration1 {

    WindowPanel wp;

    public double x, y;
    public int width, height;

    public double startSpeed;
    public double speed;
    public double a;
    public double t;
    public double d;
    public int m;
    public boolean calculated = false;

    public int counter = 0;

    public String direction;
    public boolean stop = false;

    public Acceleration1(WindowPanel wp){
        this.wp = wp;

        x = ((double) wp.screenWidth / 2) - ((double) wp.tileSize / 2);
        y = ((double) wp.screenHeight / 2) - ((double) wp.tileSize / 2);
        width = wp.tileSize;
        height = wp.tileSize;

        startSpeed = 5;
        speed = startSpeed;
        a = 1;
        t = 0;
        d = 0;
        m = 10;

        direction = "right";
    }

    public void update(){
        if (!stop){
            counter++;

            if (direction.equals("right")){
                x += speed / 6;
            }
            if (direction.equals("left")){
                x -= speed / 6;
            }

            if (x + width + 5 > wp.screenWidth){
                direction = "left";
                speed = calc(startSpeed, a, t);
                a *= -1;
            }

            if (counter == 6){
                counter = 0;
                t++;
                speed += a;
                d += Math.abs(speed) / 6;
            }


            if (speed <= 0){
                stop = true;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.setStroke(new BasicStroke(5f));

        g2.setColor(Color.DARK_GRAY);
        g2.drawRect((int) (x - 2), (int) (y - 2), width + 10, height + 10);

        g2.setColor(Color.WHITE);
        g2.drawRect((int) x - 5, (int) y - 5, width + 10, height + 10);
    }

    public double calc(double v0, double a0, double t){
        double v1 = v0 + (a0 * t);
        return v1;
    }
}
