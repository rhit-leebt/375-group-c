package team5.explodingkittens.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.Player;

public class SpectatorViewSinglePlayerUI {
    public Label nameLabel;
    public VBox playerHandArea;
    private BorderPane mainPane;
    private Player associatedPlayer;

    public SpectatorViewSinglePlayerUI(Player player) {
        this.associatedPlayer = player;

        mainPane = new BorderPane();
        nameLabel = new Label();
        nameLabel.setStyle("-fx-font-weight: bold;");

        playerHandArea = new VBox();
        updateHand();

        mainPane.setTop(nameLabel);
        mainPane.setCenter(playerHandArea);
    }

    public BorderPane getMainPane() {
        return this.mainPane;
    }

    public void updateName() {
        nameLabel.setText(associatedPlayer.getName());
    }

    public void updateHand() {
        playerHandArea.getChildren().clear();
        for (Card card : associatedPlayer.getHand()) {
            Label cardNameLabel = new Label(card.getName());
            playerHandArea.getChildren().add(cardNameLabel);
        }
    }
}
