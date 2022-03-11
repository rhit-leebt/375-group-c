package team5.explodingkittens.model;

import java.io.File;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.controller.ResourceController;

/**
 * A testing script for the {@link Card} class.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class CardTests {

    @Test
    public void testCardsGetName() {
        for (CardType type : CardType.values()) {
            Card card = new Card(type);
            Assert.assertEquals(ResourceController.getCardName(type), card.getName());
        }
    }

    @Test
    public void testCardsCheckForExplodingKitten() {
        for (CardType type : CardType.values()) {
            Card card = new Card(type);
            if (type == CardType.EXPLODING_KITTEN) {
                Assert.assertTrue(card.checkForExplodingKitten());
            } else {
                Assert.assertFalse(card.checkForExplodingKitten());
            }
        }
    }

    @Test
    public void testCardsCheckForDefuse() {
        for (CardType type : CardType.values()) {
            Card card = new Card(type);
            if (type == CardType.DEFUSE) {
                Assert.assertTrue(card.checkForDefuse());
            } else {
                Assert.assertFalse(card.checkForDefuse());
            }
        }
    }

    @Test
    public void testCardsCheckForCatCard() {
        for (CardType type : CardType.values()) {
            Card card = new Card(type);
            if (type == CardType.TACOCAT) {
                Assert.assertTrue(card.checkForCatCard());
            } else if (type == CardType.BEARD_CAT) {
                Assert.assertTrue(card.checkForCatCard());
            } else if (type == CardType.HAIRY_POTATO_CAT) {
                Assert.assertTrue(card.checkForCatCard());
            } else if (type == CardType.CATTERMELON) {
                Assert.assertTrue(card.checkForCatCard());
            } else if (type == CardType.RAINBOW_RALPHING_CAT) {
                Assert.assertTrue(card.checkForCatCard());
            } else if (type == CardType.FERAL_CAT) {
                Assert.assertTrue(card.checkForCatCard());
            } else {
                Assert.assertFalse(card.checkForCatCard());
            }
        }
    }

    @Test
    public void testCardsIsSameCatTypeAs() {
        for (CardType type1 : CardType.values()) {
            for (CardType type2 : CardType.values()) {
                Card card1 = new Card(type1);
                Card card2 = new Card(type2);
                if (card1.checkForCatCard() && card2.checkForCatCard()) {
                    if (type1 == CardType.FERAL_CAT) {
                        Assert.assertTrue(card1.isSameCatTypeAs(card2));
                    } else if (type2 == CardType.FERAL_CAT) {
                        Assert.assertTrue(card1.isSameCatTypeAs(card2));
                    } else if (type1 == type2) {
                        Assert.assertTrue(card1.isSameCatTypeAs(card2));
                    } else {
                        Assert.assertFalse(card1.isSameCatTypeAs(card2));
                    }
                } else {
                    Assert.assertFalse(card1.isSameCatTypeAs(card2));
                }
            }
        }
    }

    @Test
    public void testGetImagePath() {
        String basePath = "." + File.separator + "app" + File.separator
                + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator + "cards" + File.separator;

        Card card1 = new Card(CardType.ALTER_THE_FUTURE);
        Assert.assertEquals(basePath + "alterthefuture.png", card1.getImagePath());
        Card card2 = new Card(CardType.ATTACK);
        Assert.assertEquals(basePath + "attack.png", card2.getImagePath());
        Card card3 = new Card(CardType.TACOCAT);
        Assert.assertEquals(basePath + "tacocat.png", card3.getImagePath());
        Card card4 = new Card(CardType.RAINBOW_RALPHING_CAT);
        Assert.assertEquals(basePath + "rainbowralphingcat.png", card4.getImagePath());
        Card card5 = new Card(CardType.BEARD_CAT);
        Assert.assertEquals(basePath + "beardcat.png", card5.getImagePath());
        Card card6 = new Card(CardType.CATTERMELON);
        Assert.assertEquals(basePath + "cattermelon.png", card6.getImagePath());
        Card card7 = new Card(CardType.HAIRY_POTATO_CAT);
        Assert.assertEquals(basePath + "hairypotatocat.png", card7.getImagePath());
        Card card8 = new Card(CardType.DEFUSE);
        Assert.assertEquals(basePath + "defuse.png", card8.getImagePath());
        Card card9 = new Card(CardType.DRAW_FROM_THE_BOTTOM);
        Assert.assertEquals(basePath + "drawfromthebottom.png", card9.getImagePath());
        Card card10 = new Card(CardType.EXPLODING_KITTEN);
        Assert.assertEquals(basePath + "explodingkitten.png", card10.getImagePath());
        Card card11 = new Card(CardType.FAVOR);
        Assert.assertEquals(basePath + "favor.png", card11.getImagePath());
        Card card12 = new Card(CardType.FERAL_CAT);
        Assert.assertEquals(basePath + "feralcat.png", card12.getImagePath());
        Card card13 = new Card(CardType.NOPE);
        Assert.assertEquals(basePath + "nope.png", card13.getImagePath());
        Card card14 = new Card(CardType.SEE_THE_FUTURE);
        Assert.assertEquals(basePath + "seethefuture.png", card14.getImagePath());
        Card card15 = new Card(CardType.SHUFFLE);
        Assert.assertEquals(basePath + "shuffle.png", card15.getImagePath());
        Card card16 = new Card(CardType.SKIP);
        Assert.assertEquals(basePath + "skip.png", card16.getImagePath());
    }

    @Test
    public void testCardInfoWithMessageBundles() {
        Card card1 = new Card(CardType.ALTER_THE_FUTURE);
        Assert.assertTrue(card1.getCardInfo().startsWith(
                "Privately view the top 3 cards"));
        Card card2 = new Card(CardType.ATTACK);
        Assert.assertTrue(card2.getCardInfo().startsWith(
                "Immediately end your turn(s) without drawing"));
        Card card3 = new Card(CardType.TACOCAT);
        Assert.assertTrue(card3.getCardInfo().startsWith(
                "These cards are powerless on their own"));
        Card card4 = new Card(CardType.RAINBOW_RALPHING_CAT);
        Assert.assertTrue(card4.getCardInfo().startsWith(
                "These cards are powerless on their own"));
        Card card5 = new Card(CardType.BEARD_CAT);
        Assert.assertTrue(card5.getCardInfo().startsWith(
                "These cards are powerless on their own"));
        Card card6 = new Card(CardType.CATTERMELON);
        Assert.assertTrue(card6.getCardInfo().startsWith(
                "These cards are powerless on their own"));
        Card card7 = new Card(CardType.HAIRY_POTATO_CAT);
        Assert.assertTrue(card7.getCardInfo().startsWith(
                "These cards are powerless on their own"));
        Card card8 = new Card(CardType.DEFUSE);
        Assert.assertTrue(card8.getCardInfo().startsWith(
                "If you drew an Exploding Kitten, you can play this card"));
        Card card9 = new Card(CardType.DRAW_FROM_THE_BOTTOM);
        Assert.assertTrue(card9.getCardInfo().startsWith(
                "End your turn by drawing the bottom card"));
        Card card10 = new Card(CardType.EXPLODING_KITTEN);
        Assert.assertTrue(card10.getCardInfo().startsWith(
                "You must show this card immediately"));
        Card card11 = new Card(CardType.FAVOR);
        Assert.assertTrue(card11.getCardInfo().startsWith(
                "Force any other player to give you 1 card"));
        Card card12 = new Card(CardType.FERAL_CAT);
        Assert.assertTrue(card12.getCardInfo().startsWith(
                "Used as any Cat Card."));
        Card card13 = new Card(CardType.NOPE);
        Assert.assertTrue(card13.getCardInfo().startsWith(
                "Stop any action except for an Exploding Kitten"));
        Card card14 = new Card(CardType.SEE_THE_FUTURE);
        Assert.assertTrue(card14.getCardInfo().startsWith(
                "Privately view the top 3 cards from the Draw Pile and put them back"));
        Card card15 = new Card(CardType.SHUFFLE);
        Assert.assertTrue(card15.getCardInfo().startsWith(
                "Shuffle the Draw Pile without viewing"));
        Card card16 = new Card(CardType.SKIP);
        Assert.assertTrue(card16.getCardInfo().startsWith(
                "Immediately end your turn without drawing a card. If"));
    }

    @Test
    public void testPlayCard() {
        GameController controller = EasyMock.mock(GameController.class);
        Card card = new Card(CardType.FAVOR);
        controller.startFavor();
        EasyMock.replay(controller);
        card.playCard(controller);
        EasyMock.verify(controller);
    }
}