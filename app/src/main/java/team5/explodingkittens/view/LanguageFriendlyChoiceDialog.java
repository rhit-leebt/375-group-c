package team5.explodingkittens.view;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import team5.explodingkittens.controller.ResourceController;

/**
 * A specialized ChoiceDialog that works with locales.
 *
 * @author Maura Coriale, Duncan McKee
 */
public class LanguageFriendlyChoiceDialog<T> extends ChoiceDialog<T> {
    private final T defaultChoice;

    /**
     * Constructor creates a locale-friendly choice dialog.
     *
     * @param defaultChoice the default choice that the dialog starts with.
     * @param choices       the list of all the choices the user can choose from.
     */
    public LanguageFriendlyChoiceDialog(T defaultChoice, List<T> choices) {
        super(defaultChoice, choices);
        this.defaultChoice = defaultChoice;
        getDialogPane().getButtonTypes().clear();
    }

    public LanguageFriendlyChoiceDialog() {
        this.defaultChoice = null;
    }

    /**
     * Shows the dialog and waits until it has been closed.
     *
     * @return The selected option or the default option
     */
    public T showAndWaitDefault() {
        Optional<T> result = super.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return defaultChoice;
        }
    }
}