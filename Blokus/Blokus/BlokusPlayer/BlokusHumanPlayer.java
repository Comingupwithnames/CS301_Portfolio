package edu.up.cs301.Blokus.BlokusPlayer;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.Blokus.BlokusActions.BlokusPassAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusPlaceAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusRotateAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusSelectAction;
import edu.up.cs301.Blokus.BlokusInfo.BlokusGameState;
import edu.up.cs301.Blokus.BlokusViews.DrawBoard;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.utilities.Logger;
import edu.up.cs301.game.R;

/**
 * BlokusHumanPlayer
 *
 * GUI for Blokus game. Moves are made through a series of screen touches and button presses which
 * are set up within this class. GUI is only set to landscape mode.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class BlokusHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = "BlokusHumanPlayer"; //Tag for logging

    //Surface View being drawn on
    private DrawBoard drawBoard;
    protected BlokusGameState blokusState;
    private int layoutId; //ID
    private int selectedPiece;
    private Button quitButton;
    private Button helpButton;
    private Button rotateButton;
    private Button passButton;
    private TextView helpView;

    /**
     * BlokusHumanPlayer
     *
     * ctor for BlokusHumanPlayer
     *
     * @param name
     * @param initLayoutId
     */
    public BlokusHumanPlayer(String name, int initLayoutId) {
        super(name);
        this.layoutId = initLayoutId;
    } //BlokusHumanPlayer

    /**
     * onClick
     *
     * Method for the different buttons that can be used by user. Allows user to quit game, rotate
     * pieces, pass their turn, and access the help menu.
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (game == null) return; // If null, returns
        if(blokusState.getPlayerTurn() != playerNum) // If it is not my turn, flash the screen and returns as well
        {
            drawBoard.flash(Color.RED, 50);
            return;
        }
        //Switches through each button ID and makes an action based on the button
        if (view.getId() == R.id.rotateButton) {
                if(blokusState.getSelectedType() == -1)
                {
                    drawBoard.flash(Color.RED,50);
                    return;
                }
                else
                {
                    BlokusRotateAction blokusRA = new BlokusRotateAction(this);
                    game.sendAction(blokusRA);
                    drawBoard.setIsRotated(blokusState.getPlayerTurn(), blokusState.getSelectedType());
                    drawBoard.invalidate(); //Redraws in order to show piece rotated
                }//Rotates piece in player box
        }
        else if (view.getId() == R.id.quitButton) {
            System.exit(1); //Closes game out
            System.exit(1);
        }
        else if (view.getId() == R.id.helpButton) { //Help Menu shown regardless of player turn
            /**
             * External Citation
             * Date: 13 April 2022
             * Problem: Didn't know how to hide and show a TextView.
             * Resource:
             * https://stackoverflow.com/questions/29470875/how-to-make-a-textview-show-after-
             * the-button-click-how-to-hide-the-textview-agai
             *
             * Solution: I used this source to figure out how to use setVisibility() to remove and
             * show TextViews. This is part of a code stub from the source.
             */
            if (helpView.getVisibility() == View.GONE) {
                helpView.setVisibility(View.VISIBLE);
            }
            else {
                helpView.setVisibility(View.GONE);
            }
        }
        else if (view.getId() == R.id.passButton) {
            BlokusPassAction blokusPA = new BlokusPassAction(this);
            game.sendAction(blokusPA);
        }
    } //onClick

    /**
     * onTouch
     *
     * Method used to select a piece that will be place on board as well as used to actually place
     * pieces on board based on where the user clicks.
     *
     * @param view
     * @param motionEvent
     * @return true -given that the onTouch method is performed correctly
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();

        //Switched based on which player's turn it is.
        int playerBoxX = 0;
        int playerBoxY = 0;

        switch (blokusState.getPlayerTurn()) {
            case 0:
                playerBoxX = DrawBoard.LEFT_BOXES;
                playerBoxY = DrawBoard.TOP_BOXES;
                break;

            case 1:
                playerBoxX = DrawBoard.RIGHT_BOXES;
                playerBoxY = DrawBoard.TOP_BOXES;
                break;

            case 2:
                playerBoxX = DrawBoard.LEFT_BOXES;
                playerBoxY = DrawBoard.BOTTOM_BOXES;
                break;

            case 3:
                playerBoxX = DrawBoard.RIGHT_BOXES;
                playerBoxY = DrawBoard.BOTTOM_BOXES;
                break;
        }


        //Player boxes
        if ((x > playerBoxX) && (x < playerBoxX + DrawBoard.PBOX_WIDTH)
                && (y > playerBoxY) && (y < playerBoxY + DrawBoard.PBOX_HEIGHT)) {

            //Sets piece back to beginning of array
            selectedPiece = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 6; j++) {
                    //Left side of the piece array
                    int spaceXL = playerBoxX + ((DrawBoard.TILE_SIZE * 5) * j) + 10 + (10 * j);
                    int spaceYL = playerBoxY + ((DrawBoard.TILE_SIZE * 5) * i) + 10 + (10 * i);

                    //Right side of the piece array
                    int spaceXR = playerBoxX + ((DrawBoard.TILE_SIZE * 5) * (j + 1)) + 10 + (10 * j);
                    int spaceYR = playerBoxY + ((DrawBoard.TILE_SIZE * 5) * (i + 1)) + 10 + (10 * i);

                    //Makes sure that the user is selecting pieces that do not exist.
                    if (selectedPiece >= 21) {
                        drawBoard.flash(Color.RED, 50);
                    }
                    //Iterates through every piece to find selected piece
                    else if ((x > spaceXL) && (x < spaceXR) &&
                            (y > spaceYL) && (y < spaceYR)) {
                        if(blokusState.getBlockArray()[playerNum][selectedPiece].getOnBoard())
                        {
                            drawBoard.flash(Color.RED, 50);
                        }
                        else
                        {
                            BlokusSelectAction blokusSA = new BlokusSelectAction(this, selectedPiece);
                            game.sendAction(blokusSA);
                            drawBoard.invalidate();
                            return true;
                        }
                    }
                    selectedPiece++; //Increases piece until selected area.
                }
            }
        }

        //Size of grid
        if ((x > 700) && (x < 1300) && (y > 50) && (y < 650)) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    //Iterates through ever spot on the grid in order to find where user presses.
                    if ((x > ((j * DrawBoard.GRIDBOX_SIZE) + 700)) && (x < (((j+1) * DrawBoard.GRIDBOX_SIZE) + 700))
                            && (y > ((i * DrawBoard.GRIDBOX_SIZE) + 50)) && (y < (((i+1) * DrawBoard.GRIDBOX_SIZE) + 50))) {
                        if(blokusState.getBoard()[i][j] != BlokusGameState.tileState.LEGAL)//If tile is not legal, flashes red
                        {
                            drawBoard.flash(Color.RED,50);
                        }
                        else if(selectedPiece > 21)//If selected piece is out of bounds, flashes red
                        {
                            drawBoard.flash(Color.RED, 50);
                        }
                        //If placed piece is out of bounds, flashes red
                        else if (blokusState.placePiece(playerNum, i, j, blokusState.getBlockArray()[playerNum][selectedPiece]) == 2)
                        {
                            drawBoard.flash(Color.RED, 50);
                        }
                        else
                        {
                            BlokusPlaceAction blokusPA = new BlokusPlaceAction(this, i, j);
                            game.sendAction(blokusPA);
                            drawBoard.invalidate(); //Redraws board in order to show the piece was removed
                        }
                    }
                }
            }
        }

        return false;
    } //onTouch
    /**
     * getTopView
     *
     * Method to return the top "view" for the gui
     *
     * @return View
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    } //getTopView

    /**
     * receiveInfo
     *
     * Method to set up and receive a copy of the gamestate to modify
     *
     * @param info
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (drawBoard == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            drawBoard.flash(Color.RED, 50);
        }
        else if (!(info instanceof BlokusGameState)) {
            return; //If there is no BlokusGameState
        }
        else {
            blokusState = ((BlokusGameState)info);
            drawBoard.setState((BlokusGameState)info);
            drawBoard.invalidate();
            Logger.log(TAG, "receiving");
        }
    } //recieveInfo

    /**
     * setAsGui
     *
     * Method to set up the player's GUI
     *
     * @param activity
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);

        drawBoard = (DrawBoard)myActivity.findViewById(R.id.drawBoard);
        Logger.log("set logger", "OnTouch");
        drawBoard.setOnTouchListener(this);

        //Initializes the button variables
        this.quitButton = (Button)activity.findViewById(R.id.quitButton);
        this.helpButton = (Button)activity.findViewById(R.id.helpButton);
        this.rotateButton = (Button)activity.findViewById(R.id.rotateButton);
        this.passButton = (Button)activity.findViewById(R.id.passButton);
        this.helpView = (TextView)activity.findViewById(R.id.helpMenu);

        //Listens for button presses on this GUI
        quitButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        rotateButton.setOnClickListener(this);
        passButton.setOnClickListener(this);
    } //setAsGui
}
