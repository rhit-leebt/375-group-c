package team5.explodingkittens.view;

import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.controller.ResourceController;

/**
 * This class launches the game, starting by opening a {@link StartView} window.
 *
 * @author Duncan McKee, Maura Coriale
 */
public class GameApplication extends Application {

    public static void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.close();
        StartView startScreen = new StartView();
        startScreen.setStartHandler(e -> {
            GameController gameController = new GameController();
            Locale locale = startScreen.showLanguageDialog();
            ResourceController.setLocale(locale);
            int numberPlayers = startScreen.showNumberPlayersDialog();
            startScreen.close();
            gameController.startGame(numberPlayers, new UserViewFactory(true));
        });
    }
}
