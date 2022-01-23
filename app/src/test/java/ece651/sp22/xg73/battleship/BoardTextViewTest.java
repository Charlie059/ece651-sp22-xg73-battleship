package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_10by20() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
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
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\nC  | |  C\nD  | |  D\nE  | |  E\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invaild_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);

    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));

  }

  @Test
  public void test_display_add_ships_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5);
    Coordinate c1 = new Coordinate("c1");
    Coordinate c2 = new Coordinate("e2");

    Ship<Character> s1 = new BasicShip(c1);
    Ship<Character> s2 = new BasicShip(c2);
    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\nC  |s|  C\nD  | |  D\nE  | |s E\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

}
