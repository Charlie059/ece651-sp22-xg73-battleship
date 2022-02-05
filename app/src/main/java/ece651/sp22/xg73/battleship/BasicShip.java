package ece651.sp22.xg73.battleship;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {


  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;



  /**
   * Get the ship left top Coordinate
   */
  public Coordinate getLeftTopCoordinate(){
    ArrayList<Integer> xs = new ArrayList<Integer>();
    ArrayList<Integer> ys = new ArrayList<Integer>();
    for (Coordinate key : myPieces.keySet()) {
      xs.add(key.getRow());
      ys.add(key.getColumn());
    }

    int x = Collections.min(xs);
    int y = Collections.min(ys);
    return new Coordinate(x, y);
  }


  /**
   * Rotate the ship by 90 degree clockwise
   */
  public void rotateShip(){
    HashMap<Coordinate, Boolean> pieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate key : myPieces.keySet()) {
      Coordinate newCoordination = new Coordinate(key.getColumn(), -1 * key.getRow() );
      pieces.put(newCoordination, myPieces.get(key));
    }
    this.myPieces = pieces;

    // Get the min(row, col)
    Coordinate c = getLeftTopCoordinate();
    int offset = Math.min(c.getRow(),c.getColumn());
    shiftShip(-1 * offset, -1 *offset);
  }
  /**
   * Shift the ship
   * @param rows offset of rows
   * @param cols offset of cols
   */
  public void shiftShip(int rows, int cols){
    HashMap<Coordinate, Boolean> pieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate key : myPieces.keySet()) {
      Coordinate newCoordination = new Coordinate(key.getRow() + rows, key.getColumn() + cols);
      pieces.put(newCoordination, myPieces.get(key));
    }
    this.myPieces = pieces;
  }
  /**
   * Constructs a basic ship with iterable Coordinate c
   * 
   * @param Coordinate of the ship to the HashMap
   * 
   */

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
  }

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return this.myPieces.keySet();
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return this.myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (myPieces.get(c) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Record the hit if the coordinate is vaild
   * 
   * @param where Coordinate
   */
  @Override
  public void recordHitAt(Coordinate where) {
    // Check the coordinate is vaild first
    checkCoordinateInThisShip(where);

    // Check the status of the current coordiante
    if (this.myPieces.get(where) == false) {
      this.myPieces.replace(where, true);
    }

  }

  /**
   * Check if this coordinate is hit
   * 
   * @param Coordinate where'
   * @return Return if this coordinate is hit
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    // Check the coordinate is vaild first
    checkCoordinateInThisShip(where);

    if (this.myPieces.get(where) == true) {
      return true;
    }
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    // look up the hit status of this coordinate
    if(myShip == true){
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    else{
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }

  }

  /**
   * Check if the coordinate is vaild or is not in this ship.
   * 
   * @param Coordinate c
   * @exception if c is not in this ship, throw an IllegalArgumentException.
   */
  protected void checkCoordinateInThisShip(Coordinate c) throws IllegalArgumentException {
    if (this.myPieces.get(c) == null) {
      throw new IllegalArgumentException(
          "The Coordinate must in this ship, but this coordinate: Col: " + c.getColumn() + " Row: " + c.getRow());
    }
  }

}
