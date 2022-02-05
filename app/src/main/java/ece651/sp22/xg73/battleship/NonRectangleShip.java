package ece651.sp22.xg73.battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class NonRectangleShip<T> extends BasicShip<T> {

    private final String name;
    private String shipType; // Special ship eg. Battleships, Carriers
    private char orientation;
    private int width;
    private int height;


    /**
     * Get the ship name
     *
     */
    public String getName() {
        return name;
    }

    @Override
    public char getOrientation() {
        return this.orientation;
    }

    @Override
    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }


    /**
     * The Constructor of Rectangle ship
     *
     * @param name which def the name of this RectangleShip
     * @param shipType Special ship eg. Battleship, Carrier
     * @param upperLeft a Placement to record the upperleft to locate the ship
     * @param width
     * @param height
     * @param myDisplayInfo the myDisplayinfo. ie: data if not hit, onHit if hit
     * @param enemyDisplayInfo the enemyDisplayInfo. ie: nothing if not hit, data if hit
     */
    public NonRectangleShip(String name, String shipType, Placement upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height, shipType), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
        this.orientation = upperLeft.orientation;
        this.width = width;
        this.height = height;
    }



    public NonRectangleShip(String name, String shipType, Placement upperLeft, int width, int height, T data, T onHit) {
        this(name, shipType, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<T>(null, data));
    }

    /**
     * Static method to add the coordinate into the hashset
     *
     * @param upperLeft Placement of the ship coordinate
     * @param width     the width
     * @param height    the height
     * @param shipType  Special ship eg. Battleship, Carrier
     * @return HashSet<Coordinate>
     */

    static HashSet<Coordinate> makeCoords(Placement upperLeft, int width, int height, String shipType) {
        HashSet<Coordinate> ans = new HashSet<Coordinate>();
        Coordinate coordinate = upperLeft.getCoordinate();
        char orientation = upperLeft.getOrientation();
        if(shipType == "Battleship"){
            switch (orientation){
                case 'U':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if(i == 0 && (j == 0 || j == 2)) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
                case 'R':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if(j == 1 && (i == 0 || i == 2)) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;

                case 'D':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if(i == 1 && (j == 0 || j == 2)) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
                case 'L':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if(j == 0 && (i == 0 || i == 2)) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
            }

        }
        else {
            switch (orientation) {
                case 'U':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (j == 1 && (i == 0 || i == 1)) continue;
                            if (j == 0 && i == 4) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
                case 'R':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (i == 1 && (j == 3 || j == 4)) continue;
                            if (i == 0 && j == 0) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;

                case 'D':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (j == 0 && (i == 3 || i == 4)) continue;
                            if (j == 1 && i == 0) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
                case 'L':
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (i == 0 && (j == 0 || j == 1)) continue;
                            if (i == 1 && j == 4) continue;
                            Coordinate c = new Coordinate(coordinate.getRow() + i, coordinate.getColumn() + j);
                            ans.add(c);
                        }
                    }
                    break;
            }
        }
        return ans;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
