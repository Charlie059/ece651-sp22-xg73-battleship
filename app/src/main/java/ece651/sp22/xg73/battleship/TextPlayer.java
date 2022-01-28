package ece651.sp22.xg73.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  final String name;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;

  public Placement readPlacement(String prompt) throws IOException {
    this.out.println(prompt);
    String s = inputReader.readLine();
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
    this.doOnePlacement();

  }

  public void doOnePlacement() throws IOException {
    Placement p = this.readPlacement("Player " + this.name + " where do you want to place a Destroyer?");
    Ship<Character> s = shipFactory.makeDestroyer(p);
    this.theBoard.tryAddShip(s);
    this.out.print(view.displayMyOwnBoard());
  }

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader br, PrintStream out, V1ShipFactory factory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = br;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;
  }

}
