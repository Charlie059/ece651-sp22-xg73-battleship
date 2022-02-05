package ece651.sp22.xg73.battleship;
/**
 * This is an interface for basic Board.
 */
public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public T whatIsAtForSelf(Coordinate where);
  public T whatIsAtForEnemy(Coordinate where);
  public String tryAddShip(Ship<T> toAdd);
  public Ship<T> fireAt(Coordinate c);
  public boolean checkLose();
  public Ship<T> whichShip(Coordinate c);
  public String tryMoveShip(Coordinate from, Coordinate to, char orientation);
}
