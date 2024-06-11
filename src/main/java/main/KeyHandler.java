package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    WindowPanel wp;

    public boolean spacePressed = true;

    public KeyHandler(WindowPanel wp){
        this.wp = wp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (wp.state == wp.selectionState){
            selectionState(code);
        }

        if (wp.state == wp.rectangleState){
            rectangleState(code);
        }

        if (wp.state == wp.circleCollState){
            circleCollState(code);
        }
    }

    public void selectionState(int code){
        if (code == KeyEvent.VK_W){
            wp.ui.commandNum--;
            if (wp.ui.commandNum < 0){
                wp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S){
            wp.ui.commandNum++;
            if (wp.ui.commandNum > 1){
                wp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER){
            if (wp.ui.commandNum == 0){
                wp.state = wp.rectangleState;
            }
            if (wp.ui.commandNum == 1){
                wp.state = wp.circleCollState;
            }
            wp.runEngine = true;
        }
    }

    public void rectangleState(int code){
        if (code == KeyEvent.VK_SPACE){
            wp.freeze = !wp.freeze;
        }
    }

    public void circleCollState(int code){
        if (code == KeyEvent.VK_SPACE){
            wp.freeze = !wp.freeze;
        }
        if (code == KeyEvent.VK_R){
            wp.reset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
