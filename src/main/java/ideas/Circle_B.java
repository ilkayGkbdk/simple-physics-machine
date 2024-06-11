package ideas;

import main.WindowPanel;

import java.awt.*;

public class Circle_B extends Objects{

    WindowPanel wp;

    int counter = 0;

    public Circle_B(WindowPanel wp) {
        super(wp);
        this.wp = wp;

        setDefault();
    }

    public void setDefault(){
        x_v0 = 0;
        y_v0 = 0;
        x_velocity = x_v0;
        y_velocity = y_v0;

        mass = 1;
        pathTaken = 0;
        time = 0;

        setSolid();
    }

    public void setSolid(){
        solidArea = new Rectangle();

        solidArea.x = (int) posX;
        solidArea.y = (int) posY;
        solidArea.width = wp.tileSize;
        solidArea.height = wp.tileSize;
    }

    public void update(){
        counter++;

        if (posX < 0 || posX + solidArea.width > wp.screenWidth){
            x_velocity *= -1;
        }
        if (posY < 0 || posY + solidArea.height > wp.screenHeight){
            y_velocity *= -1;
        }

        // 1 second means 60 frames, so every 10 frames we're going to increase position,
        // so we can handle this by velocity / (FPS / 10)
        posX += x_velocity / ((double) wp.FPS / 10);
        posY += y_velocity / ((double) wp.FPS / 10);

        /*
         * every 10 position x change we're going to add 1 meters to pathTaken,
         * so this means normally x=0 to x=10 means 10 meters but now x=0 to x=10
         * will be 1 meter. Basically we can handle this by 10 frames position change / 10
         */
        pathTaken += Math.abs((x_velocity / ((double) wp.FPS / 10)) / 10);

        if (counter >= wp.FPS){
            time++;
            counter = 0;
        }
    }

    public void contactCircle(int index){
        if (index != 999){
            Objects target = wp.obj[index];
            x_velocity = ((mass - target.mass) * x_velocity + 2 * target.mass * target.x_velocity) / (mass + target.mass);
        }
    }

    public void draw(Graphics2D g2){
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(3f));

        g2.setColor(new Color(5, 200, 50));

        g2.fillOval((int) posX, (int) posY, wp.tileSize, wp.tileSize);
    }
}
