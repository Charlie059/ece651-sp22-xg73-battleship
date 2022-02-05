package ece651.sp22.xg73.battleship;

import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
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
  private int moveRemaining;
  private int scanRemaining;
  private boolean isComputer;
  private Random random;

  /**
   * Constructor of TextPlayer
   * @param name Player name. eg. Player A
   * @param theBoard Player's board
   * @param br Read the placement buffer
   * @param out Print the Map out
   * @param factory The Ship Factory object
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader br, PrintStream out, AbstractShipFactory<Character> factory, boolean isComputer) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = br;
    this.out = out;
    this.shipFactory = factory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    this.moveRemaining = 2;
    this.scanRemaining = 1;
    this.isComputer = isComputer;
    this.random = new Random();
    setupShipCreationList();
    setupShipCreationMap();
  }

  /**
   * Get user selection
   * @return int of choice
   */
  private String getUserChoice() {
    String input = null;
    boolean userReEnter = false;
    do {
      try {
        input = inputReader.readLine();
        input = input.toUpperCase();

        if( input.equals("F") || input.equals("M") || input.equals("S")){
          userReEnter = false;
        }
        else {
          userReEnter = true;
          out.println("Invalid Coordinate, please enter again!");
        }
      }catch (IOException e) {
        out.println("Invalid Coordinate, please enter again!");
        userReEnter = true;
      }
    }
    while (userReEnter == true);
    return input;
  }

  public void oneTurn(Board<Character> enemyBoard, BoardTextView view, String playerName){
    String choicePrompt = playerName + "'s turn:\n F Fire at a square\n" +
            " M Move a ship to another square (" + moveRemaining+  " remaining)\n" +
            " S Sonar scan (" + scanRemaining + " remaining)\n" +
            "\n" +
            "Player " + playerName + ", what would you like to do?";
    String choice = null;
    if(this.isComputer == false){
      out.println(choicePrompt);
      choice= getUserChoice();
    }
    else {
      choice = "F";
    }


    if(choice.equals("F")){
      playOneTurnAttack(enemyBoard, view, playerName);
    }
    else if(choice.equals("M") && moveRemaining > 0){
      playOneTurnMove(enemyBoard, view, playerName);
      moveRemaining--;
    }
    else if(choice.equals("S") && scanRemaining > 0){ // S
      playOneSonar(enemyBoard,view,playerName);
      scanRemaining--;
    }
    else {
      out.println("Invalid input, please try again!");
      oneTurn(enemyBoard, view, playerName);
    }
  }

  /**
   * User select Sonar Action
   * @param enemyBoard
   * @param view
   * @param playerName Player A or B
   */
  public void playOneSonar(Board<Character> enemyBoard, BoardTextView view, String playerName){
    String turnPrompt = "Player " + playerName + "'s turn";
    String attackPrompt = "Player " + playerName + " select a coordinate to scan!";
    this.out.println(turnPrompt);

    // Display the enemy board
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    this.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your ocean", "Player B's ocean"));

    Coordinate sonarCoordinate = reReadPlacement(attackPrompt, true).getCoordinate();
    int row_offset = sonarCoordinate.getRow();
    int col_offset = sonarCoordinate.getColumn();
    ArrayList<Coordinate> sonarList = new ArrayList<Coordinate>(
            Arrays.asList(
                    new Coordinate(-3 + row_offset,0 + col_offset),
                    new Coordinate(-2 + row_offset,-1+ col_offset),
                    new Coordinate(-2+ row_offset,0+ col_offset),
                    new Coordinate(-2+ row_offset,1+ col_offset),
                    new Coordinate(-1+ row_offset,-2+ col_offset),
                    new Coordinate(-1+ row_offset,-1+ col_offset),
                    new Coordinate(-1+ row_offset,0+ col_offset),
                    new Coordinate(-1+ row_offset,1+ col_offset),
                    new Coordinate(-1+ row_offset,2+ col_offset),
                    new Coordinate(0+ row_offset,-3+ col_offset),
                    new Coordinate(0+ row_offset,-2+ col_offset),
                    new Coordinate(0+ row_offset,-1+ col_offset),
                    new Coordinate(0+ row_offset,0+ col_offset),
                    new Coordinate(0+ row_offset,1+ col_offset),
                    new Coordinate(0+ row_offset,2+ col_offset),
                    new Coordinate(0+ row_offset,3+ col_offset),
                    new Coordinate(1+ row_offset,-2+ col_offset),
                    new Coordinate(1+ row_offset,-1+ col_offset),
                    new Coordinate(1+ row_offset,0+ col_offset),
                    new Coordinate(1+ row_offset,1+ col_offset),
                    new Coordinate(1+ row_offset,2+ col_offset),
                    new Coordinate(2+ row_offset,-1+ col_offset),
                    new Coordinate(2+ row_offset,0+ col_offset),
                    new Coordinate(2+ row_offset,1+ col_offset),
                    new Coordinate(3+ row_offset,0+ col_offset)
            )
    );

    int SubmarinesNum = 0;
    int DestroyersNum = 0;
    int BattleshipsNum = 0;
    int CarriersNum = 0;
    for(Coordinate c: sonarList){
       Ship<Character> s = enemyBoard.whichShip(c);
       if(s != null){
         if (s.getName().equals("Submarine")) SubmarinesNum++;
         if (s.getName().equals("Destroyer")) DestroyersNum++;
         if (s.getName().equals("Battleship")) BattleshipsNum++;
         if (s.getName().equals("Carrier")) CarriersNum++;
       }
    }

    String ans = "Submarines occupy " + SubmarinesNum + " squares\n"+
            "Destroyers occupy " + DestroyersNum + " squares\n"+
            "Battleships occupy " + BattleshipsNum + " squares\n"+
            "Carriers occupy " + CarriersNum + " squares\n";
    out.println(ans);

  }

  
  /**
   * One player play a turn. Give the prompt and both view, hit, report hit
   * @param enemyBoard
   * @param view
   * @param playerName Player A or B
   */
  public void playOneTurnAttack(Board<Character> enemyBoard, BoardTextView view, String playerName){
    String turnPrompt = "Player " + playerName + "'s turn";
    String attackPrompt = "Player " + playerName + " select a coordinate to attack!";
    if(this.isComputer == false)  this.out.println(turnPrompt);


    // Display the enemy board
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    if(this.isComputer == false) this.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your ocean", "Player B's ocean"));

    Coordinate attackCoordinate = null;
    if(this.isComputer == false) attackCoordinate = reReadPlacement(attackPrompt, true).getCoordinate();
    else{
      attackCoordinate = reReadPlacement("", true).getCoordinate();
    }


    Ship<Character> ship = enemyBoard.fireAt(attackCoordinate);

    // Give the attack feedback
    if(this.isComputer == false){
      if(ship == null)  this.out.println("You missed!");
      else this.out.println("You hit a " + ship.getName() + "!");
    }
    else {
      if(ship == null)  this.out.println("Player " + playerName + " miss! (Computer)\n");
      else this.out.println("Player " + playerName + " hit your " + ship.getName() + " at " + attackCoordinate + " !\n");
    }

  }

  /**
   * Do action of move
   * @param enemyBoard
   * @param view
   * @param playerName
   */
  public void playOneTurnMove(Board<Character> enemyBoard, BoardTextView view, String playerName){ //TODO display problem of hit
    // Display the enemy board
    BoardTextView enemyView = new BoardTextView(enemyBoard);
    this.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your ocean", "Player B's ocean"));

    String prompt_from = "Player " + playerName + " select a coordinate of ship to move!";

    Coordinate from = reReadPlacement(prompt_from, true).getCoordinate();

    String prompt_to = "Player " + playerName + " select a placement move to!";
    Placement to = reReadPlacement(prompt_to, false);


    String result = this.theBoard.tryMoveShip(from,to.getCoordinate(),to.getOrientation());
    if(result != null){
      out.println(result);
      playOneTurnMove(enemyBoard, view, playerName);
    }
    this.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your ocean", "Player B's ocean"));
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
    String s = null;
    if(this.isComputer == false){
      s = inputReader.readLine();
    }
    else{
      String row = String.valueOf((char)(random.nextInt(20) + 'a'));
      String col = String.valueOf(random.nextInt(10));

      int hOrL = random.nextInt(10);
      if(hOrL % 2 == 0) s = row + col + "H";
      else s = row + col + "L";
    }

    if (s == null)
      throw new IOException("Receive null string in readPlacement");

    if(this.isComputer == false && getCoordinateOnly == true) s += "H";
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
        if(isComputer == false) this.out.println("Invalid Coordinate(Empty input), please enter again!");
        userReEnter = true;
      } catch (IllegalArgumentException e){
        if(isComputer == false) this.out.println("Invalid Coordinate, please enter again!");
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

    if(this.isComputer == false){
      this.out.print(view.displayMyOwnBoard() + instruction);
    }

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
    if(this.isComputer == false){
      boolean reEnter = true;
      do {
        Placement p = reReadPlacement("Player " + name + " where do you want to place a " + shipName + "?",false);
        Ship<Character> s = createFn.apply(p);
        String result = theBoard.tryAddShip(s);
        if(result != null) out.println(result);
        else reEnter = false;
      }
      while (reEnter == true);
      out.print(view.displayMyOwnBoard());
    }
    else {
      boolean reEnter = true;
      do {
        Placement p = reReadPlacement("",false);
        Ship<Character> s = createFn.apply(p);
        String result = theBoard.tryAddShip(s);
        if(result != null){}
        else reEnter = false;
      }
      while (reEnter == true);
    }
    }


}
