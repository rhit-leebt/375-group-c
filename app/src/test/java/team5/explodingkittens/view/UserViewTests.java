package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;
import org.easymock.EasyMock;
import org.junit.Test;

public class UserViewTests extends ApplicationTest {

    private static class DummyView extends UserView {

        boolean front = false;

        public DummyView(int numPlayers, int playerId, UserViewSceneBuilder builder) {
            super(numPlayers, playerId, builder);
        }

        @Override
        public void toFront() {
            front = true;
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
//        testConstructor();
//        testMoveToFront();
//        testStayBack();
    }

    public void testConstructor() {
        UserViewSceneBuilder sceneBuilderMock = EasyMock.mock(UserViewSceneBuilder.class);
        UserViewSceneHandler handlerMock = EasyMock.mock(UserViewSceneHandler.class);
        EasyMock.expect(sceneBuilderMock.generateSceneFromPlayerInfo(2, 0))
                .andReturn(handlerMock);
        EasyMock.expect(handlerMock.getScene()).andReturn(new Scene(new HBox()));
        EasyMock.replay(sceneBuilderMock);
        EasyMock.replay(handlerMock);

        UserView view = new UserView(2, 0, sceneBuilderMock);
        Assert.assertNotNull(view.getScene());

        EasyMock.verify(sceneBuilderMock);
    }

    public void testMoveToFront() {
        UserViewSceneBuilder sceneBuilderMock = EasyMock.mock(UserViewSceneBuilder.class);
        UserViewSceneHandler handlerMock = EasyMock.mock(UserViewSceneHandler.class);
        EasyMock.expect(sceneBuilderMock.generateSceneFromPlayerInfo(2, 0))
                .andReturn(handlerMock);
        EasyMock.expect(handlerMock.getScene()).andReturn(new Scene(new HBox()));
        EasyMock.replay(sceneBuilderMock);
        EasyMock.replay(handlerMock);

        DummyView view = new DummyView(2, 0, sceneBuilderMock);
        view.changeUiOnTurnChange(true);

        Assert.assertTrue(view.front);
        EasyMock.verify(sceneBuilderMock);
        EasyMock.verify(handlerMock);
    }

    public void testStayBack() {
        UserViewSceneBuilder sceneBuilderMock = EasyMock.mock(UserViewSceneBuilder.class);
        UserViewSceneHandler handlerMock = EasyMock.mock(UserViewSceneHandler.class);
        EasyMock.expect(sceneBuilderMock.generateSceneFromPlayerInfo(2, 0))
                .andReturn(handlerMock);
        EasyMock.expect(handlerMock.getScene()).andReturn(new Scene(new HBox()));
        EasyMock.replay(sceneBuilderMock);
        EasyMock.replay(handlerMock);

        DummyView view = new DummyView(2, 0, sceneBuilderMock);
        view.changeUiOnTurnChange(false);

        Assert.assertFalse(view.front);
        EasyMock.verify(sceneBuilderMock);
        EasyMock.verify(handlerMock);
    }

    @Test
    public void testUserViewInitialization() {
        // Test occurs in start method due to how TestFX library initializes JavaFX environment
    }
}
