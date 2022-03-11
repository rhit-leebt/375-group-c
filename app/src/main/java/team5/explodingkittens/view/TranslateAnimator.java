package team5.explodingkittens.view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * An animation manager that can move nodes in a straight line,
 * from a provided node to their resting position.
 *
 * @Author Duncan McKee
 */
public class TranslateAnimator {

    private final double speed;

    public TranslateAnimator(double speed) {
        this.speed = speed;
    }

    /**
     * Starts an animation of the provided node from the provided location
     * to the other provided location. Triggers the provided EventHandler
     * when the animation is done.
     *
     * @param node The node to move
     * @param fromX The starting location's X
     * @param fromY The starting location's Y
     * @param toX The ending location's X
     * @param toY The ending location's Y
     * @param handler The event handler to trigger when the animation finishes
     */
    private void animate(Node node, double fromX, double fromY,
                         double toX, double toY, EventHandler<ActionEvent> handler) {
        TranslateTransition transition = new TranslateTransition();
        double distance = Math.sqrt((toX - fromX) * (toX - fromX)
                                + (toY - fromY) * (toY - fromY));
        transition.setDuration(Duration.millis(distance / speed));
        transition.setNode(node);
        transition.setFromX(fromX - toX);
        transition.setFromY(fromY - toY);
        transition.setToX(0);
        transition.setToY(0);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        transition.setOnFinished(handler);
    }

    /**
     * Starts an animation of the provided node from the from node
     * to the resting location (0 for both translate offsets). Triggers
     * the provided EventHandler when the animation is done.
     *
     * @param node The node to move
     * @param from The node to start the animation from
     * @param handler The event handler to trigger when the animation finishes
     */
    public void animate(Node node, Node from, EventHandler<ActionEvent> handler) {
        Bounds fromLocalBounds = from.getBoundsInLocal();
        Bounds fromSceneBounds = from.localToScene(fromLocalBounds);
        Bounds nodeLocalBounds = node.getBoundsInLocal();
        Bounds nodeSceneBounds = node.localToScene(nodeLocalBounds);
        animate(node, fromSceneBounds.getCenterX(), fromSceneBounds.getCenterY(),
                nodeSceneBounds.getCenterX(), nodeSceneBounds.getCenterY(), handler);
    }
}
