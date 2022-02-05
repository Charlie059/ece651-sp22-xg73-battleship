package ece651.sp22.xg73.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * BattleShipBoard Class which implement interface Board
 * 
 * @author Xuhui Gong
 * @version 1.0
 * @since 1.0
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  private final T missInfo;
  private HashMap<Coordinate, T> enemyViewOfHitShip;
  private HashMap<Coordinate, T> enemyViewOfHitShipAfterMove;
  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param width is the width of the newly constructed board.
   * @param height is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int width, int height, T missInfo) {
    if (width <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + width);
    }
    if (height <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + height);
    }
    this.width = width;
    this.height = height;
    this.myShips = new ArrayList<>();
    this.placementChecker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
    this.enemyViewOfHitShip = new HashMap<Coordinate, T>();
    this.enemyViewOfHitShipAfterMove = new HashMap<Coordinate, T>();
  }

  /**
   * This method should search for any ship that occupies coordinate c. If one is
   * found, that Ship is "hit" by the attack and should record it
   * Then we should return this ship.
   * 
   * @param c Coordinate to fire at
   * @return
   */
  public Ship<T> fireAt(Coordinate c) {
    for (int i = 0; i < myShips.size(); i++) {
      Ship<T> ship = myShips.get(i);
      if(ship.occupiesCoordinates(c) == true){
        ship.recordHitAt(c);
        return ship;
      }
    }
    // No hit record the miss
    enemyMisses.add(c);
    return null;
  }

  /**
   * Check if we lose the game. ie. sunk all the ships
   * @return if true, lose the game, else not lose now
   */
  @Override
  public boolean checkLose() {
    for(Ship<T> ship: myShips){
      if (ship.isSunk() == false){
        return false;
      }
    }
    return true;
  }

  /**
   * Give the coordination check which ship in this coordination or null
   * @param c coordination to be checked
   * @return Ship which occupy this coordination
   */
  @Override
  public Ship<T> whichShip(Coordinate c) {
    if (whatIsAt(c, true) == null) return null;
    for(Ship<T> ship: myShips){
      for(Coordinate shipCoordinate: ship.getCoordinates()){
        if(shipCoordinate.equals(c)){
          return ship;
        }
      }
    }
    return null;
  }


  /**
   * Record the hit info if we need to move teh ship
   * @param shipToBeMv
   */
  private void recordMoveShipHitInfo(Ship<T> shipToBeMv, HashMap<Coordinate, T> enemyViewOfHitShip, boolean before){
     Iterable<Coordinate> shipCoors = shipToBeMv.getCoordinates();
     for(Coordinate c: shipCoors){
       if(whatIsAtForEnemy(c) != null){
         enemyViewOfHitShip.put(c,whatIsAtForEnemy(c));
       }
       if(before == false){
         enemyViewOfHitShip.put(c,whatIsAtForEnemy(c));
       }
     }
  }


  @Override
  public String tryMoveShip(Coordinate from, Coordinate to, char DestOrientation) {
    Ship<T> shipToBeMv = whichShip(from);
    if(shipToBeMv == null) return "Invalid location: it seems like no ship in that position! Please try again!";

    // Record the enemyView of this ship hit coordinates and ship type
    recordMoveShipHitInfo(shipToBeMv, this.enemyViewOfHitShip, true);

    Iterable<Coordinate> originCoordinate = shipToBeMv.getCoordinates();
    Ship<T> dummyShip = null;
    String shipName = shipToBeMv.getName();
    if(shipName == "Submarine" || shipName == "Destroyer"){
      dummyShip = getDummyShip_Sub_Dest(to, DestOrientation, shipToBeMv, shipName);
    }
    else{
      dummyShip = getDummyShip_bat_Car(to, DestOrientation, shipToBeMv, shipName);
    }

    // Validate the location
    String result = this.placementChecker.checkPlacement(dummyShip, this, originCoordinate);
    if(result != null) return result;




    // Move the real ship
    moveShip(to, DestOrientation, shipToBeMv);

    // Record the before move coordinates and view
    recordMoveShipHitInfo(shipToBeMv,this.enemyViewOfHitShipAfterMove, false);

    return null;
  }

  private Ship<T> getDummyShip_bat_Car(Coordinate to, char DestOrientation, Ship<T> shipToBeMv, String shipName) {
    Ship<T> dummyShip;
    NonRectangleShip<Character> shipToBeMv_ = (NonRectangleShip<Character>) shipToBeMv;
    Placement p = new Placement(shipToBeMv.getLeftTopCoordinate(), shipToBeMv.getOrientation());
    dummyShip = (Ship<T>) new NonRectangleShip<Character>(shipName, shipName ,p, shipToBeMv_.getWidth(), shipToBeMv_.getHeight(), 't', '*');

    moveShip(to, DestOrientation, dummyShip);
    return dummyShip;
  }

  private void moveShip(Coordinate to, char DestOrientation, Ship<T> dummyShip) {
    // Check curr orientation
    char currOrientation = dummyShip.getOrientation();

    // Check the ship type
    String shipType = dummyShip.getName();

    int rotateTimes = getRotateTimes(DestOrientation, currOrientation, shipType);

    // Rotate the ship
    for (int i = 0; i < rotateTimes; i++) {
      dummyShip.rotateShip();
    }

    // Correct the location
    Coordinate wrongCoordinate =  dummyShip.getLeftTopCoordinate();
    int row_offset = to.getRow() - wrongCoordinate.getRow();
    int col_offset = to.getColumn() - wrongCoordinate.getColumn();

    dummyShip.shiftShip(row_offset, col_offset);
    // Update the ship orientation
    dummyShip.setOrientation(DestOrientation);
  }

  private Ship<T> getDummyShip_Sub_Dest(Coordinate to, char DestOrientation, Ship<T> shipToBeMv, String shipName) {
    Ship<T> dummyShip;
    RectangleShip<Character> shipToBeMv_ = (RectangleShip<Character>) shipToBeMv;
    Placement p = new Placement(shipToBeMv.getLeftTopCoordinate(), shipToBeMv.getOrientation());
    dummyShip = (Ship<T>) new RectangleShip<Character>(shipName, p, shipToBeMv_.getWidth(), shipToBeMv_.getHeight(), 't', '*');

    // Check curr orientation
    char currOrientation = dummyShip.getOrientation();

    // Check the ship type
    String shipType = dummyShip.getName();

    int rotateTimes = getRotateTimes(DestOrientation, currOrientation, shipType);

    // Rotate the ship
    for (int i = 0; i < rotateTimes; i++) {
      dummyShip.rotateShip();
    }

    // Correct the location
    Coordinate wrongCoordinate =  dummyShip.getLeftTopCoordinate();
    int row_offset = to.getRow() - wrongCoordinate.getRow();
    int col_offset = to.getColumn() - wrongCoordinate.getColumn();

    dummyShip.shiftShip(row_offset, col_offset);
    return dummyShip;
  }

  // TODO ERROR handling
  private int getRotateTimes(char DestOrientation, char currOrientation, String shipType) {
    if(shipType == "Submarine" || shipType == "Destroyer"){
      int currOrientationIdx = getIdxOfOrientation_Sub_Dest(currOrientation);
      int destOrientationIdx = getIdxOfOrientation_Sub_Dest(DestOrientation);
      if(currOrientationIdx > destOrientationIdx){
        destOrientationIdx += 2;
      }
      int rotateTimes = destOrientationIdx - currOrientationIdx;
      return rotateTimes;
    }
    else{ // Battleships and Carriers
      int currOrientationIdx = getIdxOfOrientation_Bat_Car(currOrientation);
      int destOrientationIdx = getIdxOfOrientation_Bat_Car(DestOrientation);
      if(currOrientationIdx > destOrientationIdx){
        destOrientationIdx += 4;
      }
      int rotateTimes = destOrientationIdx - currOrientationIdx;
      return rotateTimes;
    }
  }

  private int getIdxOfOrientation_Sub_Dest(char currOrientation) {
    int currOrientationIdx = -1;
    if(currOrientation == 'H'){
      currOrientationIdx = 0;
    }
    else if (currOrientation == 'V'){
      currOrientationIdx = 1;
    }
    return currOrientationIdx;
  }


  private int getIdxOfOrientation_Bat_Car(char currOrientation) {
    int currOrientationIdx = -1;
    if(currOrientation == 'U'){
      currOrientationIdx = 0;
    }
    else if (currOrientation == 'R'){
      currOrientationIdx = 1;
    }
    else if (currOrientation == 'D'){
      currOrientationIdx = 2;
    }
    else if (currOrientation == 'L'){
      currOrientationIdx = 3;
    }
    return currOrientationIdx;
  }



  /**
   * Gets the battleship board’s width.
   * 
   * @return A int repersenting board’s width.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the battleship board’s height.
   * 
   * @return A int repersenting board’s height.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Add Ships into the BattleShipBoard
   * 
   * @return Return true if we add the Ship seccess, else return false
   */
  public String tryAddShip(Ship<T> toAdd) {
    String result = this.placementChecker.checkPlacement(toAdd, this, null);
    if (result != null) {
      return result;
    }
    this.myShips.add(toAdd);
    return null;
  }

  /**
   * This method takes a Coordinate, and sees which (if any) Ship occupies that
   * coordinate.
   * @param where of Coordinate
   * @param isSelf is self or enemy
   * @return If one is found, we return whatever displayInfo it has at those
   *         coordinates. If none is found, we return null.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf){

    if(isSelf == true){
      for (Ship<T> s : myShips) {
        if (s.occupiesCoordinates(where)) {
          return s.getDisplayInfoAt(where, isSelf);
        }
      }
    }
     else if(isSelf == false){
       for (Ship<T> s : myShips) {
         if(enemyMisses.contains(where)){
           return missInfo;
         }
         if(enemyViewOfHitShip.containsKey(where)){
           return enemyViewOfHitShip.get(where);
         }
         if(enemyViewOfHitShipAfterMove.containsKey(where)){
           return null;
         }
         if (s.occupiesCoordinates(where)) {
           return s.getDisplayInfoAt(where, false);
         }
       }
       
    }
    return null;
  }

  /**
   * This method takes a Coordinate, and sees which (if any) Ship occupies that
   * coordinate.
   * @param where of Coordinate
   * @return If one is found, we return whatever displayInfo it has at those
   *         coordinates. If none is found, we return null.
   */

  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }



}
