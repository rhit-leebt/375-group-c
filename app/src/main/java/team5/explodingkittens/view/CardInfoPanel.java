package team5.explodingkittens.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;

/**
 * This is a part of the {@link UiPlayerHand} that displays the currently selected
 * card's information, and gives the player the ability to play the card.
 *
 * @author Duncan McKee
 */
public class CardInfoPanel extends VBox {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 300;
    private static final double BUTTON_HEIGHT = 50;
    private static final double INFO_PANE_HEIGHT = 220;
    private static final double INFO_TEXT_WIDTH = 180;
    private static final double CARD_NAME_FONT_SIZE = 20;
    private static final double CARD_INFO_FONT_SIZE = 15;
    private static final String PLAY_BUTTON_PLAY_TEXT = "playButton";

    private final Button playButton;
    private final Label cardNameLabel;
    private final Text cardInfoLabel;

    /**
     * Creates a card info panel with the specified event handler.
     *
     * @param playEvent The event that should be triggered when the play button is clicked.
     */
    public CardInfoPanel(EventHandler<ActionEvent> playEvent) {
        playButton = new Button(ResourceController.getString(PLAY_BUTTON_PLAY_TEXT));
        playButton.setPrefSize(WIDTH, BUTTON_HEIGHT);
        playButton.setOnAction(playEvent);
        playButton.setDisable(true);
        cardNameLabel = new Label();
        cardNameLabel.setFont(new Font(ResourceController.getFontName(), CARD_NAME_FONT_SIZE));
        cardNameLabel.setWrapText(true);
        ScrollPane infoPane = new ScrollPane();
        infoPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        infoPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        infoPane.setPrefHeight(INFO_PANE_HEIGHT);
        cardInfoLabel = new Text();
        cardInfoLabel.setFont(new Font(ResourceController.getFontName(), CARD_INFO_FONT_SIZE));
        cardInfoLabel.setWrappingWidth(INFO_TEXT_WIDTH);
        infoPane.setContent(cardInfoLabel);
        getChildren().addAll(playButton, cardNameLabel, infoPane);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
    }

    /**
     * Sets the info panel to display a specific card's information.
     *
     * @param card The card to be displayed.
     */
    public void displayCard(Card card) {
        cardNameLabel.setText(card.getName());
        cardInfoLabel.setText(card.getCardInfo());
        playButton.setDisable(false);
    }

    /**
     * Clears the currently displayed card.
     */
    public void clearCard() {
        cardNameLabel.setText("");
        cardInfoLabel.setText("");
        playButton.setDisable(true);
    }

}