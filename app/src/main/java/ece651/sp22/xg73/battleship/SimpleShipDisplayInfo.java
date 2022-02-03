package ece651.sp22.xg73.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {

  T myData;
  T onHit;

  public SimpleShipDisplayInfo(T myData, T onHit) {
    this.myData = myData;
    this.onHit = onHit;
  }

  /**
   * getInfo check if (hit) and returns onHit if so, and myData otherwise.
   * 
   */

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit) return onHit;
    else return myData;
  }

}
