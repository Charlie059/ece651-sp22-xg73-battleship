package ece651.sp22.xg73.battleship;

public class Placement {
  final Coordinate where;
  final char orientation;

  public Placement(Coordinate where, char orientation) {
    this.where = where;
    String orientationStr_ = checkOrientation(String.valueOf(orientation));
    this.orientation = orientationStr_.charAt(0);
  }

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

  private String checkOrientation(String orientationStr) {
    // Let the orientation to upper case
    String orientationStr_ = orientationStr.toUpperCase();

    // Check orientaion
    if (!(orientationStr_.equals("H") || orientationStr_.equals("V"))) {
      throw new IllegalArgumentException(
          "Placement's orientation string must be either H or V but is " + orientationStr);
    }
    return orientationStr_;
  }

  Coordinate getCoordinate() {
    return this.where;
  }

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
