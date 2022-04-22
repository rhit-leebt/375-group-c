package team5.explodingkittens.view;

import team5.explodingkittens.model.Player;

import java.util.List;

/**
 * A factory for the UserView, such that it can be fully tested.
 *
 * @author Duncan McKee
 */
public class UserViewFactory {

    /**
     * Creates a AbstractUserView with the provided information.
     *
     * @param numPlayers The number of players
     * @param playerId The id of this player
     * @return A UserView
     */
    public AbstractUserView createUserView(int numPlayers, int playerId) {
        return new UserView(numPlayers, playerId);
    }

    public SpectatorView createSpectatorView(List<Player> numPlayers) {
        return new SpectatorView(numPlayers);
    }
}
