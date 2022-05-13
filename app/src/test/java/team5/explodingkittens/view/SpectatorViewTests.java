package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;
import org.easymock.EasyMock;
import org.junit.Test;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.controller.notification.Notification;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.spectatorview.SpectatorView;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneBuilder;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;
import team5.explodingkittens.view.spectatorview.SpectatorViewSinglePlayerUI;

import java.util.ArrayList;
import java.util.List;

public class SpectatorViewTests extends ApplicationTest {

    private static class DummyView extends SpectatorView {

        public DummyView(List<Player> players) {
            super(players);
        }

    }
    @Override
    public void start(Stage stage) throws Exception {
        testConstructor();
        testUpdateOnNotification();
    }

    public void testConstructor() {
        SpectatorViewSceneBuilder sceneBuilderMock = EasyMock.mock(SpectatorViewSceneBuilder.class);
        SpectatorViewSceneHandler handlerMock = EasyMock.mock(SpectatorViewSceneHandler.class);
        EasyMock.expect(sceneBuilderMock.generateSceneFromPlayerInfo())
                .andReturn(handlerMock);
        EasyMock.expect(handlerMock.getScene()).andReturn(new Scene(new HBox()));
        EasyMock.replay(sceneBuilderMock);
        EasyMock.replay(handlerMock);

        SpectatorView view = new SpectatorView(sceneBuilderMock);
        Assert.assertNotNull(view.getScene());

        EasyMock.verify(sceneBuilderMock);
    }

    public void testUpdateOnNotification() {
        SpectatorViewSinglePlayerUI singlePlayerUIMock = EasyMock.createMock(SpectatorViewSinglePlayerUI.class);
        singlePlayerUIMock.updateName();
        EasyMock.expectLastCall();
        EasyMock.replay(singlePlayerUIMock);

        List<SpectatorViewSinglePlayerUI> singlePlayerUIs = new ArrayList<>();
        singlePlayerUIs.add(singlePlayerUIMock);
        Scene scene = EasyMock.createMock(Scene.class);

        SpectatorViewSceneHandler sceneHandler = new SpectatorViewSceneHandler(singlePlayerUIs, scene);
        sceneHandler.update(new Notification() {
            @Override
            public void applyNotification(UserController userController) {
                // no behavior
            }

            @Override
            public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
                sceneHandler.updateNames();
            }
        });

        EasyMock.verify(singlePlayerUIMock);

    }

    @Test
    public void testSpectatorViewInitialization() {
        // Test occurs in start method due to how TestFX library initializes JavaFX environment
    }
}
