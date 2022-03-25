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

public class FutureAlteringDialog extends Dialog {
    private ArrayList<Card> cards;
    private Card secondCard;
    private Card thirdCard;
    private static final String DIALOG_TITLE = "alterTheFutureTitle";
    HashMap<String, Card> map;


    /**
     * Dialog that allows player to see the top three cards and choose a reordering
     *
     * @param card0 the first card in the deck
     * @param card1 the second card in the deck
     * @param card2 the third card in the deck
     */
    public FutureAlteringDialog(Card card0, Card card1, Card card2) {
        this.map = new HashMap<>();
        this.cards = new ArrayList<>();
        this.cards.add(card0);
        this.cards.add(card1);
        this.cards.add(card2);
        for(Card card : cards){
            this.map.put(card0.getName(), card0);
            this.map.put(card1.getName(), card1);
            this.map.put(card2.getName(), card2);
        }
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
/*        confirmButton.setOnAction(e -> {
            this.chooseNewOrder();
        });*/
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

    public ArrayList<Card> chooseNewOrder(){
        ArrayList<Card> workingSet = (ArrayList<Card>) this.cards.clone();
        System.out.println(workingSet.size());
        LanguageFriendlyChoiceDialog firstChoice = new LanguageFriendlyChoiceDialog(workingSet.get(0), workingSet);
        firstChoice.setTitle(ResourceController.getString("chooseFirstCard"));
        firstChoice.addConfirmButton();
        firstChoice.showAndWaitDefault();
        workingSet.remove(firstChoice.getSelectedItem());
        getSecondChoice(workingSet);
        Card firstCard = (Card) firstChoice.getSelectedItem();
        ArrayList<Card> returnCards = new ArrayList<>();
        returnCards.add(0, firstCard);
        returnCards.add(1, secondCard);
        returnCards.add(2, thirdCard);
        System.out.println("New top three: " + returnCards.toString());
        return returnCards;
    }

    public void getSecondChoice(ArrayList<Card> workingSet){
        LanguageFriendlyChoiceDialog secondChoice = new LanguageFriendlyChoiceDialog(workingSet.get(0), workingSet);
        secondChoice.addConfirmButton();
        secondChoice.showAndWaitDefault();
        Card secondCard = (Card) secondChoice.getSelectedItem();
        this.secondCard = secondCard;
        workingSet.remove(secondCard);
        this.thirdCard = workingSet.get(0);
    }
}