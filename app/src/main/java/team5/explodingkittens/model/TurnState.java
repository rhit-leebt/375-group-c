package team5.explodingkittens.model;

/**
 * TurnState keeps track of the current player, how
 * many times they need to draw, and how they will draw.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class TurnState {

    private final int numPlayers;

    private int turnPlayerId;
    private boolean[] validPlayers;
    private int validCount;
    private int drawCount;
    public DrawType drawType = DrawType.DRAW_FROM_TOP;

    /**
     * The available of drawing methods that can be used during gameplay.
     */
    public enum DrawType {
        DRAW_FROM_TOP, DRAW_FROM_BOTTOM
    }

    /**
     * Creates a TurnState object for a given game,
     * with a given number of players.
     *
     * @param numPlayers The number of players that are playing the game.
     */
    public TurnState(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 10) {
            throw new IllegalArgumentException(
                    "Must have between 2 and 10 players to play the game");
        }
        this.numPlayers = numPlayers;
        this.validPlayers = new boolean[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            validPlayers[i] = true;
        }
        this.validCount = numPlayers;
        turnPlayerId = 0;
        drawCount = 1;
    }

    public int getTurnPlayerId() {
        return turnPlayerId;
    }

    public int getDrawCount() {
        return drawCount;
    }

    /**
     * Applies the action of drawing a card at the end of a turn to the current state.
     */
    public void drawCard() {
        drawCount--;
        drawType = DrawType.DRAW_FROM_TOP;
        if (drawCount == 0) {
            incrementTurn();
            drawCount = 1;
        }
    }

    /**
     * Applies the attack card to the current state of the turn in the game.
     */
    public void attackAction() {
        incrementTurn();
        if (drawCount == 1) {
            drawCount = 2;
        } else {
            drawCount += 2;
        }
    }

    /**
     * Applies the skip card to the current state of the turn in the game.
     */
    public void skipAction() {
        drawCount--;
        drawType = DrawType.DRAW_FROM_TOP;
        if (drawCount == 0) {
            incrementTurn();
            drawCount = 1;
        }
    }

    public void explodeAction() {
        incrementTurn();
        drawCount = 1;
    }

    /**
     * Increments the turn state to the next valid player.
     */
    public void incrementTurn() {
        turnPlayerId = (turnPlayerId + 1) % numPlayers;
        while (!validPlayers[turnPlayerId]) {
            turnPlayerId = (turnPlayerId + 1) % numPlayers;
        }
    }

    /**
     * Makes it so a player can no longer have a turn, usually after they explode.
     *
     * @param playerId The id of the player to invalidate
     */
    public void invalidatePlayer(int playerId) {
        validPlayers[playerId] = false;
        validCount--;
    }

    public int getValidCount() {
        return validCount;
    }

    public boolean isGameOver() {
        return validCount == 1;
    }

    /**
     * Finds the lowest playerId that is still in the game.
     *
     * @return A playerId
     */
    public int findLowestValid() {
        for (int i = 0; i < numPlayers; i++) {
            if (validPlayers[i]) {
                return i;
            }
        }
        return -1;
    }
}
