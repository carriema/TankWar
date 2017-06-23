package Object;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Created by myr on 6/21/17.
 */
public class Tank{

    private int POS_X;
    private int POS_Y;
    private final int ROUND = 30;

    public Tank() {
        POS_X = 100;
        POS_Y = 100;
    }

    public Tank(int x, int y) {
        this.POS_X = x;
        this.POS_Y = y;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(POS_X, POS_Y,ROUND, ROUND);
        g.setColor(c);
    }

    public int getPosX() {
        return this.POS_X;
    }

    public int getPosY() {
        return this.POS_Y;
    }

    public void setPosX(int x) {
        this.POS_X = x;
    }

    public void setPosY(int y) {
        this.POS_Y = y;
    }


    public void keyPressed(KeyEvent e) {
        int key =  e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT:
                POS_X -= 3;
                break;
            case KeyEvent.VK_RIGHT:
                POS_X += 3;
                break;
            case KeyEvent.VK_UP:
                POS_Y -= 3;
                break;
            case KeyEvent.VK_DOWN:
                POS_Y += 3;
                break;
        }

    }

}
