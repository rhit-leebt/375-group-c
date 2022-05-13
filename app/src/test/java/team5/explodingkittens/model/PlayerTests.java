package team5.explodingkittens.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * A testing script created to test {@link Player}.
 *
 * @author Andrew Orians, Duncan McKee
 */
public class PlayerTests {
    @Test
    public void testGetCardNoCards() {
        Player p = new Player();
        try {
            p.getCard(0);
            Assert.fail("getCard did not throw IllegalStateException with empty hand");
        } catch (IllegalStateException e) {
            Assert.assertEquals("Cannot get from an empty hand", e.getMessage());
        }
    }

    @Test
    public void testGetCardNegativeIndex() {
        Player p = new Player();
        Card card = EasyMock.mock(Card.class);
        p.addCard(card);
        try {
            p.getCard(-1);
            Assert.fail("getCard did not throw IllegalArgumentException with negative index");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Index is out of range of the size of the hand", e.getMessage());
        }
    }

    @Test
    public void testAddGetOneCard() {
        Player p = new Player();
        Card card = EasyMock.mock(Card.class);
        p.addCard(card);
        Assert.assertEquals(card, p.getCard(0));
    }

    @Test
    public void testAddGetManyCards() {
        Player p = new Player();
        Card[] cards = new Card[7];
        for (int i = 0; i < 7; i++) {
            cards[i] = EasyMock.mock(Card.class);
            p.addCard(cards[i]);
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(cards[i], p.getCard(i));
        }
    }

    @Test
    public void testGetIndexTooLarge() {
        Player p = new Player();
        Card[] cards = new Card[7];
        for (int i = 0; i < 7; i++) {
            cards[i] = EasyMock.mock(Card.class);
            p.addCard(cards[i]);
        }
        try {
            p.getCard(7);
            Assert.fail("getCard did not throw IllegalArgumentException with index too large");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Index is out of range of the size of the hand", e.getMessage());
        }
    }

    @Test
    public void testPlayerConstruction() {
        Player currentPlayer = new Player();
        Assert.assertEquals(0, currentPlayer.size());
    }

    @Test
    public void testPlayerAddingAndRemovingCards() {
        Player player = new Player();
        Assert.assertEquals(0, player.size());
        Card card1 = EasyMock.mock(Card.class);
        player.addCard(card1);
        Assert.assertEquals(1, player.size());
        player.removeCard(card1);
        Assert.assertEquals(0, player.size());
    }

    @Test
    public void testPlayerGettingCardsNoCardsInHand() {
        Player player = new Player();
        try {
            player.getCard(0);
            Assert.fail();
        } catch (IllegalStateException ex) {
            Assert.assertEquals("Cannot get from an empty hand", ex.getMessage());
            assert (true);
        }
    }

    @Test
    public void testPlayerOneCardInHand() {
        Card card = EasyMock.mock(Card.class);
        Player player = new Player();
        player.addCard(card);
        Assert.assertEquals(1, player.size());
        Assert.assertEquals(card, player.getCard(0));
    }

    @Test
    public void testPlayerGetCardIndexOutOfBounds() {
        Player player = new Player();
        player.addCard(EasyMock.mock(Card.class));
        try {
            player.getCard(-1);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Index is out of range of the size of the hand", ex.getMessage());
        }

        player.addCard(EasyMock.mock(Card.class));
        player.addCard(EasyMock.mock(Card.class));
        Assert.assertEquals(3, player.size());
        Assert.assertNotNull(player.getCard(0));
        Assert.assertNotNull(player.getCard(1));
        Assert.assertNotNull(player.getCard(2));

        try {
            player.getCard(3);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Index is out of range of the size of the hand", ex.getMessage());
        }
    }

