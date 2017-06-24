package Object;
import Utils.Constants;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by myr on 6/21/16.
 */
public class TankClient extends Frame{

    public static final int FRAME_X = 100;
    public static final int FRAME_Y = 100;

    Tank myTank = new Tank(100,100);

    public void paint(Graphics g) {
        myTank.draw(g);


    }

    public void launchFrame() {
        this.setLocation(FRAME_X, FRAME_Y);
        this.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        this.setResizable(false);
        this.setBackground(Color.GREEN);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());

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

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }
    }

}
