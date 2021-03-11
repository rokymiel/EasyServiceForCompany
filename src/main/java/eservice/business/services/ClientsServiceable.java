package eservice.business.services;

import eservice.business.core.Client;

import java.util.List;

public interface ClientsServiceable {
    /**
     * Возвращает всех клиентов
     *
     * @return все клиенты
     */
    List<Client> getClients();

    /**
     * Удаляет клиента по Id
     *
     * @param id идентефикатор клиента
     * @return удалось ли удалить
     */
    boolean removeClientById(String id);
}
