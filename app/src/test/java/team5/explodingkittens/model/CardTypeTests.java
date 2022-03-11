package team5.explodingkittens.model;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.ResourceController;


/**
 * A testing script created to test {@link CardType}.
 *
 * @author Duncan McKee
 */
public class CardTypeTests {
    @Test
    public void testGetName() {
        CardType type1 = CardType.ALTER_THE_FUTURE;
        Assert.assertEquals("Alter the Future", ResourceController.getCardName(type1));
        CardType type2 = CardType.ATTACK;
        Assert.assertEquals("Attack", ResourceController.getCardName(type2));
        CardType type3 = CardType.TACOCAT;
        Assert.assertEquals("Tacocat", ResourceController.getCardName(type3));
        CardType type4 = CardType.RAINBOW_RALPHING_CAT;
        Assert.assertEquals("Rainbow Ralphing Cat", ResourceController.getCardName(type4));
        CardType type5 = CardType.BEARD_CAT;
        Assert.assertEquals("Beard Cat", ResourceController.getCardName(type5));
        CardType type6 = CardType.CATTERMELON;
        Assert.assertEquals("Cattermelon", ResourceController.getCardName(type6));
        CardType type7 = CardType.HAIRY_POTATO_CAT;
        Assert.assertEquals("Hairy Potato Cat", ResourceController.getCardName(type7));
        CardType type8 = CardType.DEFUSE;
        Assert.assertEquals("Defuse", ResourceController.getCardName(type8));
        CardType type9 = CardType.DRAW_FROM_THE_BOTTOM;
        Assert.assertEquals("Draw from the Bottom", ResourceController.getCardName(type9));
        CardType type10 = CardType.EXPLODING_KITTEN;
        Assert.assertEquals("Exploding Kitten", ResourceController.getCardName(type10));
        CardType type11 = CardType.FAVOR;
        Assert.assertEquals("Favor", ResourceController.getCardName(type11));
        CardType type12 = CardType.FERAL_CAT;
        Assert.assertEquals("Feral Cat", ResourceController.getCardName(type12));
        CardType type13 = CardType.NOPE;
        Assert.assertEquals("Nope", ResourceController.getCardName(type13));
        CardType type14 = CardType.SEE_THE_FUTURE;
        Assert.assertEquals("See the Future", ResourceController.getCardName(type14));
        CardType type15 = CardType.SHUFFLE;
        Assert.assertEquals("Shuffle", ResourceController.getCardName(type15));
        CardType type16 = CardType.SKIP;
        Assert.assertEquals("Skip", ResourceController.getCardName(type16));
    }

    @Test
    public void testGetFilePath() {
        String basePath = "." + File.separator + "app" + File.separator
                + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator + "cards" + File.separator;

        CardType type1 = CardType.ALTER_THE_FUTURE;
        Assert.assertEquals(basePath + "alterthefuture.png", type1.getPath());
        CardType type2 = CardType.ATTACK;
        Assert.assertEquals(basePath + "attack.png", type2.getPath());
        CardType type3 = CardType.TACOCAT;
        Assert.assertEquals(basePath + "tacocat.png", type3.getPath());
        CardType type4 = CardType.RAINBOW_RALPHING_CAT;
        Assert.assertEquals(basePath + "rainbowralphingcat.png", type4.getPath());
        CardType type5 = CardType.BEARD_CAT;
        Assert.assertEquals(basePath + "beardcat.png", type5.getPath());
        CardType type6 = CardType.CATTERMELON;
        Assert.assertEquals(basePath + "cattermelon.png", type6.getPath());
        CardType type7 = CardType.HAIRY_POTATO_CAT;
        Assert.assertEquals(basePath + "hairypotatocat.png", type7.getPath());
        CardType type8 = CardType.DEFUSE;
        Assert.assertEquals(basePath + "defuse.png", type8.getPath());
        CardType type9 = CardType.DRAW_FROM_THE_BOTTOM;
        Assert.assertEquals(basePath + "drawfromthebottom.png", type9.getPath());
        CardType type10 = CardType.EXPLODING_KITTEN;
        Assert.assertEquals(basePath + "explodingkitten.png", type10.getPath());
        CardType type11 = CardType.FAVOR;
        Assert.assertEquals(basePath + "favor.png", type11.getPath());
        CardType type12 = CardType.FERAL_CAT;
        Assert.assertEquals(basePath + "feralcat.png", type12.getPath());
        CardType type13 = CardType.NOPE;
        Assert.assertEquals(basePath + "nope.png", type13.getPath());
        CardType type14 = CardType.SEE_THE_FUTURE;
        Assert.assertEquals(basePath + "seethefuture.png", type14.getPath());
        CardType type15 = CardType.SHUFFLE;
        Assert.assertEquals(basePath + "shuffle.png", type15.getPath());
        CardType type16 = CardType.SKIP;
        Assert.assertEquals(basePath + "skip.png", type16.getPath());
    }

