package team5.explodingkittens.model;

import java.io.File;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.model.cardaction.*;

/**
 * All available types of cards that can be seen during gameplay.
 *
 * @author Duncan McKee
 */
public enum CardType {
    ALTER_THE_FUTURE("alterthefuture",
            2, 4, true, new AlterTheFutureAction()),
    ATTACK("attack",
            4, 7, true, new AttackCardAction()),
    TACOCAT("tacocat",
            3, 4, true, new CatCardAction()),
    RAINBOW_RALPHING_CAT("rainbowralphingcat",
            3, 4, true, new CatCardAction()),
    BEARD_CAT("beardcat",
            3, 4, true, new CatCardAction()),
    CATTERMELON("cattermelon",
            3, 4, true, new CatCardAction()),
    HAIRY_POTATO_CAT("hairypotatocat",
            3, 4, true, new CatCardAction()),
    DEFUSE("defuse",
            3, 7, false, new NullCardAction()),
    DRAW_FROM_THE_BOTTOM("drawfromthebottom",
            3, 4, true, new DrawFromTheBottomCardAction()),
    EXPLODING_KITTEN("explodingkitten",
            0, 0, false, new NullCardAction()),
    FAVOR("favor",
            2, 4, true, new FavorCardAction()),
    FERAL_CAT("feralcat",
            2, 4, true, new CatCardAction()),
    NOPE("nope",
            4, 6, true, new NullCardAction()),
    SEE_THE_FUTURE("seethefuture",
            3, 3, true, new SeeTheFutureAction()),
    SHUFFLE("shuffle",
            2, 4, true, new ShuffleCardAction()),
    SKIP("skip",
            4, 6, true, new SkipCardAction());

    private static final String BASE_PATH = "." + File.separator
            + "app" + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator + "images"
            + File.separator + "cards" + File.separator;

    public final String genericName;
    public final boolean preload;

    private final int pawCount;
    private final int noPawCount;
    private final CardAction playAction;

    CardType(String genericName, int pawCount, int noPawCount,
             boolean preload, CardAction playAction) {
        this.genericName = genericName;
        this.pawCount = pawCount;
        this.noPawCount = noPawCount;
        this.preload = preload;
        this.playAction = playAction;
    }

    /**
     * Given a number of players that are playing the game,
     * this method will return how many of a given card type
     * should be included in the deck.
     *
     * @param numPlayers The number of players in the given game.
     * @return The amount of the given type of card that should be included in the deck.
     */
    public int getCount(int numPlayers) {
        if (1 >= numPlayers || 11 <= numPlayers) {
            throw new IllegalArgumentException(
                    "There cannot be less than 2 players or more than 10.");
        }
        if (this == EXPLODING_KITTEN) {
            return numPlayers;
        }
        if (numPlayers < 4) {
            return pawCount;
        } else if (numPlayers < 8) {
            return noPawCount;
        } else {
            return noPawCount + pawCount;
        }
    }

    public String getPath() {
        return BASE_PATH + genericName + ".png";
    }

    public void playCard(GameController controller) {
        playAction.applyAction(controller);
    }

    public String getGenericName() {
        return this.genericName;
    }
}
