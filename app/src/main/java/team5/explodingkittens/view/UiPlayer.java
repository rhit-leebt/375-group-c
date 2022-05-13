package team5.explodingkittens.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.view.userview.UserView;

/**
 * The interface that bridges the gap between {@link UserView}
 * and concrete UiPlayers.
 *
 * @author Duncan McKee
 */
public interface UiPlayer {
    String getName();

    void setName(String name);

    UiCard drawCard(Card card);

    EventHandler<ActionEvent> getDrawAnimationHandler(UiCard target);

    Node getNode();

    void discardCard(Card card);

    void discardAllCards();
}
