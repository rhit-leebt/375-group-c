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
    private Button cancelButton;
    private Button confirmButton;

    /**
     * Creates the specialized custom version of a TextInputDialog.
     */
    public LanguageFriendlyTextInputDialog() {
        super();
        getDialogPane().getButtonTypes().clear();
    }

    /**
     * Adds a confirm button to the dialog.
     */
    public void addConfirmButton() {
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button confirmButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        confirmButton.setText(ResourceController.getString("confirm"));
    }

    /**
     * Adds a cancel button to the dialog.
     */
    public void addCancelButton() {
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Button cancelButton = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText(ResourceController.getString("cancel"));
    }
}
