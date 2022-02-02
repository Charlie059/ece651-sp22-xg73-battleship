package ece651.sp22.xg73.battleship;
/**
 * This is an interface for basic Board.
 */
public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public T whatIsAt(Coordinate where);
  public String tryAddShip(Ship<T> toAdd);
}
