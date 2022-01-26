package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_ship_v() {
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    V1ShipFactory f = new V1ShipFactory();

    Ship<Character> sub = f.makeSubmarine(v1_2);
    checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));

    Ship<Character> bat = f.makeBattleship(v1_2);
    checkShip(bat, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2));

    Ship<Character> car = f.makeCarrier(v1_2);
    checkShip(car, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
        new Coordinate(4, 2), new Coordinate(5, 2), new Coordinate(6, 2));

  }

  @Test
  public void test_ship_h() {

    Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory f = new V1ShipFactory();

    Ship<Character> sub = f.makeSubmarine(h1_2);
    checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));

    Ship<Character> dst = f.makeDestroyer(h1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

    Ship<Character> bat = f.makeBattleship(h1_2);
    checkShip(bat, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
        new Coordinate(1, 5));

    Ship<Character> car = f.makeCarrier(h1_2);
    checkShip(car, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
        new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(1, 7));
  }

  
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);

    for (Coordinate c : expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c));
    }
  }
}