    @Test
    public void testGetCountTooFewPlayers() {
        for (CardType type : CardType.values()) {
            try {
                type.getCount(1);
                Assert.fail();
            } catch (IllegalArgumentException e) {
                Assert.assertEquals(
                        "There cannot be less than 2 players or more than 10.", e.getMessage());
            }
        }
    }

    @Test
    public void testGetCountTooManyPlayers() {
        for (CardType type : CardType.values()) {
            try {
                type.getCount(11);
                Assert.fail();
            } catch (IllegalArgumentException e) {
                Assert.assertEquals(
                        "There cannot be less than 2 players or more than 10.", e.getMessage());
            }
        }
    }

    private void compareCount(int expected, CardType type, int numPlayers) {
        Assert.assertEquals(expected, type.getCount(numPlayers));
    }

    @Test
    public void testGetCountTwoToThreePlayers() {
        for (int i = 2; i < 4; i++) {
            compareCount(2, CardType.ALTER_THE_FUTURE, i);
            compareCount(4, CardType.ATTACK, i);
            compareCount(3, CardType.TACOCAT, i);
            compareCount(3, CardType.RAINBOW_RALPHING_CAT, i);
            compareCount(3, CardType.BEARD_CAT, i);
            compareCount(3, CardType.CATTERMELON, i);
            compareCount(3, CardType.HAIRY_POTATO_CAT, i);
            compareCount(3, CardType.DEFUSE, i);
            compareCount(3, CardType.DRAW_FROM_THE_BOTTOM, i);
            compareCount(i, CardType.EXPLODING_KITTEN, i);
            compareCount(2, CardType.FAVOR, i);
            compareCount(2, CardType.FERAL_CAT, i);
            compareCount(4, CardType.NOPE, i);
            compareCount(3, CardType.SEE_THE_FUTURE, i);
            compareCount(2, CardType.SHUFFLE, i);
            compareCount(4, CardType.SKIP, i);
        }
    }

    @Test
    public void testGetCountFourToSevenPlayers() {
        for (int i = 4; i < 8; i++) {
            compareCount(4, CardType.ALTER_THE_FUTURE, i);
            compareCount(7, CardType.ATTACK, i);
            compareCount(4, CardType.TACOCAT, i);
            compareCount(4, CardType.RAINBOW_RALPHING_CAT, i);
            compareCount(4, CardType.BEARD_CAT, i);
            compareCount(4, CardType.CATTERMELON, i);
            compareCount(4, CardType.HAIRY_POTATO_CAT, i);
            compareCount(7, CardType.DEFUSE, i);
            compareCount(4, CardType.DRAW_FROM_THE_BOTTOM, i);
            compareCount(i, CardType.EXPLODING_KITTEN, i);
            compareCount(4, CardType.FAVOR, i);
            compareCount(4, CardType.FERAL_CAT, i);
            compareCount(6, CardType.NOPE, i);
            compareCount(3, CardType.SEE_THE_FUTURE, i);
            compareCount(4, CardType.SHUFFLE, i);
            compareCount(6, CardType.SKIP, i);
        }
    }

