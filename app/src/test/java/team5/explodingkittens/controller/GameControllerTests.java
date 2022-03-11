package team5.explodingkittens.controller;

import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.notification.Notification;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Deck;
import team5.explodingkittens.model.DiscardPile;
import team5.explodingkittens.model.TurnState;
import team5.explodingkittens.view.AbstractUserView;
import team5.explodingkittens.view.NullUserView;
import team5.explodingkittens.view.UserViewFactory;

/**
 * A testing script created to test {@link GameController}.
 *
 * @author Maura Coriale, Andrew Orians, Duncan McKee
 */
public class GameControllerTests {

    private static class TestObserver implements Observer {
        public ArrayList<Notification> notifications = new ArrayList<Notification>();

        @Override
        public void update(Notification notification) {
            this.notifications.add(notification);
        }
    }

    @Test
    public void testStartGameNegativePlayers() {
        GameController gameController = new GameController();
        int numPlayers = -1;
        try {
            gameController.startGame(numPlayers, new UserViewFactory(false));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Must have more than two players to start game", e.getMessage());
        }
    }

    @Test
    public void testStartGameTooManyPlayers() {
        GameController gameController = new GameController();
        int numPlayers = 11;
        try {
            gameController.startGame(numPlayers, new UserViewFactory(false));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Must have less than ten players to start game", e.getMessage());
        }
    }

    @Test
    public void testAddCard() {
        Deck deck = EasyMock.mock(Deck.class);
        Card card = EasyMock.mock(Card.class);
        GameController controller = new GameController(deck);

        deck.insertCard(card, 3);

        EasyMock.replay(deck);
        EasyMock.replay(card);

        controller.addCard(card, 3);

        EasyMock.verify(deck);
        EasyMock.verify(card);
    }

    @Test
    public void testCloseGame() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        userMock.closeGame();

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.closeGame();
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testNotifyUserOfTurn() {
    }

