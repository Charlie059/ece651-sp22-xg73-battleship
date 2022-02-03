package ece651.sp22.xg73.battleship;

import org.checkerframework.checker.units.qual.C;

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

  /**
   * Constructor of TextPlayer
   * @param name Player name. eg. Player A
   * @param theBoard Player's board
   * @param br Read the placement buffer
   * @param out Print the Map out
   * @param factory The Ship Factory object
   */
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

  /**
   * One player play a turn. Give the prompt and both view, hit, report hit
   * @param enemyBoard
   * @param view
   * @param playerName Player A or B
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView view, String playerName){
    String turnPrompt = "Player " + playerName + "'s turn";
    String attackPrompt = "Player " + playerName + " select a coordinate to attack!";
    this.out.println(turnPrompt);

    // Display the enemy board
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    this.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your ocean", "Player B's ocean"));
    Coordinate attackCoordinate = reReadPlacement(attackPrompt, true).getCoordinate();
    Ship<Character> ship = enemyBoard.fireAt(attackCoordinate);

    // Give the attack feedback
    if(ship == null)  this.out.println("You missed!");
    else this.out.println("You hit a " + ship.getName() + "!");
  }



  /**
   * Set up V1 ships shapes by shipFactory
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", shipFactory::makeSubmarine);
    shipCreationFns.put("Carrier", shipFactory::makeCarrier);
    shipCreationFns.put("Destroyer", shipFactory::makeDestroyer);
    shipCreationFns.put("BattleShip", shipFactory::makeBattleship);
  }

  /**
   * Add up nums of V1 ships shapes to shipsToPlace
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    shipsToPlace.addAll(Collections.nCopies(3, "BattleShip"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
  }

  /**
   * Read the user input of placement data
   * 
   * @param prompt a String taht info the user
   * @return return new placement object
   * @throws  if user input null, throws IOException
   */
  public Placement readPlacement(String prompt, boolean getCoordinateOnly) throws IOException {
    this.out.println(prompt);
    String s = inputReader.readLine();
    if (s == null)
      throw new IOException("Receive null string in readPlacement");

    if(getCoordinateOnly == true) s += "H";
    return new Placement(s);
  }


  public Placement reReadPlacement(String prompt, boolean getCoordinateOnly){
    Placement placementRes = null;
    boolean userReEnter = false;
    do {
      try {
        Placement placement = readPlacement(prompt, getCoordinateOnly);
        userReEnter = false; // if pass the Coordinate test
        placementRes = placement; // pass the var outside
      } catch (IOException e) {
        this.out.println("Invalid Coordinate(Empty input), please enter again!");
        userReEnter = true;
      } catch (IllegalArgumentException e){
        this.out.println("Invalid Coordinate, please enter again!");
        userReEnter = true;
      }
    }
    while (userReEnter == true);
    return placementRes;
  }

  public void doPlacementPhase() throws IOException {
    String instruction = "Player " + this.name + ": you are going to place the following ships (which are all\n"
        + "rectangular). For each ship, type the coordinate of the upper left\n"
        + "side of the ship, followed by either H (for horizontal) or V (for\n"
        + "vertical).  For example M4H would place a ship horizontally starting\n"
        + "at M4 and going to the right.  You have\n\n" +

        "2 \"Submarines\" ships that are 1x2\n" + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n"
        + "2 \"Carriers\" that are 1x6\n";
    this.out.print(view.displayMyOwnBoard() + instruction);
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }

  }

  /**
   *
   * @param shipName A String indicate the shipName
   * @param createFn Function to create the ship by Placement
   * @throws IOException if add ship failure
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = reReadPlacement("Player " + name + " where do you want to place a " + shipName + "?",false);
    Ship<Character> s = createFn.apply(p);
    theBoard.tryAddShip(s);
    out.print(view.displayMyOwnBoard());
  }

}
