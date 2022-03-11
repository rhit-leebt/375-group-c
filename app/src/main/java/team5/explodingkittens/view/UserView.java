package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;

/**
 * A PlayerWindow displays all information relavent to a
 * specific player using JavaFX, via the {@link Stage}.
 *
 * @author Duncan McKee, Maura Coriale, Andrew Orians
 */
public class UserView extends Stage implements AbstractUserView {
    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private static final double OUTSIDE_PADDING = 25;
    private static final double VERTICAL_SPACING = 10;
    private static final double DECK_DISCARD_SPACING = 20;
    private static final String PLAYER_WINDOW_TITLE = "gameTitle";
    private static final String NAME_DIALOG_TITLE = "nameDialogTitle";
    private static final String NAME_DIALOG_HEADER = "nameDialogHeader";
    private static final String NAME_DIALOG_CONTENT = "nameDialogContent";
    private static final String FAVOR_PICK_DIALOG_TITLE = "favorPickDialogTitle";
    private static final String FAVOR_PICK_DIALOG_HEADER = "favorPickDialogHeader";
    private static final String FAVOR_PICK_DIALOG_CONTENT = "favorPickDialogContent";
    private static final String FAVOR_SELECT_DIALOG_TITLE = "favorSelectDialogTitle";
    private static final String FAVOR_SELECT_DIALOG_HEADER = "favorSelectDialogHeader";
    private static final String FAVOR_SELECT_DIALOG_CONTENT = "favorSelectDialogContent";
    private static final String EXPLODE_DIALOG_TITLE = "explodeDialogTitle";
    private static final String EXPLODE_DIALOG_HEADER = "explodeDialogHeader";
    private static final String EXPLODE_DIALOG_CONTENT = "explodeDialogContent";
    private static final String CANT_DEFUSE_DIALOG_TITLE = "cantDefuseDialogTitle";
    private static final String CANT_DEFUSE_DIALOG_HEADER = "cantDefuseDialogHeader";
    private static final String CANT_DEFUSE_DIALOG_CONTENT = "cantDefuseDialogContent";
    private static final String RETURN_EXPLODING_KITTEN_DIALOG_TITLE
            = "returnExplodingKittenDialogTitle";
    private static final String RETURN_EXPLODING_KITTEN_DIALOG_HEADER
            = "returnExplodingKittenDialogHeader";
    private static final String RETURN_EXPLODING_KITTEN_DIALOG_CONTENT
            = "returnExplodingKittenDialogContent";
    private static final String WIN_DIALOG_TITLE = "winDialogTitle";
    private static final String WIN_DIALOG_HEADER = "winDialogHeader";
    private static final String WIN_DIALOG_CONTENT = "winDialogContent";
    private static final String PICK_PAIR_DIALOG_TITLE = "pickPairDialogTitle";
    private static final String PICK_PAIR_DIALOG_HEADER = "pickPairDialogHeader";
    private static final String PICK_PAIR_DIALOG_CONTENT = "pickPairDialogContent";
    private static final String PICK_PLAYER_DIALOG_TITLE = "pickPlayerDialogTitle";
    private static final String PICK_PLAYER_DIALOG_HEADER = "pickPlayerDialogHeader";
    private static final String PICK_PLAYER_DIALOG_CONTENT = "pickPlayerDialogContent";
    private static final String CANT_CAT_DIALOG_TITLE = "cantCatDialogTitle";
    private static final String CANT_CAT_DIALOG_HEADER = "cantCatDialogHeader";
    private static final String CANT_CAT_DIALOG_CONTENT = "cantCatDialogContent";
    private static final String NOPE_DIALOG_TITLE = "nopeDialogTitle";
    private static final String NOPE_DIALOG_HEADER = "nopeDialogHeader";
    private static final String NOPE_DIALOG_CONTENT = "nopeDialogContent";
    private static final String PLAYER_NO_NAME = "noNameEntered";
    private static final String SEE_THE_FUTURE_DIALOG_TITLE = "TODO";

    private TranslateAnimator drawAnimator;
    private UiDeck deck;
    private UiDiscard discard;
    private List<UiPlayer> players;
    private UiPlayerHand playerHand;
    private UserController userController;
    private LanguageFriendlyEmptyDialog nopeDialog;
    private FutureSeeingDialog seeFutureDialog;

