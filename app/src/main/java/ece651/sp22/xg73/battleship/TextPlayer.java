package ece651.sp22.xg73.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final String name;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader br, PrintStream out, V1ShipFactory factory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = br;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationList();
    setupShipCreationMap();
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("BattleShip", (p) -> shipFactory.makeBattleship(p));
  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    shipsToPlace.addAll(Collections.nCopies(3, "BattleShip"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
  }

  /**
   * Read the user input of placement data
   * 
   * @param String a prompt taht info the user
   * @return return new placement object
   * @throws  if user inpit null, thorw {@link IOException}
   */
  public Placement readPlacement(String prompt) throws IOException {
    this.out.println(prompt);
    String s = inputReader.readLine();
    if (s == null)
      throw new IOException("Recieve null string in readPlacement");
    return new Placement(s);
  }

  public void doPlacementPhase() throws IOException {
    String instrcution = "Player " + this.name + ": you are going to place the following ships (which are all\n"
        + "rectangular). For each ship, type the coordinate of the upper left\n"
        + "side of the ship, followed by either H (for horizontal) or V (for\n"
        + "vertical).  For example M4H would place a ship horizontally starting\n"
        + "at M4 and going to the right.  You have\n\n" +

        "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n"
        + "2 \"Carriers\" that are 1x6\n";
    this.out.print(view.displayMyOwnBoard() + instrcution);
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }

  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    theBoard.tryAddShip(s);
    out.print(view.displayMyOwnBoard());
  }

}
