package ece651.sp22.xg73.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard, Iterable<Coordinate> coordinates);

  /**
   * Recursion to check the placement problem.
   * 
   * @param The objection of ship with type T
   * @param The board need to be checked
   * @return A string which indicate the placement or return null(no probelm)
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard, Iterable<Coordinate> coordinates) {
    String check = checkMyRule(theShip, theBoard, coordinates);
    // if we fail our own rule: stop the placement is not legal
    if (check != null) {
      return check;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard, coordinates);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }

}
