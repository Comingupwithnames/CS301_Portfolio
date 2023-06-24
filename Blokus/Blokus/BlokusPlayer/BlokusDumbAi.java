package edu.up.cs301.Blokus.BlokusPlayer;

import edu.up.cs301.Blokus.BlokusActions.BlokusPassAction;
import edu.up.cs301.Blokus.BlokusInfo.BlokusGameState;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.utilities.Logger;

/**
 * Dumb computer player for Blokus that places first piece in first legal spot.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class BlokusDumbAi extends GameComputerPlayer {

    //21 pieces
    BlokusGameState myState;

    /**
     * BlokusDumbAi
     *
     * Default ctor for the dumb computer player
     *
     * @param name
     */
    public BlokusDumbAi(String name) {
        super(name);
    }

    /**
     * recieveInfo
     *
     * Method that makes action for the dumb computer player.
     *
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return; //nothing happens if it isn't players turn
        if (info instanceof IllegalMoveInfo) return; //nothing happens if the info is an illegal

        Logger.log("BlokusDumbAi", "My turn!");

        myState = (BlokusGameState)info;

        //Allow for AI to take time between plays
        sleep(3);

        Logger.log("BlokusDumbAi", "Sending move");
        game.sendAction(new BlokusPassAction(this)); //Passes its turn
    } //receiveInfo
}
