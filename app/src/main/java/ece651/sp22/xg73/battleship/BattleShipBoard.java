package ece651.sp22.xg73.battleship;

import java.util.ArrayList;

/**
 * BattleShipBoard Class which implement interface Board
 * 
 * @author Xuhui Gong
 * @version 1.0
 * @since 1.0
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int width, int height) {
    if (width <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + width);
    }
    if (height <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + height);
    }
    this.width = width;
    this.height = height;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null));
  }

  /**
   * Gets the battleship board’s width.
   * 
   * @return A int repersenting board’s width.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the battleship board’s height.
   * 
   * @return A int repersenting board’s height.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Add Ships into the BattleShipBoard
   * 
   * @return Return true if we add the Ship seccess, else return false
   */
  public String tryAddShip(Ship<T> toAdd) {
    String check = this.placementChecker.checkPlacement(toAdd, this);
    if (check != null) {
      return check;
    }
    this.myShips.add(toAdd);
    return null;
  }

  /**
   * This method takes a Coordinate, and sees which (if any) Ship occupies that
   * coordinate.
   * 
   * @return If one is found, we return whatever displayInfo it has at those
   *         coordinates. If none is found, we return null.
   */
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }
}
