package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javafx.stage.Stage;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;
import javafx.scene.control.Dialog;

/**
 * A PlayerWindow displays all information relevant to a
 * specific player using JavaFX, via the {@link Stage}.
 *
 * @author Duncan McKee, Maura Coriale, Andrew Orians
 */
public class UserView extends Stage implements AbstractUserView {
    private static final String NAME_DIALOG = "nameDialog";
    private static final String FAVOR_SELECT_DIALOG = "favorSelectDialog";
    private static final String EXPLODE_DIALOG = "explodeDialog";
    private static final String CANT_DEFUSE_DIALOG = "cantDefuseDialog";
    private static final String RETURN_EXPLODING_KITTEN_DIALOG
            = "returnExplodingKittenDialog";
    private static final String PICK_PAIR_DIALOG = "pickPairDialog";
    private static final String PICK_PLAYER_DIALOG = "pickPlayerDialog";
    private static final String CANT_CAT_DIALOG = "cantCatDialog";
    private static final String NOPE_DIALOG = "nopeDialog";
    private static final String WIN_DIALOG = "winDialog";

    private static final String TITLE_SUFFIX = "Title";
    private static final String HEADER_SUFFIX = "Header";
    private static final String CONTENT_SUFFIX = "Content";

    private static final String PLAYER_NO_NAME = "noNameEntered";
    private static final String SEE_THE_FUTURE_DIALOG_TITLE = "TODO";

    private UserController userController;
    private LanguageFriendlyEmptyDialog nopeDialog;
    private final UserViewSceneHandler sceneHandler;

    /**
     * Creates a PlayerWindow object with the provided details.
     *
     * @param numPlayers The number of players who are playing the game.
     * @param playerId   The ID of the player whose window this is.
     */
    public UserView(int numPlayers, int playerId) {
        UserViewSceneBuilder builder = new UserViewSceneBuilder(
                e -> this.tryPlayCard(),
                e -> this.tryDrawCard());
        sceneHandler = builder.generateSceneFromPlayerInfo(numPlayers, playerId);
        setScene(sceneHandler.getScene());
        show();

        generateNameInputDialog().show();
    }

    // Testing constructor for dependency injection
    public UserView(int numPlayers, int playerId, UserViewSceneBuilder builder) {
        sceneHandler = builder.generateSceneFromPlayerInfo(numPlayers, playerId);
        setScene(sceneHandler.getScene());
        show();

        generateNameInputDialog().show();
    }

    public void changeUiOnTurnChange(boolean currentTurnIsNow) {
        if (currentTurnIsNow) {
            setTitle("It is your turn!");
            toFront();
        } else {
            setTitle("Waiting for your turn...");
        }
    }

    private LanguageFriendlyTextInputDialog generateNameInputDialog() {
        LanguageFriendlyTextInputDialog nameDialog = new LanguageFriendlyTextInputDialog();
        setCommonDialogFields(nameDialog, NAME_DIALOG);
        nameDialog.addConfirmButton();
        nameDialog.setOnCloseRequest(event -> {
            if (nameDialog.getResult() == null || nameDialog.getResult().isEmpty()) {
                userController.trySetName(ResourceController.getString(PLAYER_NO_NAME));
            } else {
                userController.trySetName(nameDialog.getResult());
            }
        });

        return nameDialog;
    }

    @Override
    public void setName(int playerId, String name) {
        sceneHandler.setNameOfPlayerUi(playerId, name);
    }

    @Override
    public void drawCard(int playerId, Card card) {
        sceneHandler.drawCardAndAnimateAction(playerId, card);
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
        setCommonDialogFields(favorDialog, FAVOR_SELECT_DIALOG);
        favorDialog.addConfirmButton();
        String response = favorDialog.showAndWaitDefault();
        return player.getCard(cardNames.indexOf(response));
    }

    @Override
    public void giveCard(int toPlayerId, int fromPlayerId, Card card) {
        sceneHandler.giveCardAndAnimateAction(toPlayerId, fromPlayerId, card);
    }

    @Override
    public void discardCard(int playerId, Card card) {
        sceneHandler.discardCardAndAnimateAction(playerId, card);
    }

    @Override
    public void discardAllCards(int playerId) {
        sceneHandler.playerUis.get(playerId).discardAllCards();
    }

    /**
     * Finds the card that was selected by the user in the UI element.
     *
     * @return The card that was selected.
     */
    public Card getSelectedCard() {
        Card cardToPlay = sceneHandler.playerHandUi.getSelectedCard();
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
        setCommonDialogFields(dialog, EXPLODE_DIALOG);
        dialog.addConfirmButton();
        dialog.addCancelButton();
        return dialog.showAndWaitDefault();
    }

