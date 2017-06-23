package Object;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by myr on 6/21/16.
 */
public class TankClient extends Frame{

    private int pos_x = 50;
    private int pos_y = 50;
    private int circle_size = 30;

    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 600;
    public static final int FRAME_X = 100;
    public static final int FRAME_Y = 100;

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(pos_x,pos_y,circle_size,circle_size);
        g.setColor(c);
    }

    public void launchFrame() {
        this.setLocation(FRAME_X, FRAME_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setBackground(Color.GREEN);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.addKeyListener(new DirectControl());

        new Thread(new PaintThread()).start();
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class DirectControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key =  e.getKeyCode();
            switch(key) {
                case KeyEvent.VK_LEFT:
                    pos_x -= 3;
                    break;
                case KeyEvent.VK_RIGHT:
                    pos_x += 3;
                    break;
                case KeyEvent.VK_UP:
                    pos_y -= 3;
                    break;
                case KeyEvent.VK_DOWN:
                    pos_y += 3;
                    break;
            }

        }
    }

}
