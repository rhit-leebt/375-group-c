package team5.explodingkittens.view;

import javafx.stage.Stage;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import team5.explodingkittens.model.Card;

import java.util.ArrayList;

public class FutureAlteringDialogTests extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        testChooseNewOrder();
    }

    public void testChooseNewOrder() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        FutureAlteringDialog dialog = EasyMock.partialMockBuilder(FutureAlteringDialog.class)
                .addMockedMethod("getSecondChoice").createMock();
        LanguageFriendlyChoiceDialog<Card> langDialog = new LanguageFriendlyChoiceDialog<>(cards.get(1), cards);

        dialog.getSecondChoice(cards);
        for (int i = 0; i < cards.size(); i++) {
            EasyMock.replay(cards.get(i));
        }
        EasyMock.replay(dialog);

        langDialog.setSelectedItem(cards.get(1));
        ArrayList<Card> output = dialog.chooseNewOrder(cards, langDialog);

        Assert.assertEquals(3, output.size());

        for (int i = 0; i < cards.size(); i++) {
            EasyMock.verify(cards.get(i));
        }
        EasyMock.verify(dialog);
    }

    @Test
    public void run() {}
}