    @Override
    public void showCantDefuseDialog() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        setCommonDialogFields(dialog, CANT_DEFUSE_DIALOG);
        dialog.addConfirmButton();
        dialog.showAndWait();
    }

    @Override
    public int showPutExplodingKittenBackDialog() {
        LanguageFriendlyTextInputDialog dialog = new LanguageFriendlyTextInputDialog();
        setCommonDialogFields(dialog, RETURN_EXPLODING_KITTEN_DIALOG);
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
        dialog.setTitle(ResourceController.getString(WIN_DIALOG + TITLE_SUFFIX));
        dialog.setHeaderText(ResourceController.getString(WIN_DIALOG + HEADER_SUFFIX));
        dialog.setContentText(ResourceController.getFormatString(
                WIN_DIALOG + CONTENT_SUFFIX, sceneHandler.playerHandUi.getName()));
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
        Iterator<CardType> typeIterator = types.iterator();
        List<String> cardTypes = new ArrayList<>();
        List<CardType> typesList = new ArrayList<>();
        while (typeIterator.hasNext()) {
            CardType type = typeIterator.next();
            cardTypes.add(ResourceController.getCardName(type));
            typesList.add(type);
        }

        LanguageFriendlyChoiceDialog<String> pickPairDialog =
                new LanguageFriendlyChoiceDialog<>(
                        cardTypes.get(0), cardTypes);
        setCommonDialogFields(pickPairDialog, PICK_PAIR_DIALOG);
        pickPairDialog.addConfirmButton();
        String result = pickPairDialog.showAndWaitDefault();
        return typesList.get(cardTypes.indexOf(result));
    }

    @Override
    public int showPickOtherPlayer() {
        Map<String, Integer> namesToId = new HashMap<>();
        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < sceneHandler.playerUis.size(); i++) {
            if (sceneHandler.playerUis.get(i) != sceneHandler.playerHandUi) {
                String name = sceneHandler.playerUis.get(i).getName();
                playerNames.add(name);
                namesToId.put(name, i);
            }
        }

        LanguageFriendlyChoiceDialog<String> pickPlayerDialog =
                new LanguageFriendlyChoiceDialog<>(
                        playerNames.get(0), playerNames);
        setCommonDialogFields(pickPlayerDialog, PICK_PLAYER_DIALOG);
        pickPlayerDialog.addConfirmButton();
        String result = pickPlayerDialog.showAndWaitDefault();
        return namesToId.get(result);
    }

    @Override
    public void showCantPlayCat() {
        LanguageFriendlyEmptyDialog dialog = new LanguageFriendlyEmptyDialog();
        setCommonDialogFields(dialog, CANT_CAT_DIALOG);
        dialog.addConfirmButton();
        dialog.showAndWait();
    }

    @Override
    public boolean showNopePlay(int playerId, Card card) {
        nopeDialog = new LanguageFriendlyEmptyDialog();
        nopeDialog.setTitle(ResourceController.getString(NOPE_DIALOG + TITLE_SUFFIX));
        nopeDialog.setHeaderText(String.format(ResourceController.getString(NOPE_DIALOG + HEADER_SUFFIX),
                sceneHandler.playerUis.get(playerId).getName()));
        nopeDialog.setContentText(String.format(ResourceController.getString(NOPE_DIALOG + CONTENT_SUFFIX),
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
        FutureSeeingDialog seeFutureDialog = new FutureSeeingDialog(card0, card1, card2);
        seeFutureDialog.setTitle(SEE_THE_FUTURE_DIALOG_TITLE);
        seeFutureDialog.show();
    }

    @Override
    public ArrayList<Card> alterTheFuture(Card card0, Card card1, Card card2) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card0);
        cards.add(card1);
        cards.add(card2);
        FutureAlteringDialog futureDialog = new FutureAlteringDialog(cards);
        futureDialog.showAndWait();
        LanguageFriendlyChoiceDialog<Card> langDialog = new LanguageFriendlyChoiceDialog<>(cards.get(0), cards);
        langDialog.setTitle(ResourceController.getString("chooseFirstCard"));
        return futureDialog.chooseNewOrder((ArrayList<Card>) cards.clone(), langDialog);
    }

    private void setCommonDialogFields(Dialog dialog, String dialogType) {
        dialog.setTitle(ResourceController.getString(dialogType + TITLE_SUFFIX));
        dialog.setHeaderText(ResourceController.getString(dialogType + HEADER_SUFFIX));
        dialog.setContentText(ResourceController.getString(dialogType + CONTENT_SUFFIX));
    }
}
