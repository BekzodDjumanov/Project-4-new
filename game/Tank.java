import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * CLASS: Tank
 * DESCRIPTION: Represents the Tank classes which implements KeyListener (for movement) and Collidable (for collisions).
 * This class draws the main tank and allows the user to move.
 */

public class Tank extends Polygon implements KeyListener, Collidable {

  /* movement variables */
  private boolean forward, left, right, backward;
  /* center variables for tank */
  private int cx, cy;
  private double speed = 3.0;
  public ArrayList<Bullet> bullets = new ArrayList<>();
  /* if the tank is rendered */
  private boolean active = true;

  /**
   * Constructs a Tank with shapes and positions, alongside rotation
   *
   * @param shape an array of points to draw the tank
   * @param position the position of the tank
   * @param rotation the rotation of the tank
   */
  public Tank(Point[] shape, Point position, double rotation) {
    super(shape, position, rotation);
    forward = false;
    left = false;
    right = false;
    backward = false;
  }

  /**
   * Move method for moving the tank
   */
  public void move() {
    if (forward) {
      /* for registering movement using trig */
      position.x += speed * Math.cos(Math.toRadians(rotation));
      position.y += speed * Math.sin(Math.toRadians(rotation));
    }
    if (backward) {
      position.x -= speed * Math.cos(Math.toRadians(rotation));
      position.y -= speed * Math.sin(Math.toRadians(rotation));
    }
    if (left) rotate(-5);
    if (right) rotate(5);
  }

  /**
   * Paint for drawing the tank and its components
   *
   * @param brush Graphics object
   */
  public void paint(Graphics brush) {
    Point[] points = getPoints();
    int[] xPoints = new int[points.length];
    int[] yPoints = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      xPoints[i] = (int) points[i].x;
      yPoints[i] = (int) points[i].y;
    }

    /* tank body */
    brush.setColor(new Color(34, 139, 34));
    brush.fillPolygon(xPoints, yPoints, points.length);

    /* dark outline */
    brush.setColor(new Color(0, 80, 0));
    brush.drawPolygon(xPoints, yPoints, points.length);

    /* turret (uses cx and cy respectively for aligning turret in the middle) */
    cx = 0;
    cy = 0;
    for (int i = 0; i < points.length; i++) {
      cx += xPoints[i];
      cy += yPoints[i];
    }
    cx /= points.length;
    cy /= points.length;

    /* all ux components */
    brush.setColor(new Color(20, 100, 20));
    brush.fillOval(cx - 8, cy - 8, 16, 16);
    brush.setColor(new Color(0, 60, 0));
    brush.drawOval(cx - 8, cy - 8, 16, 16);

    /* for aligning the turret and such */
    int barrelEndX = (int) (cx + 20 * Math.cos(Math.toRadians(rotation)));
    int barrelEndY = (int) (cy + 20 * Math.sin(Math.toRadians(rotation)));
    brush.setColor(new Color(0, 60, 0));
    // draw turret
    brush.drawLine(cx, cy, barrelEndX, barrelEndY);
  }

  /**
   * Method for when specific key presses are activated
   *
   * @param e KeyEvent object
   */
  public void keyPressed(KeyEvent e) {
    /* all key functions */
    if (e.getKeyCode() == KeyEvent.VK_UP) forward = true;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) backward = true;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

    /* for when space is pressed = bullet is fired with trig */
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      double angle = Math.toRadians(rotation);
      Point bulletStart = new Point(
        cx + 22 * Math.cos(angle),
        cy + 22 * Math.sin(angle)
      );
      bullets.add(new Bullet(bulletStart, rotation));
    }
  }

  /**
   * Method for when specific key presses are released
   *
   * @param e KeyEvent object
   */
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) forward = false;
    if (e.getKeyCode() == KeyEvent.VK_DOWN) backward = false;
    if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
  }

  /**
   * Method for determining whether of not current Tank object is rendered
   *
   * @return true if active, false if destroyed
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
   */
  public boolean collides(Polygon other) {
    Point[] otherPoints = other.getPoints();
    for (Point p : otherPoints) {
      if (this.contains(p)) return true;
    }
    return false;
  }

  public void keyTyped(KeyEvent e) {}
}
