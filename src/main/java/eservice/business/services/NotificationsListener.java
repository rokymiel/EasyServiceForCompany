package eservice.business.services;

import eservice.business.core.Registration;

public interface NotificationsListener<T> {

    void add(T item);

    void modify(T item);

    void remove(T item);
}
