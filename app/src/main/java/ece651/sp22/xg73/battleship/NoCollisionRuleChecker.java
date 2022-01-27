package ece651.sp22.xg73.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if(theBoard.whatIsAt(c) != null){
        return false;
      }
    }
    return true;
  }
}
