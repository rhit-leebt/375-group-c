package team5.explodingkittens.controller;

import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;

public abstract class ActionController {
    protected GameController gameController;
    protected AbstractUserView view;
    protected int playerId;
    protected Player player;

    public ActionController(GameController gameController, AbstractUserView view, int playerId, Player player) {
        this.gameController = gameController;
        this.view = view;
        this.playerId = playerId;
        this.player = player;
    }

}
