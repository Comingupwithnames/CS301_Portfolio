package edu.up.cs301.Blokus;

import edu.up.cs301.Blokus.BlokusInfo.BlokusGameState;
import edu.up.cs301.Blokus.BlokusPlayer.BlokusDumbAi;
import edu.up.cs301.Blokus.BlokusPlayer.BlokusHumanPlayer;
import edu.up.cs301.Blokus.BlokusPlayer.BlokusSmartAi;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.game.R;
import java.util.ArrayList;

/**
 * Primary Activity for Blokus
 *
 * PROJECT #J INFO:
 * Game runs well with both AI and with remote play. Legal places are not highlighted based on
 * certain pieces. If a piece were to go through another piece when placed, it will not highlight
 * that area as a legal placement anymore. Also fixed bug where if piece were to go off the board,
 * it would rotate piece which sometimes ended up causing pieces to overlap. Now the piece will no
 * longer be placeable if it will go out of bounds.
 *
 * With the Smart AI, the AI continuously tries to place pieces. If it reaches a certain amount of
 * attempted placements it passes the turn. And if all players pass their turn, it ends the game
 * and finds out who has the highest score.
 *
 * @author Max C., Comingupwithnames, Gavin R.
 * @version April 22nd 2022
 */
public class BlokusMainActivity extends GameMainActivity {

    private static final String TAG = "BlokusMainActivity";
    public static final int PORT_NUMBER = 56437;

    /**
     * Blokus game for 4 players. Default to 1 Human Player and 3 Computer Players.
     *
     * @return defaultConfig
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        //Blokus GUI
        playerTypes.add(new GamePlayerType("Local Blokus Human Player") {
            public GamePlayer createPlayer(String name) {
                return new BlokusHumanPlayer(name, R.layout.activity_main);
            }
        });

        //Dumb Computer Player
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new BlokusDumbAi(name);
            }
        });


        //Dumb Computer Player #2
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new BlokusSmartAi(name);
            }
        });

        // Create a game configuration class for Blokus
        GameConfig defaultConfig = new GameConfig(playerTypes, 4,4, "Blokus", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); //GUI
        defaultConfig.addPlayer("Computer Player #1", 2); //Default is Dumb AI
        defaultConfig.addPlayer("Computer Player #2", 2); //Default is Dumb AI
        defaultConfig.addPlayer("Computer Player #3", 2); //Default is Dumb AI

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        return defaultConfig;
    } //createDefaultConfig

    /**
     * Creates a new game to run on tablet given the GameState is null
     *
     * @param gameState - Game State for this game.
     *
     * @return gameState typeCasted to BlokusGameState for LocalGame
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) {
            gameState = new BlokusGameState();
        }

        return new BlokusLocalGame((BlokusGameState) gameState);
    } //createLocalGame
}
