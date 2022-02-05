package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
    @Test
    public void test_shift_ship(){
        Coordinate c = new Coordinate(0, 0);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        Ship<Character> ship = new NonRectangleShip("Carrier", "Carrier", p, 2, 5, myDisplay, enemyDisplay);
        ship.shiftShip(2,3);
        Placement p1 = new Placement(c,'U');
    }


    @Test
    public void test_getLeftTopCoordinate(){
        Coordinate c = new Coordinate(0, 0);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        Ship<Character> ship = new NonRectangleShip("Carrier", "Carrier", p, 2, 5, myDisplay, enemyDisplay);
        ship.shiftShip(2,3);
        Coordinate c1 =  ship.getLeftTopCoordinate();
        assertEquals(c1.getRow(), 2);
        assertEquals(c1.getColumn(), 3);
    }

    @Test
    public void test_getLeftTopCoordinate_Shift_Rotate(){
        Coordinate c = new Coordinate(0, 0);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        Ship<Character> ship = new NonRectangleShip("Battleship", "Battleship", p, 3, 2, myDisplay, enemyDisplay);

        ship.shiftShip(2,3);
        Coordinate c1 =  ship.getLeftTopCoordinate();
        assertEquals(c1.getRow(), 2);
        assertEquals(c1.getColumn(), 3);

        ship.rotateShip();
        Coordinate c2 =  ship.getLeftTopCoordinate();
        assertEquals(c2.getRow(), 6);
        assertEquals(c2.getColumn(), 0);

        ship.rotateShip();
        ship.rotateShip();
        Coordinate c3 =  ship.getLeftTopCoordinate();
        ship.shiftShip(-1 * c3.getRow(),-1 * c3.getColumn());

        Coordinate c4 =  ship.getLeftTopCoordinate();
    }

    @Test
    public void getOrientation(){
        Coordinate c = new Coordinate(0, 0);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        Ship<Character> ship = new NonRectangleShip("Battleship", "Battleship", p, 3, 2, myDisplay, enemyDisplay);
        assertEquals('U', ship.getOrientation());
    }
}
