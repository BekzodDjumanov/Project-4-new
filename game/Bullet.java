import java.awt.*;

/**
 * CLASS: Bullet
 * DESCRIPTION: Represents the projectile fired by the main tank that can collide with enemy tanks
 *              Implements the Collidable interface for collision detection.
 */
public class Bullet extends Polygon implements Collidable {
 
  private final double speed = 8.0;
  private boolean active;
  public double direction;
  /**
   * Constructor to initialize a bullet at a given position and direction according to the tank location.
   *
   * @param position The starting point of the bullet
   * @param direction The direction the bullet will travel in degrees
   */
  public Bullet(Point position, double direction) {
    super( new Point[] { new Point(0, 0), new Point(10, 4), new Point(10, -4) }, position, direction);
    this.direction = direction;
    this.active = true;
  }

  /**
   * Checks if the bullet is still active or not.
   *
   * @return true if active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Moves the bullet by changing it's position based on its speed and direction.
   */
  public void move() {
    position.x += speed * Math.cos(Math.toRadians(direction));
    position.y += speed * Math.sin(Math.toRadians(direction));
  }

  /**
   * Removes bullet once it reaches end of the canvas.
   *
   * @param width The width of the game canvas
   * @param height The height of the game canvas
   */
  public void checkBounds(int width, int height) {
    if (position.x < 0 || position.x > width || position.y < 0 || position.y > height) {
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
   * Checks if this bullet collides with another tank/polygon.
   *
   * @param other The other tank/polygon to check collision with
   * @return true if any point of this bullet is inside the other polygon
   * @see Tank
   * @see Enemy
   */
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
    int[] xCoords = new int[points.length];
    int[] yCoords = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xCoords[i] = (int) points[i].x;
      yCoords[i] = (int) points[i].y;
    }
    brush.setColor(Color.gray);
    brush.fillPolygon(xCoords, yCoords, points.length);
  }
}
