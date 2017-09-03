package Object;

import Utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by myr on 6/21/17.
 */
public class Tank{

	public int id;
    protected int POS_X;
    protected int POS_Y;
    protected int oldX;
    protected int oldY;
    private int SPEED = 3;
    protected final int ROUND = Constants.TANK_SIZE;
    protected Direction dir = Direction.STAY;
    protected Direction barrelDir = Direction.U;
    protected final int barrelLength = 20;
    TankClient tc = TankClient.getInstance();
    protected boolean bGood;
    protected boolean alive = true;
    private Random r = new Random();
    private int step = 0;
    private Direction[] directions = {Direction.D, Direction.L, Direction.LD, Direction.LU,Direction.R,
            Direction.RD,Direction.RU,Direction.U};

    
    public Tank(int id, int x, int y, boolean isGood, Direction dir) {
    	// TODO Auto-generated constructor stub
    	this.POS_X = x;
        this.POS_Y = y;
    	this.bGood = isGood;
    	this.dir = dir;
    	this.id = id;
    }

	public Tank() {
		// TODO Auto-generated constructor stub
        POS_X = r.nextInt(Constants.FRAME_WIDTH - ROUND);
        POS_Y = r.nextInt(Constants.FRAME_HEIGHT - ROUND);
        this.bGood = id % 2 == 0;
	}

	public Direction getDir() {
    	return this.dir;
    }
    
    public void setDirection(Direction d) {
    	this.dir = d;
    }
    
	public boolean hitTank(Tank b) {
        if (!this.equals(b) && this.getRect().intersects(b.getRect())) {
            POS_X = oldX;
            POS_Y = oldY;
            dir = directions[r.nextInt(8)];
            return true;
        }
        return false;
    }

    public boolean isGood() {
        return bGood;
    }

    public void drawBarrelPos(Graphics g) {
        g.setColor(Color.BLUE);
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
    }
    public void changeDirection(Direction newDir) {
        this.dir = newDir;
    }

    public void draw(Graphics g) {
        if (alive) {
            Color c = g.getColor();
            g.setColor(bGood? Color.RED : Color.LIGHT_GRAY);
//            move();
            g.fillOval(POS_X, POS_Y,ROUND, ROUND);
            drawBarrelPos(g);
            g.setColor(c);
        }
        if (!alive) {
            if (!(this instanceof MyTank)) {
                tc.getTanks().remove(this.id);
            }
        }
    }

//    public void setDirection() {
//        if (step == 0 || POS_X <= 0 || POS_X >= Constants.FRAME_WIDTH - ROUND
//                || POS_Y <= 23 || POS_Y >= Constants.FRAME_HEIGHT - ROUND) {
//            step = r.nextInt(10);
//            dir = directions[r.nextInt(directions.length)];
//        }
//
//        if (dir != Direction.STAY) {
//            barrelDir = dir;
//        }
//
//        if (r.nextInt(40) > 35) {
//            fire();
//        }
//    }

//    public void move() {
//        oldX = POS_X;
//        oldY = POS_Y;
//        setDirection();
//        switch(dir) {
//            case D:
//                POS_Y += SPEED;
//                break;
//            case U:
//                POS_Y -= SPEED;
//                break;
//            case L:
//                POS_X -= SPEED;
//                break;
//            case R:
//                POS_X += SPEED;
//                break;
//            case LU:
//                POS_X -= SPEED;
//                POS_Y -= SPEED;
//                break;
//            case RU:
//                POS_X += SPEED;
//                POS_Y -= SPEED;
//                break;
//            case LD:
//                POS_X -= SPEED;
//                POS_Y += SPEED;
//                break;
//            case RD:
//                POS_X += SPEED;
//                POS_Y += SPEED;
//                break;
//
//        }
//        POS_X = POS_X <= 0 ? 0 : POS_X >= Constants.FRAME_WIDTH - ROUND ? Constants.FRAME_WIDTH - ROUND: POS_X;
//        POS_Y = POS_Y <= 23 ? 23 : POS_Y >= Constants.FRAME_HEIGHT - ROUND ? Constants.FRAME_HEIGHT - ROUND: POS_Y;
//
//        ArrayList<Tank> tanks = tc.getTanks();
//        for (Tank c : tanks) {
//            this.hitTank(c);
//        }
//    }

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


    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }

    public void fire() {
        if (!alive) return;
        int x = POS_X + ROUND/2;
        int y = POS_Y + ROUND/2;
        tc.getBombs().add(new Bomb(barrelDir,x,y, bGood));
    }

    public void setGood(boolean isGood) {
    	this.bGood = isGood;
    }

    public Rectangle getRect() {
        return new Rectangle(POS_X,POS_Y,ROUND,ROUND);
    }

}
