package by.it_academy.fitness.service.api;

public interface IMailService {
    void send(String emailTo, String subject, String message);
}
