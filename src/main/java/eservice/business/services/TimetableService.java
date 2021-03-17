package eservice.business.services;

import com.google.cloud.Timestamp;
import eservice.business.core.Registration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimetableService implements TimetableServiceable {

    private final Map<String, Registration> registrationMap = new HashMap<>();

    public TimetableService(RegistrationsNotifierable notifier) {
        notifier.addListener(new NotificationsListener() {
            @Override
            public void add(Registration registration) {
                registrationMap.put(registration.getId(), registration);
            }

            @Override
            public void modify(Registration registration) {
                registrationMap.put(registration.getId(), registration);
            }

            @Override
            public void remove(Registration registration) {
                registrationMap.remove(registration.getId());
            }
        });
    }

    @Override
    public List<Registration> getRegistrationsByDate(Timestamp date) {
        return registrationMap.values()
                .stream()
                .filter(x -> getDays(x.getDateOfRegistration()).equals(getDays(date)))
                .collect(Collectors.toList());
    }

    private Instant getDays(Timestamp timestamp) {
        return timestamp.toDate().toInstant().truncatedTo(ChronoUnit.DAYS);
    }
}
