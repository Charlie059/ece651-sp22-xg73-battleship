package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2,null);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expected = expectedHeader +  "A  |  A\n"+
      "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_10by20() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20,null);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2|3|4|5|6|7|8|9\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader
        + "A  | | | | | | | | |  A\nB  | | | | | | | | |  B\nC  | | | | | | | | |  C\nD  | | | | | | | | |  D\nE  | | | | | | | | |  E\nF  | | | | | | | | |  F\nG  | | | | | | | | |  G\nH  | | | | | | | | |  H\nI  | | | | | | | | |  I\nJ  | | | | | | | | |  J\nK  | | | | | | | | |  K\nL  | | | | | | | | |  L\nM  | | | | | | | | |  M\nN  | | | | | | | | |  N\nO  | | | | | | | | |  O\nP  | | | | | | | | |  P\nQ  | | | | | | | | |  Q\nR  | | | | | | | | |  R\nS  | | | | | | | | |  S\nT  | | | | | | | | |  T\n"
        + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  
  @Test
  public void test_display_empty_3by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, null);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader
        + "A  | |  A\nB  | |  B\n"
        + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  
  @Test
  public void test_display_empty_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, null);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader
        + "A  | |  A\nB  | |  B\nC  | |  C\nD  | |  D\nE  | |  E\n"
        + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  
  @Test
  public void test_invaild_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20,null);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27,null);

    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));

  }

}
