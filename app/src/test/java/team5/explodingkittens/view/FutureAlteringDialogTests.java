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
//        testChooseNewOrder();
//        testGetSecondChoice();
    }

    public void testChooseNewOrder() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        FutureAlteringDialog dialog = EasyMock.partialMockBuilder(FutureAlteringDialog.class)
                .addMockedMethod("getSecondChoice").createMock();
        EasyMock.resetToNice(dialog);
        LanguageFriendlyChoiceDialog<Card> langDialog = EasyMock.partialMockBuilder(LanguageFriendlyChoiceDialog.class)
                .withConstructor(cards.get(1), cards)
                .addMockedMethod("addConfirmButton")
                .addMockedMethod("showAndWaitDefault")
                .createMock();
        LanguageFriendlyChoiceDialog<Card> secondChoice = EasyMock.mock(LanguageFriendlyChoiceDialog.class);
        langDialog.addConfirmButton();
        EasyMock.expect(langDialog.showAndWaitDefault()).andReturn(null);
        dialog.getSecondChoice(cards, secondChoice);
        for (int i = 0; i < cards.size(); i++) {
            EasyMock.replay(cards.get(i));
        }
        EasyMock.replay(dialog);
        EasyMock.replay(langDialog);
        EasyMock.replay(secondChoice);

        langDialog.setSelectedItem(cards.get(1));
        ArrayList<Card> output = dialog.chooseNewOrder(cards, langDialog);

        Assert.assertEquals(3, output.size());
        Assert.assertNotNull(output.get(0));
        Assert.assertNull(output.get(1));
        Assert.assertNull(output.get(2));

        for (int i = 0; i < cards.size(); i++) {
            EasyMock.verify(cards.get(i));
        }
        EasyMock.verify(langDialog);
        EasyMock.verify(secondChoice);
    }

    public void testGetSecondChoice() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        FutureAlteringDialog dialog = EasyMock.partialMockBuilder(FutureAlteringDialog.class).createMock();
        LanguageFriendlyChoiceDialog<Card> langDialog = EasyMock.partialMockBuilder(LanguageFriendlyChoiceDialog.class)
                .withConstructor(cards.get(1), cards)
                .addMockedMethod("addConfirmButton")
                .addMockedMethod("showAndWaitDefault")
                .createMock();

        langDialog.addConfirmButton();
        EasyMock.expect(langDialog.showAndWaitDefault()).andReturn(null);
        for (int i = 0; i < cards.size(); i++) {
            EasyMock.replay(cards.get(i));
        }
        EasyMock.replay(langDialog);
        EasyMock.replay(dialog);

        dialog.getSecondChoice(cards, langDialog);

        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(cards.get(0), dialog.thirdCard);
        for (int i = 0; i < cards.size(); i++) {
            EasyMock.verify(cards.get(i));
        }
        EasyMock.verify(langDialog);
        EasyMock.verify(dialog);
    }

    @Test
    public void run() {}
}
