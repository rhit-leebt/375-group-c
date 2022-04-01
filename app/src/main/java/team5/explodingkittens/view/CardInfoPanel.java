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
    private static final WrapperClasses.SizeObject SIZE = new WrapperClasses.SizeObject(200, 300);
    private static final double BUTTON_HEIGHT = 50;
    private static final WrapperClasses.SizeObject INFO_SIZE = new WrapperClasses.SizeObject(180, 220);
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
        playButton = createPlayButton(playEvent);
        cardNameLabel = createNameLabel();
        ScrollPane infoPane = createScrollPane();
        cardInfoLabel = createInfoLabel();
        infoPane.setContent(cardInfoLabel);
        getChildren().addAll(playButton, cardNameLabel, infoPane);
        setMinSize(SIZE.width, SIZE.height);
        setMaxSize(SIZE.width, SIZE.height);
        setPrefSize(SIZE.width, SIZE.height);
    }

    private Button createPlayButton(EventHandler<ActionEvent> playEvent) {
        Button playButton = new Button(ResourceController.getString(PLAY_BUTTON_PLAY_TEXT));
        playButton.setPrefSize(SIZE.width, BUTTON_HEIGHT);
        playButton.setOnAction(playEvent);
        playButton.setDisable(true);
        return playButton;
    }

    private Label createNameLabel() {
        Label cardNameLabel = new Label();
        cardNameLabel.setFont(new Font(ResourceController.getFontName(), CARD_NAME_FONT_SIZE));
        cardNameLabel.setWrapText(true);
        return cardNameLabel;
    }

    private ScrollPane createScrollPane() {
        ScrollPane infoPane = new ScrollPane();
        infoPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        infoPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        infoPane.setPrefHeight(INFO_SIZE.height);
        return infoPane;
    }

    private Text createInfoLabel() {
        Text cardInfoLabel = new Text();
        cardInfoLabel.setFont(new Font(ResourceController.getFontName(), CARD_INFO_FONT_SIZE));
        cardInfoLabel.setWrappingWidth(INFO_SIZE.width);
        return cardInfoLabel;
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