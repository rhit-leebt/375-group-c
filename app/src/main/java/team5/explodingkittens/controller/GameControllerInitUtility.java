package team5.explodingkittens.controller;

import team5.explodingkittens.model.Deck;
import team5.explodingkittens.model.DiscardPile;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;
import team5.explodingkittens.view.spectatorview.SpectatorView;
import team5.explodingkittens.view.UserViewFactory;

import java.util.ArrayList;
import java.util.List;

public class GameControllerInitUtility {

    GameController gameController;

    public GameControllerInitUtility(GameController gameController) {
        this.gameController = gameController;
    }

    public void validatePlayerCount(int numPlayers) {
        if (numPlayers < 2) {
            throw new IllegalArgumentException("Must have more than two players to start game");
        }
        if (numPlayers > 10) {
            throw new IllegalArgumentException("Must have less than ten players to start game");
        }
    }

    public List<Player> initializePlayerList(int numPlayers) {
        List<Player> players = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player();
            players.add(player);
        }
        return players;
    }

    public List<UserController> createUsersWithViewsFromPlayers(List<Player> players, UserViewFactory userViewFactory) {
        List<UserController> users = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            AbstractUserView userView = userViewFactory.createUserView(players.size(), i);
            UserController userController = new UserController(gameController, userView, players.get(i), i);
            users.add(userController);
            userView.setUserController(userController);
        }
        return users;
    }

    public void setUpSpectatorView(List<Player> players, UserViewFactory userViewFactory) {
        SpectatorView spectatorView = userViewFactory.createSpectatorView(players);
        for (Player player : players) {
            player.registerObserver(spectatorView.getObservableHandler());
        }
        gameController.registerObserver(spectatorView.getObservableHandler());
    }

    public void setUpPiles(List<UserController> users) {
        gameController.deck = new Deck(users.size());
        gameController.deck.dealCards(users);
        gameController.discardPile = new DiscardPile();
    }

}
