package edu.up.cs301.Blokus.BlokusActions;

import java.io.Serializable;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * Passes a user's turn (given it is their turn)
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 19th 2022
 */
public class BlokusPassAction extends GameAction implements Serializable {

    //For Serializable interface
    public static final long serialVersionUID = 2091823719L;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BlokusPassAction(GamePlayer player) {
        super(player);
    }
}
