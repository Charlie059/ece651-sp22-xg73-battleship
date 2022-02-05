package ece651.sp22.xg73.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {
    @Test
    public void test_sub(){
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        V2ShipFactory f = new V2ShipFactory();

        Ship<Character> sub = f.makeSubmarine(v1_2);
        checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

        Ship<Character> dst = f.makeDestroyer(v1_2);
        checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));

        Placement v0_R = new Placement(new Coordinate(0, 0), 'R');
        Ship<Character> bat = f.makeBattleship(v0_R);

        Placement v0_D = new Placement(new Coordinate(0, 0), 'D');
        Ship<Character> car2 = f.makeCarrier(v0_D);
        Ship<Character> car = f.makeCarrier(v0_R);


    }


    private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
                           Coordinate... expectedLocs) {
        assertEquals(testShip.getName(), expectedName);

        for (Coordinate c : expectedLocs) {
            assertEquals(true, testShip.occupiesCoordinates(c));
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c,true));
        }
    }
}