    /**
     * Creates a PlayerWindow object with the provided details.
     *
     * @param numPlayers     The number of players who are playing the game.
     * @param playerId       The ID of the player who's window this is.
     */
    public UserView(int numPlayers, int playerId) {
        players = new ArrayList<>(numPlayers);

        drawAnimator = new TranslateAnimator(1);

        HBox otherPlayers = new HBox();
        otherPlayers.setAlignment(Pos.CENTER);

        playerHand = new UiPlayerHand(e -> tryPlayCard());

        for (int i = 0; i < numPlayers; i++) {
            if (i == playerId) {
                players.add(playerHand);
            } else {
                UiOtherPlayer otherPlayer = new UiOtherPlayer(i);
                otherPlayers.getChildren().add(otherPlayer);
                players.add(otherPlayer);
            }
        }

        HBox decks = new HBox();
        decks.setSpacing(DECK_DISCARD_SPACING);
        deck = new UiDeck();
        deck.setOnMouseClicked(event -> this.tryDrawCard());
        discard = new UiDiscard();
        decks.getChildren().addAll(deck, discard);

        VBox verticalAlign = new VBox();
        verticalAlign.setSpacing(VERTICAL_SPACING);
        verticalAlign.getChildren().addAll(otherPlayers, decks, playerHand);

        HBox horizontalAlign = new HBox();
        horizontalAlign.getChildren().add(verticalAlign);
        horizontalAlign.setPadding(new Insets(OUTSIDE_PADDING));
        horizontalAlign.setAlignment(Pos.CENTER);

        Scene scene = new Scene(horizontalAlign, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        setTitle(ResourceController.getString(PLAYER_WINDOW_TITLE));
        setScene(scene);
        show();
        LanguageFriendlyTextInputDialog nameDialog = new LanguageFriendlyTextInputDialog();
        nameDialog.setTitle(ResourceController.getString(NAME_DIALOG_TITLE));
        nameDialog.setHeaderText(ResourceController.getString(NAME_DIALOG_HEADER));
        nameDialog.setContentText(ResourceController.getString(NAME_DIALOG_CONTENT));
        nameDialog.addConfirmButton();
        nameDialog.show();
        nameDialog.setOnCloseRequest(event -> {
            if (nameDialog.getResult() == null || nameDialog.getResult().isEmpty()) {
                userController.trySetName(ResourceController.getString(PLAYER_NO_NAME));
            } else {
                userController.trySetName(nameDialog.getResult());
            }
        });
    }

    @Override
    public void setName(int playerId, String name) {
        players.get(playerId).setName(name);
    }

    @Override
    public void drawCard(int playerId, Card card) {
        UiCard uiCard = players.get(playerId).drawCard(card);
        EventHandler<ActionEvent> handler = players.get(playerId).getDrawAnimationHandler(uiCard);
        drawAnimator.animate(uiCard, deck, handler);
    }

    @Override
    public Card showFavorDialog(int toPlayerId, Player player) {
        if (player.size() == 0) {
            return null;
        }
        List<String> cardNames = new ArrayList<>();
        for (int i = 0; i < player.size(); i++) {
            String name = player.getCard(i).getName();
            cardNames.add(name);
        }

        LanguageFriendlyChoiceDialog<String> favorDialog =
                new LanguageFriendlyChoiceDialog<>(
                        cardNames.get(0), cardNames);
        favorDialog.setTitle(ResourceController.getString(FAVOR_SELECT_DIALOG_TITLE));
        favorDialog.setHeaderText(ResourceController.getString(FAVOR_SELECT_DIALOG_HEADER));
        favorDialog.setContentText(ResourceController.getString(FAVOR_SELECT_DIALOG_CONTENT));
        favorDialog.addConfirmButton();
        String response = favorDialog.showAndWaitDefault();
        return player.getCard(cardNames.indexOf(response));
    }

    @Override
    public void giveCard(int toPlayerId, int fromPlayerId, Card card) {
        UiCard uiCard = players.get(toPlayerId).drawCard(card);
        players.get(fromPlayerId).discardCard(card);
        EventHandler<ActionEvent> handler = players.get(toPlayerId).getDrawAnimationHandler(uiCard);
        drawAnimator.animate(uiCard, players.get(fromPlayerId).getNode(), handler);
    }

    @Override
    public void discardCard(int playerId, Card card) {
        players.get(playerId).discardCard(card);
        UiCard uiCard = discard.discardCard(card);
        EventHandler<ActionEvent> handler = discard.getDiscardAnimationHandler(uiCard, card);
        drawAnimator.animate(uiCard, players.get(playerId).getNode(), handler);
    }

    @Override
    public void discardAllCards(int playerId) {
        players.get(playerId).discardAllCards();
    }

    /**
     * Finds the card that was selected by the user in the UI element.
     *
     * @return The card that was selected.
     */
    public Card getSelectedCard() {
        Card cardToPlay = playerHand.getSelectedCard();
        if (cardToPlay == null) {
            throw new IllegalArgumentException(
                    "Must choose a card to play or choose to only draw card");
        }
        return cardToPlay;
    }

    public void tryPlayCard() {
        Card cardToPlay = this.getSelectedCard();
        this.userController.tryPlayCard(cardToPlay);
    }

    public void tryDrawCard() {
        this.userController.tryDrawCard();
    }

    @Override
    public boolean showExplodeDialog() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        dialog.setTitle(ResourceController.getString(EXPLODE_DIALOG_TITLE));
        dialog.setHeaderText(ResourceController.getString(EXPLODE_DIALOG_HEADER));
        dialog.setContentText(ResourceController.getString(EXPLODE_DIALOG_CONTENT));
        dialog.addConfirmButton();
        dialog.addCancelButton();
        return dialog.showAndWaitDefault();
    }

