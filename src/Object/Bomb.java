package Object;

import Utils.Constants;

import java.awt.*;

/**
 * Created by myr on 6/23/17.
 */
public class Bomb {

    private Direction dir;
    private final int ROUND = Constants.BOMB_SIZE;
    private Tank belongToTank;
    private int POS_X;
    private int POS_Y;
    private int SPEED = 6;
    private boolean alive;

    public Bomb(Direction d, Tank tank) {
        this.dir = d;
        belongToTank = tank;
        this.POS_X = belongToTank.getPosX() + belongToTank.getRound()/2;
        this.POS_Y = belongToTank.getPosY() + belongToTank.getRound()/2;
        alive = true;
    }

    public void move() {
        switch(dir) {
            case R:
                POS_X += SPEED;
                break;
            case L:
                POS_X -= SPEED;
                break;
            case U:
                POS_Y -= SPEED;
                break;
            case D:
                POS_Y += SPEED;
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

    public void draw(Graphics g) {
        move();
        if (alive) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillOval(POS_X, POS_Y, ROUND, ROUND);
            g.setColor(c);
        }

    }


    public boolean isAlive() {
        if (POS_X > Constants.FRAME_WIDTH || POS_X < 0
                || POS_Y > Constants.FRAME_HEIGHT || POS_Y < 0) {
            alive = false;
        }
        return alive;
    }

}
