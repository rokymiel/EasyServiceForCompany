package eservice.business.services;

import com.google.cloud.Timestamp;
import eservice.business.core.Registration;


import java.util.List;

public interface TimetableServiceable {
    /**
     * Возвращает список записей в автосервис для переданной даты
     *
     * @param date дата записи
     * @return список записей
     */
    List<Registration> getRegistrationsByDate(Timestamp date);
}
