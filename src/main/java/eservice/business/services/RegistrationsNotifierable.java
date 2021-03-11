package eservice.business.services;

import eservice.business.core.Registration;

public interface RegistrationsNotifierable {
    /**
     * Добавляет слушателя на событие получения новой записи в автосервис
     *
     * @param listener слушатель
     */
    void addListener(NotificationsListener listener);

}
