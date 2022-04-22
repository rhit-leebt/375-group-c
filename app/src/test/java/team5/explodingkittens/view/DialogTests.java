package team5.explodingkittens.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.easymock.EasyMock;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DialogTests extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        testLanguageFriendlyChoiceDialogAddConfirm();
        testLanguageFriendlyChoiceDialogAddCancel();
        testLanguageFriendlyEmptyDialogAddConfirm();
        testLanguageFriendlyEmptyDialogAddCancel();
        testLanguageFriendlyTextInputDialogAddConfirm();
        testLanguageFriendlyTextInputDialogAddCancel();
        testChangeImageDialogAddConfirm();
        testChangeImageDialogAddCancel();
    }

    public void testLanguageFriendlyChoiceDialogAddConfirm() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        LanguageFriendlyChoiceDialog<Card> dialog = EasyMock.partialMockBuilder(LanguageFriendlyChoiceDialog.class)
                .withConstructor(cards.get(1), cards)
                .addMockedMethod("showAndWaitDefault")
                .createMock();
        DialogBuilder.addConfirmButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        assertEquals(button.getText(), "OK");
    }

    public void testLanguageFriendlyChoiceDialogAddCancel() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(EasyMock.mock(Card.class));
        }
        LanguageFriendlyChoiceDialog<Card> dialog = EasyMock.partialMockBuilder(LanguageFriendlyChoiceDialog.class)
                .withConstructor(cards.get(1), cards)
                .addMockedMethod("showAndWaitDefault")
                .createMock();
        DialogBuilder.addCancelButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        assertEquals(button.getText(), "Cancel");
    }

    public void testLanguageFriendlyEmptyDialogAddConfirm() {
        LanguageFriendlyEmptyDialog dialog = EasyMock.partialMockBuilder(LanguageFriendlyEmptyDialog.class)
                .withConstructor()
                .addMockedMethod("showAndWaitDefault")
                .createMock();
        DialogBuilder.addConfirmButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        assertEquals(button.getText(), "OK");
    }

    public void testLanguageFriendlyEmptyDialogAddCancel() {
        LanguageFriendlyEmptyDialog dialog = EasyMock.partialMockBuilder(LanguageFriendlyEmptyDialog.class)
                .withConstructor()
                .addMockedMethod("showAndWaitDefault")
                .createMock();
        DialogBuilder.addCancelButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        assertEquals(button.getText(), "Cancel");
    }

    public void testLanguageFriendlyTextInputDialogAddConfirm() {
        LanguageFriendlyTextInputDialog dialog = EasyMock.partialMockBuilder(LanguageFriendlyTextInputDialog.class)
                .withConstructor()
                .createMock();
        DialogBuilder.addConfirmButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        assertEquals(button.getText(), "OK");
    }

    public void testLanguageFriendlyTextInputDialogAddCancel() {
        LanguageFriendlyTextInputDialog dialog = EasyMock.partialMockBuilder(LanguageFriendlyTextInputDialog.class)
                .withConstructor()
                .createMock();
        DialogBuilder.addCancelButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        assertEquals(button.getText(), "Cancel");
    }

    public void testChangeImageDialogAddConfirm() {
        List<CardType> cardTypes = List.of(CardType.values());
        ChangeImageDialog dialog = EasyMock.partialMockBuilder(ChangeImageDialog.class)
                .withConstructor(cardTypes.get(0), cardTypes)
                .createMock();
        DialogBuilder.addConfirmButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        assertEquals(button.getText(), "OK");
    }

    public void testChangeImageDialogAddCancel() {
        List<CardType> cardTypes = List.of(CardType.values());
        ChangeImageDialog dialog = EasyMock.partialMockBuilder(ChangeImageDialog.class)
                .withConstructor(cardTypes.get(0), cardTypes)
                .createMock();
        DialogBuilder.addCancelButton(dialog);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        assertEquals(button.getText(), "Cancel");
    }

    @Test
    public void run()  {}
}
