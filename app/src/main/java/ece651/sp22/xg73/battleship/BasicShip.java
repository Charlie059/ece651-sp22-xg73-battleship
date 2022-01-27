package ece651.sp22.xg73.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {

  // private final Coordinate myLocation;
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  /**
   * Constructs a basic ship with iterable Coordinate c
   * 
   * @param Coordinate of the ship to the HashMap
   * 
   */

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
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
   * @param Coordinate where
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
  public T getDisplayInfoAt(Coordinate where) {
    // look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, wasHitAt(where));
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
