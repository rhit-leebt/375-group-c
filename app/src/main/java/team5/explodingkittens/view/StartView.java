package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;

/**
 * A UI object that contains all UI elements related to Starting the game.
 *
 * @author Maura Coriale
 */
public class StartView extends Stage {
    private static final String START_SCREEN_TITLE = "gameTitle";
    private static final String START_BUTTON_KEY = "startGameButton";
    private static final String START_SCREEN_BACKGROUND_FILE = "start_screen_background.png";
    private static final WrapperClasses.SelectInfo LOCALE_INFO =
            new WrapperClasses.SelectInfo("localeTitle", "localeHeader", "localeContent");
    private static final WrapperClasses.SelectInfo PLAYER_INFO =
            new WrapperClasses.SelectInfo("playerTitle", "playerHeader", "playerContent");
    private static final String PLAYERS_TEXT_KEY = "players";


    private final Button startButton;

    /**
     * Creates an empty StartScreen, and finds the default screen size.
     */
    public StartView() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        HBox horizontalLayout = createHBox(screenBounds);
        startButton = createStartButton();
        horizontalLayout.getChildren().add(startButton);
        setTitle(ResourceController.getString(START_SCREEN_TITLE));
        Scene scene = new Scene(horizontalLayout,
                screenBounds.getWidth(), screenBounds.getHeight());
        setScene(scene);
        show();
    }

    private HBox createHBox(Rectangle2D screenBounds) {
        HBox horizontalLayout = new HBox();
        horizontalLayout.setBackground(new Background(new BackgroundImage(new Image(
                START_SCREEN_BACKGROUND_FILE, screenBounds.getWidth(),
                screenBounds.getHeight(), false, false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        horizontalLayout.setAlignment(Pos.BOTTOM_CENTER);
        horizontalLayout.setSpacing(100);
        horizontalLayout.setPadding(new Insets(100, 100, 100, 100));
        return horizontalLayout;
    }

    private Button createStartButton() {
        Button startButton = new Button(ResourceController.getString(START_BUTTON_KEY));
        startButton.setPrefHeight(75);
        startButton.setPrefWidth(215);
        return startButton;
    }

    /**
     * Creates a popup dialog that prompts the user for the language to play in.
     *
     * @return The locale for the language selected.
     */
    public Locale showLanguageDialog() {
        List<String> localeOptions = new ArrayList<>();
        for (int i = 0; i < ResourceController.getNumberLocales(); i++) {
            localeOptions.add(ResourceController.getLocale(i)
                    .getDisplayName(ResourceController.getLocale(i)));
        }
        LanguageFriendlyChoiceDialog<String> localeDialog =
                new LanguageFriendlyChoiceDialog<>(localeOptions.get(0), localeOptions);
        localeDialog.setTitle(ResourceController.getString(LOCALE_INFO.title));
        localeDialog.setHeaderText(ResourceController.getString(LOCALE_INFO.header));
        localeDialog.setContentText(ResourceController.getString(LOCALE_INFO.content));
        localeDialog.addConfirmButton();
        String result = localeDialog.showAndWaitDefault();
        return ResourceController.getLocale(localeOptions.indexOf(result));
    }

    /**
     * Creates a popup dialog that prompts the user for the number of players.
     *
     * @return the number of players
     */
    public int showNumberPlayersDialog() {
        List<String> numberPlayersOptions = new ArrayList<>();
        for (int i = 2; i <= 10; i++) {
            numberPlayersOptions.add(ResourceController.getFormatString(PLAYERS_TEXT_KEY, i));
        }
        LanguageFriendlyChoiceDialog<String> numberPlayersDialog =
                new LanguageFriendlyChoiceDialog<>(
                        numberPlayersOptions.get(0), numberPlayersOptions);
        numberPlayersDialog.setTitle(
                ResourceController.getString(PLAYER_INFO.title));
        numberPlayersDialog.setHeaderText(
                ResourceController.getString(PLAYER_INFO.header));
        numberPlayersDialog.setContentText(
                ResourceController.getString(PLAYER_INFO.content));
        numberPlayersDialog.addConfirmButton();
        String result = numberPlayersDialog.showAndWaitDefault();
        return numberPlayersOptions.indexOf(result) + 2;
    }

    public void setStartHandler(EventHandler<ActionEvent> handler) {
        startButton.setOnAction(handler);
    }
}