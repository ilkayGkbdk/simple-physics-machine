package main;

import java.awt.*;

public class UI {

    WindowPanel wp;
    Graphics2D g2;

    public int commandNum = 0;

    public UI (WindowPanel wp){
        this.wp = wp;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 15));

        if (wp.state == wp.selectionState){
            drawSelectionState();
        }

        if (wp.state == wp.rectangleState){
            drawRectangleState();
        }

        if (wp.state == wp.circleCollState){
            drawCircleCollState();
        }
    }

    public void drawSelectionState(){
        int x = wp.tileSize * 4;
        int y = wp.tileSize * 2;
        int width = wp.tileSize * 8;
        int height = wp.tileSize * 5;

        drawSubWindow(x, y, width, height);

        String text = "CHOOSE IDEA";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));

        x = getX_CenteredText(text);
        y += wp.tileSize;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        text = "Rectangle Acceleration";
        x = wp.tileSize * 5;
        y += wp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x - wp.tileSize / 2, y);
        }

        text = "Circle Collision";
        y += (int) (wp.tileSize * 0.7);
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x - wp.tileSize / 2, y);
        }

        text = "Press enter to action";
        x = getX_CenteredText(text);
        y += (int) (wp.tileSize * 1.3);
        g2.drawString(text, x, y);
    }

    public void drawRectangleState(){
        String text1 = "Speed: " + wp.acc1.speed + "m/s Start Speed(V0): " + wp.acc1.startSpeed + "m/s";
        String text2 = "Acceleration: " + wp.acc1.a + "m/s^2, t: " + wp.acc1.t + "s";
        String text3 = "d: " + wp.acc1.d + "m";
        String text4 = "X: " + wp.acc1.x + ", Y: " + wp.acc1.y;

        int x = wp.tileSize / 3;
        int y = wp.screenHeight - (wp.tileSize / 3);
        int gap = 20;

        g2.drawString(text4, x, y);
        y -= gap;

        g2.drawString(text3, x, y);
        y -= gap;

        g2.drawString(text2, x, y);
        y -= gap;

        g2.drawString(text1, x, y);

        y = wp.tileSize / 3;
        g2.drawString("FPS: " + wp.currentFPS, x, y);
    }

    public void drawCircleCollState(){
        int i = 0;

        String text1 = "Velocity: X " + wp.obj[i].x_velocity + " m/s   Y " + wp.obj[i].y_velocity + " m/s";
        String text2 = "V0: " + wp.obj[i].x_v0 + " m/s" + "    Time: " + wp.obj[i].time + " seconds";
        String text3 = "Path Taken: " + (int) wp.obj[i].pathTaken + " meters";
        String text4 = "X: " + (int) wp.obj[i].posX + ", Y: " + wp.obj[i].posY;

        int x = wp.tileSize / 3;
        int y = wp.screenHeight - (wp.tileSize / 3);
        int gap = 20;

        g2.drawString(text4, x, y);
        y -= gap;

        g2.drawString(text3, x, y);
        y -= gap;

        g2.drawString(text2, x, y);
        y -= gap;

        g2.drawString(text1, x, y);

        y = wp.tileSize / 3;
        g2.drawString("FPS: " + wp.currentFPS, x, y);

        i++;

        text1 = "Velocity: X " + wp.obj[i].x_velocity + " m/s     Y " + wp.obj[i].y_velocity + " m/s";
        text2 = "V0: " + wp.obj[i].x_v0 + " m/s" + "    Time: " + wp.obj[i].time + " seconds";
        text3 = "Path Taken: " + (int) wp.obj[i].pathTaken + " meters";
        text4 = "X: " + (int) wp.obj[i].posX + ", Y: " + wp.obj[i].posY;

        x = getAlignToRightTextX(text2, wp.screenWidth - wp.tileSize / 3);
        y = wp.screenHeight - (wp.tileSize / 3);

        g2.drawString(text4, x, y);
        y -= gap;

        g2.drawString(text3, x, y);
        y -= gap;

        g2.drawString(text2, x, y);
        y -= gap;

        g2.drawString(text1, x, y);

        text1 = "Collision Count: " + wp.obj[0].collCounter;
        x = getX_CenteredText(text1);
        y = wp.screenHeight - (wp.tileSize / 3);

        g2.drawString(text1, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height){
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(x, y, width, height);

        g2.setStroke(new BasicStroke(5f));
        g2.setColor(new Color(255, 255, 255));
        g2.drawRect(x, y, width, height);
    }

    public int getX_CenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return wp.screenWidth / 2 - length / 2;
    }

    public int getAlignToRightTextX(String text, int tailX){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
