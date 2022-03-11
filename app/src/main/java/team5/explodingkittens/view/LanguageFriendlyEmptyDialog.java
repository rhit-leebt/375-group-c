package team5.explodingkittens.view;

import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import team5.explodingkittens.controller.ResourceController;

/**
 * A specialized Dialog ButtonType that works with locales.
 *
 * @author Duncan McKee
 */
public class LanguageFriendlyEmptyDialog extends Dialog<ButtonType> {

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
        Button confirmButton = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
        confirmButton.setText(ResourceController.getString("cancel"));
    }

    /**
     * Shows the dialog and waits until it has been closed.
     *
     * @return If the user pressed OK or not
     */
    public boolean showAndWaitDefault() {
        Optional<ButtonType> result = showAndWait();
        if (result.isPresent()) {
            return result.get() == ButtonType.OK;
        } else {
            return false;
        }
    }

}
