package Object;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by myr on 6/24/17.
 */
public class MyTank extends Tank {
	
    public MyTank() {
		// TODO Auto-generated constructor stub
    	super();
	}

	boolean bL = false, bU = false, bR = false, bD = false;

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
        Set<Tank> tanks = (Set<Tank>) tc.getTanks().values();
        for (Tank b : tanks) {
            if (!this.equals(b) && this.getRect().intersects(b.getRect())) {
                this.dir = Direction.STAY;
               
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int key =  e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_SHIFT:
                this.alive = true;
                break;
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

    @Override
    public void fire() {
        int x = POS_X + ROUND/2;
        int y = POS_Y + ROUND/2;
        tc.getBombs().add(new Bomb(barrelDir,x,y, true));
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
                if (alive) {
                    fire();
                }
                break;
        }
    }



}
