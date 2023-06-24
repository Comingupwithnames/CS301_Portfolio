package edu.up.cs301.Blokus.BlokusInfo;

import java.io.Serializable;

import edu.up.cs301.Blokus.BlokusViews.DrawBoard;

/**
 * Class for each of the Blokus block pieces and their array representations
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 19th 2022
 */
public class BlokusBlock implements Serializable {

    //For Serializable interface
    public static final long serialVersionUID = 7210794871L;

    //Instance Variables
    private int type;
    private int blockScore;
    private boolean onBoard;
    private int[][] pieceArr;

    /**
     * No param ctor
     */
    public BlokusBlock() {
        this.type = 4;
        this.blockScore = 4;
        this.pieceArr = new int[5][5];
        this.onBoard = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.pieceArr[i][j] = 0;
            }
        }
    }

    /**
     * Deep copy ctor
     */
    public BlokusBlock(BlokusBlock toCopy) {
        this.type = toCopy.type;
        this.blockScore = toCopy.blockScore;
        this.onBoard = toCopy.onBoard;
        this.pieceArr = new int[5][5];
        this.onBoard = toCopy.onBoard;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.pieceArr[i][j] = toCopy.pieceArr[i][j];
            }
        }
    }

    /**
     * Getters of BlokusBlock variables
     */
    public int getType() {
        return this.type;
    }

    public int getBlockScore() {
        return this.blockScore;
    }

    public int[][] getPieceArr() {
        return this.pieceArr;
    }

    /**
     * Setters of BlokusBlock variables
     */
    public void setType(int toSet) {
        this.type = toSet;
    }

    public void setBlockScore(int toSet) {
        this.blockScore = toSet;
    }

    /** Setter and Getter method onBoard boolean */
    public void setOnBoard(boolean toSet) { this.onBoard = toSet; }
    public boolean getOnBoard() { return this.onBoard; }

    /**
     * setPiece
     *
     * Sets pieceArr equal to a given array for each piece
     *
     * @return The array of a given piece
     */
    public void setPiece(int pieceNum) {
        //Goes through and wipes pieceArr by setting everything to 0
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.pieceArr[i][j] = 0;
            }
        }

        switch (pieceNum) {//Depending on the piece number, sets it to a specific piece array representation
            case 0:
                this.pieceArr[0][0] = 2;

                this.setBlockScore(1);
                break;
            case 1:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;

                this.setBlockScore(3);
                break;
            case 2:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;

                this.setBlockScore(1);
                break;
            case 3:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][0] = 1;
                this.pieceArr[1][1] = 1;

                this.setBlockScore(4);
                break;
            case 4:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][1] = 1;

                this.setBlockScore(3);
                break;
            case 5:
                this.pieceArr[1][0] = 2;
                this.pieceArr[1][1] = 1;
                this.pieceArr[0][1] = 1;
                this.pieceArr[2][1] = 1;
                this.pieceArr[1][2] = 1;

                this.setBlockScore(5);
                break;
            case 6:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;

                this.setBlockScore(2);
                break;
            case 7:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;

                this.setBlockScore(4);
                break;
            case 8:
                this.pieceArr[0][0] = 2;
                this.pieceArr[1][0] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[2][0] = 1;

                this.setBlockScore(4);
                break;
            case 9:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;
                this.pieceArr[1][3] = 1;

                this.setBlockScore(5);
                break;
            case 10:
                this.pieceArr[0][0] = 2;
                this.pieceArr[1][0] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;
                this.pieceArr[2][2] = 1;

                this.setBlockScore(5);
                break;
            case 11:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[0][3] = 1;
                this.pieceArr[1][3] = 1;

                this.setBlockScore(5);
                break;
            case 12:
                this.pieceArr[0][0] = 2;
                this.pieceArr[1][0] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[2][0] = 1;
                this.pieceArr[3][0] = 1;

                this.setBlockScore(5);
                break;
            case 13:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;
                this.pieceArr[2][2] = 1;

                this.setBlockScore(5);
                break;
            case 14:
                this.pieceArr[0][0] = 2;
                this.pieceArr[1][0] = 1;
                this.pieceArr[1][1] = 1;
                this.pieceArr[2][1] = 1;
                this.pieceArr[1][2] = 1;

                this.setBlockScore(5);
                break;
            case 15:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[1][0] = 1;
                this.pieceArr[2][0] = 1;
                this.pieceArr[2][1] = 1;

                this.setBlockScore(5);
                break;
            case 16:
                this.pieceArr[1][0] = 2;
                this.pieceArr[1][1] = 1;
                this.pieceArr[1][2] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[2][2] = 1;

                this.setBlockScore(5);
                break;
            case 17:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[1][2] = 1;

                this.setBlockScore(4);
                break;
            case 18:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[0][3] = 1;

                this.setBlockScore(4);
                break;
            case 19:
                this.pieceArr[0][0] = 2;
                this.pieceArr[0][1] = 1;
                this.pieceArr[0][2] = 1;
                this.pieceArr[0][3] = 1;
                this.pieceArr[0][4] = 1;

                this.setBlockScore(5);
                break;
            case 20:
                this.pieceArr[0][0] = 2;
                this.pieceArr[1][0] = 1;
                this.pieceArr[2][0] = 1;
                this.pieceArr[2][1] = 1;
                this.pieceArr[2][2] = 1;

                this.setBlockScore(5);
                break;
        }
    }

    /**
     * getPiece
     *
     * Returns the array of a given piece
     *
     * @return the piece array
     */
    public int[][] getPiece() {
        return this.pieceArr;
    }

    /**
     * getX
     *
     * Helper method that gets an x position for a given player (either left or right)
     *
     * @param player
     * @return x
     */
    public int getX(int player) {
        int x = 0;

        if (player == 0) {
            x = DrawBoard.LEFT_BOXES; //Left boxes
        }
        else if (player == 1) {
            x = DrawBoard.RIGHT_BOXES; //Left boxes
        }
        else if (player == 2) {
            x = DrawBoard.LEFT_BOXES; //Right boxes
        }
        else if (player == 3) {
            x = DrawBoard.RIGHT_BOXES; //Right boxes
        }

        return x;
    } //getX

    /**
     * getY
     *
     * Helper method that gets an y position for a given player (either left or right)
     *
     * @param player
     * @return y
     */
    public int getY(int player) {
        int y = 0;

        if (player == 0) {
            y = DrawBoard.TOP_BOXES; //Top boxes
        }
        else if (player == 1) {
            y = DrawBoard.TOP_BOXES; //Bottom boxes
        }
        else if (player == 2) {
            y = DrawBoard.BOTTOM_BOXES; //Top Boxes
        }
        else if (player == 3) {
            y = DrawBoard.BOTTOM_BOXES; //Bottom boxes
        }

        return y;
    } //getY

    /**
     *  Returns a string version of the BlokusBlock
     */
    @Override
    public String toString() {
        String toReturn = "Type: " + this.type + " Score: " + this.blockScore + "\n";
        for(int i = 0; i<5; i++) {
            for(int j = 0; j<5; j++) {
                toReturn += this.pieceArr[i][j] + " ";
            }
            toReturn += "\n";
        }
        return toReturn;
    }
}
