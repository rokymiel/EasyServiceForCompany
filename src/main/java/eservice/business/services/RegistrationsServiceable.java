package eservice.business.services;

import eservice.business.core.Registration;

import java.util.List;

public interface RegistrationsServiceable {
    List<UpdatableRegistration> getRegistrations(NotificationsListener<Registration> registrationListener);

    List<String> getRegistrations();

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

    /**
     * Обновляет данные в записи в автосервис
     *
     * @param registration запись
     */
    void update(Registration registration);

    /**
     * Переводит в статус "Закончено"
     *
     * @param registration запись
     */
    void finish(Registration registration);

//    List<Registration> getRegistrations();
}
