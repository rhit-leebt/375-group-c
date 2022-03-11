package team5.explodingkittens.model;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.GameController;

/**
 * A testing script created to test {@link TurnState}.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class TurnStateTests {

    @Test
    public void testWrongNumberOfPlayers() {
        try {
            new TurnState(1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(
                    "Must have between 2 and 10 players to play the game", e.getMessage());
        }
        try {
            new TurnState(11);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(
                    "Must have between 2 and 10 players to play the game", e.getMessage());
        }
    }

    @Test
    public void testDrawCards() {
        TurnState state = new TurnState(2);
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.drawCard();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.drawCard();
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
    }

    @Test
    public void testDrawType() {
        TurnState state = new TurnState(2);
        Assert.assertEquals(TurnState.DrawType.DRAW_FROM_TOP, state.drawType);
        state.drawType = TurnState.DrawType.DRAW_FROM_BOTTOM;
        state.drawCard();
        Assert.assertEquals(TurnState.DrawType.DRAW_FROM_TOP, state.drawType);
    }

    @Test
    public void testAttackAction() {
        TurnState state = new TurnState(2);
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.attackAction();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(2, state.getDrawCount());
        state.drawCard();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.drawCard();
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.attackAction();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(2, state.getDrawCount());
        state.attackAction();
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(4, state.getDrawCount());
    }

    @Test
    public void testSkipAction() {
        TurnState state = new TurnState(2);
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.skipAction();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.attackAction();
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(2, state.getDrawCount());
        state.skipAction();
        Assert.assertEquals(0, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
        state.skipAction();
        Assert.assertEquals(1, state.getTurnPlayerId());
        Assert.assertEquals(1, state.getDrawCount());
    }

    @Test
    public void testIncrementNoOutPlayers() {
        TurnState state = new TurnState(4);
        Assert.assertEquals(state.getTurnPlayerId(), 0);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 1);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 2);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 3);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 0);
    }

    @Test
    public void testIncrementOneOut() {
        TurnState state = new TurnState(4);
        state.invalidatePlayer(1);
        Assert.assertEquals(state.getTurnPlayerId(), 0);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 2);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 3);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 0);
    }

    @Test
    public void testIncrementLastOut() {
        TurnState state = new TurnState(4);
        state.invalidatePlayer(3);
        Assert.assertEquals(state.getTurnPlayerId(), 0);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 1);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 2);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 0);
    }

    @Test
    public void testIncrementMultipleInvalid() {
        TurnState state = new TurnState(4);
        state.invalidatePlayer(1);
        state.invalidatePlayer(2);
        Assert.assertEquals(state.getTurnPlayerId(), 0);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 3);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 0);
    }

    @Test
    public void testIncrementWhenCurrentInvalidated() {
        TurnState state = new TurnState(4);
        state.invalidatePlayer(0);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 1);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 2);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 3);
        state.incrementTurn();
        Assert.assertEquals(state.getTurnPlayerId(), 1);
    }

    @Test
    public void testStateChangeOnExplodeTurnsLeft() {
        TurnState state = new TurnState(4);
        state.attackAction();
        Assert.assertEquals(state.getDrawCount(), 2);
        Assert.assertEquals(state.getTurnPlayerId(), 1);
        state.explodeAction();
        Assert.assertEquals(state.getDrawCount(), 1);
        Assert.assertEquals(state.getTurnPlayerId(), 2);
    }

    @Test
    public void testStateChangeOnExplodeOneTurn() {
        TurnState state = new TurnState(4);
        Assert.assertEquals(state.getDrawCount(), 1);
        Assert.assertEquals(state.getTurnPlayerId(), 0);
        state.explodeAction();
        Assert.assertEquals(state.getDrawCount(), 1);
        Assert.assertEquals(state.getTurnPlayerId(), 1);
    }

    @Test
    public void testOnlyPlayerLeftWins() {
        TurnState state = new TurnState(4);
        Assert.assertEquals(4, state.getValidCount());
        state.invalidatePlayer(0);
        Assert.assertEquals(3, state.getValidCount());
        state.invalidatePlayer(2);
        Assert.assertEquals(2, state.getValidCount());
        state.invalidatePlayer(3);
        Assert.assertEquals(1, state.getValidCount());
        Assert.assertTrue(state.isGameOver());
    }

    @Test
    public void testNotOverWithMultipleValidPlayers() {
        TurnState state = new TurnState(4);
        Assert.assertEquals(4, state.getValidCount());
        state.invalidatePlayer(0);
        Assert.assertEquals(3, state.getValidCount());
        state.invalidatePlayer(2);
        Assert.assertEquals(2, state.getValidCount());
        Assert.assertFalse(state.isGameOver());
    }

    @Test
    public void testFindLowestValid() {
        TurnState state = new TurnState(10);
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i, state.findLowestValid());
            state.invalidatePlayer(i);
        }
        Assert.assertEquals(-1, state.findLowestValid());
    }
}
