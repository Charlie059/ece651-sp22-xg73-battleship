package ece651.sp22.xg73.battleship;

import java.util.Set;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (0 > c.getRow() || c.getRow() >= theBoard.getHeight() || 0 > c.getColumn()
          || c.getColumn() >= theBoard.getWidth()) {
        return false;
      }
    }
    return true;
  }

}
