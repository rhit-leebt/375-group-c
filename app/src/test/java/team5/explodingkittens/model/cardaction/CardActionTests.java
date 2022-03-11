package team5.explodingkittens.model.cardaction;

import org.easymock.EasyMock;
import org.junit.Test;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.model.CardType;

/**
 * A testing script created to test {@link CardAction}.
 *
 * @author Duncan McKee
 */
public class CardActionTests {

    @Test
    public void testNullCardAction() {
        NullCardAction cardAction = new NullCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testShuffleCardAction() {
        ShuffleCardAction cardAction = new ShuffleCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.shuffleDeck();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testDrawFromTheBottomCardAction() {
        DrawFromTheBottomCardAction cardAction = new DrawFromTheBottomCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.drawFromTheBottom();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testSkipCardAction() {
        SkipCardAction cardAction = new SkipCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.skipAction();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testAttackCardAction() {
        AttackCardAction cardAction = new AttackCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.attackAction();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testFavorCardAction() {
        FavorCardAction cardAction = new FavorCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.startFavor();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testBeardCatCardAction() {
        CatCardAction cardAction = new CatCardAction();
        GameController controllerMock = EasyMock.strictMock(GameController.class);
        controllerMock.startCatCard();
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testSeeTheFutureCardAction() {
        SeeTheFutureAction cardAction = new SeeTheFutureAction();
        GameController controller = EasyMock.strictMock(GameController.class);
        controller.seeTheFuture();
        EasyMock.replay(controller);
        cardAction.applyAction(controller);
        EasyMock.verify(controller);
    }

    @Test
    public void testAlterTheFutureCardAction() {
        AlterTheFutureAction cardAction = new AlterTheFutureAction();
        GameController controller = EasyMock.strictMock(GameController.class);
        controller.alterTheFuture();
        EasyMock.replay(controller);
        cardAction.applyAction(controller);
        EasyMock.verify(controller);
    }
}
