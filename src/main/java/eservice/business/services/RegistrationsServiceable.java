package eservice.business.services;

import eservice.business.core.Registration;

public interface RegistrationsServiceable {
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
     * @param registration запись
     */
    void finish(Registration registration);
}
