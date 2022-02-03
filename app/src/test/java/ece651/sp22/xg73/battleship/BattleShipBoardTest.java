package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invaild_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-10, 0, 'X'));
  }

  @Test
  public void test_add_ships() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
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
        assertEquals(expect[i][j], b.whatIsAtForSelf(c));
      }
    }
  }

  @Test
  public void test_checkPlacement() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10, 'X');
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A7");
    Coordinate c2 = new Coordinate("A1");
    Coordinate c3 = new Coordinate("A3");

    Placement p = new Placement(c, 'h');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c3, 'v');
    Ship<Character> battShip1 = v1shipfactory.makeBattleship(p);
    Ship<Character> battShip3 = v1shipfactory.makeBattleship(p3);
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);

    // Out of bound
    assertEquals("That placement is invalid: the ship goes off the right of the board.", board.tryAddShip(battShip1));

    // Success add ship 2 to board
    assertEquals(null, board.tryAddShip(battShip2));

    // Coll -> fail
    assertEquals("That placement is invalid: the ship overlaps another ship.", board.tryAddShip(battShip3));
  }

  @Test
  public void checkfireAt() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Coordinate c1 = new Coordinate(0,0);
    Placement p1 = new Placement(c1, 'h');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> ship = sf.makeBattleship(p1);
    b1.tryAddShip(ship);
    b1.fireAt(c1);
    assertSame(ship,b1.fireAt(c1));

    Coordinate c2 = new Coordinate(0,1);
    Coordinate c3 = new Coordinate(0,2);
    Coordinate c4 = new Coordinate(0,3);
    b1.fireAt(c2);
    b1.fireAt(c3);
    b1.fireAt(c4);

    assertEquals(true,ship.isSunk());
  }

  @Test
  public void checkfireAtMiss() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Coordinate c1 = new Coordinate(0,0);
    Coordinate c2 = new Coordinate(4,0);
    Placement p1 = new Placement(c1, 'h');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> ship = sf.makeBattleship(p1);
    b1.tryAddShip(ship);
    b1.fireAt(c1);
    assertSame(null,b1.fireAt(c2));
  }

  @Test
  public void check_whatIsAt(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Coordinate c1 = new Coordinate(0,0);
    Placement p1 = new Placement(c1, 'h');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> ship = sf.makeBattleship(p1);
    b1.tryAddShip(ship);
    // is self
    assertEquals(b1.whatIsAt(c1,true), 'b');
    // is enemy
    assertEquals(b1.whatIsAt(c1,false), null);

  }

  @Test
  public void check_enemyMisses(){

  }

  @Test
  public void check_whatIsAtForEnemy(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Coordinate c1 = new Coordinate(0,0);
    Coordinate c2 = new Coordinate(4,0);
    Placement p1 = new Placement(c1, 'h');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> ship = sf.makeBattleship(p1);
    b1.tryAddShip(ship);
    assertEquals(null, b1.whatIsAtForEnemy(c1));

    b1.fireAt(c2);

    assertEquals('X', b1.whatIsAtForEnemy(c2));
  }

}
