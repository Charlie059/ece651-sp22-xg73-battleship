package ece651.sp22.xg73.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {

  final String name;
  private char orientation;
  private int width;
  private int height;

  /**
   * Get the ship name
   * 
   */
  public String getName() {
    return name;
  }


  /**
   * set orientation
   * @param orientation
   */
  @Override
  public void setOrientation(char orientation) {
    this.orientation = orientation;
  }

  /**
   * Get the ship orientation
   * @return orientation
   */
  @Override
  public char getOrientation() {
    return this.orientation;
  }


  /**
   * The Constructor of Rectangle ship
   *
   * @param String which def the name of this RectangleShip
   * @param Coordinate recoed the upperleft to locate the ship
   * @param int width
   * @param int height
   * @param Display the myDisplayinfo. ie: data if not hit, onHit if hit
   * @param Display the enemyDisplayInfo. ie: nothing if not hit, data if hit
   */
  public RectangleShip(String name, Placement upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft.getCoordinate(), width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
    this.orientation = upperLeft.orientation;
    this.width = width;
    this.height = height;
  }

  /**
   * The Constructor of Rectangle ship
   *
   * @param String which def the name of this RectangleShip
   * @param Coordinate recoed the upperleft to locate the ship
   * @param int width
   * @param int height
   * @param Type of data. ie: data if not hit, onHit if hit(myview); ie: nothing if not hit, data if hit(enemy view)
   * @param Type of onHit.
   */
  public RectangleShip(String name, Placement upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
            new SimpleShipDisplayInfo<T>(null, data));
  }

  public RectangleShip(Placement upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   * Static method to add the coordinate into the hashset
   * 
   * @param upperLeft of the ship coordinate
   * @param Ship      width
   * @param Ship      height
   * @return HashSet<Coordinate>
   */

  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coordinate c = new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j);
        ans.add(c);
      }
    }
    return ans;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
