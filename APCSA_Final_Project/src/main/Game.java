package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mino.Block;
import mino.*;

public class Game {
    
    final int HEIGHT = 600;
    final int WIDTH = 360;

    public static int  left_x, right_x, top_y, bottom_y;

    Mino currentMino;
    int MINO_START_X, MINO_START_Y;
    Mino nextMino;
    int NEXTMINO_X, NEXTMINO_Y;

    public static ArrayList<Block> staticBlocks = new ArrayList<Block>();

    public static int dropInterval = 60;

    static boolean gameOver;

    boolean effectCounterOn;
    int effectCounter;

     ArrayList<Integer> effectY = new ArrayList<>(); 

     int level = 1;
     int lines;
     int score;

     public static int invert = 0;
     static boolean inverting;

    public Game() {
        left_x = ((GamePanel.WIDTH)/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
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

        int power = new Random().nextInt(5);
        if (power == 0) {
            mino.powerUp = true;
            mino.b[0].setColor(Color.gray);
            mino.b[1].setColor(Color.gray);
            mino.b[2].setColor(Color.gray);
            mino.b[3].setColor(Color.gray);
        }

        return mino;
    }

    private void Invert() {
        inverting = true;
        if (invert == 0) {
            invert = 1;
        } else {
            invert = 0;
        }
            for (int i = 0; i < staticBlocks.size(); i++) {
                int temp = MINO_START_X - staticBlocks.get(i).x;

                staticBlocks.get(i).x = MINO_START_X + temp + Block.SIZE;
                
            }
        
    }

    private void PowerUps() {
        Timer timer = new Timer();
        if (currentMino.powerUp) {
            int random = new Random().nextInt(9);

            switch (random) {
                case 0, 1, 2: // Speed Up
                        dropInterval /= 2;
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                dropInterval *= 3;
                            }
                            
                        }, 20000);
                        break;

                case 3, 4, 5: // Slow Down
                        dropInterval *= 2;
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                dropInterval /= 2;
                            }
                            
                        }, 20000);
                        break; 

                case 6: // Clear                       
                        staticBlocks.clear();
                        break;

                case 7, 8, 9: // Invert x
                        Invert();
                        break;
            }
        }
    }

    public void update() {

        if (currentMino.active == false) {

            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                gameOver = true;
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);

            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            PowerUps();

            checkDelete();

        } else {
            currentMino.update();
        }
    }

    private void checkDelete() {

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        score += 17;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y && !inverting) {
                    blockCount++;
                }
            }
            inverting = false;

            x += Block.SIZE;

            if (x == right_x) {

                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() -1; i > -1; i--) {

                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }
                    
                    lineCount++;
                    lines++;

                    if (lines % 10 == 0 && dropInterval > 1) {

                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    for (int i = 0; i < staticBlocks.size(); i++) {

                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        switch (lineCount) {
            case 1: 
                    int singleLineScore = 40*level;
                    score += singleLineScore;
                    break;

            case 2: 
                    int doubleLineScore = 100 * level;
                    score += doubleLineScore;
                    break;

            case 3: 
                    int tripleLineScore = 300 * level;
                    score += tripleLineScore;
                    break;

            case 4: 
                    int quadrupleLineScore = 1200 * level;
                    score += quadrupleLineScore;
        }
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

        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x , y); 
        y += 70;
        g2.drawString("LINES: " + lines, x , y); 
        y += 70;
        g2.drawString("SCORE: " + score, x , y); 

        if (currentMino != null) {
            currentMino.draw(g2);
        }

        nextMino.draw(g2);

        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
                
            }
            
            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));

        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER!", x, y);
        }
        else if (KeyHandler.pause) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }

        x = 95;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Cool Tetris", x, y);
    }
}
