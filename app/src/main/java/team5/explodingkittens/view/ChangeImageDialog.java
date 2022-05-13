package team5.explodingkittens.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.CardType;

import java.util.List;
import java.util.Optional;

public class ChangeImageDialog<T> extends ChoiceDialog<T> {
    private T defaultChoice;

    public ChangeImageDialog(T defaultChoice, List<T> choices) {
        super(defaultChoice, choices);
        this.defaultChoice = defaultChoice;
        getDialogPane().getButtonTypes().clear();
    }

    public ChangeImageDialog() {}

    public T showAndWaitDefault() {
        Optional<T> result = super.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return this.defaultChoice;
        }
    }
}
