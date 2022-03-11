package team5.explodingkittens.controller.notification;

import org.easymock.EasyMock;
import org.junit.Test;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;

/**
 * A testing script created to test {@link Notification} and all subclasses.
 *
 * @author Duncan McKee
 */
public class NotificationTests {

    @Test
    public void testCatPickPairNotification() {
        Notification notification = new CatStealNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.catStealCard(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testCloseGameNotification() {
        Notification notification = new CloseGameNotification();
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.closeGame();
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testDrawNotification() {
        Card card = new Card(CardType.TACOCAT);
        Notification notification = new DrawNotification(0, card);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.drawCard(0, card);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testExplodeNotification() {
        Notification notification = new ExplodeNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.explodePlayer(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testFavorPickPlayerNotification() {
        Notification notification = new FavorPickPlayerNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.favorPickPlayer(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testFavorSelectCardNotification() {
        Notification notification = new FavorSelectCardNotification(0, 1);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.favorSelectCard(0, 1);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testGiveCardNotification() {
        Card card = new Card(CardType.TACOCAT);
        Notification notification = new GiveCardNotification(0, 1, card);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.giveCard(0, 1, card);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testPlayNotification() {
        Card card = new Card(CardType.TACOCAT);
        Notification notification = new PlayNotification(0, card);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.playCard(0, card);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testSetNameNotification() {
        Notification notification = new SetNameNotification(0, "testname");
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.setName(0, "testname");
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testStealCardNotification() {
        Notification notification = new StealCardNotification(0, 1);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.stealCard(0, 1);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testTryExplodeNotification() {
        Card card = new Card(CardType.EXPLODING_KITTEN);
        Notification notification = new TryExplodeNotification(0, card);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.tryExplode(0, card);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testWinGameNotification() {
        Notification notification = new WinGameNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.winGame(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testCloseNopeNotification() {
        Notification notification = new CloseNopeNotification();
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.closeNope();
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testSeeTheFutureNotification() {
        Notification notification = new SeeTheFutureNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.seeTheFuture(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

    @Test
    public void testAlterTheFutureNotification() {
        Notification notification = new AlterTheFutureNotification(0);
        UserController userMock = EasyMock.strictMock(UserController.class);
        userMock.alterTheFuture(0);
        EasyMock.replay(userMock);
        notification.applyNotification(userMock);
        EasyMock.verify(userMock);
    }

}
