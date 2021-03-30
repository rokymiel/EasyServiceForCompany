package eservice.business.services;

import eservice.business.core.Registration;

public interface NotificationsListener<T> {

    default void add(T item) {
    }

    default void modify(T item) {
    }

    default void remove(T item) {
    }

    default void add(Object sender, T item) {
    }

    default void modify(Object sender, T item) {
    }

    default void remove(Object sender, T item) {
    }
}
