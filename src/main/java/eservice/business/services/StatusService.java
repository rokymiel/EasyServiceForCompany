package eservice.business.services;

public class StatusService {
    public String getNext(String status) {
        switch (status) {
            case "new":
                return "Принять";
            case "accepted":
                return "Начать работы";
            case "in_progress":
                return "Завершить работы";
        }
        return null;
    }

    public String getPrevious(String status) {
        if ("accepted".equals(status)) {
            return "Перевести в новую";
        }
        return null;
    }

    public String getCancellation(String status) {
        switch (status) {
            case "new":
                return "Отклонить";
            case "accepted":
            case "in_progress":
                return "Отменить";
        }
        return null;
    }

    public String getNextStatus(String status) {
        switch (status) {
            case "new":
                return "accepted";
            case "accepted":
                return "in_progress";
            case "in_progress":
                return "completed";
        }
        return null;
    }

    public String getPreviousStatus(String status) {
        if ("accepted".equals(status)) {
            return "new";
        }
        return null;
    }

    public String getCancellationStatus(String status) {
        switch (status) {
            case "new":
                return "denied";
            case "accepted":
            case "in_progress":
                return "canceled";
        }
        return null;
    }

    public String getCurrent(String status) {
        switch (status) {
            case "denied":
                return "Отклонена";
            case "new":
                return "Новая";
            case "accepted":
                return "Принята";
            case "canceled":
                return "Отменена";
            case "in_progress":
                return "Ведутся работы";
            case "completed":
                return "Работы завершены";
        }
        return null;
    }
}
//denied
//        new
//        accepted
//        canceled
//        in_progress
//        completed