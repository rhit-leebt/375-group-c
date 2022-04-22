package team5.explodingkittens.view;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class FutureAlteringDialog extends Dialog<Card> {
    private final ArrayList<Card> cards;
    protected Card secondCard;
    protected Card thirdCard;
    private static final String DIALOG_TITLE = "alterTheFutureTitle";
    HashMap<String, Card> map;


    /**
     * Dialog that allows player to see the top three cards and choose a reordering
     *
     * @param cards a list of the top three cards in the deck
     */
    public FutureAlteringDialog(ArrayList<Card> cards) {
        this.map = new HashMap<>();
        this.cards = cards;
        for(Card card : cards){
            this.map.put(cards.get(0).getName(), cards.get(0));
            this.map.put(cards.get(1).getName(), cards.get(1));
            this.map.put(cards.get(2).getName(), cards.get(2));
        }
        this.setTitle(ResourceController.getString(DIALOG_TITLE));
        VBox verticalBox = new VBox();
        verticalBox.setAlignment(Pos.CENTER);
        createHBox(cards.get(0), cards.get(1), cards.get(2), verticalBox);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button confirmButton = createConfirmButton();
        verticalBox.getChildren().add(confirmButton);
        this.getDialogPane().setContent(verticalBox);
    }

    private void createHBox(Card card0, Card card1, Card card2, VBox verticalBox) {
        HBox horizontalBox = new HBox();
        horizontalBox.getChildren().add(renderCard(card0));
        horizontalBox.getChildren().add(renderCard(card1));
        horizontalBox.getChildren().add(renderCard(card2));
        verticalBox.getChildren().add(horizontalBox);
    }

    private Button createConfirmButton() {
        Button confirmButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        confirmButton.setText(ResourceController.getString("confirm"));
        return confirmButton;
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

    public ArrayList<Card> chooseNewOrder(ArrayList<Card> workingSet, LanguageFriendlyChoiceDialog<Card> firstChoice){
        DialogBuilder.addConfirmButton(firstChoice);
        firstChoice.showAndWaitDefault();
        workingSet.remove(firstChoice.getSelectedItem());
        getSecondChoice(workingSet, new LanguageFriendlyChoiceDialog<>(workingSet.get(0), workingSet));
        Card firstCard = firstChoice.getSelectedItem();
        ArrayList<Card> returnCards = new ArrayList<>();
        returnCards.add(0, firstCard);
        returnCards.add(1, secondCard);
        returnCards.add(2, thirdCard);
        return returnCards;
    }

    public void getSecondChoice(ArrayList<Card> workingSet, LanguageFriendlyChoiceDialog<Card> secondChoice){
        DialogBuilder.addConfirmButton(secondChoice);
        secondChoice.showAndWaitDefault();
        Card secondCard = secondChoice.getSelectedItem();
        this.secondCard = secondCard;
        workingSet.remove(secondCard);
        this.thirdCard = workingSet.get(0);
    }
}