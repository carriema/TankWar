package Object;

import java.awt.*;

/**
 * Created by myr on 6/25/17.
 */
public class Wall {

    private int x;
    private int y;
    private int width;
    private int height;
    private TankClient tc;

    public Wall(int x, int y, int w, int h, TankClient tc) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.tc = tc;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(c);
    }


}
