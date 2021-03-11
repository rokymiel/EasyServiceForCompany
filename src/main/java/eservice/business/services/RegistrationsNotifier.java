package eservice.business.services;

import eservice.business.core.Registration;

import java.util.List;

public class RegistrationsNotifier implements RegistrationsNotifierable {
    private List<NotificationsListener> listeners;
    private RegistrationsServiceable registrationsService;

    // TODO Добавить код получения обновлений от Firestore


    public void addListener(NotificationsListener listener) {
        listeners.add(listener);
    }

    public void accept(Registration registration) {
        registrationsService.accept(registration);
    }

    public void deny(Registration registration) {
        registrationsService.deny(registration);
    }
}
