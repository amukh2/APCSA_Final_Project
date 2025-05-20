public class Main {
    public static void main(String[] args) {
        Board frame = new Board();
        GamePanel gp = new GamePanel();
        Thread thread = new Thread(gp);
        thread.start();
        gp.stop();
    }
}