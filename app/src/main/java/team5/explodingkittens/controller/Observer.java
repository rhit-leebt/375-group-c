package team5.explodingkittens.controller;

import team5.explodingkittens.controller.notification.Notification;

/**
 * An interface for each type of object that can observe
 * a {@link GameController}, and receive notifications from it.
 *
 * @author Duncan McKee, Maura Coriale
 */
public interface Observer {

    void update(Notification notification);

}
