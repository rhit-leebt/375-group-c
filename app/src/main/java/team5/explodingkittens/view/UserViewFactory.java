package team5.explodingkittens.view;

/**
 * A factory for the UserView, such that it can be fully tested.
 *
 * @author Duncan McKee
 */
public class UserViewFactory {
    private boolean applicationMode;

    public UserViewFactory(boolean applicationMode) {
        this.applicationMode = applicationMode;
    }

    /**
     * Creates a AbstractUserView with the provided information.
     *
     * @param numPlayers The number of players
     * @param playerId The id of this player
     * @return A UserView
     */
    public AbstractUserView createUserView(int numPlayers, int playerId) {
        if (applicationMode) {
            return new UserView(numPlayers, playerId);
        } else {
            return new NullUserView();
        }
    }
}
