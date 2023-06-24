package edu.up.cs301.Blokus.BlokusInfo;

import java.io.Serializable;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;

/**
 * Game State for the Blokus game. Provides information about the game as well as a constructor
 * and deep copy constructor for all of the information being supplied and initialized.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class BlokusGameState extends GameState implements Serializable {

    //For Serializable interface
    public static final long serialVersionUID = 3819873219821L;

    /** Declaration of an enum type representing the states a certain tile can have */
    public enum tileState{
        EMPTY, LEGAL, RED, BLUE, YELLOW, GREEN
    }

    /** Declaration of instance variables*/
    private int playerTurn;
    private int selectedType;
    private int[] playerScore;
    private BlokusBlock [][] blockArray; //Represents each players collection of pieces
    private tileState[][] board;
    private boolean gameOn;
    private boolean[] isPassed;

    /** Default ctor */
    public BlokusGameState() {

        /* int containing the current player's turn*/
        this.playerTurn = 0;

        /* int containing the currently selected block type */
        this.selectedType = -1;

        /* Array for holding player scores */
        this.playerScore = new int[] {-85,-85,-85,-85};

        /* Array holding whether or not player's passed their turn */
        this.isPassed = new boolean[] {false, false, false, false};

        /* Array containing the block objects within each player box and each player's box will be populated with the appropriate blocks */
        blockArray = new BlokusBlock[4][21];
        for(int i = 0; i<4; i++) {
            for (int j = 0; j<21; j++) {
                this.blockArray[i][j] = new BlokusBlock();
                this.blockArray[i][j].setType(j);
                this.blockArray[i][j].setPiece(j);

                switch (i) {
                    case 1:
                        this.rotatePiece(this.blockArray[i][j]);
                        break;
                    case 2:
                        for (int k = 0; k < 3; k++) {
                            this.rotatePiece(this.blockArray[i][j]);
                        }
                        break;
                    case 3:
                        for (int k = 0; k < 2; k++) {
                            this.rotatePiece(this.blockArray[i][j]);
                        }
                        break;
                }
            }
        }

        /* Array representing the board and the legal moves within it and all values are initially set to EMPTY */
        this.board = new tileState[20][20];
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                this.board[i][j] = tileState.EMPTY;
            }
        }

        this.gameOn = true;
    }

    /**
     *  Deep copy ctor
     *
     * @param toCopy given BlokusGameState to copy
     */
    public BlokusGameState(BlokusGameState toCopy) {

        /* Copy process for the player turn setting the playerTurn to the other state's playerTurn */
        this.playerTurn = toCopy.playerTurn;

        /* Copy process for the currently selected type of piece*/
        this.selectedType = toCopy.selectedType;

        /* Copy process for the player scores Starts by initializing a new array then copies contents over */
        this.playerScore = new int[4];
        for (int i = 0; i<4; i++) {
            this.playerScore[i] = toCopy.playerScore[i];
        }

        this.isPassed = new boolean[4];
        for (int i = 0; i<4; i++) {
            this.isPassed[i] = toCopy.isPassed[i];
        }

        /* Copy process for the array of blocks Starts by initializing a new array then copies contents over */
        this.blockArray = new BlokusBlock[4][21];
        for (int i = 0; i<4; i++) {
            for (int j = 0; j<21; j++) {
                if(toCopy.blockArray[i][j] == null) {
                    this.blockArray[i][j] = null;
                }
                else {
                    this.blockArray[i][j] = new BlokusBlock(toCopy.blockArray[i][j]);
                }
            }
        }

        /* Copies array over from orig state by going cell by cell to set the values */
        this.board = new tileState[20][20];
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                this.board[i][j] = toCopy.board[i][j];
            }
        }
        this.gameOn = toCopy.gameOn;
    }

    /**
     *  This will check to see if a legal move is at the current touch position
     *  and then will increment the appropriate players score based on the
     *  piece placed
     *
     * @param playerTurn given player turn
     * @param xPos given x position
     * @param yPos given y position
     * @param piece given BlokusBlock
     *
     * @return int
     */
    public int placePiece(int playerTurn, int xPos, int yPos, BlokusBlock piece) {
        /* Variables to represent the relative x and y coords as well as the current playerState*/
        int relX = 0;
        int relY = 0;

        /* Variables to represent the currently selected row a columns for brevity*/
        int currRow = 0;
        int currCol = 0;

        /* New temp board to make sure no piece overwrites another*/
        tileState[][] tempBoard = new tileState[20][20];
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
               tempBoard[i][j] = this.board[i][j];
            }
        }

        tileState playerState = getTileStateForId(playerTurn);

        if(piece.getOnBoard()) //If passed piece is null (already placed or none selected) returns 1
        {
            return 1;
        }

        /* Iterates through the board to find the relative x and y coordinates */
        if (tempBoard[yPos][xPos] == tileState.LEGAL) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (piece.getPieceArr()[i][j] == 2) {
                        relX = j;
                        relY = i;
                    }
                    else {
                        continue;
                    }
                }
            }
            try {
                /* Then, iterates through the board again to place the piece on the board according to the playerNum*/
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (piece.getPieceArr()[i][j] == 0) {
                            continue;
                        }
                        else {
                            currRow = yPos + i - relY;
                            currCol = xPos + j - relX;

                            tempBoard[currRow][currCol] = playerState;
                        }
                    }
                }
                /* Copies contents of tempBoard over to the board */
                for(int i = 0; i<20; i++) {
                    for(int j = 0; j<20; j++) {
                        this.board[i][j] = tempBoard[i][j];
                    }
                }

                this.blockArray[playerTurn][piece.getType()].setOnBoard(true);//Sets the piece to be on the board
                clearBoard(this.getBoard());
                return 0;//Returns 0, indicating a successful action
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return 2;//Specific int for out of bounds
            }
        }
        return 1;//If we get here, ends the player's turn
    }

    /**
     * This will rotate a player's given piece 90 degrees clockwise given that it is the
     * player's turn.
     *
     * @param piece given BlokusBlock
     *
     * @return boolean
     */
    public boolean rotatePiece(BlokusBlock piece) {
       /**
        * External Citation
        * Date: 18 April 2022
        * Problem: Could not rotate pieces correctly
        * Resource:
        * https://stackoverflow.com/questions/45847027/trying-to-rotate-a-2d-array-90-degrees-clockwise-but-it-is-going-counter-clockw
        * Solution: I used code from a user named alirabiee
        */
        /* First, creates a temporary array to store the "rotation" in */
        int[][] tempArr = new int[5][5];
        for(int i = 0; i<5; i++) {
            for(int j = 0; j<5; j++) {
                tempArr[j][4-i] = piece.getPieceArr()[i][j];
            }
        }

        /* Then, will copy over the rotated array into the piece array without the need for swapping or temp ints */
        for(int i = 0; i<5; i++) {
            for (int j = 0; j<5; j++) {
                piece.getPieceArr()[i][j] = tempArr[i][j];
            }
        }
        return true;
    }

    /**
     * This will iterate through the given board to calculate legal moves based off of
     * a given piece's orientation using helper methods to cut down on length
     *
     * @param board given board
     * @param playerTurn given player turn
     *
     * @return boolean
     */
    public boolean calcLegalMoves(tileState[][] board, int playerTurn) {
        int numChanged = 0; //int representing the number of legal moves successfully calculated and changed
        tileState playerState = getTileStateForId(playerTurn);
        if(beginningGameCheck(playerTurn)) {
            numChanged++;
        }
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                if (i < 19) { //This check will run as long as i is to the left of the right most column
                    if(checkNeighbor(board,playerTurn,i,j,1,1)) { //If successful increment numChanged
                        numChanged++;
                    }
                }
                if (i > 0) { //This check will run as long as i is to the right of the left most column
                    if(checkNeighbor(board,playerTurn,i,j,1,-1)) {
                        numChanged++;
                    }
                }
                if(j < 19) { //This check will run as long as j is to the left of the right most column
                    if(checkNeighbor(board,playerTurn,i,j,-1,1)) {
                        numChanged++;
                    }
                }
                if(j > 0) { //This check will run as long as j is to the right of the left most column
                    if(checkNeighbor(board,playerTurn,i,j,-1,-1)) {
                        numChanged++;
                    }
                }
            }
        }

        if(numChanged > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This will act to clear all the current legal moves for the next player's turn
     *
     * @param board given board
     */
    public void clearBoard(tileState[][] board) {
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                if(board[i][j] == tileState.LEGAL) { //If tile is legal, set to empty
                    board[i][j] = tileState.EMPTY;
                }
            }
        }
    }
    /**
     * This will be the main helper method for calculating legal moves by going through a set of
     * checks that will check appropriate neighbors based on passed in ints
     *
     * @param board given board
     * @param playerTurn given player turn
     * @param yPos given y position
     * @param xPos given x position
     * @param yDelta given y shift
     * @param xDelta given x shift
     *
     * @return boolean
     */
    public boolean checkNeighbor(tileState[][] board, int playerTurn, int yPos, int xPos, int yDelta, int xDelta) {
        tileState playerState = getTileStateForId(playerTurn);
        try {
            if ((board[yPos][xPos] == playerState //Checks to see if specified tile matches playerState
                    && board[yPos + yDelta][xPos]!=playerState && board[yPos][xPos + xDelta]!=playerState)//Checks the tile below and tile to the right to see if they are not equal to the playerState
                    && board[yPos + yDelta][xPos + xDelta] == tileState.EMPTY) { //Finally, tile to the bottom right must be empty
                board[yPos+yDelta][xPos+xDelta] = tileState.LEGAL;
                return true;
            }
            else {
                return false;
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * checkLegalPerPiece
     *
     *  Checks whether or not the tile above, to the left, to the right, and below a given tile
     *  for a piece has a tile of the same color in it to prevent overlapping. Method also goes and
     *  makes sure that a tile is not being placed over a tile with a color in it already.
     *
     * @param board
     * @param playerTurn
     * @param piece
     *
     * @return boolean
     */
    public boolean checkLegals(tileState[][] board, int playerTurn, BlokusBlock piece) {
        if(this.selectedType == -1) { //No type selected, return false
            return false;
        }

        try {
            //Iterates through all legal places & removes places that do not accept piece array
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j] == tileState.LEGAL) {
                        if (checkLegalPerPiece(board, playerTurn, piece, i, j)) {
                            board[i][j] = tileState.EMPTY;
                        }
                    }
                }
            }
            return true;
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * checkLegalPerPiece
     *
     * Checks for any neighbors for a given piece. Helper method for checkLegals.
     *
     * @param board
     * @param playerTurn
     * @param piece
     * @param yPos
     * @param xPos
     *
     * @return true if piece can't be placed legally, false if it can
     */
    public boolean checkLegalPerPiece(tileState[][] board, int playerTurn, BlokusBlock piece, int yPos, int xPos) {

       /* Declaration of important ints for later use */
        int neighborNum = 0;
        int row = 0;
        int col = 0;
        int xDiff;
        int yDiff;
        int currRow;
        int currCol;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (piece.getPieceArr()[i][j] == 2) {
                    //Records location of i and j in order to subtract it from other tiles of piece
                    row = i;
                    col = j;
                }
            }
        }

        //Iterates through all tiles in a piece array to check for neighbors without placing piece
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (piece.getPieceArr()[i][j] == 0) {
                    continue; //Empty tile value - do nothing
                }
                else if (piece.getPieceArr()[i][j] == 2) { //Tile that is placed in legal spot

                    if (xPos < 19) {
                        //Checks right neighbor
                        if (this.checkLegalPerTileX(board, playerTurn, yPos, xPos, 1)) {
                            neighborNum++;
                        }
                    }
                    if (xPos > 0) {
                        //Checks left neighbor
                        if (this.checkLegalPerTileX(board, playerTurn, yPos, xPos, -1)) {
                            neighborNum++;
                        }
                    }
                    if (yPos < 19) {
                        //Checks bottom neighbor
                        if (this.checkLegalPerTileY(board, playerTurn, yPos, xPos, 1)) {
                            neighborNum++;
                        }
                    }
                    if (yPos > 0) {
                        //Checks top neighbor
                        if (this.checkLegalPerTileY(board, playerTurn, yPos, xPos, -1)) {
                            neighborNum++;
                        }
                    }
                }
                else if (piece.getPieceArr()[i][j] == 1) { //Rest of tiles in the piece array
                    xDiff = j - col;
                    yDiff = i - row;

                    currCol = xPos + xDiff;
                    currRow = yPos + yDiff;

                    if (currCol < 19) {
                        //Checks right neighbor
                        if (this.checkLegalPerTileX(board, playerTurn, currRow, currCol, 1)) {
                            neighborNum++;
                        }
                    }
                    if (currCol > 0) {
                        //Checks left neighbor
                        if (this.checkLegalPerTileX(board, playerTurn, currRow, currCol, -1)) {
                            neighborNum++;
                        }
                    }
                    if (currRow < 19) {
                        //Checks bottom neighbor
                        if (this.checkLegalPerTileY(board, playerTurn, currRow, currCol, 1)) {
                            neighborNum++;
                        }
                    }
                    if (currRow > 0) {
                        //Checks top neighbor
                        if (this.checkLegalPerTileY(board, playerTurn, currRow, currCol, -1)) {
                            neighborNum++;
                        }
                    }
                }

                //Will get rid of legal placement
                if (neighborNum != 0) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * checkLegalPerTileX
     *
     * Checks left (-1 for xDelta) or right (1 for xDelta) tile to see if it has a neighbor.
     * Helper method for checkLegalPerPiece
     *
     * @param board
     * @param playerTurn
     * @param yPos
     * @param xPos
     * @param xDelta
     *
     * @return false if no neighbor of same color, true if neighbor of same color
     */
    public boolean checkLegalPerTileX(tileState[][] board, int playerTurn, int yPos, int xPos, int xDelta) {
        tileState playerState = getTileStateForId(playerTurn);
        try {
            if ((board[yPos][xPos] == tileState.BLUE) || (board[yPos][xPos] == tileState.RED)
                    || (board[yPos][xPos] == tileState.GREEN) || (board[yPos][xPos] == tileState.YELLOW)) {
                return true;
            }
            else if (board[yPos][xPos + xDelta] == playerState) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * checkLegalPerTileY
     *
     * Checks top (-1 for yDelta) or bottom (1 for yDelta) tile to see if it has a neighbor.
     * Helper method for checkLegalPerPiece
     *
     * @param board
     * @param playerTurn
     * @param yPos
     * @param xPos
     * @param yDelta
     *
     * @return false if no neighbor of same color, true if neighbor of same color
     */
    public boolean checkLegalPerTileY(tileState[][] board, int playerTurn, int yPos, int xPos, int yDelta) {
        tileState playerState = getTileStateForId(playerTurn);
        try {
            if (board[yPos][xPos] == playerState) {
                return true;
            }
            else if (board[yPos + yDelta][xPos] == playerState) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
    }


    /**
     * This will be ran everytime that calcLegalMoves is called to catch the beginning game
     * conditions where the corners of the board are EMPTY and will set the appropriate
     * legal positions based on whose turn it is
     *
     * @param playerTurn given player turn
     *
     * @return boolean
     */
    public boolean beginningGameCheck(int playerTurn) {
        tileState playerState = getTileStateForId(playerTurn);
        switch(playerState) { //Checks the appropriate playerState to set the needed corner to a legal tile
            case RED:
                if(this.board[0][0] == tileState.EMPTY) {
                    this.board[0][0] = tileState.LEGAL;
                    return true;
                }
                break;

            case BLUE:
                if(this.board[0][19] == tileState.EMPTY) {
                    this.board[0][19] = tileState.LEGAL;
                    return true;
                }
                break;

            case GREEN:
                if(this.board[19][0] == tileState.EMPTY) {
                    this.board[19][0] = tileState.LEGAL;
                    return true;
                }
                break;

            case YELLOW:
                if(this.board[19][19] == tileState.EMPTY) {
                    this.board[19][19] = tileState.LEGAL;
                    return true;
                }
                break;

            default:
                return false;
        }
        return false;
    }

    /**
     * This will be another helper method that checkNeighbor utilizes to get a specific player's
     * block color by their ID that is passed in
     *
     * @param playerTurn given player turn
     *
     * @return tileState
     */
    public tileState getTileStateForId(int playerTurn) {
        //Based on playerTurn, returns specific tileStates where red is associated with 1 and so on
        switch(playerTurn) {
            case 0:
                return tileState.RED;
            case 1:
                return tileState.BLUE;
            case 2:
                return tileState.GREEN;
            case 3:
                return tileState.YELLOW;
            default: //Something invalid passed in, returning null
                return null;
        }
    }

    /**
     * Getter methods for the game state
     */
    public BlokusBlock[][] getBlockArray() { return this.blockArray; }
    public tileState[][] getBoard() { return this.board; }
    public int getPlayerTurn() { return this.playerTurn; }
    public int getPlayerScore(int player) { return this.playerScore[player]; }
    public int getSelectedType() { return this.selectedType; }
    public boolean getGameOn() { return this.gameOn; }
    public boolean[] getIsPassed() { return this.isPassed; }

    /**
     * Gets color based on given player
     *
     * @param player
     * @return Player's color
     */
    public String getPlayerColor(int player) {
        String playerColor = "";

        switch(player) {
            case 0:
                playerColor = "Red";
                break;
            case 1:
                playerColor = "Blue";
                break;
            case 2:
                playerColor = "Green";
                break;
            case 3:
                playerColor = "Yellow";
                break;
        }

        return playerColor;
    }

    /**
     * Setter methods for the game state
     */
    public void setPlayerTurn(int toSet) { this.playerTurn = toSet; }
    public void setSelectedType(int toSet) { this.selectedType = toSet; }
    public void setPlayerScore(int idx, int toAdd){ this.playerScore[idx] += toAdd; }
    public void setGameOn(boolean toSet) { this.gameOn = toSet; }
    public void setIsPassed(int player, boolean toSet) { this.isPassed[player] = toSet; }

    /**
     * This will return a string version of the entire BlokusGameState
     */
    @Override
    public String toString() {
        String gameStateInfo;

        gameStateInfo = "Game status is: " + gameOn + "\n";

        /* Concatenates gameStateInfo with the scores of all the players */
        for (int i = 0; i < playerScore.length; i++) {
            gameStateInfo += "Player " + (i+1) + " score: " + playerScore[i] + "\n";
        }

        /* Concatenates gameStateInfo with the turn strings of all the players */
        gameStateInfo += "Player " + playerTurn + "'s turn. \n";

        /* Concatenates gameStateInfo with the string versions of each block */
        for(int i = 0; i<4; i++) {
            for(int j = 0; j<21; j++) {
                if(this.blockArray[i][j] != null) {
                    gameStateInfo += this.blockArray[i][j].toString() + "\n";
                }
                else {
                    gameStateInfo += "This block is null!\n\n";
                }
            }
        }

        /* Concatenates gameStateInfo with array of legal moves on the board */
        gameStateInfo += "Board of tile states: \n";
        for(int i = 0; i<20; i++) {
            for(int j = 0; j<20; j++) {
                gameStateInfo += this.board[i][j].toString() + " ";
            }
            gameStateInfo += "\n";
        }
        return gameStateInfo;
    }
}