    @Test
    public void testDiscardAll() {
        Player player = new Player();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        for (int i = 0; i < 5; i++) {
            EasyMock.replay(cards.get(i));
            player.addCard(cards.get(i));
        }
        Assert.assertEquals(5, player.size());
        player.removeAllCards();
        Assert.assertEquals(0, player.size());
        for (int i = 0; i < 5; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testDiscardAllEmptyHand() {
        Player player = new Player();
        Assert.assertEquals(0, player.size());
        player.removeAllCards();
        Assert.assertEquals(0, player.size());
    }

    @Test
    public void testHasNoDefuse() {
        Player player = new Player();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        for (int i = 0; i < 5; i++) {
            EasyMock.expect(cards.get(i).checkForCardType(CardType.DEFUSE)).andReturn(false);
            EasyMock.replay(cards.get(i));
            player.addCard(cards.get(i));
        }
        Assert.assertFalse(player.hasCardType(CardType.DEFUSE));
        for (int i = 0; i < 5; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testHasDefuse() {
        Player player = new Player();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        for (int i = 0; i < 4; i++) {
            EasyMock.expect(cards.get(i).checkForCardType(CardType.DEFUSE)).andReturn(false);
            EasyMock.replay(cards.get(i));
            player.addCard(cards.get(i));
        }
        EasyMock.expect(cards.get(4).checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.replay(cards.get(4));
        player.addCard(cards.get(4));
        Assert.assertTrue(player.hasCardType(CardType.DEFUSE));
        for (int i = 0; i < 5; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testHasDefuseEmptyHand() {
        Player player = new Player();
        Assert.assertFalse(player.hasCardType(CardType.DEFUSE));
    }

    @Test
    public void testGetDefuseEmptyHand() {
        Player player = new Player();
        try {
            player.getCardType(CardType.DEFUSE);
            Assert.fail("Trying to get a defuse card from an"
                    + " empty hand should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This "
                    + "player does not have that card");
        }
    }

    @Test
    public void testGetDefuseDoesntHave() {
        Player player = new Player();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        for (int i = 0; i < 5; i++) {
            EasyMock.expect(cards.get(i).checkForCardType(CardType.DEFUSE)).andReturn(false);
            EasyMock.replay(cards.get(i));
            player.addCard(cards.get(i));
        }
        try {
            player.getCardType(CardType.DEFUSE);
            Assert.fail("Trying to get a defuse card from an "
                    + "empty hand should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This player does not have that card");
        } finally {
            for (int i = 0; i < 5; i++) {
                EasyMock.verify(cards.get(i));
            }
        }
    }

    @Test
    public void testGetDefuseAndHas() {
        Player player = new Player();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        for (int i = 0; i < 4; i++) {
            EasyMock.expect(cards.get(i).checkForCardType(CardType.DEFUSE)).andReturn(false);
            EasyMock.replay(cards.get(i));
            player.addCard(cards.get(i));
        }
        EasyMock.expect(cards.get(4).checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.expect(cards.get(4).checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.replay(cards.get(4));
        player.addCard(cards.get(4));
        Assert.assertTrue(player.getCardType(CardType.DEFUSE).checkForCardType(CardType.DEFUSE));
        for (int i = 0; i < 5; i++) {
            EasyMock.verify(cards.get(i));
        }
    }

    @Test
    public void testHasNoNope() {
        Player player = new Player();
        for (int i = 0; i < 5; i++) {
            player.addCard(new Card(CardType.TACOCAT));
        }
        Assert.assertFalse(player.hasCardType(CardType.NOPE));
    }

    @Test
    public void testHasNope() {
        Player player = new Player();
        for (int i = 0; i < 4; i++) {
            player.addCard(new Card(CardType.TACOCAT));
        }
        player.addCard(new Card(CardType.NOPE));
        Assert.assertTrue(player.hasCardType(CardType.NOPE));
    }

    @Test
    public void testHasNopeEmptyHand() {
        Player player = new Player();
        Assert.assertFalse(player.hasCardType(CardType.NOPE));
    }

    @Test
    public void testGetNopeEmptyHand() {
        Player player = new Player();
        try {
            player.getCardType(CardType.NOPE);
            Assert.fail("Trying to get a nope card from an"
                    + " empty hand should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This "
                    + "player does not have that card");
        }
    }

    @Test
    public void testGetNopeDoesntHave() {
        Player player = new Player();
        for (int i = 0; i < 5; i++) {
            player.addCard(new Card(CardType.TACOCAT));
        }
        try {
            player.getCardType(CardType.NOPE);
            Assert.fail("Trying to get a nope card from an "
                    + "empty hand should throw an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertEquals(e.getMessage(), "This player does not have that card");
        }
    }

    @Test
    public void testGetNopeAndHas() {
        Player player = new Player();
        for (int i = 0; i < 4; i++) {
            player.addCard(new Card(CardType.TACOCAT));
        }
        Card nopeCard = new Card(CardType.NOPE);
        player.addCard(nopeCard);
        Assert.assertEquals(nopeCard, player.getCardType(CardType.NOPE));
    }

    @Test
    public void testHasMatchingPairEmptyHand() {
        Player player = new Player();
        Card card = new Card(CardType.TACOCAT);
        Assert.assertFalse(player.hasMatchingPair(card));
    }

    @Test
    public void testHasMatchingPairHasAndNot() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Assert.assertFalse(player.hasMatchingPair(handCard1));
    }

    @Test
    public void testHadMatchingPairHasAndHas() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.FERAL_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Assert.assertTrue(player.hasMatchingPair(handCard1));
    }

    @Test
    public void testFindMatchingTypesEmpty() {
        Player player = new Player();
        Set<CardType> matches = player.findMatchingTypes(CardType.TACOCAT);
        Assert.assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindMatchingTypesHasAndNot() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Set<CardType> matches = player.findMatchingTypes(CardType.TACOCAT);
        Assert.assertFalse(matches.isEmpty());
        Assert.assertTrue(matches.contains(CardType.TACOCAT));
    }

    @Test
    public void testFindMatchingTypesHasAndHas() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.FERAL_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Set<CardType> matches = player.findMatchingTypes(CardType.TACOCAT);
        Assert.assertFalse(matches.isEmpty());
        Assert.assertTrue(matches.contains(CardType.TACOCAT));
        Assert.assertTrue(matches.contains(CardType.FERAL_CAT));
    }

    @Test
    public void testFindMatchingTypesAll() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        Card handCard3 = new Card(CardType.CATTERMELON);
        Card handCard4 = new Card(CardType.HAIRY_POTATO_CAT);
        Card handCard5 = new Card(CardType.RAINBOW_RALPHING_CAT);
        Card handCard6 = new Card(CardType.FERAL_CAT);
        Card handCard7 = new Card(CardType.DEFUSE);
        player.addCard(handCard1);
        player.addCard(handCard2);
        player.addCard(handCard3);
        player.addCard(handCard4);
        player.addCard(handCard5);
        player.addCard(handCard6);
        player.addCard(handCard7);
        Set<CardType> matches = player.findMatchingTypes(CardType.FERAL_CAT);
        Assert.assertFalse(matches.isEmpty());
        Assert.assertTrue(matches.contains(CardType.TACOCAT));
        Assert.assertTrue(matches.contains(CardType.BEARD_CAT));
        Assert.assertTrue(matches.contains(CardType.CATTERMELON));
        Assert.assertTrue(matches.contains(CardType.HAIRY_POTATO_CAT));
        Assert.assertTrue(matches.contains(CardType.RAINBOW_RALPHING_CAT));
        Assert.assertTrue(matches.contains(CardType.FERAL_CAT));
    }

    @Test
    public void testFindCardOfTypeEmpty() {
        Player player = new Player();
        Card card = player.findCardOfTypeExcluding(CardType.TACOCAT, null);
        Assert.assertNull(card);
    }

    @Test
    public void testFindCardOfTypeMatchAndNot() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Card card = player.findCardOfTypeExcluding(CardType.BEARD_CAT, handCard1);
        Assert.assertEquals(handCard2, card);
    }

    @Test
    public void testFindCardOfTypeDuplicate() {
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.TACOCAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        Card card = player.findCardOfTypeExcluding(CardType.TACOCAT, handCard1);
        Assert.assertEquals(handCard2, card);
    }

    @Test
    public void testGetRandomCard() {
        Random randomMock = EasyMock.createMock(Random.class);
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);

        EasyMock.expect(randomMock.nextInt(2)).andReturn(0);
        EasyMock.replay(randomMock);

        Card card = player.getRandomCard(randomMock);
        EasyMock.verify(randomMock);
        Assert.assertEquals(handCard1, card);
    }
}
