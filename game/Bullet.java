import java.awt.*;

/**
 * CLASS: Bullet
 * DESCRIPTION: Represents the projectile fired by the main tank that can collide with enemy tanks
 *              Implements the Collidable interface for collision detection.
 */
public class Bullet extends Polygon implements Collidable {

  /** Speed of the bullet in pixels per frame */
  private double speed = 8.0;

  /** Whether the bullet is still active (on screen) */
  private boolean active;

  /** Direction in degrees the bullet is moving */
  public double direction;

  /**
   * Constructor to initialize a bullet at a given position and direction.
   *
   * @param position The starting point of the bullet
   * @param direction The direction the bullet will travel in degrees
   */
  public Bullet(Point position, double direction) {
    super(
      new Point[] { new Point(0, 0), new Point(10, 4), new Point(10, -4) },
      position,
      direction
    );
    this.direction = direction;
    this.active = true;
  }

  /**
   * Checks if the bullet is still active / rendered.
   *
   * @return true if active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Updates the bullet's position based on its speed and direction / handles all movement logic for the bullet.
   */
  public void move() {
    position.x += speed * Math.cos(Math.toRadians(direction));
    position.y += speed * Math.sin(Math.toRadians(direction));
  }

  /**
   * Unrenders the bullet once it reaches end of map.
   *
   * @param width The width of the game canvas
   * @param height The height of the game canvas
   */
  public void checkBounds(int width, int height) {
    if (
      position.x < 0 ||
      position.x > width ||
      position.y < 0 ||
      position.y > height
    ) {
      active = false;
    }
  }

  /**
   * Removes the bullet from in-game
   */
  public void deactivate() {
    active = false;
  }

  /**
   * Checks if this bullet collides with another polygon.
   *
   * @param other The other polygon to check collision with
   * @return true if any point of this bullet is inside the other polygon
   * @see Tank
   * @see Enemy
   */
  @Override
  public boolean collides(Polygon other) {
    for (Point p : this.getPoints()) {
      if (other.contains(p)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Draws the bullet on the canvas.
   *
   * @param brush The Graphics object used to render the bullet
   */
  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }
    brush.setColor(Color.gray);
    brush.fillPolygon(xPoints, yPoints, points.length);
  }
}
