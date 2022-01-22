package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_palcement_equal() {
    Coordinate c1 = new Coordinate("c1");
    Coordinate c2 = new Coordinate("c1");
    Coordinate c3 = new Coordinate("c2");

    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c2, 'v');
    Placement p4 = new Placement(c3, 'h');
    Placement p5 = new Placement("c3H");
    Placement p6 = new Placement("c3V");
    Placement p7 = new Placement("d3H");
    Placement p8 = new Placement("c3h");

    assertEquals(p1, p1);
    assertEquals(p5, p5);
    assertEquals(p2, p3);
    assertEquals(p1, p3);
    assertNotEquals(p1, c1);
    assertNotEquals(p3, p4);
    assertNotEquals(p5, p6);
    assertNotEquals(p4, p5);
    assertEquals(p5, p8);
    assertNotEquals(p5, p7);
    assertNotEquals(p3, p8);
  }

  @Test
  public void test_palcement_orientation() {
    Coordinate c1 = new Coordinate("c1");

    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, '\n'));
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'c'));
    assertThrows(IllegalArgumentException.class, () -> new Placement("c2HH"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("c21v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("cHH"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("c2P"));

  }

  @Test
  public void test_getter() {
    Placement p1 = new Placement("c2H");
    Coordinate c = new Coordinate("c2");
    assertEquals('H', p1.getOrientation());
    assertEquals(c, p1.getCoordinate());
  }

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement("a2H");
    Placement p2 = new Placement("a2H");
    Placement p3 = new Placement("a2V");
    Placement p4 = new Placement("a3H");

    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
  }

}
