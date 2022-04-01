package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;
import org.easymock.EasyMock;
import org.junit.Test;

public class UserViewTests extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
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

    @Test
    public void testUserViewInitialization() {
        // Test occurs in start method due to how TestFX library initializes JavaFX environment
    }
}
