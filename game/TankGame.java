import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * CLASS: TankGame
 * DESCRIPTION: Represents the primary workflow for the TankGame, including explosions, enemies and bullets which are all important
 * components of the game. This classes also features two inner classes, lambda usage, and game over and game win screens.
 */
class TankGame extends Game {

  // contains all enemies that are supposed to be generated
  private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

  // contains all explosion that are supposed to be carried out, implementation of Explosion is in inner class
  private ArrayList<Explosion> explosions = new ArrayList<>();

  private boolean gameOver = false;
  private boolean gameWin = false;
  private boolean paused = false;

  // Implementation inside scoreTracker inner class
  ScoreTracker tracker = new ScoreTracker();

  // #1 inner class
  class Explosion {

    private Point position;
    private int radius = 0;

    // frames until it disappears (explosion)
    private int lifetime = 15;

    /**
     * Constructor for explosion
     *
     * @param pos Point object
     */
    Explosion(Point pos) {
      /* get a copy of its position for explosion */
      position = pos.clone();
    }

    /**
     * Method for updating the radius and life span of explosion
     */
    public void update() {
      /* circle grows */
      radius += 3;
      /* time reduces */
      lifetime--;
    }

    /**
     * Method for if the explosion is active
     *
     * @return returns true if is still active, false otherwise
     */
    public boolean isActive() {
      return lifetime > 0;
    }

    /**
     * Method for painting the explosion with relative radius and color
     *
     * @param brush Graphics object
     */
    // draw explostion using its position (cloned) and decrease radius
    public void paint(Graphics brush) {
      brush.setColor(Color.orange);
      brush.fillOval(
        (int) (position.x - radius),
        (int) (position.y - radius),
        radius * 2,
        radius * 2
      );
    }
  }

  // #2 inner class for score
  class ScoreTracker {

    private int score = 0;

    /**
     * Method for adding points to score
     */
    public void addPoint() {
      score++;
    }

    /**
     * Method for returning the score
     *
     * @return returns score as int
     */
    public int getScore() {
      return score;
    }
  }

  /* this draws a tank (main one) */
  Point[] tankShape = {
    new Point(0, 0),
    new Point(40, 0),
    new Point(40, 25),
    new Point(0, 25),
  };
  Tank tank = new Tank(tankShape, new Point(400, 300), 0);

  /**
   * Constructor for TankGame class, adds enemies, draws tank, and manages control movements
   */
  public TankGame() {
    super("Tank Battle!", 800, 600);
    this.setFocusable(true);
    this.requestFocus();
    addKeyListener(tank);

    // all three enemy tanks instantiated
    enemies.add(
      new Enemy(
        tankShape,
        new Point((Math.random() * 550) + 1, (Math.random() * 600) + 1),
        0
      )
    );
    enemies.add(
      new Enemy(
        tankShape,
        new Point((Math.random() * 550) + 1, (Math.random() * 600) + 1),
        180
      )
    );
    enemies.add(
      new Enemy(
        tankShape,
        new Point((Math.random() * 550) + 1, (Math.random() * 600) + 1),
        90
      )
    );

    /* uses event keys for paused game (anonymous class) */
    addKeyListener(
      new KeyListener() {
        /**
         * Method for pausing, if p is pressed, then game is paused
         *
         * @param e KeyEvent object
         */
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
          }
        }

        /**
         * Unimplemented keyReleased method
         *
         * @param e KeyEvent object
         */
        public void keyReleased(KeyEvent e) {}

        /**
         * Unimplemented keyTyped method
         *
         * @param e KeyEvent object
         */
        public void keyTyped(KeyEvent e) {}
      }
    );
  }

  /**
   * Method for painting all objects that require color and brush strokes like game over and game win
   *
   * @param brush Graphics object
   */
  public void paint(Graphics brush) {
    brush.fillRect(0, 0, width, height);

    int count = 0;
    int y = 0;
    for (int i = 0; i < 1000; i += 50) {
      if (i > 800) {
        y += 50;
        i = 0;
      }
      if (count % 2 == 0) {
        brush.setColor(Color.BLACK);
      } else {
        brush.setColor(Color.darkGray);
      }
      brush.fillRect(i, y, 50, 50);
      count++;
      if (y > 800) {
        break;
      }
    }
    /* all text in the top left */
    brush.setColor(Color.white);
    brush.drawString("Score is " + tracker.getScore(), 10, 20);
    brush.drawString("↑, ↓, ←, → for movement", 10, 40);
    brush.drawString("Space to shoot", 10, 60);
    brush.drawString("P to pause", 10, 80);
    brush.drawString("Dont hit the tanks!", 10, 100);

    /* paint for paused */
    if (paused) {
      brush.setColor(Color.black);
      brush.fillRect(0, 0, width, height);
      brush.setColor(Color.yellow);
      brush.setFont(new Font("Arial", Font.BOLD, 36));
      brush.drawString("PAUSED", 250, height / 2);
      brush.drawString("Press P to resume", 250, height / 2 + 40);
      return;
    }

    /* paint for gameWin */
    if (gameWin) {
      brush.setColor(Color.black);
      brush.fillRect(0, 0, width, height);
      brush.setColor(Color.green);
      brush.setFont(new Font("Arial", Font.BOLD, 48));
      brush.drawString("YOU WIN!", 250, height / 2);
      /* stop drawing the game */
      return;
    }

    tank.move();
    tank.paint(brush);

    /* collides interface */
    for (Enemy e : enemies) {
      if (e.isActive() && tank.collides(e)) {
        gameOver = true;
      }
    }

    /* render bullets */
    for (Bullet b : tank.bullets) {
      b.paint(brush);
    }

    for (Bullet b : tank.bullets) {
      b.move();
      b.checkBounds(width, height);

      /* check collision with enemies */
      for (Enemy e : enemies) {
        if (b.isActive() && e.isActive() && e.collides(b)) {
          b.deactivate();
          /* enemy is deactivated */
          e.destroy();
          /* for adding points (inner class) */
          tracker.addPoint();

          /* compute tank center */
          Point[] tankPoints = e.getPoints();
          double cx = 0;
          double cy = 0;
          for (Point p : tankPoints) {
            cx += p.x;
            cy += p.y;
          }
          /* this is all for mechanics for handling explosion */
          cx /= tankPoints.length;
          cy /= tankPoints.length;
          explosions.add(new Explosion(new Point(cx, cy)));
        }
      }
    }
    /* draws enemy tanks */
    for (Enemy e : enemies) {
      if (e.isActive()) {
        e.paint(brush);
      }

      for (int i = explosions.size() - 1; i >= 0; i--) {
        Explosion ex = explosions.get(i);
        if (ex.isActive()) {
          ex.update();
          /* main logic for drawing explosion */
          ex.paint(brush);
        } else {
          /* remove finished explosions */
          explosions.remove(i);
        }
      }

      /* remove inactive bullets */
      tank.bullets.removeIf(b -> !b.isActive()); // #1 lambda
    }

    if (gameOver) {
      brush.setColor(Color.black);
      brush.fillRect(0, 0, width, height);
      brush.setColor(Color.red);
      brush.setFont(new Font("Arial", Font.BOLD, 48));
      brush.drawString("GAME OVER", width / 2 - 150, height / 2);
      return; // stop drawing anything else
    }
    if (tracker.getScore() >= 3) {
      gameWin = true;
    }
  }

  /**
   * Main method for drawing and starting game
   *
   * @param args String[] object
   */
  public static void main(String[] args) {
    TankGame a = new TankGame();
    a.repaint();
  }
}
