package ece651.sp22.xg73.battleship;

/**
 * BattleShipBoard Class which implement interface Board
 * 
 * @author Xuhui Gong
 * @version 1.0
 * @since 1.0
 */
public class BattleShipBoard implements Board {
  private final int width;
  private final int height;

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
  }

 /** Gets the battleship board’s width.
 * @return A int repersenting board’s width.
 */
  public int getWidth() {
    return this.width;
  }

 /** Gets the battleship board’s height.
 * @return A int repersenting board’s height.
 */
  public int getHeight() {
    return this.height;
  }
}
