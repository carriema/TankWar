package Object;

import java.awt.*;

/**
 * Created by myr on 6/24/17.
 */
public class Explode {

    private int x;
    private int y;
    private int round = 50;
    private final int SMAL_ROUND = 20;
    private final int LARGE_ROUND = 80;
    private boolean exist = true;
    private boolean increase = true;
    private TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void roundChange() {
        if (round > LARGE_ROUND) {
            increase = false;
        }
        if (round < SMAL_ROUND) {
            exist = false;
        }

        if (increase) {
            round += 20;
        } else {
            round -= 20;
        }
    }

    public void draw(Graphics g) {
        roundChange();
        if (!exist) {
            tc.getExplodes().remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, round, round);
        g.setColor(c);

    }
}
