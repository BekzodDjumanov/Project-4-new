import java.awt.*;

/**
 * CLASS: Enemy
 * DESCRIPTION: Represents the Enemy class where enemy tanks are drawn and operated, defining collisions
 * and serving as the main plot of the game
 */
public class Enemy extends Polygon implements Collidable {

  private boolean active = true;

  /**
   * Creates a new Enemy tank.
   *
   * @param shape the points defining the tanks shape
   * @param position the starting position of the tank
   * @param rotation the initial rotation angle in degrees
   */
  public Enemy(Point[] shape, Point position, double rotation) {
    super(shape, position, rotation);
  }

  /**
   * Draws the enemy tank, including its body, outline, and turret
   *
   * @param brush the Graphics object used for drawing
   */
  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }

    brush.setColor(new Color(139, 0, 0));
    brush.fillPolygon(xPoints, yPoints, points.length);

    brush.setColor(new Color(80, 0, 0));
    brush.drawPolygon(xPoints, yPoints, points.length);

    int cx = 0;
    int cy = 0;
    for (int i = 0; i < points.length; i++) {
      cx += xPoints[i];
      cy += yPoints[i];
    }
    cx /= points.length;
    cy /= points.length;

    brush.setColor(new Color(100, 0, 0));
    brush.fillOval(cx - 8, cy - 8, 16, 16);
    brush.setColor(new Color(60, 0, 0));
    brush.drawOval(cx - 8, cy - 8, 16, 16);
  }

  /**
   * Returns whether the enemy tank is still active (rendered)
   *
   * @return true if active, false if not
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Checks if this enemy tank is colliding with another polygon (bullet or main tank)
   *
   * @param other the polygon to check collision against
   * @return true if a collision is detected, false otherwise
   * @see Bullet
   * @see Tank
   */
  public boolean collides(Polygon other) {
    Point[] otherPoints = other.getPoints();
    for (Point p : otherPoints) {
      if (this.contains(p)) return true;
    }
    return false;
  }

  /**
   * Destroys the enemy tank by marking it as inactive.
   */
  public void destroy() {
    active = false;
  }
}
