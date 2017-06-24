package Object;

import Utils.Constants;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


/**
 * Created by myr on 6/21/17.
 */
public class Tank{

    private int POS_X;
    private int POS_Y;
    private int SPEED = 3;
    private final int ROUND = Constants.TANK_SIZE;
    private Direction dir = Direction.STAY;
    private ArrayList<Bomb> bombs;
    private Direction barrelDir = Direction.U;
    private final int barrelLength = 20;


    boolean bL = false, bU = false, bR = false, bD = false;

    public Tank() {
        POS_X = 100;
        POS_Y = 100;
        bombs = new ArrayList<Bomb>();
    }

    public Tank(int x, int y) {
        this.POS_X = x;
        this.POS_Y = y;
        bombs = new ArrayList<Bomb>();
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        move();
        g.fillOval(POS_X, POS_Y,ROUND, ROUND);

        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            if (!b.isAlive()) {
                bombs.remove(i);
            } else {
                b.draw(g);
            }
        }

        g.drawString(String.valueOf(bombs.size()), 100, 100);
        g.setColor(Color.darkGray);
        switch(barrelDir) {
            case D:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2, POS_Y + ROUND/2 + barrelLength);
                break;
            case U:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2, POS_Y + ROUND/2 - barrelLength);
                break;
            case L:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - barrelLength, POS_Y + ROUND/2);
                break;
            case R:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + barrelLength, POS_Y + ROUND/2);
                break;
            case LU:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - (int)(0.7*barrelLength), POS_Y + ROUND/2 - (int)(0.7*barrelLength));
                break;
            case RU:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + (int)(0.7*barrelLength), POS_Y + ROUND/2 - (int)(0.7*barrelLength));
                break;
            case LD:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - (int)(0.7*barrelLength), POS_Y + ROUND/2 + (int)(0.7*barrelLength));
                break;
            case RD:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + (int)(0.7*barrelLength), POS_Y + ROUND/2 + (int)(0.7*barrelLength));
                break;

        }
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
        if (dir != Direction.STAY) {
            barrelDir = dir;
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

    public int getRound() {
        return ROUND;
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
            case KeyEvent.VK_SPACE:
                bombs.add(new Bomb(barrelDir, this));
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
