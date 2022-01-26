package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Integer> display = new SimpleShipDisplayInfo<Integer>(1,2);
    Coordinate c = new Coordinate(3,4);
    assertEquals(2, display.getInfo(c, true));
  }

}
