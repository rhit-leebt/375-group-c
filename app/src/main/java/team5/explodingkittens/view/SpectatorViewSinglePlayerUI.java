package team5.explodingkittens.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SpectatorViewSinglePlayerUI {
    public Label nameLabel;
    private BorderPane mainPane;

    public SpectatorViewSinglePlayerUI(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    public BorderPane getMainPane() {
        return this.mainPane;
    }
}
