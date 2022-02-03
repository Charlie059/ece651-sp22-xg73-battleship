package ece651.sp22.xg73.battleship;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {

  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * 
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
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
  public String makeRows(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      ans.append((char)('A' + i) + " ");
      for (int j = 0; j < toDisplay.getWidth(); j++) {
         Coordinate c = new Coordinate(i,j);
        if (j != toDisplay.getWidth() - 1) {
          ans.append(getSquareFn.apply(c) != null ?getSquareFn.apply(c):" ");
          ans.append("|");
        }
        else{
          ans.append(getSquareFn.apply(c) != null ? getSquareFn.apply(c):" ");
          ans.append(" " + (char)('A' + i) + "\n");
        }
      }
    }
    return ans.toString();
  }

  /**
   * This display the empty board
   * @param getSquareFn
   * @return the String that display the empty board with head and rows
   */
  public String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    ans.append(makeRows(getSquareFn));
    ans.append(makeHeader());
    return ans.toString();
  }


  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard(){
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }


  /**
   * Make Space of Str
   * @param size Space needed
   * @return Space Str
   */
  public String makeSpace(int size){
    String ans = new String();
    for(int i = 0; i < size; i++){
      ans += " ";
    }
    return ans;
  }
  /**
   *
   * @param enemyView BoardTextView Object of enemy view
   * @param myHeader My "header" lines, shown in the README, e.g.
   *  "Your ocean"
   * @param enemyHeader Enemy "header" lines, shown in the README, e.g.
   *  "Player B's ocean".
   * @return A string which shows the two board
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String ans = new String();
    String spaceOfBoard = "                "; // 16 space
    String offset = "  "; // 2 space
    String header =  makeHeader(myHeader, enemyHeader);
    ans += header;

    // Spilt the board
    String[] myViewSpilt = displayMyOwnBoard().split("\n");
    String[] enemyViewSpilt = enemyView.displayEnemyBoard().split("\n");
    ArrayList<String> mergeView = new ArrayList<>();
    for (int i = 0; i < myViewSpilt.length; i++) {
      // If not the first line or the last line
      if(i != 0 && i != myViewSpilt.length - 1){
        mergeView.add(myViewSpilt[i] + spaceOfBoard + enemyViewSpilt[i]);
      }
      else {
        mergeView.add(myViewSpilt[i]  + spaceOfBoard + offset + enemyViewSpilt[i]);
      }
      ans += mergeView.get(i) + "\n";
    }
    return ans;
  }

  /**
   * Make haeder of displayMyBoardWithEnemyNextToIt
   * @param myHeader
   * @param enemyHeader
   * @return
   */
  private String  makeHeader(String myHeader, String enemyHeader) {
    String spaceOfFirstHeader = "     "; // 5 space
    int w = this.toDisplay.getWidth();
    int secondHeaderStart = 2 * w + 22;
    int myHeaderLen = myHeader.length();
    int spaceBetweenHeader = secondHeaderStart - 5 - myHeaderLen;
    String spaceOfGapBetweenHeader = makeSpace(spaceBetweenHeader);
    String header = spaceOfFirstHeader + myHeader + spaceOfGapBetweenHeader +  enemyHeader + "\n";
    return header;
  }
}
