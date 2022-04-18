package team5.explodingkittens.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SpectatorViewSinglePlayerUI {
    public Label nameLabel;
    private BorderPane mainPane;

    public SpectatorViewSinglePlayerUI() {
        mainPane = new BorderPane();
        nameLabel = new Label();
        mainPane.setCenter(nameLabel);
    }

    public BorderPane getMainPane() {
        return this.mainPane;
    }

    public void updateName(String name) {
        nameLabel.setText(name);
    }
}
