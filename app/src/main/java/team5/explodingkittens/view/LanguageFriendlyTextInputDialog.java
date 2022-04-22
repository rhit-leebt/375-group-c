package team5.explodingkittens.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import team5.explodingkittens.controller.ResourceController;

/**
 * A specialized TextInputDialog that works with locales.
 *
 * @author Maura Coriale, Duncan McKee
 */
public class LanguageFriendlyTextInputDialog extends TextInputDialog {

    /**
     * Creates the specialized custom version of a TextInputDialog.
     */
    public LanguageFriendlyTextInputDialog() {
        super();
        getDialogPane().getButtonTypes().clear();
    }
}
