package ece651.sp22.xg73.battleship;

/**
 * Placement Class
 * 
 * @author Xuhui Gong
 * @version 1.0
 * @since 1.0
 */
public class Placement {
  final Coordinate where;
  final char orientation;

  /**
   * Constructs a placement with coordinate and orientation
   * 
   * @param A Coordinate type of where info
   * @param A char indicate the orientation. eg: H or V
   * @throws Throw if Coordinate is invaild or orinentation is not H or V
   */

  public Placement(Coordinate where, char orientation) {
    this.where = where;
    String orientationStr_ = checkOrientation(String.valueOf(orientation));
    this.orientation = orientationStr_.charAt(0);
  }

  /**
   * Constructs a placement with String. eg: a2H
   * 
   * @param A String with first two char defined type of where info and A char
   *          indicate the orientation. eg: H or V
   * @throws Throw if Coordinate is invaild or orinentation is not H or V
   * 
   */

  public Placement(String str) {
    if (str.length() != 3)
      throw new IllegalArgumentException("Placement's string must be length of three but is " + str.length());

    // Get the subString
    String coordinateStr = str.substring(0, 2);
    String orientationStr = str.substring(2, 3);

    Coordinate coor = new Coordinate(coordinateStr);

    String orientationStr_ = checkOrientation(orientationStr);

    this.where = coor;
    this.orientation = orientationStr_.charAt(0);
  }

  /**
   * Check the orinentation is either H or V.
   * 
   * @return A uppercase string if pass the test.
   */
  private String checkOrientation(String orientationStr) {
    // Let the orientation to upper case
    String orientationStr_ = orientationStr.toUpperCase();

    // Check orientaion
    if (!(orientationStr_.equals("H") || orientationStr_.equals("V"))) {
      throw new IllegalArgumentException(
          "That placement is invalid: it does not have the correct format.");
    }
    return orientationStr_;
  }

  /**
   * Gets the coordinate object.
   * 
   * @return A coordinate object.
   */

  Coordinate getCoordinate() {
    return this.where;
  }

  /**
   * Gets the orientation char.
   * 
   * @return A char indicate the orientation.
   */

  char getOrientation() {
    return this.orientation;
  }

  /**
   * Override the method of toString
   * 
   * @return A String will return which repersent row + col
   */

  @Override
  public String toString() {
    return "(" + where.getRow() + ", " + where.getColumn() + orientation + ")";
  }

  /**
   * Override the method of hashCode
   * 
   * @return A int will return which repersent the hash code of String
   */

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Override the method of equals to compare the coordinate
   * 
   * @return a boolean will be return to indicate that if the two objects are
   *         euqal
   */

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(this.getClass())) {
      Placement c = (Placement) o;
      return this.orientation == c.orientation && this.where.equals(c.where);
    }
    return false;
  }

}
