package main;

import ideas.Circle_A;
import ideas.Circle_B;

public class AssetSetter {

    WindowPanel wp;

    public AssetSetter(WindowPanel wp){
        this.wp = wp;
    }

    public void setCircles(){
        int i = 0;

        wp.obj[i] = new Circle_A(wp);
        wp.obj[i].posX = ((double) wp.screenWidth / 2) - ((double) wp.tileSize / 2 * 5);
        wp.obj[i].posY = ((double) wp.screenHeight / 2) - ((double) wp.tileSize / 2);
        i++;

        wp.obj[i] = new Circle_B(wp);
        wp.obj[i].posX = ((double) wp.screenWidth / 2) + ((double) wp.tileSize / 2 * 5);
        wp.obj[i].posY = ((double) wp.screenHeight / 2) - ((double) wp.tileSize / 2);
    }
}
