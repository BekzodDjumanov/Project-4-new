/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access 
             coordinates.
*/

public class Point implements Cloneable {

  public double x, y;

  /**
   * Point constructor for defining x and y grid
   *
   * @param inX x value
   * @param inY y value
   */
  public Point(double inX, double inY) {
    x = inX;
    y = inY;
  }

  /**
   * Returns the x-coordinate of this point.
   *
   * @return the x-coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y-coordinate of this point.
   *
   * @return the y-coordinate
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the x-coordinate of this point.
   *
   * @param x the new x-coordinate
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Sets the y-coordinate of this point.
   *
   * @param y the new y-coordinate
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Creates and returns a copy of this point.
   *
   * @return a new Point with the same x and y values
   */
  public Point clone() {
    return new Point(x, y);
  }
}
