package ece651.sp22.xg73.battleship;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NonRectangleShipTest {
    @Test
    public void test_make_NonRectangleShip_object(){
        Coordinate c = new Coordinate(2, 3);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        NonRectangleShip nonShip = new NonRectangleShip("Battleship", "Battleship", p, 3, 2, myDisplay, enemyDisplay);


        Coordinate c1 = new Coordinate(3, 3);
        Coordinate c2 = new Coordinate(3, 4);
        Coordinate c3 = new Coordinate(2, 4);
        Coordinate c4 = new Coordinate(3, 5);
        ArrayList<Coordinate> Exceptedcoords = new ArrayList<Coordinate>();
        Exceptedcoords.add(c1);
        Exceptedcoords.add(c2);
        Exceptedcoords.add(c3);
        Exceptedcoords.add(c4);

        Iterable<Coordinate> coords = nonShip.getCoordinates();
        int i = 0;
        for(Coordinate coord: coords){
            assertEquals(coord, Exceptedcoords.get(i));
            i++;
        }



    }



    @Test
    public void test_make_NonRectangleShip_object_Carr(){
        Coordinate c = new Coordinate(0, 0);
        Placement p = new Placement(c,'U');
        ShipDisplayInfo<Character> myDisplay = new SimpleShipDisplayInfo<>('s', '*');
        ShipDisplayInfo<Character> enemyDisplay = new SimpleShipDisplayInfo<>(null, 's');
        NonRectangleShip nonShip = new NonRectangleShip("Carrier", "Carrier", p, 2, 5, myDisplay, enemyDisplay);
        Coordinate c1 = new Coordinate(0, 0);

    }
}