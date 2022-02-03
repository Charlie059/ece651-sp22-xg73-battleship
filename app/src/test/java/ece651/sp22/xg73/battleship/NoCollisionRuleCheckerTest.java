package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule_coll() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10, 'X');
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(null);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Coordinate c2 = new Coordinate("A1");
    Coordinate c3 = new Coordinate("c3");

    Placement p = new Placement(c, 'h');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c3, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);
    board.tryAddShip(battShip);

    // Coll with b1
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkMyRule(battShip2, board));

    // No coll
    Ship<Character> battShip3 = v1shipfactory.makeBattleship(p3);
    assertEquals(null, checker.checkMyRule(battShip3, board));

  }

  @Test
  public void test_checkMyRule_coll_and_In_bound() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10, 'X');

    PlacementRuleChecker<Character> in_bound_checker = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> no_coll_checker = new NoCollisionRuleChecker<Character>(in_bound_checker);

    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Coordinate c2 = new Coordinate("A1");
    Coordinate c3 = new Coordinate("c3");

    Placement p = new Placement(c, 'h');
    Placement p2 = new Placement(c2, 'h');
    Placement p3 = new Placement(c3, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);
    board.tryAddShip(battShip);
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);

    assertEquals("That placement is invalid: the ship overlaps another ship.", no_coll_checker.checkMyRule(battShip2, board));


    Ship<Character> battShip3 = v1shipfactory.makeBattleship(p3);
    assertEquals(null, no_coll_checker.checkMyRule(battShip3, board));

  }

}
