import java.awt.*;
import java.awt.event.*;

/**
 * CLASS: Game
 * DESCRIPTION: Represents the main game class, where all operations will take place, including player viewing and window sizing.
 */

abstract class Game extends Canvas {

  protected boolean on = true;
  protected int width, height;
  protected Image buffer;

  /**
   * Game constructor for resizing window and making game viewable
   *
   * @param name name for the game (displayed on window)
   * @param inWidth width for window size
   * @param inHeight height for the window size
   */
  public Game(String name, int inWidth, int inHeight) {
    width = inWidth;
    height = inHeight;

    // Frame can be read as 'window' here.
    Frame frame = new Frame(name);
    frame.add(this);
    frame.setSize(width, height);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        }
      }
    );

    buffer = createImage(width, height);
  }

  /**
   * Abstract method for paint, drawing the game.
   *
   * @param brush Graphics object
   */
  // 'paint' will be called every tenth of a second that the game is on.
  public abstract void paint(Graphics brush);

  /**
   * Method for updating game every tenth of a second.
   *
   * @param brush Graphics object
   */
  // 'update' paints to a buffer then to the screen, then waits a tenth of
  // a second before repeating itself, assuming the game is on. This is done
  // to avoid a choppy painting experience if repainted in pieces.
  public void update(Graphics brush) {
    paint(buffer.getGraphics());
    brush.drawImage(buffer, 0, 0, this);
    if (on) {
      sleep(10);
      repaint();
    }
  }

  /**
   * Sleep method for update method.
   *
   * @param time time for sleeping
   */
  // 'sleep' is a simple helper function used in 'update'.
  private void sleep(int time) {
    try {
      Thread.sleep(time);
    } catch (Exception exc) {}
  }
}
