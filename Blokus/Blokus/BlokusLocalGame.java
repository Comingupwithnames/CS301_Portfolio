package edu.up.cs301.Blokus;

import android.util.Log;

import edu.up.cs301.Blokus.BlokusActions.BlokusPassAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusPlaceAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusQuitAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusRotateAction;
import edu.up.cs301.Blokus.BlokusActions.BlokusSelectAction;
import edu.up.cs301.Blokus.BlokusInfo.BlokusGameState;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * LocalGame for Blokus which handles interactions between players.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class BlokusLocalGame extends LocalGame {

    /**
     * BlokusLocalGame
     *
     * default ctor for the Blokus local game.
     */
    public BlokusLocalGame() {
        super();
        Log.i("BlokusLocalGame", "create game");
        super.state = new BlokusGameState();
    } //BlokusLocalGame

    /**
     * BlokusLocalGame
     *
     * ctor that uses a BlokusGameState instead of making a new one.
     *
     * @param initState
     */
    public BlokusLocalGame(BlokusGameState initState) {
        super();
        super.state = new BlokusGameState(initState);
    } //BlokusLocalGame

    /**
     * start
     *
     * Uses super method given the array of players.
     *
     * @param players all players in the game that are not null
     */
    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    } //start

    /**
     * sendUpdatedStateTo
     *
     * sends updated Blokus game state to a given player.
     *
     * @param p current player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new BlokusGameState((BlokusGameState) state));
    } //sendUpdatedStateTo

    /**
     * canMove
     *
     * Checks if given player can make a move
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return boolean
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx == ((BlokusGameState)state).getPlayerTurn()) {
            return true;
        }
        else {
            return false;
        }
    } //canMove

    /**
     * checkIfGameOver
     *
     * Checks if the game is over and returns the winner if it is. Also adds score to a player's
     * score given they managed to place all their pieces.
     *
     * @return a string stating who the winner is based on their score
     */
    @Override
    protected String checkIfGameOver() {
        BlokusGameState blokusState = (BlokusGameState) super.state;
        int piecesUsed = 0;
        int playersPassed = 0;
        int lastPiece;

        //Iterates through each piece per player and checks if it is on the board or not
        for (int i = 0; i < 21; i++) {
            if (blokusState.getBlockArray()[blokusState.getPlayerTurn()][i].getOnBoard() == true) {
                piecesUsed++;
            }
        }

        //Checks whether or not the last piece is the single tile piece
        if (piecesUsed == 20) {
            if (blokusState.getBlockArray()[blokusState.getPlayerTurn()][0].getOnBoard() == false) {
                lastPiece = 1;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (blokusState.getIsPassed()[i]) {
                playersPassed++;
            }
        }

        //If all pieces are played then state who the winner is
        if (piecesUsed == 21) {
            lastPiece = 0;
            //Adds bonus points for placing all pieces as stated in rules
            blokusState.setPlayerScore(blokusState.getPlayerTurn(), 15);

            //Bonus points if last piece placed is the single tile piece
            if (lastPiece == 1) {
                blokusState.setPlayerScore(blokusState.getPlayerTurn(), 5);
            }

            return "Player " + blokusState.getPlayerTurn() + " has won with a score of " +
                    blokusState.getPlayerScore(blokusState.getPlayerTurn()) + "! ";
        }
        else if (playersPassed == 4) { //Means no legal turns left - AI pass if no legal turns found
            int winningPlayer = 0;

            if ((blokusState.getPlayerScore(0) > blokusState.getPlayerScore(1)) &&
                    (blokusState.getPlayerScore(0) > blokusState.getPlayerScore(2)) &&
                    (blokusState.getPlayerScore(0) > blokusState.getPlayerScore(3))) {
                winningPlayer = 0;
            }
            else if ((blokusState.getPlayerScore(1) > blokusState.getPlayerScore(2)) &&
                    (blokusState.getPlayerScore(1) > blokusState.getPlayerScore(3)) &&
                    (blokusState.getPlayerScore(1) > blokusState.getPlayerScore(0))) {
                        winningPlayer = 1;
            }
            else if ((blokusState.getPlayerScore(2) > blokusState.getPlayerScore(3)) &&
                    (blokusState.getPlayerScore(2) > blokusState.getPlayerScore(0)) &&
                    (blokusState.getPlayerScore(2) > blokusState.getPlayerScore(1))) {
                winningPlayer = 2;
            }
            else if ((blokusState.getPlayerScore(3) > blokusState.getPlayerScore(0)) &&
                    (blokusState.getPlayerScore(3) > blokusState.getPlayerScore(1)) &&
                    (blokusState.getPlayerScore(3) > blokusState.getPlayerScore(2))) {
                winningPlayer = 3;
            }

            return "Player " + winningPlayer + " has won with a score of " +
                    blokusState.getPlayerScore(winningPlayer) + "! ";
        }
        else if (blokusState.getGameOn() == false) {
            return "Game Quit! ";
        }
        else {
            return null;
        }
    } //checkIfGameOver

    /**
     * makeMove
     *
     * makes a move for a player
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return boolean
     */
    @Override
    protected boolean makeMove(GameAction action) {
        BlokusGameState state = (BlokusGameState) super.state; //Retrieves the game state for use below
        int playerId; //Current player's ID

        /* Checks to see what the action was and takes the appropriate actions */
        if(action instanceof BlokusSelectAction) {
           BlokusSelectAction bs = (BlokusSelectAction) action;
           playerId = getPlayerIdx(bs.getPlayer()); //Sets player ID as seen here and below
           state.setSelectedType(bs.getBlockType()); //Sets the selected type after the selection action happens
           state.calcLegalMoves(state.getBoard(), playerId); //Calculate legal moves after selecting the piece
           state.checkLegals(state.getBoard(), playerId, state.getBlockArray()[playerId][state.getSelectedType()]);
           return true;
        }
        else if(action instanceof BlokusRotateAction) {
          BlokusRotateAction br = (BlokusRotateAction) action;
          playerId = getPlayerIdx(br.getPlayer());
          if(state.getSelectedType() == -1) {
              return false;
          }
          state.rotatePiece(state.getBlockArray()[playerId][state.getSelectedType()]);//Gets the appropriate piece based off the selected type and ID
          state.calcLegalMoves(state.getBoard(), playerId); //Calculate legal moves after selecting the piece
          state.checkLegals(state.getBoard(), playerId, state.getBlockArray()[playerId][state.getSelectedType()]);
          return true;
        }
        else if(action instanceof BlokusPlaceAction) {
            BlokusPlaceAction bp = (BlokusPlaceAction) action;
            playerId = getPlayerIdx(bp.getPlayer());
            if(state.placePiece(playerId, bp.getCol(), bp.getRow(), state.getBlockArray()[playerId][state.getSelectedType()]) == 0) {
                state.setPlayerScore(playerId, state.getBlockArray()[playerId][state.getSelectedType()].getBlockScore());
                state.setIsPassed(playerId, false); //States player did not skip their turn
                /* Sets the appropriate player's turn based on whose turn it currently is */
                switch (playerId) {
                    case 0:
                        state.setPlayerTurn(1);
                        break;

                    case 1:
                        state.setPlayerTurn(2);
                        break;

                    case 2:
                        state.setPlayerTurn(3);
                        break;

                    case 3:
                        state.setPlayerTurn(0);
                        break;
                }
                return true;
            }
            else {
                return false;
            }
        }
        else if(action instanceof BlokusQuitAction) {
            state.setGameOn(false); //Quits game if its player's turn
            return true;
        }
        else if (action instanceof BlokusPassAction) {
            BlokusPassAction bpa = (BlokusPassAction) action;
            playerId = getPlayerIdx(bpa.getPlayer());
            state.clearBoard(state.getBoard());
            state.setIsPassed(playerId, true); //States player skipped turn

            switch(playerId) {
                case 0:
                    state.setPlayerTurn(1);
                    break;

                case 1:
                    state.setPlayerTurn(2);
                    break;

                case 2:
                    state.setPlayerTurn(3);
                    break;

                case 3:
                    state.setPlayerTurn(0);
                    break;
            }
            return true;
        }

        //Other action, return false
        return false;
    } //makeMove
}
