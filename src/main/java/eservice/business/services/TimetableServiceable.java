package eservice.business.services;

import eservice.business.core.Registration;

import java.util.Date;
import java.util.List;

public interface TimetableServiceable {
    /**
     * Возвращает список записей в автосервис для переданной даты
     *
     * @param date дата записи
     * @return список записей
     */
    List<Registration> getRegistrationsByDate(Date date);
}
