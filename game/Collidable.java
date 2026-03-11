/**
 * INTERFACE: Collidable
 * DESCRIPTION: Represents the Collidable interface, defines methods for collision detection and checking whether the state is active or not.
 */
interface Collidable {
  /**
   * Checks if the object is still active or not.
   *
   * @return true if active, false otherwise
   */
  boolean isActive();

  /**
   * Checks if this object collides with another polygon.
   *
   * @param other The other polygon to check collision with
   * @return true if any point of this object is inside the other polygon
   * @see Bullet
   * @see Tank
   * @see Enemy
   */
  boolean collides(Polygon other);
}
