package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.MyError;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.repositories.RegistrationRepository;
import by.it_academy.fitness.entity.UserCreateEntity;
import by.it_academy.fitness.service.api.IRegistrationService;
import by.it_academy.fitness.service.api.IUserService;
import by.it_academy.fitness.service.validators.api.IValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class RegistrationService implements IRegistrationService {

    @Value("${spring.data.redis.url}")
    private static String URL;
    private static final String EMAIL_REGEX =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private final RegistrationRepository registrationRepository;
    private final IUserService userService;
    private final MailSender mailSender;
    private final ConversionService conversionService;
    private final IValidator <UserRegistration> validator;

    public RegistrationService(RegistrationRepository registrationRepository, IUserService userService,
                               MailSender mailSender, ConversionService conversionService, IValidator <UserRegistration> validator) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.mailSender = mailSender;
        this.conversionService = conversionService;
        this.validator = validator;
    }

    @Override
    public void addUser(UserRegistration userRegistration) {
        if(userRegistration == null){
            throw new SingleErrorResponse("Заполните форму для регистрации нового пользователя.");
        }
        if(registrationRepository.findByMail(userRegistration.getMail()).isPresent()){
            throw new SingleErrorResponse("Пользователь с таким email уже существует.");
        }

        validator.validation(userRegistration);

        LocalDateTime dtCreate = LocalDateTime.now();
        UserCreateEntity userCreateEntity = conversionService.convert(userRegistration, UserCreateEntity.class);
        userCreateEntity.setDtCreate(dtCreate);
        userCreateEntity.setDtUpdate(dtCreate);

        registrationRepository.save(userCreateEntity);

        String message = "Здравствуйте, " + userCreateEntity.getFio() + "! \n" +
                "Добро пожаловать на наш сайт. Пожалуйста, перейдите по ссылки для активации аккаунта " + URL +
                "?code=" + userCreateEntity.getUuid()+ "&mail=" + userCreateEntity.getMail();

        mailSender.send(userCreateEntity.getMail(), "Активация аккаунта", message);
    }

    @Override
    public void verification(String email, String code) {
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(email == null ||email.isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("structured_error");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(code == null ||code.isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("structured_error");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "code"));
        }

        Optional<UserCreateEntity> optionalUserCreateEntityEmail = registrationRepository.findByMail(email);

        if(optionalUserCreateEntityEmail.isEmpty()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("structured_error");
            }
            multipleError.setErrors(new MyError("Пользователя с указанным email не найдено.", "mail"));
        }

        UserCreateEntity userCreateEntity = optionalUserCreateEntityEmail.get();
        String userInfo = userCreateEntity.getMail() + userCreateEntity.getUuid();
        String reqInfo = email + code;

        if(!userInfo.equals(reqInfo)){
            throw new SingleErrorResponse("error", "Указаны не верные данные для верификации.");
        }

        if(userCreateEntity.getStatus().equals(UserStatus.ACTIVATED)){
            throw new SingleErrorResponse("Ваш аккаунт уже активирован.");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.DEACTIVATED)){
            throw new SingleErrorResponse("Ваш аккаунт заблокирован, обратитесь к администратору.");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.WAITING_ACTIVATION)){
            userCreateEntity.setDtUpdate(LocalDateTime.now());
            userCreateEntity.setStatus(UserStatus.ACTIVATED);
            registrationRepository.save(userCreateEntity);
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
                multipleError.setLogref("structured_error");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(userLogin.getPassword() == null || userLogin.getPassword().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("structured_error");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "password"));
        }

        Optional<UserCreateEntity> optionalUserCreateEntityMail = registrationRepository.findByMail(userLogin.getMail());

        if(optionalUserCreateEntityMail.isEmpty()){
            throw new SingleErrorResponse("Пользователя с email " + userLogin.getMail() + " не существует");
        }
        UserCreateEntity userCreateEntity = optionalUserCreateEntityMail.get();

        if(userCreateEntity.getStatus().equals(UserStatus.WAITING_ACTIVATION)){
            throw new SingleErrorResponse("Ваш аккаунт не активирован! Пройдите верификацию!");
        }
        if(userCreateEntity.getStatus().equals(UserStatus.DEACTIVATED)){
            throw new SingleErrorResponse("Ваш аккаунт заблокирован, обратитесь к администратору!");
        }
        if(!userCreateEntity.getPassword().equals(userLogin.getPassword())){
            throw new SingleErrorResponse("Неверно указан пароль");
        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }

    @Override
    public User getCard(UUID uuid) {
       return userService.getCard(uuid);
    }
}
