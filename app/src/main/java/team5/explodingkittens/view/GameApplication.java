package team5.explodingkittens.view;

import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;
import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.cardaction.AttackCardAction;

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
        //FutureAlteringDialog dialog = new FutureAlteringDialog(new Card(CardType.SKIP), new Card(CardType.CATTERMELON), new Card(CardType.BEARD_CAT));
        //dialog.show();
    }
}
