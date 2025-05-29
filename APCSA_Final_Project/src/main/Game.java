package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import mino.Block;
import mino.*;

public class Game {
    
    final int HEIGHT = 600;
    final int WIDTH = 360;

    public static int  left_x, right_x, top_y, bottom_y;

    Mino currentMino;
    final int MINO_START_X, MINO_START_Y;

    public static int dropInterval = 60;

    public Game() {
        left_x = ((GamePanel.WIDTH)/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch(i) {
            case 0: mino = new L1(); break;
            case 1: mino = new L2(); break;
            case 2: mino = new Square(); break;
            case 3: mino = new Bar(); break;
            case 4: mino = new T(); break;
            case 5: mino = new Z1(); break;
            case 6: mino = new Z2(); break;
        }

        return mino;
    }

    public void update() {
        currentMino.update();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        int x = right_x+100;
        int y = bottom_y-200;

        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("Next", x+70, y+40);

        if (currentMino != null) {
            currentMino.draw(g2);
        }

        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandler.pause) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    }
}
