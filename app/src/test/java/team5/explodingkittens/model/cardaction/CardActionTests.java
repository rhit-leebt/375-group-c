package team5.explodingkittens.model.cardaction;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.model.CardType;

/**
 * A testing script created to test {@link CardAction}.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class CardActionTests {

    CardAction cardAction;
    GameController controllerMock;

    @Before
    public void initController() {
        controllerMock = EasyMock.strictMock(GameController.class);
    }

    @After
    public void confirm() {
        EasyMock.replay(controllerMock);
        cardAction.applyAction(controllerMock);
        EasyMock.verify(controllerMock);
    }

    @Test
    public void testNullCardAction() {
        cardAction = new NullCardAction();
    }

    @Test
    public void testShuffleCardAction() {
        cardAction = new ShuffleCardAction();
        controllerMock.shuffleDeck();
    }

    @Test
    public void testDrawFromTheBottomCardAction() {
        cardAction = new DrawFromTheBottomCardAction();
        controllerMock.drawFromTheBottom();
    }

    @Test
    public void testSkipCardAction() {
        cardAction = new SkipCardAction();
        controllerMock.skipAction();
    }

    @Test
    public void testAttackCardAction() {
        cardAction = new AttackCardAction();
        controllerMock.attackAction();
    }

    @Test
    public void testFavorCardAction() {
        cardAction = new FavorCardAction();
        controllerMock.startFavor();
    }

    @Test
    public void testBeardCatCardAction() {
        cardAction = new CatCardAction();
        controllerMock.startCatCard();
    }

    @Test
    public void testSeeTheFutureCardAction() {
        cardAction = new SeeTheFutureAction();
        controllerMock.seeTheFuture();
    }

    @Test
    public void testAlterTheFutureCardAction() {
        cardAction = new AlterTheFutureAction();
        controllerMock.alterTheFuture();
    }
}
