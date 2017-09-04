package Object;

import java.awt.*;

/**
 * Created by myr on 6/24/17.
 */
public class Explode {

    private int x;
    private int y;
    public int id;
    private int round = 50;
    private final int SMAL_ROUND = 20;
    private final int LARGE_ROUND = 80;
    private boolean exist = true;
    private boolean increase = true;
    private TankClient tc = TankClient.getInstance();

   
	public Explode(int posX, int posY) {
		// TODO Auto-generated constructor stub
		this.x = posX;
        this.y = posY;
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
	
	public boolean isExst() {
		return exist;
	}

    public void draw(Graphics g) {
        roundChange();
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, round, round);
        g.setColor(c);

    }
}
