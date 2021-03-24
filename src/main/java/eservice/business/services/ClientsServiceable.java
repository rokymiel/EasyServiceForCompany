package eservice.business.services;

import eservice.business.core.Car;
import eservice.business.core.Client;

import java.util.List;

public interface ClientsServiceable {
    /**
     * Возвращает всех клиентов
     *
     * @return все клиенты
     */
    List<UpdatableClient> getClients(NotificationsListener<Client> clientListener, NotificationsListener<Car> carListener);

    List<String> getClientIds();

    /**
     * Удаляет клиента по Id
     *
     * @param id идентефикатор клиента
     * @return удалось ли удалить
     */
    @Deprecated
    boolean removeClientById(String id);
}
