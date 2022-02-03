package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_10by20() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
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
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\nC  | |  C\nD  | |  D\nE  | |  E\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invaild_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');

    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));

  }

  @Test
  public void test_display_add_ships_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    Coordinate c1 = new Coordinate("c1");
    Coordinate c2 = new Coordinate("e2");

    RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\nB  | |  B\nC  |s|  C\nD  | |  D\nE  | |s E\n" + expectedHeader;

    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_displayAnyBoard(){
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4,3,'X');
    Coordinate c1 = new Coordinate("b0");
    Coordinate c2 = new Coordinate("a3");
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement(c1, 'H'));
    Ship<Character> s2 = sf.makeDestroyer(new Placement(c2, 'v'));
    board.tryAddShip(s1);
    board.tryAddShip(s2);
    BoardTextView view = new BoardTextView(board);

    String myView =
            "  0|1|2|3\n" +
                    "A  | | |d A\n" +
                    "B s|s| |d B\n" +
                    "C  | | |d C\n" +
                    "  0|1|2|3\n";
    //make sure we laid things out the way we think we did.
    assertEquals(myView, view.displayMyOwnBoard());

    String myView_hit =
            "  0|1|2|3\n" +
                    "A  | | |d A\n" +
                    "B *|s| |d B\n" +
                    "C  | | |d C\n" +
                    "  0|1|2|3\n";

    board.fireAt(c1);
    assertEquals(myView_hit, view.displayMyOwnBoard());

    String enemyView_hit =
            "  0|1|2|3\n" +
                    "A  | | |  A\n" +
                    "B s| | |  B\n" +
                    "C  | | |  C\n" +
                    "  0|1|2|3\n";
    assertEquals(enemyView_hit, view.displayEnemyBoard());

    String enemyView_hit_Enemy =
            "  0|1|2|3\n" +
                    "A  | | |  A\n" +
                    "B s| | |  B\n" +
                    "C X| | |  C\n" +
                    "  0|1|2|3\n";
    Coordinate c3 = new Coordinate("c0");
    board.fireAt(c3);
    assertEquals(enemyView_hit_Enemy, view.displayEnemyBoard());
  }



}