    @Test
    public void testRemoveUser() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock);

        TestObserver testObserver = new TestObserver();
        gameController.registerObserver(testObserver);
        stateMock.invalidatePlayer(0);
        EasyMock.expect(stateMock.isGameOver()).andReturn(true);
        EasyMock.expect(stateMock.findLowestValid()).andReturn(1);
        userMock.winGame(1);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.removeUser(0);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testDrawNotTurn() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        GameController gameController = new GameController(stateMock, pileMock);
        TestObserver testObserver = new TestObserver();
        gameController.registerObserver(testObserver);
        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(1);
        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        gameController.drawCard(0);
        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
    }

    @Test
    public void testDrawNormalFromTop() {
        TurnState stateMock = EasyMock.partialMockBuilder(TurnState.class)
                .addMockedMethod("getTurnPlayerId").addMockedMethod("drawCard").createMock();
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        Deck deckMock = EasyMock.mock(Deck.class);
        Card cardMock = EasyMock.mock(Card.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        stateMock.drawType = TurnState.DrawType.DRAW_FROM_TOP;
        GameController gameController = new GameController(stateMock, pileMock, deckMock);
        TestObserver testObserver = new TestObserver();
        gameController.registerObserver(testObserver);

        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        EasyMock.expect(deckMock.draw()).andReturn(cardMock);
        EasyMock.expect(cardMock.checkForExplodingKitten()).andReturn(false);
        stateMock.drawCard();
        userMock.drawCard(0, cardMock);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(deckMock);
        EasyMock.replay(cardMock);
        EasyMock.replay(userMock);

        gameController.drawCard(0);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(deckMock);
        EasyMock.verify(cardMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testDrawNormalFromBottom() {
        TurnState stateMock = EasyMock.partialMockBuilder(TurnState.class)
                .addMockedMethod("getTurnPlayerId").addMockedMethod("drawCard").createMock();
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        Deck deckMock = EasyMock.mock(Deck.class);
        Card cardMock = EasyMock.mock(Card.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        stateMock.drawType = TurnState.DrawType.DRAW_FROM_BOTTOM;
        GameController gameController = new GameController(stateMock, pileMock, deckMock);
        TestObserver testObserver = new TestObserver();
        gameController.registerObserver(testObserver);

        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        EasyMock.expect(deckMock.drawAtBottom()).andReturn(cardMock);
        EasyMock.expect(cardMock.checkForExplodingKitten()).andReturn(false);
        stateMock.drawCard();
        userMock.drawCard(0, cardMock);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(deckMock);
        EasyMock.replay(cardMock);
        EasyMock.replay(userMock);

        gameController.drawCard(0);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(deckMock);
        EasyMock.verify(cardMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testDrawExplodingKittenFromTop() {
        TurnState stateMock = EasyMock.partialMockBuilder(TurnState.class)
                .addMockedMethod("getTurnPlayerId").addMockedMethod("drawCard").createMock();
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        Deck deckMock = EasyMock.mock(Deck.class);
        Card cardMock = EasyMock.mock(Card.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        stateMock.drawType = TurnState.DrawType.DRAW_FROM_TOP;
        GameController gameController = new GameController(stateMock, pileMock, deckMock);
        TestObserver testObserver = new TestObserver();
        gameController.registerObserver(testObserver);

        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        EasyMock.expect(deckMock.draw()).andReturn(cardMock);
        EasyMock.expect(cardMock.checkForExplodingKitten()).andReturn(true);
        stateMock.drawCard();
        userMock.tryExplode(0, cardMock);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(deckMock);
        EasyMock.replay(cardMock);
        EasyMock.replay(userMock);

        gameController.drawCard(0);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(deckMock);
        EasyMock.verify(cardMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testDrawFromBottom() {
        TurnState state = new TurnState(2);
        Assert.assertEquals(TurnState.DrawType.DRAW_FROM_TOP, state.drawType);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController controller = new GameController(state, discardPile);
        EasyMock.replay(discardPile);
        controller.drawFromTheBottom();
        Assert.assertEquals(TurnState.DrawType.DRAW_FROM_BOTTOM, state.drawType);
        EasyMock.verify(discardPile);
    }

    @Test
    public void testReplaceCard() {
    }

    @Test
    public void testDiscardCard() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        Card card = EasyMock.mock(Card.class);
        TestObserver testObserver = new TestObserver();
        GameController controller = new GameController(state, discardPile);

        controller.registerObserver(testObserver);
        discardPile.discardCard(card);
        EasyMock.expect(state.getTurnPlayerId()).andReturn(0);
        userMock.discardCard(0, card);

        EasyMock.replay(discardPile);
        EasyMock.replay(card);
        EasyMock.replay(state);
        EasyMock.replay(userMock);

        controller.discardCard(card);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(discardPile);
        EasyMock.verify(card);
        EasyMock.verify(state);
        EasyMock.verify(userMock);
    }

    @Test
    public void testViewDiscard() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        List<Card> cards = new ArrayList<Card>();
        GameController controller = new GameController(state, discardPile);
        EasyMock.expect(discardPile.viewCards()).andReturn(cards);

        EasyMock.replay(discardPile);
        EasyMock.replay(state);

        Assert.assertEquals(cards, controller.viewDiscard());

        EasyMock.verify(discardPile);
        EasyMock.verify(state);
    }

    @Test
    public void testViewDiscardNonempty() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        Card card = EasyMock.mock(Card.class);
        List<Card> cards = new ArrayList<Card>();
        cards.add(card);
        GameController controller = new GameController(state, discardPile);
        EasyMock.expect(discardPile.viewCards()).andReturn(cards);

        EasyMock.replay(discardPile);
        EasyMock.replay(card);
        EasyMock.replay(state);

        Assert.assertEquals(cards, controller.viewDiscard());

        EasyMock.verify(discardPile);
        EasyMock.verify(card);
        EasyMock.verify(state);
    }

    @Test
    public void testTakeFromDiscard() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        Card card = EasyMock.mock(Card.class);
        GameController controller = new GameController(state, discardPile);
        EasyMock.expect(discardPile.takeCard(5)).andReturn(card);

        EasyMock.replay(discardPile);
        EasyMock.replay(card);
        EasyMock.replay(state);

        Assert.assertEquals(card, controller.takeFromDiscard(5));

        EasyMock.verify(discardPile);
        EasyMock.verify(card);
        EasyMock.verify(state);
    }

    @Test
    public void testShuffle() {
        Deck deck = EasyMock.mock(Deck.class);
        GameController controller = new GameController(deck);

        deck.shuffle();

        EasyMock.replay(deck);

        controller.shuffleDeck();

        EasyMock.verify(deck);
    }

    @Test
    public void testSkipAction() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController controller = new GameController(state, discardPile);

        state.skipAction();

        EasyMock.replay(discardPile);
        EasyMock.replay(state);

        controller.skipAction();

        EasyMock.verify(discardPile);
        EasyMock.verify(state);
    }

    @Test
    public void testAttackAction() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController controller = new GameController(state, discardPile);

        state.attackAction();

        EasyMock.replay(discardPile);
        EasyMock.replay(state);

        controller.attackAction();

        EasyMock.verify(discardPile);
        EasyMock.verify(state);
    }

    @Test
    public void testSetName() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        userMock.setName(0, "testname");

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.setName(0, "testname");
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testStartFavor() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        userMock.favorPickPlayer(0);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.startFavor();
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testFavorSelect() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        userMock.favorSelectCard(0, 1);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.favorSelect(0, 1);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testFavorGive() {
        Deck deckMock = EasyMock.mock(Deck.class);
        Card cardMock = EasyMock.mock(Card.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        userMock.giveCard(0, 1, cardMock);

        EasyMock.replay(deckMock);
        EasyMock.replay(cardMock);
        EasyMock.replay(userMock);

        gameController.giveCard(0, 1, cardMock);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(cardMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testStartCatCard() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        userMock.catStealCard(0);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.startCatCard();
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testStealCardFrom() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        userMock.stealCard(0, 1);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.stealCardFrom(0, 1);
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testAddCardTop() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = new Card(CardType.TACOCAT);

        gameController.registerObserver(testObserver);
        deckMock.insertCard(card, -1);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        deckMock.insertCard(card, 0);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.addCard(card, -1);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testAddCardBottom() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = new Card(CardType.TACOCAT);

        gameController.registerObserver(testObserver);
        deckMock.insertCard(card, 1000);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        EasyMock.expect(deckMock.getSize()).andReturn(50);
        deckMock.insertCard(card, 50);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.addCard(card, 1000);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testAddCardShallowBottom() {
        Deck deckMock = EasyMock.mock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = new Card(CardType.TACOCAT);

        gameController.registerObserver(testObserver);
        deckMock.insertCard(card, 0);
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
        EasyMock.expect(deckMock.getSize()).andReturn(0);
        deckMock.insertCard(card, 0);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);

        gameController.addCard(card, 0);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
    }


    @Test
    public void testAcknowledgePlayCard() {
        Deck deckMock = EasyMock.mock(Deck.class);
        TurnState turnMock = EasyMock.createMock(TurnState.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        DiscardPile discardMock = EasyMock.createMock(DiscardPile.class);
        GameController gameController = new GameController(turnMock, discardMock, deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = EasyMock.createMock(Card.class);


        gameController.registerObserver(testObserver);
        EasyMock.expect(turnMock.getTurnPlayerId()).andReturn(0);
        discardMock.discardCard(card);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);
        card.playCard(gameController);


        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);
        EasyMock.replay(turnMock);
        EasyMock.replay(discardMock);
        EasyMock.replay(card);

        gameController.playCard(0, card);
        gameController.acknowledgePlayCard();
        gameController.acknowledgePlayCard();

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
        EasyMock.verify(turnMock);
        EasyMock.verify(discardMock);
        EasyMock.verify(card);
    }

    @Test
    public void testAcknowledgePlayCardNotAcknowledged() {
        Deck deckMock = EasyMock.mock(Deck.class);
        TurnState turnMock = EasyMock.createMock(TurnState.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        DiscardPile discardMock = EasyMock.createMock(DiscardPile.class);
        GameController gameController = new GameController(turnMock, discardMock, deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = EasyMock.createMock(Card.class);


        gameController.registerObserver(testObserver);
        EasyMock.expect(turnMock.getTurnPlayerId()).andReturn(0);
        discardMock.discardCard(card);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);


        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);
        EasyMock.replay(turnMock);
        EasyMock.replay(discardMock);
        EasyMock.replay(card);

        gameController.playCard(0, card);
        gameController.acknowledgePlayCard();

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
        EasyMock.verify(turnMock);
        EasyMock.verify(discardMock);
        EasyMock.verify(card);
    }

    @Test
    public void testAcknowledgePlayCardWithNope() {
        Deck deckMock = EasyMock.mock(Deck.class);
        TurnState turnMock = EasyMock.createMock(TurnState.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        DiscardPile discardMock = EasyMock.createMock(DiscardPile.class);
        GameController gameController = new GameController(turnMock, discardMock, deckMock);
        TestObserver testObserver = new TestObserver();
        Card card = EasyMock.createMock(Card.class);
        Card nopeCard = new Card(CardType.NOPE);

        gameController.registerObserver(testObserver);
        EasyMock.expect(turnMock.getTurnPlayerId()).andReturn(0);
        discardMock.discardCard(card);
        discardMock.discardCard(nopeCard);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);
        EasyMock.expect(turnMock.getValidCount()).andReturn(3);
        userMock.playCard(0, card);
        userMock.closeNope();
        userMock.playCard(1, nopeCard);

        EasyMock.replay(deckMock);
        EasyMock.replay(userMock);
        EasyMock.replay(turnMock);
        EasyMock.replay(discardMock);
        EasyMock.replay(card);

        gameController.playCard(0, card);
        gameController.playCard(1, nopeCard);
        gameController.acknowledgePlayCard();
        gameController.acknowledgePlayCard();
        gameController.acknowledgePlayCard();
        testObserver.notifications.get(0).applyNotification(userMock);
        testObserver.notifications.get(1).applyNotification(userMock);
        testObserver.notifications.get(2).applyNotification(userMock);

        EasyMock.verify(deckMock);
        EasyMock.verify(userMock);
        EasyMock.verify(turnMock);
        EasyMock.verify(discardMock);
        EasyMock.verify(card);
    }

    @Test
    public void testStartGame() {
        for (int numPlayers = 2; numPlayers < 11; numPlayers++) {
            UserViewFactory factoryMock = EasyMock.createMock(UserViewFactory.class);
            boolean[] hasBeenSet = new boolean[numPlayers];
            boolean[] hasBeenDealt = new boolean[numPlayers];

            AbstractUserView[] userViews = new AbstractUserView[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                int index = i;
                userViews[i] = new NullUserView() {
                    @Override
                    public void setUserController(UserController userController) {
                        super.setUserController(userController);
                        hasBeenSet[index] = true;
                    }

                    @Override
                    public void drawCard(int playerId, Card card) {
                        super.drawCard(playerId, card);
                        hasBeenDealt[index] = true;
                    }

                    @Override
                    public void discardCard(int playerId, Card card) {
                        super.discardCard(playerId, card);

                    }
                };
            }

            for (int i = 0; i < numPlayers; i++) {
                EasyMock.expect(factoryMock.createUserView(numPlayers, i)).andReturn(userViews[i]);
            }


            EasyMock.replay(factoryMock);

            GameController gameController = new GameController();
            gameController.startGame(numPlayers, factoryMock);

            EasyMock.verify(factoryMock);
            for (int i = 0; i < numPlayers; i++) {
                Assert.assertTrue(hasBeenSet[i]);
                Assert.assertTrue(hasBeenDealt[i]);
            }
        }
    }

    @Test
    public void testSeeTheFuture() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        userMock.seeTheFuture(0);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.seeTheFuture();
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testAlterTheFuture() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock);
        TestObserver testObserver = new TestObserver();

        gameController.registerObserver(testObserver);
        EasyMock.expect(stateMock.getTurnPlayerId()).andReturn(0);
        userMock.alterTheFuture(0);

        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.alterTheFuture();
        testObserver.notifications.get(0).applyNotification(userMock);

        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testRearrangeTopThree() {
        TurnState stateMock = EasyMock.mock(TurnState.class);
        DiscardPile pileMock = EasyMock.mock(DiscardPile.class);
        Deck deckMock = EasyMock.createMock(Deck.class);
        UserController userMock = EasyMock.createMock(UserController.class);
        GameController gameController = new GameController(stateMock, pileMock, deckMock);
        TestObserver testObserver = new TestObserver();
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.TACOCAT);
        Card card2 = new Card(CardType.TACOCAT);
        Card card3 = new Card(CardType.TACOCAT);
        list.add(card1);
        list.add(card2);
        list.add(card3);

        gameController.registerObserver(testObserver);
        EasyMock.expect(deckMock.draw()).andReturn(card1);
        EasyMock.expect(deckMock.draw()).andReturn(card1);
        EasyMock.expect(deckMock.draw()).andReturn(card1);
        deckMock.insertCard(card1, 0);
        deckMock.insertCard(card2, 1);
        deckMock.insertCard(card3, 2);

        EasyMock.replay(deckMock);
        EasyMock.replay(stateMock);
        EasyMock.replay(pileMock);
        EasyMock.replay(userMock);

        gameController.rearrangeTopThree(list);

        EasyMock.verify(deckMock);
        EasyMock.verify(stateMock);
        EasyMock.verify(pileMock);
        EasyMock.verify(userMock);
    }
}
