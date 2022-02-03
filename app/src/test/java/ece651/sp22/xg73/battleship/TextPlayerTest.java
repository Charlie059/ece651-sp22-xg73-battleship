package ece651.sp22.xg73.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  void test_read_placement_null() throws IOException {
    String prompt = "Please enter a location for a ship:";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "", bytes);
    V1ShipFactory sf = new V1ShipFactory();
    assertThrows(IOException.class, () -> player.doOnePlacement("Submarine", (p) -> sf.makeSubmarine(p)));
  }

  @Test
  void test_read_placement_invalid_placement() throws IOException {
    String prompt = "Please enter a location for a ship:";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "AAV", bytes);
    V1ShipFactory sf = new V1ShipFactory();
    assertThrows(IllegalArgumentException.class, () -> player.doOnePlacement("Submarine", (p) -> sf.makeSubmarine(p)));
  }

  @Test
  void test_read_placement() throws IOException {
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline

      bytes.reset(); // clear out bytes for next time around
    }

  }

  @Test
  void test_do_one_placement() throws IOException {
    StringReader sr = new StringReader("B2V\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);

    Board<Character> b = new BattleShipBoard<Character>(3, 4, 'X');
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    V1ShipFactory sf = new V1ShipFactory();
    player.doOnePlacement("Submarine", (p) -> sf.makeSubmarine(p));
  }

  @Test
  void test_do_placement_phase() throws IOException {
    StringReader sr = new StringReader("A1h\nA5h\nB1h\nB5h\nC1h\nC5h\nD1h\nD5h\nE1h\nE5h\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);

    Board<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    player.doPlacementPhase();

  }

}
