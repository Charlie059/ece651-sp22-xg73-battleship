package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10);
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<Character>(null);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Placement p = new Placement(c, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);
    assertEquals(true, checker.checkMyRule(battShip, board));
    Coordinate c2 = new Coordinate("T9");
    Placement p2 = new Placement(c2, 'h');
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    assertEquals(false, checker.checkMyRule(battShip2, board));

  }

  @Test
  public void test_check_my_placement() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10);
    PlacementRuleChecker<Character> checker_ = new InBoundsRuleChecker<Character>(null);

    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<Character>(checker_);
    V1ShipFactory v1shipfactory = new V1ShipFactory();
    Coordinate c = new Coordinate("A0");
    Placement p = new Placement(c, 'h');
    Ship<Character> battShip = v1shipfactory.makeBattleship(p);
    assertEquals(true, checker.checkPlacement(battShip, board));
    Coordinate c2 = new Coordinate("T9");
    Placement p2 = new Placement(c2, 'h');
    Ship<Character> battShip2 = v1shipfactory.makeBattleship(p2);
    assertEquals(false, checker.checkPlacement(battShip2, board));

  }

}
