package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    ArrayList<Ship<Character>> ships = new ArrayList<>();
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, ships);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invaild_dimensions() {
    ArrayList<Ship<Character>> ships = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, ships));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, ships));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, ships));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 5, ships));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 0, ships));
  }

  @Test
  public void test_add_ships() {
    ArrayList<Ship<Character>> ships = new ArrayList<>();
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, ships);
    Character expect[][] = new Character[4][3];
    checkWhatIsAtBoard(b1, expect);

    Coordinate c = new Coordinate(1,2);
    BasicShip s = new BasicShip(c);
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

}
