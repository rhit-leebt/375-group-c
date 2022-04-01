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
 * @author Duncan McKee
 */
public class TranslateAnimator {

    private final double speed;

    public TranslateAnimator(double speed) {
        this.speed = speed;
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
        double deltaX = getNodePositionDeltaX(from, node);
        double deltaY = getNodePositionDeltaY(from, node);

        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.millis(getDistance(deltaX, deltaY) / speed));
        transition.setNode(node);
        transition.setFromX(deltaX);
        transition.setFromY(deltaY);

        setAndPlay(transition, handler);
    }

    private double getNodePositionDeltaX(Node from, Node to) {
        return getBoundFromNode(from).getCenterX() - getBoundFromNode(to).getCenterX();
    }

    private double getNodePositionDeltaY(Node from, Node to) {
        return getBoundFromNode(from).getCenterY() - getBoundFromNode(to).getCenterY();
    }

    private Bounds getBoundFromNode(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    private double getDistance(double deltaX, double deltaY) {
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    private void setAndPlay(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.setToX(0);
        transition.setToY(0);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        transition.setOnFinished(handler);
    }
}
