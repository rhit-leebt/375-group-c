package team5.explodingkittens.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import team5.explodingkittens.controller.ResourceController;

public class DialogBuilder {

    public static void addConfirmButton(Dialog dialog) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button confirmButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        confirmButton.setText(ResourceController.getString("confirm"));
    }

    public static void addCancelButton(Dialog dialog) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText(ResourceController.getString("cancel"));
    }


}
