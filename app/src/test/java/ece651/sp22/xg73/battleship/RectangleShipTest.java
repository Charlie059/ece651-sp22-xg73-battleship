package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_make_object() {
    Coordinate c1 = new Coordinate(2, 3);
    Coordinate c2 = new Coordinate(2, 4);
    RectangleShip<Character> rect = new RectangleShip<Character>(c1, 2, 1,
        new SimpleShipDisplayInfo<Character>('s', '*'));

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
    Coordinate c4 = new Coordinate(2, 3);
    Coordinate c5 = new Coordinate(3, 2);
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

}
