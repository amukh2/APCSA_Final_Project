package main;
import javax.swing.*;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    Thread gameThread;

    private final int FPS = 60;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    Game game;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        
        game = new Game();

        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start(); 
    }

    @Override
    public void run() {
        double drawInterval = 1000/FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();

        while (gameThread != null) {
            long currentTime = System.currentTimeMillis();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (KeyHandler.pause == false) {
            game.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        game.draw(g2);
    }
}
