package disc;

import java.awt.*;

public class Disc {

    private Point center;
    private final int radius;
    private final Color color;

    public Disc(Point center, int radius, int index) {
        this.center = center;
        this.radius = radius;
        this.color = new Color(Color.HSBtoRGB((float) (index % 10) / 10, 1.0f, 1.0f));
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getX() {
        return center.x - radius;
    }

    public int getY() {
        return center.y - radius;
    }

    public int getDiameter() {
        return radius * 2;
    }

    public Color getColor() {
        return color;
    }

    public boolean contains(Point point) {
        return center.distance(point) <= radius;
    }
}