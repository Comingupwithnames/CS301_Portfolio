package edu.up.cs301.Blokus.BlokusViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import edu.up.cs301.Blokus.BlokusInfo.BlokusBlock;
import edu.up.cs301.Blokus.BlokusInfo.BlokusGameState;
import edu.up.cs301.game.GameFramework.utilities.FlashSurfaceView;

/**
 * Class that draws all of the Blokus information required for the game on a surface view. Shows
 * pieces, player boxes, grid, and score/ player turn.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class DrawBoard extends FlashSurfaceView {

    /* Instance variables */
    public static final int GRIDBOX_SIZE = 30;
    public static final int TILE_SIZE = 20;
    public static final int BOARD_START_HEIGHT = 50;
    public static final int BOARD_START_WIDTH = 700;
    public static final int LEFT_BOXES = 10;
    public static final int RIGHT_BOXES = 1310;
    public static final int TOP_BOXES = 10;
    public static final int BOTTOM_BOXES = 465;
    public static final int PBOX_HEIGHT = 450;
    public static final int PBOX_WIDTH = 665;

    /* Paint objects representing drawings on the canvas */
    Paint gridPaint = new Paint();
    Paint legalPaint = new Paint();
    Paint yellow = new Paint();
    Paint red = new Paint();
    Paint green = new Paint();
    Paint blue = new Paint();
    Paint textPaint = new Paint();

    /* Instance variables for the game state this will have access to and the pieces it needs to draw */
    protected BlokusGameState blokusState;
    private BlokusBlock pieces = new BlokusBlock();
    int[][] pieceArray = new int[5][5];
    int[][] isRotated = new int[4][21];

    /**
     * default ctor for DrawBoard method
     *
     * @param context
     */
    public DrawBoard(Context context) {
        super(context);
        initialize();
    }

    /**
     * ctor for DrawBoard method that uses an AttributeSet
     *
     * @param context
     * @param attrib
     */
    public DrawBoard(Context context, AttributeSet attrib) {
        super(context, attrib);
        initialize();
    } //DrawBoard

    /**
     * Initializes SurfaceView being drawn on.
     */
    public void initialize() {
        setWillNotDraw(false);
        this.blokusState = new BlokusGameState();
        this.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pieceArray[i][j] = 0;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 21; j++) {
                switch(i) {
                    case 0:
                        isRotated[i][j] = 0;
                        break;
                    case 1:
                        isRotated[i][j] = 1;
                        break;
                    case 2:
                        isRotated[i][j] = 3;
                        break;
                    case 3:
                        isRotated[i][j] = 2;
                        break;
                }
            }
        }
    } //initialize

    /**
     * Helper method for onDraw method: Creates the 20x20 grid for Blokus.
     *
     * @param c canvas to draw on
     */
    public void drawGrid(Canvas c) {
        for(int i = 0; i < 20; i++) { //Scans the board to draw tiles based off their current state
            for (int j = 0; j < 20; j++) {
                drawGridSquare(c,j,i,getPaint(blokusState.getBoard()[i][j]));
            }
        }
        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if(blokusState.getBoard()[i][j] == BlokusGameState.tileState.LEGAL) {
                    drawGridSquare(c,j,i,legalPaint); //Redraws the legalPaint so the entire tile is highlighted
                }
            }
        }
    } //drawGrid

    /**
     * Helper method for drawGrid based on the board to the appropriate square
     *
     * @param c canvas to draw on
     * @param x given x coord
     * @param y given y coord
     */
    public void drawGridSquare(Canvas c, int x, int y, Paint myPaint) {
        c.drawRect(GRIDBOX_SIZE*x + BOARD_START_WIDTH, GRIDBOX_SIZE*y + BOARD_START_HEIGHT,
                GRIDBOX_SIZE*x + GRIDBOX_SIZE + BOARD_START_WIDTH,
                GRIDBOX_SIZE*y + GRIDBOX_SIZE + BOARD_START_HEIGHT, myPaint);
    }

    /**
     * Draws a piece given the array of a piece provided by the user. However, method also takes
     * in an "onBoard" boolean statement that will decide whether or not the piece is drawn in the
     * player box.
     *
     * @param c
     * @param player
     * @param pieceNum
     * @param row
     * @param col
     */
    public void drawPieces(Canvas c, int player, int pieceNum, int row, int col) {
        //Sets piece to a certain array based on given piece
        pieces.setPiece(pieceNum);

        //Piece has rotated back to original position so reset back to 0
        if (isRotated[player][pieceNum] == 4) {
            this.isRotated[player][pieceNum] = 0;
        }

        //Rotates the array of pieces so that the new array can be drawn
        if (isRotated[player][pieceNum] != 0) {
            for (int i = 0; i < isRotated[player][pieceNum]; i++) {
                blokusState.rotatePiece(pieces);
            }
        }

        //Sets a 5x5 2d array equal to the 5x5 2d array of a given piece (given by a param)
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pieceArray[i][j] = pieces.getPiece()[i][j];
            }
        }

        //Creates an X and Y variable that indicate which player box a piece should go into
        int playerX = pieces.getX(player) + ((TILE_SIZE * 5) * col) + 10 + (col * 10); //Extra 10 for margin
        int playerY = pieces.getY(player) + ((TILE_SIZE * 5) * row) + 10 + (row * 10); //Extra 10 for margin

        //Initialize and set color based on player
        Paint pColor = new Paint();
        switch (player) {
            case 0:
                pColor.setColor(Color.RED);
                break;
            case 1:
                pColor.setColor(Color.BLUE);
                break;
            case 2:
                pColor.setColor(Color.GREEN);
                break;
            case 3:
                pColor.setColor(Color.YELLOW);
                break;
        }

        //Iterates through array of each piece and draws it in the player box
        if (blokusState.getBlockArray()[player][pieceNum].getOnBoard() == false) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if ((pieceArray[i][j] == 2) || (pieceArray[i][j] == 1)) {
                        c.drawRect(playerX + (TILE_SIZE * j), playerY + (TILE_SIZE * i),
                                playerX + (TILE_SIZE * (j + 1)), playerY + (TILE_SIZE * (i + 1)), pColor);
                    }
                }
            }
        }
    } //drawPieces

    /**
     * Method to return the appropriate paint object based off the state of the tile in the board
     *
     * @param currState current tile
     *
     * @return paint object
     */
    public Paint getPaint(BlokusGameState.tileState currState) {
        switch (currState) {
            case EMPTY:
                return gridPaint;

            case RED:
                return red;

            case BLUE:
                return blue;

            case GREEN:
                return green;

            case YELLOW:
                return yellow;

        }
        return gridPaint;
    }


    /**
     * setState
     *
     * Sets the Blokus Game State for the SurfaceView.
     *
     * @param initBlokusState
     */
    public void setState(BlokusGameState initBlokusState) {
        this.blokusState = initBlokusState;
    } //setState

    public void setIsRotated(int player, int pieceNum) {
        this.isRotated[player][pieceNum] += 1;
    }

    /**
     * onDraw
     *
     * Draws the grid, player boxes, and pieces for all players
     *
     * @param c
     */
    @Override
    public void onDraw(Canvas c) {
        //Initialize paints
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStyle(Paint.Style.STROKE);
        legalPaint.setColor(Color.CYAN);
        legalPaint.setStyle(Paint.Style.STROKE);
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(48.0f);

        //Draws grid for game
        this.drawGrid(c);

        /** Player boxes themselves */
        //Top left box
        c.drawRect(LEFT_BOXES, TOP_BOXES, LEFT_BOXES + PBOX_WIDTH,
                TOP_BOXES + PBOX_HEIGHT, gridPaint);
        //Bottom left box
        c.drawRect(LEFT_BOXES, BOTTOM_BOXES, LEFT_BOXES + PBOX_WIDTH,
                BOTTOM_BOXES + PBOX_HEIGHT, gridPaint);
        //Top right box
        c.drawRect(RIGHT_BOXES, TOP_BOXES, RIGHT_BOXES + PBOX_WIDTH,
                TOP_BOXES + PBOX_HEIGHT, gridPaint);
        //Bottom right box
        c.drawRect(RIGHT_BOXES, BOTTOM_BOXES, RIGHT_BOXES + PBOX_WIDTH,
                BOTTOM_BOXES + PBOX_HEIGHT, gridPaint);

        /** PLAYER ONE BOX -- RED */
        int pieceNum = 0;
        //Draws all 21 pieces for Red
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (pieceNum < 21) {
                    this.drawPieces(c, 0, pieceNum, i, j);
                    pieceNum++;
                }
                else {
                    break; //No more pieces in array
                }
            }
        }

        /** PLAYER TWO BOX -- BLUE */
        pieceNum = 0;
        //Draws all 21 pieces for Blue
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (pieceNum < 21) {
                    this.drawPieces(c, 1, pieceNum, i, j);
                    pieceNum++;
                }
                else {
                    break; //No more pieces in array
                }
            }
        }

        /** PLAYER THREE BOX -- GREEN */
        //Draws all 21 pieces for Green
        pieceNum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (pieceNum < 21) {
                    this.drawPieces(c, 2, pieceNum, i, j);
                    pieceNum++;
                }
                else {
                    break; //No more pieces in array
                }
            }
        }

        /** PLAYER FOUR BOX -- YELLOW */
        pieceNum = 0;
        //Draws all 21 pieces for Yellow
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (pieceNum < 21) {
                    this.drawPieces(c, 3, pieceNum, i, j);
                    pieceNum++;
                }
                else {
                    break; //No more pieces in array
                }
            }
        }

        //Player & Score
        this.drawPlayerInfo(c, blokusState.getPlayerScore(blokusState.getPlayerTurn()),
                blokusState.getPlayerColor(blokusState.getPlayerTurn()));
    } //onDraw

    /**
     * drawPlayerInfo
     *
     * Draws player's turn as well as given player's score.
     *
     * @param canvas the canvas to draw on
     * @param initPlayerPoints the initial player points
     * @param initPlayerTurn string representation of the player turn
     */
    public void drawPlayerInfo(Canvas canvas, int initPlayerPoints, String initPlayerTurn) {
        String playerTurn = initPlayerTurn;
        int playerPoints = initPlayerPoints;

        canvas.drawText("Player Turn: " + playerTurn, 800.0f, 700.0f, textPaint);
        canvas.drawText(playerTurn + "'s points: " + playerPoints, 800.0f, 750.0f, textPaint);

    }
}