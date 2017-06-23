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
    private int SPEED = 3;
    private final int ROUND = 30;
    private Direction dir = Direction.STAY;

    boolean bL = false, bU = false, bR = false, bD = false;

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
        move();
        g.fillOval(POS_X, POS_Y,ROUND, ROUND);
        g.setColor(c);
    }

    public void setDirection() {
        if (bL == true && bU == false && bR == false && bD == false) {
            dir = Direction.L;
        } else if (bL == false && bU == true && bR == false && bD == false) {
            dir = Direction.U;
        } else if (bL == false && bU == false && bR == true && bD == false) {
            dir = Direction.R;
        } else if (bL == false && bU == false && bR == false && bD == true) {
            dir = Direction.D;
        } else if (bL == true && bU == true && bR == false && bD == false) {
            dir = Direction.LU;
        } else if (bL == true && bU == false && bR == false && bD == true) {
            dir = Direction.LD;
        } else if (bL == false && bU == true && bR == true && bD == false) {
            dir = Direction.RU;
        } else if (bL == false && bU == false && bR == true && bD == true) {
            dir = Direction.RD;
        } else {
            dir = Direction.STAY;
        }
    }

    public void move() {
        setDirection();
        switch(dir) {
            case D:
                POS_Y += SPEED;
                break;
            case U:
                POS_Y -= SPEED;
                break;
            case L:
                POS_X -= SPEED;
                break;
            case R:
                POS_X += SPEED;
                break;
            case LU:
                POS_X -= SPEED;
                POS_Y -= SPEED;
                break;
            case RU:
                POS_X += SPEED;
                POS_Y -= SPEED;
                break;
            case LD:
                POS_X -= SPEED;
                POS_Y += SPEED;
                break;
            case RD:
                POS_X += SPEED;
                POS_Y += SPEED;
                break;

        }
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

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key =  e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }

    }

}
