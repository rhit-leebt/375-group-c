package team5.explodingkittens.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;


/**
 * Dialog that lets someone see the future.
 *
 * @author Maura Coriale
 */
public class FutureSeeingDialog extends Dialog {
    private static final String DIALOG_TITLE = "seeTheFutureTitle";


    /**
     * Dialog that allows player to see the top three cards.
     *
     * @param card0 the first card in the deck
     * @param card1 the second card in the deck
     * @param card2 the third card in the deck
     */
    public FutureSeeingDialog(Card card0, Card card1, Card card2) {
        this.setTitle(ResourceController.getString(DIALOG_TITLE));
        VBox verticalBox = new VBox();
        verticalBox.setAlignment(Pos.CENTER);
        HBox horizontalBox = new HBox();
        horizontalBox.getChildren().add(renderCard(card0));
        horizontalBox.getChildren().add(renderCard(card1));
        horizontalBox.getChildren().add(renderCard(card2));
        verticalBox.getChildren().add(horizontalBox);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button confirmButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        confirmButton.setText(ResourceController.getString("confirm"));
        verticalBox.getChildren().add(confirmButton);
        this.getDialogPane().setContent(verticalBox);
    }

    /**
     * Renders the card that is provided.
     *
     * @param card the card that we want to render the image for
     * @return the ImageView created
     */
    public Node renderCard(Card card) {
        String imagePath = card.getImagePath();
        try {
            Image cardImage = new Image(new FileInputStream(imagePath));
            ImageView view = new ImageView(cardImage);
            view.setFitHeight(300);
            view.setFitWidth(200);
            return view;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
