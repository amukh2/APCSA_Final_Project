import javax.swing.*;
import java.awt.*;

public class Board {
    public Board() {
        JFrame board = new JFrame();
        JButton start = new JButton();

        board.setSize(500, 500);
        board.getContentPane().setBackground(Color.BLACK);
        board.setVisible(true);
    }
}
