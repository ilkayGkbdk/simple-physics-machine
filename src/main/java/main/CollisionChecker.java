package main;

import ideas.Objects;

public class CollisionChecker {

    WindowPanel wp;

    public CollisionChecker(WindowPanel wp){
        this.wp = wp;
    }

    public int checkCircle(Objects object, Objects[] target){
        int index = 999;

        for (int i = 0; i < wp.obj.length; i++){
            if (wp.obj[i] != null && object != target[i]){
                object.solidArea.x = (int) object.posX;
                object.solidArea.y = (int) object.posY;

                target[i].solidArea.x = (int) target[i].posX;
                target[i].solidArea.y = (int) target[i].posY;

                if (object.solidArea.intersects(target[i].solidArea)){
                    object.collisionOn = true;
                    index = i;
                }
            }
        }
        return index;
    }
}
