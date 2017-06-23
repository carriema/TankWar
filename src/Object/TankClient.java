package Object;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    private int x_change = 0;
    private int y_change = 0;

    private Direction direction = Direction.STAY;

    private void directionChange() {
        if (direction == Direction.UP) {
            x_change = 0;
            y_change = -3;
        } else if (direction == Direction.DOWN) {
            x_change = 0;
            y_change = 3;
        } else if (direction == Direction.LEFT) {
            x_change = -3;
            y_change = 0;
        } else if (direction == Direction.RIGHT) {
            x_change = 3;
            y_change = 0;
        } else {
            x_change = 0;
            y_change = 0;
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(pos_x,pos_y,circle_size,circle_size);
        g.setColor(c);
        pos_y += y_change;
        pos_x += x_change;
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

    private class DirectControl implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_KP_LEFT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction = Direction.LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_KP_RIGHT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction = Direction.RIGHT;
            } else if (e.getKeyCode() == KeyEvent.VK_KP_UP || e.getKeyCode() == KeyEvent.VK_UP) {
                direction = Direction.UP;
            } else if (e.getKeyCode() == KeyEvent.VK_KP_DOWN || e.getKeyCode() == KeyEvent.VK_DOWN) {
                direction = Direction.DOWN;
            }
            directionChange();

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
