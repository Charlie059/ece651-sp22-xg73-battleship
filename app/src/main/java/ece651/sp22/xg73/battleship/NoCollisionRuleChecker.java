package ece651.sp22.xg73.battleship;

import java.util.HashSet;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * Check the ship has no collision
   * 
   * @param The object of ship
   * @param the object of the board
   * @return A string indicate if the ship has collision with each others, if no collision happened, return null
   */

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard, Iterable<Coordinate> coordinates) {
    if (coordinates == null){
      for (Coordinate c : theShip.getCoordinates()) {
        if (theBoard.whatIsAtForSelf(c) != null) {
          return "That placement is invalid: the ship overlaps another ship.";
        }
      }
      return null;
    }
    else {
      // Create Exemptions Hashset
      HashSet<Coordinate> hs = new HashSet<Coordinate>();
      for (Coordinate c: coordinates){
        hs.add(c);
      }

      for (Coordinate c : theShip.getCoordinates()) {
        if (theBoard.whatIsAtForSelf(c) != null) {
          if (hs.contains(c)) continue;
          return "That placement is invalid: the ship overlaps another ship.";
        }
      }
      return null;

    }

  }
}
