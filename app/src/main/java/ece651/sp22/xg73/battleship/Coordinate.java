package ece651.sp22.xg73.battleship;

/**
 * Coordinate Class
 * 
 * @author Xuhui Gong
 * @version 1.0
 * @since 1.0
 */
public class Coordinate {
  private final int row;
  private final int column;

  /**
   * Constructs a {@link Coordinate} with row and col
   * 
   * @param Define the row of the coordinate
   * @param Define the column of the corrdinate
   * 
   */
  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Constructs a {@link Coordinate} with String. eg A2 (row=0, col = 2)
   * 
   * @param Define the String: The first char is define the row, and the second
   *               char is defined col
   * @throws
   */
  public Coordinate(String descr) {
    if (descr.length() != 2)
      throw new IllegalArgumentException("Coordinate's length must be 2 but is " + descr.length());

    String descr_ = descr.toUpperCase();
    char rowLetter = descr_.charAt(0);
    char colLetter = descr_.charAt(1);

    // Check the row letter
    if (rowLetter < 'A' || rowLetter > 'Z')  throw new IllegalArgumentException("Coordinate's row must be a letter but is " + descr.charAt(0));

    // Check the column letter
    if (!Character.isDigit(colLetter)) throw new IllegalArgumentException("Coordinate's column must be a number but is " + descr.charAt(1));

    // Init the value
    this.row = rowLetter - 'A';
    this.column = Character.getNumericValue(colLetter);
  }

  /**
   * Gets the coordinate’s row.
   * 
   * @return A int repersenting the coordinate row value.
   */

  public int getRow() {
    return this.row;
  }

  /**
   * Gets the coordinate’s columns.
   * 
   * @return A int repersenting the coordinate columns value.
   */
  public int getColumn() {
    return this.column;
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
      Coordinate c = (Coordinate) o;
      return this.row == c.getRow() && this.column == c.getColumn();
    }
    return false;
  }

  /**
   * Override the method of toString
   * 
   * @return A String will return which repersent row + col
   */
  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
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

}
