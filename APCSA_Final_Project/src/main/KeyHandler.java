package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public static boolean up, down, left, right, pause;

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            if (Game.invert == 0) {
                up = true;
            } else {
                down = true;
            }
        }
        if(code == KeyEvent.VK_S) {
            if (Game.invert == 0) {
                down = true;
            } else {
                up = true;
            }
        }
        if(code == KeyEvent.VK_A) {
            left = true;
        }
        if(code == KeyEvent.VK_D) {
            right = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pause) {
                pause = false;
            } else {
                pause = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