    @Override
    public void showCantDefuseDialog() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        dialog.setTitle(ResourceController.getString(CANT_DEFUSE_DIALOG_TITLE));
        dialog.setHeaderText(ResourceController.getString(CANT_DEFUSE_DIALOG_HEADER));
        dialog.setContentText(ResourceController.getString(CANT_DEFUSE_DIALOG_CONTENT));
        dialog.addConfirmButton();
        dialog.showAndWait();
    }

    @Override
    public int showPutExplodingKittenBackDialog() {
        LanguageFriendlyTextInputDialog dialog = new LanguageFriendlyTextInputDialog();
        dialog.setTitle(ResourceController.getString(RETURN_EXPLODING_KITTEN_DIALOG_TITLE));
        dialog.setHeaderText(ResourceController.getString(RETURN_EXPLODING_KITTEN_DIALOG_HEADER));
        dialog.setContentText(ResourceController.getString(RETURN_EXPLODING_KITTEN_DIALOG_CONTENT));
        dialog.addConfirmButton();
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return Integer.parseInt(result.get());
        }
        return 0;
    }

    @Override
    public void winGame() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        dialog.setTitle(ResourceController.getString(WIN_DIALOG_TITLE));
        dialog.setHeaderText(ResourceController.getString(WIN_DIALOG_HEADER));
        dialog.setContentText(ResourceController.getFormatString(
                WIN_DIALOG_CONTENT, playerHand.getName()));
        dialog.addConfirmButton();
        dialog.showAndWait();
        userController.tryCloseGame();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Override
    public CardType showPickPair(Set<CardType> types) {
        Iterator<CardType> iter = types.iterator();
        List<String> cardTypes = new ArrayList<>();
        List<CardType> typesList = new ArrayList<>();
        while (iter.hasNext()) {
            CardType type = iter.next();
            cardTypes.add(ResourceController.getCardName(type));
            typesList.add(type);
        }

        LanguageFriendlyChoiceDialog<String> pickPairDialog =
                new LanguageFriendlyChoiceDialog<>(
                        cardTypes.get(0), cardTypes);
        pickPairDialog.setTitle(ResourceController.getString(PICK_PAIR_DIALOG_TITLE));
        pickPairDialog.setHeaderText(ResourceController.getString(PICK_PAIR_DIALOG_HEADER));
        pickPairDialog.setContentText(ResourceController.getString(PICK_PAIR_DIALOG_CONTENT));
        pickPairDialog.addConfirmButton();
        String result = pickPairDialog.showAndWaitDefault();
        return typesList.get(cardTypes.indexOf(result));
    }

    @Override
    public int showPickOtherPlayer() {
        Map<String, Integer> namesToId = new HashMap<>();
        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != playerHand) {
                String name = players.get(i).getName();
                playerNames.add(name);
                namesToId.put(name, i);
            }
        }

        LanguageFriendlyChoiceDialog<String> pickPlayerDialog =
                new LanguageFriendlyChoiceDialog<>(
                        playerNames.get(0), playerNames);
        pickPlayerDialog.setTitle(ResourceController.getString(PICK_PLAYER_DIALOG_TITLE));
        pickPlayerDialog.setHeaderText(ResourceController.getString(PICK_PLAYER_DIALOG_HEADER));
        pickPlayerDialog.setContentText(ResourceController.getString(PICK_PLAYER_DIALOG_CONTENT));
        pickPlayerDialog.addConfirmButton();
        String result = pickPlayerDialog.showAndWaitDefault();
        return namesToId.get(result);
    }

    @Override
    public void showCantPlayCat() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        dialog.setTitle(ResourceController.getString(CANT_CAT_DIALOG_TITLE));
        dialog.setHeaderText(ResourceController.getString(CANT_CAT_DIALOG_HEADER));
        dialog.setContentText(ResourceController.getString(CANT_CAT_DIALOG_CONTENT));
        dialog.addConfirmButton();
        dialog.showAndWait();
    }

    @Override
    public boolean showNopePlay(int playerId, Card card) {
        nopeDialog = new LanguageFriendlyEmptyDialog();
        nopeDialog.setTitle(ResourceController.getString(NOPE_DIALOG_TITLE));
        nopeDialog.setHeaderText(String.format(ResourceController.getString(NOPE_DIALOG_HEADER),
                players.get(playerId).getName()));
        nopeDialog.setContentText(String.format(ResourceController.getString(NOPE_DIALOG_CONTENT),
                card.getName()));
        nopeDialog.addConfirmButton();
        nopeDialog.addCancelButton();
        return nopeDialog.showAndWaitDefault();
    }

    @Override
    public void closeNope() {
        if (nopeDialog != null && nopeDialog.isShowing()) {
            nopeDialog.close();
        }
    }

    @Override
    public void seeTheFuture(Card card0, Card card1, Card card2) {
        seeFutureDialog = new FutureSeeingDialog(card0, card1, card2);
        seeFutureDialog.setTitle(SEE_THE_FUTURE_DIALOG_TITLE);
        seeFutureDialog.show();
    }

    @Override
    public ArrayList<Card> alterTheFuture(Card card0, Card card1, Card card2) {
       FutureAlteringDialog futureDialog = new FutureAlteringDialog(card0, card1, card2);
       futureDialog.showAndWait();
       return futureDialog.chooseNewOrder();
    }
}