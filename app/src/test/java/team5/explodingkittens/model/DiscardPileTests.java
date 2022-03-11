package team5.explodingkittens.model;

import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * A testing script created to test {@link DiscardPile}.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class DiscardPileTests {
    @Test
    public void testGetSizeStarting() {
        DiscardPile discardPile = new DiscardPile();
        Assert.assertEquals(0, discardPile.getSize());
    }

    @Test
    public void testDiscardingEmpty() {
        DiscardPile discardPile = new DiscardPile();
        Card card = EasyMock.mock(Card.class);

        EasyMock.replay(card);
        discardPile.discardCard(card);

        Assert.assertEquals(1, discardPile.getSize());
        EasyMock.verify(card);
    }

    @Test
    public void testDiscardingSeveral() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {
            Card card = EasyMock.mock(Card.class);
            cards.add(card);
        }

        for (int i = 0; i < 25; i++) {
            EasyMock.replay(cards.get(i));

            discardPile.discardCard(cards.get(i));
        }

        Assert.assertEquals(25, discardPile.getSize());
        for (int i = 0; i < 25; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testTakingNegativeIndex() {
        DiscardPile discardPile = new DiscardPile();
        try {
            discardPile.takeCard(-1);
            Assert.fail(
                    "trying to take at a negative index should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot take from a negative depth", e.getMessage());
        }
    }

    @Test
    public void testTakingMinInt() {
        DiscardPile discardPile = new DiscardPile();
        try {
            discardPile.takeCard(Integer.MIN_VALUE);
            Assert.fail(
                    "trying to take at a negative index should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot take from a negative depth", e.getMessage());
        }
    }

    @Test
    public void testTakingEmptyPile() {
        DiscardPile discardPile = new DiscardPile();
        try {
            discardPile.takeCard(0);
            Assert.fail("trying to take from an empty pile should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertEquals("Cannot take from an empty pile", e.getMessage());
        }
    }

    @Test
    public void testTakingOnlyCard() {
        DiscardPile discardPile = new DiscardPile();
        Card card = EasyMock.mock(Card.class);

        EasyMock.replay(card);
        discardPile.discardCard(card);

        Assert.assertEquals(card, discardPile.takeCard(0));
        EasyMock.verify(card);
    }

    @Test
    public void testTakingFromMiddle() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {
            Card card = EasyMock.mock(Card.class);
            cards.add(card);
        }

        for (int i = 0; i < 25; i++) {
            EasyMock.replay(cards.get(i));

            discardPile.discardCard(cards.get(i));
        }

        Assert.assertEquals(cards.get(8), discardPile.takeCard(16));
        for (int i = 0; i < 25; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testTakingFromTop() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {
            Card card = EasyMock.mock(Card.class);
            cards.add(card);
        }

        for (int i = 0; i < 25; i++) {
            EasyMock.replay(cards.get(i));

            discardPile.discardCard(cards.get(i));
        }

        Assert.assertEquals(cards.get(24), discardPile.takeCard(0));
        for (int i = 0; i < 25; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testTakingFromBottom() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {
            Card card = EasyMock.mock(Card.class);
            cards.add(card);
        }

        for (int i = 0; i < 25; i++) {
            EasyMock.replay(cards.get(i));

            discardPile.discardCard(cards.get(i));
        }

        Assert.assertEquals(cards.get(0), discardPile.takeCard(24));
        for (int i = 0; i < 25; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testTakingFromTooLarge() {
        DiscardPile discardPile = new DiscardPile();
        Card card = EasyMock.mock(Card.class);

        EasyMock.replay(card);
        discardPile.discardCard(card);

        try {
            discardPile.takeCard(1);
            Assert.fail("taking from too large an index should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("That index is larger than the deck", e.getMessage());
        }
    }

    @Test
    public void testTakingMaxInt() {
        DiscardPile discardPile = new DiscardPile();
        Card card = EasyMock.mock(Card.class);

        EasyMock.replay(card);
        discardPile.discardCard(card);

        try {
            discardPile.takeCard(Integer.MAX_VALUE);
            Assert.fail("taking from too large an index should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("That index is larger than the deck", e.getMessage());
        }
    }

    @Test
    public void testViewingEmptyPile() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> viewedPile = discardPile.viewCards();
        Assert.assertEquals(0, viewedPile.size());
    }

    @Test
    public void testViewedPileEqual() {
        DiscardPile discardPile = new DiscardPile();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {
            Card card = EasyMock.mock(Card.class);
            cards.add(card);
        }

        for (int i = 0; i < 25; i++) {
            EasyMock.replay(cards.get(i));

            discardPile.discardCard(cards.get(i));
        }
        List<Card> viewedPile = discardPile.viewCards();

        Assert.assertEquals(cards.size(), viewedPile.size());
        for (int i = 0; i < viewedPile.size(); i++) {
            Assert.assertEquals(cards.get(i), viewedPile.get(i));
            EasyMock.verify(cards.get(i));
        }
    }
}
