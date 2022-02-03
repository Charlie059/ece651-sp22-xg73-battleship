package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 20);
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<Character>(null);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Placement p = new Placement(c, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);

    // No error placeement
    assertEquals(null, checker.checkMyRule(battShip, board));

    Coordinate c2 = new Coordinate("T9");
    Placement p2 = new Placement(c2, 'v');
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.",
        checker.checkMyRule(battShip2, board));

  }

  @Test
  public void test_check_my_placement() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 20);
    PlacementRuleChecker<Character> checker_ = new InBoundsRuleChecker<Character>(null);

    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<Character>(checker_);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Placement p = new Placement(c, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);

    // Vaild Placement
    assertEquals(null, checker.checkPlacement(battShip, board));

    // Placement Out of bound
    Coordinate c2 = new Coordinate("T9");
    Placement p2 = new Placement(c2, 'h');
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    assertEquals("That placement is invalid: the ship goes off the right of the board.",
        checker.checkPlacement(battShip2, board));

    // Placement Out of bound
    Coordinate c3 = new Coordinate(-1, 0);
    Placement p3 = new Placement(c3, 'v');
    Ship<Character> battShip3 = v1shipfactory.makeBattleship(p3);
    assertEquals("That placement is invalid: the ship goes off the top of the board.",
        checker.checkPlacement(battShip3, board));

    // Placement Out of bound
    Coordinate c4 = new Coordinate(1, -1);
    Placement p4 = new Placement(c4, 'v');
    Ship<Character> battShip4 = v1shipfactory.makeBattleship(p4);
    assertEquals("That placement is invalid: the ship goes off the left of the board.",
        checker.checkPlacement(battShip4, board));



  }

}
