package team5.explodingkittens.controller;

import java.util.ArrayList;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import team5.explodingkittens.controller.notification.DrawNotification;
import team5.explodingkittens.controller.notification.Notification;

/**
 * A testing script created to test {@link GameController} and {@link Observer}.
 *
 * @author Maura Coriale
 */
public class SubjectTests {

    private static class SubjectTestExtension extends GameController {
        public SubjectTestExtension() {
            this.observers = new ArrayList<>();
        }
    }

    private static class ObserverTestImplementation implements Observer {
        @Override
        public void update(Notification notification) {

        }
    }

    @Test
    public void testRegisterNullObserver() {
        GameController subject = new SubjectTestExtension();
        try {
            subject.registerObserver(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Cannot pass a null observer", e.getMessage());
        }

    }

    @Test
    public void testRegisterObserver() {
        GameController subject = new SubjectTestExtension();
        ObserverTestImplementation observer = EasyMock.mock(ObserverTestImplementation.class);
        Assert.assertEquals(0, subject.observers.size());
        subject.registerObserver(observer);
        Assert.assertEquals(1, subject.observers.size());
    }

    @Test
    public void testRemoveNonexistentObserver() {
        GameController subject = new SubjectTestExtension();
        try {
            subject.removeObserver(EasyMock.mock(ObserverTestImplementation.class));
            Assert.fail();
        } catch (IllegalStateException e) {
            Assert.assertEquals(
                    "Can't remove an observer when none are registered", e.getMessage());
        }
    }

    @Test
    public void testRemoveWrongObserver() {
        GameController subject = new SubjectTestExtension();
        subject.registerObserver(EasyMock.mock(ObserverTestImplementation.class));
        try {
            subject.removeObserver(EasyMock.mock(ObserverTestImplementation.class));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(
                    "This observer is not registered with this subject", e.getMessage());
            Assert.assertEquals(1, subject.observers.size());
        }
    }

    @Test
    public void testRemoveCorrectObserver() {
        GameController subject = new SubjectTestExtension();
        Observer observer = EasyMock.mock(ObserverTestImplementation.class);
        subject.registerObserver(observer);
        Assert.assertEquals(1, subject.observers.size());
        subject.removeObserver(observer);
        Assert.assertEquals(0, subject.observers.size());
    }

    @Test
    public void testNotifyObserver() {
        GameController subject = new SubjectTestExtension();
        Observer observer = EasyMock.mock(ObserverTestImplementation.class);
        subject.registerObserver(observer);
        Assert.assertEquals(1, subject.observers.size());
        subject.notifyObservers(new DrawNotification(0, null));
        EasyMock.expectLastCall();
        EasyMock.replay(observer);
    }

}
