package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.MyError;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.repositories.UserRepository;
import by.it_academy.fitness.entity.UserCreateEntity;
import by.it_academy.fitness.service.api.IRegistrationService;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class RegistrationService implements IRegistrationService {
    private static final String EMAIL_REGEX =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final UserRepository repository;

    private MailSender mailSender;

    public RegistrationService(UserRepository repository, MailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    @Override
    public void addUser(UserRegistration userRegistration, String url) {
        if(userRegistration == null){
            throw new SingleErrorResponse("error", "Заполните форму для регистрации нового пользователя.");
        }
        validation(userRegistration);

        LocalDateTime dt_create = LocalDateTime.now();
        LocalDateTime dt_update = dt_create;
        UserCreateEntity userCreateEntity = new UserCreateEntity(UUID.randomUUID(),
                userRegistration.getFio(),
                dt_create, dt_update,
                userRegistration.getMail(),
                UserRole.USER,
                UserStatus.WAITING_ACTIVATION,
                userRegistration.getPassword());

        repository.save(userCreateEntity);

        String message = "Здравствуйте, " + userCreateEntity.getFio() + "! \n" +
                "Добро пожаловать на наш сайт. Пожалуйста, перейдите по ссылки для активации аккаунта " + url +
                "/verification?code=" +
                userCreateEntity.getUuid()+ "&mail=" + userCreateEntity.getMail();

        mailSender.send(userCreateEntity.getMail(), "Активация аккаунта", message);
    }

    @Override
    public void verification(String email, String code) {
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(email == null ||email.isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(code == null ||code.isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "code"));
        }
        if(email == null ||email.isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Пользователя с указанным email не найдено.", "mail"));
        }

        UserCreateEntity userCreateEntity = repository.findByMail(email).get();
        String userInfo = userCreateEntity.getMail() + userCreateEntity.getUuid();
        String reqInfo = email + code;

        if(!userInfo.equals(reqInfo)){
            throw new SingleErrorResponse("error", "Указаны не верные данные для верификации.");
        }

        if(userCreateEntity.getStatus().equals(UserStatus.ACTIVATED)){
            throw new SingleErrorResponse("error","Ваш аккаунт уже активирован.");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.DEACTIVATED)){
            throw new SingleErrorResponse("error","Ваш аккаунт заблокирован, обратитесь к администратору.");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.WAITING_ACTIVATION)){
            userCreateEntity.setStatus(UserStatus.ACTIVATED);
            repository.save(userCreateEntity);
        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }

    @Override
    public void loging(UserLogin userLogin) {
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(userLogin.getMail() == null || userLogin.getMail().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(userLogin.getPassword() == null || userLogin.getPassword().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "password"));
        }
        if(repository.findByMail(userLogin.getMail()).isEmpty()){
            throw new SingleErrorResponse("error","Пользователя с email " + userLogin.getMail() + " не существует");
        }
        UserCreateEntity userCreateEntity = repository.findByMail(userLogin.getMail()).get();

        if(userCreateEntity.getStatus().equals(UserStatus.WAITING_ACTIVATION)){
            throw new SingleErrorResponse("error", "Ваш аккаунт не активирован! Пройдите верификацию!");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.DEACTIVATED)){
            throw new SingleErrorResponse("error","Ваш аккаунт заблокирован, обратитесь к администратору!");
        }
        if(!userCreateEntity.getPassword().equals(userLogin.getPassword())){
            throw new SingleErrorResponse("error","Неверно указан пароль");
        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }

    @Override
    public User getCard(UUID uuid) {
        if (repository.findById(uuid).isEmpty()) {
            throw new SingleErrorResponse("error", "Пользователя с указанным uuid не найдено.");
        }
        UserCreateEntity userCreateEntity = repository.findById(uuid).get();
        User user = new User(userCreateEntity.getUuid(), userCreateEntity.getDtCreate(), userCreateEntity.getDtUpdate(),
                userCreateEntity.getMail(), userCreateEntity.getFio(), userCreateEntity.getRole(), userCreateEntity.getStatus());
        return user;
    }

    @Override
    public void validation(UserRegistration userRegistration) {
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(userRegistration.getMail() == null || userRegistration.getMail().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(userRegistration.getPassword() == null || userRegistration.getPassword().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "password"));
        }
        if(userRegistration.getFio() == null || userRegistration.getFio().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "fio"));
        }
        if(repository.findByMail(userRegistration.getMail()).isPresent()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Пользователь с таким email уже существует.", "mail"));
        }
        if(!EMAIL_PATTERN.matcher(userRegistration.getMail()).matches()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Проверьте корректность введенной почты.", "mail"));
        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }
}
