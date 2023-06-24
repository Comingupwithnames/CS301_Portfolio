package edu.up.cs301.Blokus.BlokusActions;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * Help menu action for user who clicks Help Menu button
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version March 31st 2022
 *
 */
public class BlokusSelectAction extends GameAction {

    //For Serializable interface
    public static final long serialVersionUID = 51812318949888L;

    /* Instance variables */
    private int type;

    /**
     * constructor for BlokusSelectAction
     *
     * @param player the player who created the action
     * @param type the passed in type of block
     */
    public BlokusSelectAction(GamePlayer player, int type)
    {
        super(player);

        this.type = type;
    }

    /**
     * Getter method for the type of selected block
     *
     * @return type of block
     */
    public int getBlockType(){ return this.type; }
}
