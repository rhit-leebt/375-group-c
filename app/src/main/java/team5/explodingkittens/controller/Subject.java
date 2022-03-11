package team5.explodingkittens.controller;

import java.util.ArrayList;
import java.util.List;
import team5.explodingkittens.controller.notification.Notification;

/**
 * An abstract class that is used for each object
 * that can be registered to by an {@link Observer}.
 *
 * @author Maura Coriale, Duncan McKee
 */
public abstract class Subject {
    protected List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    /**
     * Registers an observer to this subject.
     *
     * @param observer The observer that is going to be registered.
     */
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Cannot pass a null observer");
        }
        observers.add(observer);
    }

    /**
     * Removes an observer from being registered to this subject.
     *
     * @param observer The observer that is going to be removed.
     */
    public void removeObserver(Observer observer) {
        if (observers.size() < 1) {
            throw new IllegalStateException("Can't remove an observer when none are registered");
        }
        if (!observers.contains(observer)) {
            throw new IllegalArgumentException("This observer is not registered with this subject");
        }
        observers.remove(observer);
    }

    /**
     * Notifies every registered observer with a notification.
     *
     * @param notification The notification to distribute.
     */
    public void notifyObservers(Notification notification) {
        for (Observer observer : this.observers) {
            observer.update(notification);
        }
    }
}
