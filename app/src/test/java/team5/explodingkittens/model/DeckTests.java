package team5.explodingkittens.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.AbstractUserView;

/**
 * A testing script created to test {@link Deck}.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class DeckTests {

    private static class TestUserController extends UserController {

        public ArrayList<Card> cards = new ArrayList<>();

        public TestUserController(int playerId) {
            super(EasyMock.createMock(GameController.class),
                    EasyMock.createMock(AbstractUserView.class),
                    EasyMock.createMock(Player.class), playerId);
        }

        @Override
        public void drawCard(Card card) {
            cards.add(card);
        }
    }

    @Test
    public void testGetSizeTooFewPlayers() {
        try {
            new Deck(1);
            Assert.fail("Should have thrown an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(
                    "Number of players should be greater than or equal to 2", e.getMessage());
        }
    }

    @Test
    public void testGetSizeTooManyPlayers() {
        try {
            new Deck(11);
            Assert.fail("Should have thrown an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(
                    "Number of players should be less than or equal to 10", e.getMessage());
        }
    }

    @Test
    public void testGetSizeTwoToThreePlayers() {
        Deck deck = new Deck(2);
        Assert.assertEquals(41, deck.getSize());
    }

    @Test
    public void testGetSizeFourToSevenPlayers() {
        Deck deck = new Deck(4);
        Assert.assertEquals(62, deck.getSize());
    }

    @Test
    public void testGetSizeEightToTenPlayers() {
        Deck deck = new Deck(8);
        Assert.assertEquals(103, deck.getSize());
    }

    @Test
    public void testDrawFromDealtDeckTwo() {
        Deck deck = EasyMock.partialMockBuilder(Deck.class)
                .addMockedMethod("shuffle").withConstructor(2).createMock();
        TestUserController u1 = new TestUserController(0);
        TestUserController u2 = new TestUserController(1);
        List<UserController> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        deck.shuffle();
        deck.shuffle();
        EasyMock.replay(deck);
        deck.dealCards(users);
        Assert.assertEquals(29, deck.getSize());
        Assert.assertTrue(u1.cards.get(0).checkForCardType(CardType.DEFUSE));
        Assert.assertTrue(u2.cards.get(0).checkForCardType(CardType.DEFUSE));
        Assert.assertEquals(Player.HAND_SIZE, u1.cards.size());
        Assert.assertEquals(Player.HAND_SIZE, u2.cards.size());
        EasyMock.verify(deck);
    }

    @Test
    public void testShuffle() {
        Deck deckShuffled = new Deck(2);
        deckShuffled.random = new Random(0);
        deckShuffled.shuffle();
        Deck deckNotShuffled = new Deck(2);
        boolean foundMismatch = false;
        while (deckShuffled.getSize() > 0) {
            String card1 = deckShuffled.draw().getName();
            String card2 = deckNotShuffled.draw().getName();
            if (!card1.equals(card2)) {
                foundMismatch = true;
                break;
            }
        }
        Assert.assertTrue(
                "Decks should should never match, as " +
                        "the seeded random is known not to shuffle " +
                        "into a default unshuffled deck",
                foundMismatch);
    }

    @Test
    public void testDraw() {
        Deck deck = new Deck(2);
        Assert.assertEquals(41, deck.getSize());
        for (int i = 0; i < 41; i++) {
            deck.draw();
        }
        try {
            deck.draw();
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot draw when there are no cards remaining", e.getMessage());
        }
    }

    @Test
    public void testDrawAtBottom() {
        Deck deck = new Deck(2);
        Assert.assertEquals(41, deck.getSize());
        for (int i = 0; i < 41; i++) {
            deck.drawAtBottom();
        }
        try {
            deck.drawAtBottom();
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot draw when there are no cards remaining", e.getMessage());
        }
    }

    @Test
    public void testInsertEmptyDeck() {
        Deck deck = new Deck(2);
        int size = deck.getSize();
        for (int i = 0; i < size; i++) {
            deck.draw();
        }

        Card kitten = EasyMock.mock(Card.class);
        EasyMock.replay(kitten);

        deck.insertCard(kitten, 0);

        Assert.assertEquals(1, deck.getSize());
        Assert.assertEquals(kitten, deck.draw());

        EasyMock.verify(kitten);
    }

    @Test
    public void testInsertNegativeIndex() {
        Deck deck = new Deck(2);
        Card kitten = EasyMock.mock(Card.class);
        EasyMock.replay(kitten);
        try {
            deck.insertCard(kitten, -1);
            Assert.fail("Inserting a card at a negative index"
                    + " should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot insert a card at a negative depth", e.getMessage());
        } finally {
            EasyMock.verify(kitten);
        }
    }

    @Test
    public void testInsertDepthAboveSize() {
        Deck deck = new Deck(2);
        Card kitten = EasyMock.mock(Card.class);
        EasyMock.replay(kitten);
        try {
            deck.insertCard(kitten, 43);
            Assert.fail("Inserting a card at an index greater than"
                    + " the deck size should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Deck depth is not that large", e.getMessage());
        } finally {
            EasyMock.verify(kitten);
        }
    }

    @Test
    public void testInsertMaxDepth() {
        Deck deck = new Deck(2);
        Card kitten = EasyMock.mock(Card.class);
        EasyMock.replay(kitten);
        deck.insertCard(kitten, 41);
        Assert.assertEquals(42, deck.getSize());
        Assert.assertEquals(kitten, deck.drawAtBottom());
        EasyMock.verify(kitten);
    }

    @Test
    public void testInsertMinDepth() {
        Deck deck = new Deck(2);
        Card kitten = EasyMock.mock(Card.class);
        EasyMock.replay(kitten);
        deck.insertCard(kitten, 0);
        Assert.assertEquals(42, deck.getSize());
        Assert.assertEquals(kitten, deck.draw());
        EasyMock.verify(kitten);
    }

    @Test
    public void testGetCard() {
        // This is before shuffling, so the order is not random
        Deck deck = new Deck(10);
        ResourceController.setLocale(Locale.US);
        Assert.assertEquals("Skip", deck.getCard(0).getName());
        Assert.assertEquals("Nope", deck.getCard(26).getName());
        Assert.assertEquals("Hairy Potato Cat", deck.getCard(51).getName());
        Assert.assertEquals("Rainbow Ralphing Cat", deck.getCard(77).getName());
        Assert.assertEquals("Alter the Future", deck.getCard(102).getName());
    }
}
