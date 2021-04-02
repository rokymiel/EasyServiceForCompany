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