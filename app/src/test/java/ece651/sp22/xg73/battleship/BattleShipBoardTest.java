package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invaild_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 0));
  }

  @Test
  public void test_add_ships() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3);
    Character expect[][] = new Character[4][3];
    checkWhatIsAtBoard(b1, expect);

    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');
    b1.tryAddShip(s);
    expect[1][2] = 's';
    checkWhatIsAtBoard(b1, expect);

  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expect) {
    int width = b.getWidth();
    int height = b.getHeight();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Coordinate c = new Coordinate(i, j);
        assertEquals(expect[i][j], b.whatIsAt(c));
      }
    }
  }

  @Test
  public void test_checkPlacement() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A7");
    Coordinate c2 = new Coordinate("A1");
    Coordinate c3 = new Coordinate("A3");

    Placement p = new Placement(c, 'h');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c3, 'v');
    Ship<Character> battShip1 = v1shipfactory.makeBattleship(p);
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    Ship<Character> battShip3 = v1shipfactory.makeBattleship(p3);
    // Out of bound
    assertEquals(false, board.tryAddShip(battShip1));

    // Success add ship 2 to board
    assertEquals(true, board.tryAddShip(battShip2));

    // Coll -> fail
    assertEquals(false, board.tryAddShip(battShip3));
  }

}
