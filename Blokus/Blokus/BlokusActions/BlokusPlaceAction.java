package edu.up.cs301.Blokus.BlokusActions;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * Place action for user.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version March 31st 2022
 */
public class BlokusPlaceAction extends GameAction {

    //For Serializable interface
    public static final long serialVersionUID = 81237812936718L;

    /* Instance variables */
    private int row;
    private int col;

    /**
     * constructor for BlokusPlaceAction
     *
     * @param player the player who created the action
     * @param row the given row
     * @param col the given column
     */
    public BlokusPlaceAction(GamePlayer player, int row, int col)
    {
        super(player);

        /* Sets the row and col to the ones passed in above */
        this.row = Math.max(0, Math.min(19,row));
        this.col = Math.max(0, Math.min(19,col));
    }

    /**
     * Getter method for the current row
     *
     * @return the current row
     */
    public int getRow(){ return this.row; }

    /**
     * Getter method for the current column
     *
     * @return the current column
     */
    public int getCol(){ return this.col; }
}
