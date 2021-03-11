package eservice.business.services;

import eservice.business.core.Registration;

public interface RegistrationsNotifierable {
    /**
     * Добавляет слушателя на событие получения новой записи в автосервис
     *
     * @param listener слушатель
     */
    void addListener(NotificationsListener listener);

    /**
     * Одобрение записи в автосервис
     *
     * @param registration запись
     */
    void accept(Registration registration);

    /**
     * Отклонение записи в автосервис
     *
     * @param registration запись
     */
    void deny(Registration registration);
}
