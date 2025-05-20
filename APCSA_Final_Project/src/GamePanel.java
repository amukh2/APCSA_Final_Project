import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private boolean isRunning = true;
    private final long frameDuration = 1000/60;

    public GamePanel() {
        this.setSize(600, 1000);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }

    @Override
    public void updateUI() {
        super.updateUI();

    }

    @Override
    public void run() {
        long currentTime, elapsedTime, previousTime, sleepTime;
        previousTime = System.currentTimeMillis();


        while (isRunning) {
            // Perform game logic and rendering here
            updateUI();

            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - previousTime;
            sleepTime = frameDuration - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            previousTime = System.currentTimeMillis();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