    @Test
    public void testGetCountEightToTenPlayers() {
        for (int i = 8; i < 11; i++) {
            compareCount(6, CardType.ALTER_THE_FUTURE, i);
            compareCount(11, CardType.ATTACK, i);
            compareCount(7, CardType.TACOCAT, i);
            compareCount(7, CardType.RAINBOW_RALPHING_CAT, i);
            compareCount(7, CardType.BEARD_CAT, i);
            compareCount(7, CardType.CATTERMELON, i);
            compareCount(7, CardType.HAIRY_POTATO_CAT, i);
            compareCount(10, CardType.DEFUSE, i);
            compareCount(7, CardType.DRAW_FROM_THE_BOTTOM, i);
            compareCount(i, CardType.EXPLODING_KITTEN, i);
            compareCount(6, CardType.FAVOR, i);
            compareCount(6, CardType.FERAL_CAT, i);
            compareCount(10, CardType.NOPE, i);
            compareCount(6, CardType.SEE_THE_FUTURE, i);
            compareCount(6, CardType.SHUFFLE, i);
            compareCount(10, CardType.SKIP, i);
        }
    }

    @Test
    public void testCardInfoWithMessageBundles() {
        CardType type1 = CardType.ALTER_THE_FUTURE;
        Assert.assertTrue(ResourceController.getCardInfo(type1).startsWith(
                "Privately view the top 3 cards"));
        CardType type2 = CardType.ATTACK;
        Assert.assertTrue(ResourceController.getCardInfo(type2).startsWith(
                "Immediately end your turn(s) without drawing"));
        CardType type3 = CardType.TACOCAT;
        Assert.assertTrue(ResourceController.getCardInfo(type3).startsWith(
                "These cards are powerless on their own"));
        CardType type4 = CardType.RAINBOW_RALPHING_CAT;
        Assert.assertTrue(ResourceController.getCardInfo(type4).startsWith(
                "These cards are powerless on their own"));
        CardType type5 = CardType.BEARD_CAT;
        Assert.assertTrue(ResourceController.getCardInfo(type5).startsWith(
                "These cards are powerless on their own"));
        CardType type6 = CardType.CATTERMELON;
        Assert.assertTrue(ResourceController.getCardInfo(type6).startsWith(
                "These cards are powerless on their own"));
        CardType type7 = CardType.HAIRY_POTATO_CAT;
        Assert.assertTrue(ResourceController.getCardInfo(type7).startsWith(
                "These cards are powerless on their own"));
        CardType type8 = CardType.DEFUSE;
        Assert.assertTrue(ResourceController.getCardInfo(type8).startsWith(
                "If you drew an Exploding Kitten, you can play this card"));
        CardType type9 = CardType.DRAW_FROM_THE_BOTTOM;
        Assert.assertTrue(ResourceController.getCardInfo(type9).startsWith(
                "End your turn by drawing the bottom card"));
        CardType type10 = CardType.EXPLODING_KITTEN;
        Assert.assertTrue(ResourceController.getCardInfo(type10).startsWith(
                "You must show this card immediately"));
        CardType type11 = CardType.FAVOR;
        Assert.assertTrue(ResourceController.getCardInfo(type11).startsWith(
                "Force any other player to give you 1 card"));
        CardType type12 = CardType.FERAL_CAT;
        Assert.assertTrue(ResourceController.getCardInfo(type12).startsWith(
                "Used as any Cat Card."));
        CardType type13 = CardType.NOPE;
        Assert.assertTrue(ResourceController.getCardInfo(type13).startsWith(
                "Stop any action except for an Exploding Kitten"));
        CardType type14 = CardType.SEE_THE_FUTURE;
        Assert.assertTrue(ResourceController.getCardInfo(type14).startsWith(
                "Privately view the top 3 cards from the Draw Pile and put them back"));
        CardType type15 = CardType.SHUFFLE;
        Assert.assertTrue(ResourceController.getCardInfo(type15).startsWith(
                "Shuffle the Draw Pile without viewing"));
        CardType type16 = CardType.SKIP;
        Assert.assertTrue(ResourceController.getCardInfo(type16).startsWith(
                "Immediately end your turn without drawing a card. If"));
    }

}
