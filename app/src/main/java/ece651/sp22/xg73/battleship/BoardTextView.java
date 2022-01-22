package ece651.sp22.xg73.battleship;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {

  /**
   * The Board to display
   */
  private final Board toDisplay;

  /**
   * 
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  public String makeHeader() {
    StringBuilder ans = new StringBuilder("  ");
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(i);
      if (i != toDisplay.getWidth() - 1) {
        ans.append("|");
      }
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This makes the rows e.g. A  | | | |  A\n
   * 
   * @return the String that is the rows (except header) for the given board
   */
  public String makeRows() {
    StringBuilder ans = new StringBuilder("");
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      ans.append((char)('A' + i) + " ");
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        if (j != toDisplay.getWidth() - 1) {
          ans.append(" |");
        }
      }
      ans.append("  " + (char)('A' + i) + "\n");
    }
    return ans.toString();
  }


  /**
   * This display the empty board
   * 
   * @return the String that display the empty board with head and rows
   */
  public String displayMyOwnBoard() {
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    ans.append(makeRows());
    ans.append(makeHeader());
    return ans.toString();
  }
}
