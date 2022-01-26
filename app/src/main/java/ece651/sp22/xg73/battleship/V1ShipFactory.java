package ece651.sp22.xg73.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    Character orientation = where.getOrientation();
    RectangleShip<Character> ship;
    if (orientation == 'V') {
      ship = new RectangleShip<Character>(name, where.getCoordinate(), w, h, letter, '*');
    } else {
      ship = new RectangleShip<Character>(name, where.getCoordinate(), h, w, letter, '*');
    }
    return ship;
  }

}
