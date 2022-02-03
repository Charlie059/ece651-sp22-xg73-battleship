package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_make_object() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1, 's', '*');

    HashMap<Coordinate, Boolean> res = rect.myPieces;
    HashMap<Coordinate, Boolean> excepted = new HashMap<>();
    excepted.put(c1, false);
    excepted.put(c2, false);
    assertEquals(excepted, res);

  }

  @Test
  public void test_make_coordinate() {
    Coordinate c = new Coordinate(1, 2);

    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 3);
    Coordinate c3 = new Coordinate(2, 2);
    Coordinate c5 = new Coordinate(3, 2);
    Coordinate c4 = new Coordinate(2, 3);
    Coordinate c6 = new Coordinate(3, 3);
    HashSet<Coordinate> excepted = new HashSet<Coordinate>();
    excepted.add(c1);
    excepted.add(c2);
    excepted.add(c3);
    excepted.add(c4);
    excepted.add(c5);
    excepted.add(c6);

    assertEquals(excepted, RectangleShip.makeCoords(c, 2, 3));
  }

  @Test
  public void test_checkCoordinateInThisShip() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 5);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
        's', '*');
    assertThrows(IllegalArgumentException.class, () -> rect.checkCoordinateInThisShip(c2));
  }

  @Test
  public void test_get_coordinates() {
    Coordinate c1 = new Coordinate("c1");
    Coordinate c2 = new Coordinate("c1");
    Coordinate c3 = new Coordinate("c2");

    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
        's', '*');
    Set<Coordinate> excpted = new HashSet<Coordinate>();
    excpted.add(c2);
    excpted.add(c3);
    assertEquals(rect.getCoordinates(), excpted);
  }

  @Test
  public void test_record_hit() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
        's', '*');
    rect.recordHitAt(c2);
    assertEquals(rect.wasHitAt(c2), true);
    assertEquals(rect.wasHitAt(c1), false);

    rect.recordHitAt(c2);
    assertEquals(rect.wasHitAt(c2), true);
  }

  @Test
  public void test_is_sunk() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1, 's', '*');
    rect.recordHitAt(c1);
    rect.recordHitAt(c2);
    assertEquals(rect.isSunk(), true);
  }

  @Test
  public void test_is_not_sunk() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
        's', '*');
    rect.recordHitAt(c1);
    assertEquals(rect.isSunk(), false);
  }

  @Test
  public void test_get_display_info() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
        's', '*');

    assertEquals('s', rect.getDisplayInfoAt(c2,true));
    rect.recordHitAt(c2);
    assertEquals('*', rect.getDisplayInfoAt(c2,true));
  }

  @Test
  public void test_get_name() {
    Coordinate c1 = new Coordinate(2, 3);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1, 's', '*');
    assertEquals("destroyer", rect.getName());
  }

  @Test
  public void test_getDisplayInfoAt() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 3);
    RectangleShip<Character> rect = new RectangleShip<Character>("destroyer", c1, 2, 1,
            's', '*');
    rect.recordHitAt(c2);
    assertEquals(rect.getDisplayInfoAt(c1,true), '*');
    assertEquals(rect.getDisplayInfoAt(c1,false), 's');
  }
}
