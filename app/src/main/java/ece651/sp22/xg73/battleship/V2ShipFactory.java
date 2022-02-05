package ece651.sp22.xg73.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }


    @Override
    public Ship<Character> makeBattleship(Placement where) {
        if (where.orientation == 'U'){
            return createShip(where, 3, 2, 'b', "Battleship");
        }
        else if (where.orientation == 'R'){
            return createShip(where, 2, 3, 'b', "Battleship");
        }
        else if (where.orientation == 'D'){
            return createShip(where, 3, 2, 'b', "Battleship");
        }
        else if (where.orientation == 'L'){
            return createShip(where, 2, 3, 'b', "Battleship");
        }
        else return null;
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        if (where.orientation == 'U'){
            return createShip(where, 2, 5, 'c', "Carrier");
        }
        else if (where.orientation == 'R'){
            return createShip(where, 5, 2, 'c', "Carrier");
        }
        else if (where.orientation == 'D'){
            return createShip(where, 2, 5, 'c', "Carrier");
        }
        else if (where.orientation == 'L'){
            return createShip(where, 5, 2, 'c', "Carrier");
        }
        else return null;
    }


    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        Character orientation = where.getOrientation();
        RectangleShip<Character> ship;
        NonRectangleShip<Character> no_ship;
        if (name == "Submarine" || name == "Destroyer"){
            if (orientation == 'V') {
                ship = new RectangleShip<Character>(name, where, w, h, letter, '*');
                return ship;
            } else if (orientation == 'H'){
                ship = new RectangleShip<Character>(name, where, h, w, letter, '*');
                return ship;
            }
            else return null;
        }
        else{
                no_ship = new NonRectangleShip<Character>(name, name, where, w, h, letter, '*');
                return no_ship;
            }
    }

}
