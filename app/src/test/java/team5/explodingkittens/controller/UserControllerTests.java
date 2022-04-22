package team5.explodingkittens.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.notification.CloseGameNotification;
import team5.explodingkittens.controller.notification.DrawNotification;
import team5.explodingkittens.controller.notification.FavorPickPlayerNotification;
import team5.explodingkittens.controller.notification.FavorSelectCardNotification;
import team5.explodingkittens.controller.notification.GiveCardNotification;
import team5.explodingkittens.controller.notification.PlayNotification;
import team5.explodingkittens.controller.notification.SetNameNotification;
import team5.explodingkittens.controller.notification.TryExplodeNotification;
import team5.explodingkittens.controller.notification.WinGameNotification;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Deck;
import team5.explodingkittens.model.DiscardPile;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.model.TurnState;
import team5.explodingkittens.view.AbstractUserView;
import team5.explodingkittens.view.NullUserView;

/**
 * A testing script created to test {@link UserController}.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class UserControllerTests {

    private GameController controllerMock;
    private AbstractUserView viewMock;
    private Player playerMock;
    private UserController userController;

    public void setupMocks(boolean mockMethod, String mockedMethod, boolean realPlayer) {
        if (mockMethod) {
            controllerMock = EasyMock.partialMockBuilder(GameController.class)
                    .addMockedMethod(mockedMethod).createMock();
        } else {
            controllerMock = EasyMock.partialMockBuilder(GameController.class).createMock();
        }
        controllerMock.observers = new ArrayList<>();
        viewMock = EasyMock.mock(AbstractUserView.class);
        if (realPlayer) {
            playerMock = new Player();
        } else {
            playerMock = EasyMock.mock(Player.class);
        }
        userController = new UserController(controllerMock, viewMock, playerMock, 0);
    }

    public void replayMocks(boolean realPlayer) {
        EasyMock.replay(controllerMock);
        EasyMock.replay(viewMock);
        if (!realPlayer)
            EasyMock.replay(playerMock);
    }

    public void verifyMocks(boolean realPlayer) {
        EasyMock.verify(controllerMock);
        EasyMock.verify(viewMock);
        if (!realPlayer)
            EasyMock.verify(playerMock);
    }

    @Test
    public void testDrawCards() {
        Card card = new Card(CardType.TACOCAT);
        GameController gameMock = EasyMock.createMock(GameController.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = EasyMock.mock(Player.class);
        UserController user = new UserController(gameMock, viewMock, player, 0);

        player.addCard(card);
        viewMock.drawCard(0, card);

        EasyMock.replay(viewMock);
        EasyMock.replay(player);

        user.drawCard(card);

        EasyMock.verify(viewMock);
        EasyMock.verify(player);
    }

    @Test
    public void testDrawCardPlayerId() {
        Card card = new Card(CardType.TACOCAT);
        GameController gameMock = EasyMock.createMock(GameController.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = EasyMock.mock(Player.class);
        UserController user = new UserController(gameMock, viewMock, player, 0);

        player.addCard(card);
        viewMock.drawCard(0, card);

        EasyMock.replay(viewMock);
        EasyMock.replay(player);

        user.drawCard(0, card);

        EasyMock.verify(viewMock);
        EasyMock.verify(player);
    }

    @Test
    public void testPlayCards() {
        Card card = new Card(CardType.TACOCAT);
        GameController gameMock = EasyMock.createMock(GameController.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = EasyMock.mock(Player.class);
        UserController user = new UserController(gameMock, viewMock, player, 0);
        player.removeCard(card);

        viewMock.discardCard(0, card);

        EasyMock.replay(viewMock);
        EasyMock.replay(player);

        user.playCard(0, card);

        EasyMock.verify(viewMock);
        EasyMock.verify(player);
    }

    @Test
    public void testTryPlayCard() {
        int[] playerIds = new int[1];
        Card[] cards = new Card[1];
        GameController gameMock = new GameController() {
            @Override
            public void playCard(int playerId, Card card) {
                playerIds[0] = playerId;
                cards[0] = card;
            }
        };
        AbstractUserView view = new NullUserView();
        Player player = new Player();
        Card handCard = new Card(CardType.SKIP);
        player.addCard(handCard);

        UserController userController = new UserController(gameMock, view, player, 1);

        userController.tryPlayCard(handCard);

        Assert.assertEquals(1, playerIds[0]);
        Assert.assertEquals(handCard, cards[0]);
    }

    @Test
    public void testTryPlayCatCardsDuplicates() {
        int[] playerIds = new int[1];
        Card[] cards = new Card[2];
        GameController gameMock = new GameController() {
            @Override
            public void playCard(int playerId, Card card) {
                playerIds[0] = playerId;
                cards[0] = card;
            }

            @Override
            public void discardCard(Card card) {
                cards[1] = card;
            }
        };
        AbstractUserView view = EasyMock.createMock(AbstractUserView.class);
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.TACOCAT);
        player.addCard(handCard1);
        player.addCard(handCard2);
        EasyMock.expect(view.showPickPair(player.findMatchingTypes(
                CardType.TACOCAT))).andReturn(CardType.TACOCAT);

        EasyMock.replay(view);

        UserController userController = new UserController(gameMock, view, player, 1);

        userController.tryPlayCard(handCard1);

        EasyMock.verify(view);
        Assert.assertEquals(1, playerIds[0]);
        Assert.assertEquals(handCard1, cards[0]);
        Assert.assertEquals(handCard2, cards[1]);
    }

    @Test
    public void testTryPlayCatCardsDifferent() {
        int[] playerIds = new int[1];
        Card[] cards = new Card[1];
        boolean[] dialogShown = new boolean[1];
        GameController gameMock = new GameController() {
            @Override
            public void playCard(int playerId, Card card) {
                playerIds[0] = playerId;
                cards[0] = card;
            }
        };
        AbstractUserView view = new NullUserView() {
            @Override
            public void showCantPlayCat() {
                dialogShown[0] = true;
            }
        };
        Player player = new Player();
        Card handCard1 = new Card(CardType.TACOCAT);
        Card handCard2 = new Card(CardType.BEARD_CAT);
        player.addCard(handCard1);
        player.addCard(handCard2);

        UserController userController = new UserController(gameMock, view, player, 1);

        userController.tryPlayCard(handCard1);

        Assert.assertEquals(0, playerIds[0]);
        Assert.assertNull(cards[0]);
        Assert.assertTrue(dialogShown[0]);
    }

    @Test
    public void testNotifyUser() {
        GameController subject = new GameController() {
        };
        Card card1 = new Card(CardType.TACOCAT);
        Card card2 = new Card(CardType.BEARD_CAT);
        GameController gameMock = EasyMock.createMock(GameController.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = new Player();
        UserController user = new UserController(gameMock, viewMock, player, 0);
        subject.registerObserver(user);

        viewMock.drawCard(5, card1);
        viewMock.discardCard(4, card2);

        EasyMock.replay(viewMock);

        subject.notifyObservers(new DrawNotification(5, card1));
        subject.notifyObservers(new PlayNotification(4, card2));

        EasyMock.verify(viewMock);
    }

    @Test
    public void testNonExplodeWithDefuse() {
        Deck deck = EasyMock.mock(Deck.class);
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController gameController = new GameController(state, discardPile, deck);
        Card defuseCard = EasyMock.mock(Card.class);
        Card explodingKitten = EasyMock.mock(Card.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = new Player();
        player.addCard(defuseCard);

        UserController user = new UserController(gameController, viewMock, player, 0);

        EasyMock.expect(defuseCard.checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.expect(viewMock.showExplodeDialog()).andReturn(true);
        EasyMock.expect(viewMock.showPutExplodingKittenBackDialog()).andReturn(3);
        deck.insertCard(explodingKitten, 3);
        EasyMock.expect(defuseCard.checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.expect(state.getTurnPlayerId()).andReturn(0);
        discardPile.discardCard(defuseCard);
        viewMock.discardCard(0, defuseCard);

        EasyMock.replay(viewMock);
        EasyMock.replay(defuseCard);
        EasyMock.replay(state);
        EasyMock.replay(discardPile);
        EasyMock.replay(deck);
        EasyMock.replay(explodingKitten);

        user.update(new TryExplodeNotification(0, explodingKitten));

        EasyMock.verify(viewMock);
        EasyMock.verify(defuseCard);
        EasyMock.verify(state);
        EasyMock.verify(discardPile);
        EasyMock.verify(deck);
        EasyMock.verify(explodingKitten);
    }

    @Test
    public void testExplodeDenyDefuse() {
        Deck deck = EasyMock.mock(Deck.class);
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController gameController = new GameController(state, discardPile, deck);
        Card defuseCard = EasyMock.mock(Card.class);
        Card explodingKitten = EasyMock.mock(Card.class);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = new Player();
        player.addCard(defuseCard);
        UserController user = new UserController(gameController, viewMock, player, 0);

        EasyMock.expect(defuseCard.checkForCardType(CardType.DEFUSE)).andReturn(true);
        EasyMock.expect(viewMock.showExplodeDialog()).andReturn(false);
        viewMock.discardAllCards(0);
        state.invalidatePlayer(0);
        EasyMock.expect(state.isGameOver()).andReturn(true);
        EasyMock.expect(state.findLowestValid()).andReturn(1);

        EasyMock.replay(viewMock);
        EasyMock.replay(defuseCard);
        EasyMock.replay(state);
        EasyMock.replay(discardPile);
        EasyMock.replay(deck);
        EasyMock.replay(explodingKitten);

        user.update(new TryExplodeNotification(0, explodingKitten));

        EasyMock.verify(viewMock);
        EasyMock.verify(defuseCard);
        EasyMock.verify(state);
        EasyMock.verify(discardPile);
        EasyMock.verify(deck);
        EasyMock.verify(explodingKitten);
    }

    @Test
    public void testExplodeNoDefuse() {
        TurnState state = EasyMock.mock(TurnState.class);
        DiscardPile discardPile = EasyMock.mock(DiscardPile.class);
        GameController gameController = new GameController(state, discardPile);
        AbstractUserView viewMock = EasyMock.createMock(AbstractUserView.class);
        Player player = EasyMock.mock(Player.class);
        UserController user = new UserController(gameController, viewMock, player, 0);

        EasyMock.expect(player.hasCardType(CardType.DEFUSE)).andReturn(false);
        viewMock.showCantDefuseDialog();
        player.removeAllCards();
        viewMock.discardAllCards(0);
        state.invalidatePlayer(0);
        EasyMock.expect(state.isGameOver()).andReturn(true);
        EasyMock.expect(state.findLowestValid()).andReturn(1);

        EasyMock.replay(viewMock);
        EasyMock.replay(player);
        EasyMock.replay(state);
        EasyMock.replay(discardPile);

        user.update(new TryExplodeNotification(0,
                new Card(CardType.EXPLODING_KITTEN)));

        EasyMock.verify(viewMock);
        EasyMock.verify(player);
        EasyMock.verify(state);
        EasyMock.verify(discardPile);
    }

    @Test
    public void testTrySetName() {
        feature/oriansaj/m3-refactorings
        setupMocks(true, "setName", false);
        playerMock.setName("testname");
        EasyMock.expectLastCall();
        controllerMock.setName(0, "testname");
        replayMocks(false);
        UserController userController = new UserController(controllerMock, viewMock, playerMock, 0);
        userController.trySetName("testname");
        verifyMocks(false);
    }

    @Test
    public void testSetName() {
        setupMocks(true, "setName", false);
        viewMock.setName(0, "testname");
        replayMocks(false);
        userController.update(new SetNameNotification(0, "testname"));
        verifyMocks(false);
    }

    @Test
    public void testTryDrawCard() {
        setupMocks(true, "drawCard", false);
        controllerMock.drawCard(0);
        replayMocks(false);
        userController.tryDrawCard();
        verifyMocks(false);
    }

    @Test
    public void testTryCloseGame() {
        setupMocks(true, "closeGame", false);
        controllerMock.closeGame();
        replayMocks(false);
        userController.tryCloseGame();
        verifyMocks(false);
    }

    @Test
    public void testFavorGiveFrom() {
        setupMocks(true, "closeGame", false);
        Card cardMock = EasyMock.mock(Card.class);
        playerMock.removeCard(cardMock);
        viewMock.giveCard(1, 0, cardMock);
        replayMocks(false);
        EasyMock.replay(cardMock);
        userController.update(new GiveCardNotification(1, 0, cardMock));
        verifyMocks(false);
        EasyMock.verify(cardMock);
    }

    @Test
    public void testFavorGiveTo() {
        setupMocks(true, "closeGame", false);
        Card cardMock = EasyMock.mock(Card.class);
        playerMock.addCard(cardMock);
        viewMock.giveCard(0, 1, cardMock);
        replayMocks(false);
        EasyMock.replay(cardMock);
        userController.update(new GiveCardNotification(0, 1, cardMock));
        verifyMocks(false);
        EasyMock.verify(cardMock);
    }

    @Test
    public void testFavorGiveUninvolved() {
        setupMocks(true, "closeGame", false);
        Card cardMock = EasyMock.mock(Card.class);
        viewMock.giveCard(2, 1, cardMock);
        replayMocks(false);
        EasyMock.replay(cardMock);
        userController.update(new GiveCardNotification(2, 1, cardMock));
        verifyMocks(false);
        EasyMock.verify(cardMock);
    }

    @Test
    public void testCatPickPairThisPlayer() {
        setupMocks(true, "discardCard", false);
        Card card1 = new Card(CardType.TACOCAT);
        Card card2 = new Card(CardType.CATTERMELON);

        Set<CardType> types = new HashSet<>();

        EasyMock.expect(playerMock.findMatchingTypes(CardType.TACOCAT)).andReturn(types);
        EasyMock.expect(viewMock.showPickPair(types)).andReturn(CardType.TACOCAT);
        EasyMock.expect(playerMock.findCardOfTypeExcluding(
                CardType.TACOCAT, card1)).andReturn(card2);
        controllerMock.discardCard(card2);

        replayMocks(false);

        userController.catPickPair(card1);

        verifyMocks(false);
    }

    @Test
    public void testCatStealCardThisPlayer() {
        setupMocks(true, "stealCardFrom", false);
        EasyMock.expect(viewMock.showPickOtherPlayer()).andReturn(1);
        controllerMock.stealCardFrom(0, 1);
        replayMocks(false);
        userController.catStealCard(0);
        verifyMocks(false);
    }

    @Test
    public void testCatStealCardOtherPlayer() {
        setupMocks(true, "stealCardFrom", false);
        replayMocks(false);
        userController.catStealCard(1);
        verifyMocks(false);
    }

    @Test
    public void testUpdateFavorPickPlayerThisPlayer() {
        setupMocks(true, "favorSelect", false);
        EasyMock.expect(viewMock.showPickOtherPlayer()).andReturn(1);
        controllerMock.favorSelect(0, 1);
        replayMocks(false);
        userController.update(new FavorPickPlayerNotification(0));
        verifyMocks(false);
    }

    @Test
    public void testUpdateFavorPickPlayerDifferentPlayer() {
        setupMocks(true, "setName", false);
        replayMocks(false);
        userController.update(new FavorPickPlayerNotification(1));
        verifyMocks(false);
    }

    @Test
    public void testUpdateFavorSelectCardThisPlayer() {
        setupMocks(true, "giveCard", false);
        Card cardMock = EasyMock.createMock(Card.class);
        EasyMock.expect(viewMock.showFavorDialog(1, playerMock)).andReturn(cardMock);
        controllerMock.giveCard(1, 0, cardMock);
        replayMocks(false);
        userController.update(new FavorSelectCardNotification(1, 0));
        verifyMocks(false);
    }

    @Test
    public void testUpdateFavorSelectCardDifferentPlayer() {
        setupMocks(true, "setName", false);
        replayMocks(false);
        userController.update(new FavorSelectCardNotification(1, 2));
        verifyMocks(false);
    }

    @Test
    public void testUpdateCloseGame() {
        setupMocks(true, "setName", false);
        viewMock.close();
        replayMocks(false);
        userController.update(new CloseGameNotification());
        verifyMocks(false);
    }

    @Test
    public void testUpdateWinGameSamePlayer() {
        setupMocks(true, "setName", false);
        viewMock.winGame();
        replayMocks(false);
        userController.update(new WinGameNotification(0));
        verifyMocks(false);
    }

    @Test
    public void testStealCardThisPlayer() {
        setupMocks(true, "giveCard", true);
        Card card = new Card(CardType.TACOCAT);
        playerMock = new Player() {
            @Override
            public Card getRandomCard(Random random) {
                return card;
            }
        };
        controllerMock.giveCard(0, 1, card);
        replayMocks(true);
        UserController userController = new UserController(controllerMock, viewMock, playerMock, 1);
        userController.stealCard(0, 1);
        verifyMocks(true);
    }

    @Test
    public void testStealCardOtherPlayer() {
        setupMocks(true, "giveCard", true);
        replayMocks(true);
        userController.stealCard(0, 1);
        verifyMocks(true);
    }

    @Test
    public void testCloseNope() {
        setupMocks(true, "giveCard", true);
        controllerMock = EasyMock.partialMockBuilder(GameController.class).createMock();
        controllerMock.observers = new ArrayList<>();
        viewMock.closeNope();
        replayMocks(true);
        UserController userController = new UserController(controllerMock, viewMock, playerMock, 0);
        userController.closeNope();
        verifyMocks(true);
    }

    @Test
    public void testDiscardCardOtherPlayer() {
        setupMocks(false, "", true);
        Card card = new Card(CardType.TACOCAT);
        viewMock.discardCard(1, card);
        replayMocks(true);
        userController.discardCard(1, card);
        verifyMocks(true);
    }

    @Test
    public void testDiscardCardThisPlayer() {
        setupMocks(false, "", false);
        Card card = new Card(CardType.TACOCAT);

        viewMock.discardCard(0, card);
        playerMock.removeCard(card);
        replayMocks(false);
        UserController userController = new UserController(controllerMock, viewMock, playerMock, 0);
        userController.discardCard(0, card);
        verifyMocks(false);
    }

    @Test
    public void testPlayCardNopeHasNopePlay() {
        setupMocks(true, "playCard", true);
        Card card = new Card(CardType.TACOCAT);
        Card nopeCard = new Card(CardType.NOPE);
        playerMock.addCard(nopeCard);
        viewMock.discardCard(1, card);
        EasyMock.expect(viewMock.showNopePlay(1, card)).andReturn(true);
        controllerMock.playCard(0, nopeCard);
        replayMocks(true);
        userController.playCard(1, card);
        verifyMocks(true);
    }

    @Test
    public void testPlayCardNopeHasNopeNotPlay() {
        setupMocks(true, "acknowledgePlayCard", true);
        Card card = new Card(CardType.TACOCAT);
        Card nopeCard = new Card(CardType.NOPE);
        playerMock.addCard(nopeCard);
        viewMock.discardCard(1, card);
        EasyMock.expect(viewMock.showNopePlay(1, card)).andReturn(false);
        controllerMock.acknowledgePlayCard();
        replayMocks(true);
        userController.playCard(1, card);
        verifyMocks(true);
    }

    @Test
    public void testPlayCardNopeNoNope() {
        setupMocks(true, "acknowledgePlayCard", true);
        Card card = new Card(CardType.TACOCAT);
        viewMock.discardCard(1, card);
        controllerMock.acknowledgePlayCard();
        replayMocks(true);
        userController.playCard(1, card);
        verifyMocks(true);
    }

    @Test
    public void testSeeTheFutureThisPlayer() {
        Deck deck = EasyMock.createMock(Deck.class);
        setupMocks(true, "giveCard", true);
        controllerMock.deck = deck;
        Card card1 = new Card(CardType.TACOCAT);
        Card card2 = new Card(CardType.BEARD_CAT);
        Card card3 = new Card(CardType.RAINBOW_RALPHING_CAT);

        EasyMock.expect(deck.getCard(0)).andReturn(card1);
        EasyMock.expect(deck.getCard(1)).andReturn(card2);
        EasyMock.expect(deck.getCard(2)).andReturn(card3);
        viewMock.seeTheFuture(card1, card2, card3);

        EasyMock.replay(deck);
        replayMocks(true);

        userController.seeTheFuture(0);

        EasyMock.verify(deck);
        verifyMocks(true);
    }

    @Test
    public void testSeeTheFutureOtherPlayer() {
        GameController controllerMock = EasyMock.partialMockBuilder(GameController.class)
                .addMockedMethod("giveCard").createMock();
        Deck deck = EasyMock.createMock(Deck.class);
        controllerMock.deck = deck;
        controllerMock.observers = new ArrayList<>();
        AbstractUserView viewMock = EasyMock.mock(AbstractUserView.class);
        Player playerMock = new Player();

        EasyMock.replay(deck);
        EasyMock.replay(viewMock);
        EasyMock.replay(controllerMock);

        UserController userController = new UserController(controllerMock, viewMock, playerMock, 0);
        userController.seeTheFuture(1);

        EasyMock.verify(deck);
        EasyMock.verify(viewMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testAlterTheFutureThisPlayer() {
        setupMocks(true, "rearrangeTopThree", true);
        Deck deck = EasyMock.createMock(Deck.class);
        controllerMock.deck = deck;
        Card card1 = new Card(CardType.TACOCAT);
        Card card2 = new Card(CardType.BEARD_CAT);
        Card card3 = new Card(CardType.RAINBOW_RALPHING_CAT);
        ArrayList<Card> cards = new ArrayList<>();

        EasyMock.expect(deck.getCard(0)).andReturn(card1);
        EasyMock.expect(deck.getCard(1)).andReturn(card2);
        EasyMock.expect(deck.getCard(2)).andReturn(card3);
        EasyMock.expect(viewMock.alterTheFuture(card1, card2, card3)).andReturn(cards);
        controllerMock.rearrangeTopThree(cards);

        EasyMock.replay(deck);
        replayMocks(true);

        userController.alterTheFuture(0);

        EasyMock.verify(deck);
        verifyMocks(true);
    }

    @Test
    public void testAlterTheFutureOtherPlayer() {
        setupMocks(true, "giveCard", true);
        Deck deck = EasyMock.createMock(Deck.class);
        controllerMock.deck = deck;
        EasyMock.replay(deck);
        replayMocks(true);
        userController.alterTheFuture(1);
        EasyMock.verify(deck);
        verifyMocks(true);
    }
}
