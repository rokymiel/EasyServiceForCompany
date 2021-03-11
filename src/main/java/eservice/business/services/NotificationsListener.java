package eservice.business.services;

import eservice.business.core.Registration;

public interface NotificationsListener {
    /**
     * Отправляет уведомление о полученной записи
     *
     * @param registration запись в ывтосервис
     */
    void add(Registration registration);

    void modify(Registration registration);

    void remove(Registration registration);
}
