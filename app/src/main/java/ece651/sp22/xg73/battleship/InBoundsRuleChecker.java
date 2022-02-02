package ece651.sp22.xg73.battleship;

import java.util.Set;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * Check if the placement is out of bound
   * 
   * @param The ship object which needs to be placed
   * @param The board object with type T
   * @return A String indicate the probelms or return null which is no error
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (0 > c.getRow()) {
        return "That placement is invalid: the ship goes off the top of the board.";
      } else if (c.getRow() >= theBoard.getHeight()) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      } else if (0 > c.getColumn()) {
        return "That placement is invalid: the ship goes off the left of the board.";
      } else if (c.getColumn() >= theBoard.getWidth()) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }
    return null;
  }

}
