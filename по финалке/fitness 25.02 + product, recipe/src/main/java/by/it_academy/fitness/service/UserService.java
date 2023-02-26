package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.Page;
import by.it_academy.fitness.core.dto.User;
import by.it_academy.fitness.core.dto.UserCreate;
import by.it_academy.fitness.core.dto.UserRole;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.MyError;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.repositories.AdminRepository;
import by.it_academy.fitness.entity.UserCreateEntity;
import by.it_academy.fitness.service.api.IUserService;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserService implements IUserService {
    private static final String EMAIL_REGEX =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final AdminRepository repository;

    public UserService(AdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(UserCreate userCreate) {
        if(userCreate == null){
            throw new SingleErrorResponse("error", "Заполните форму для регистрации нового пользователя.");
        }
        validation(userCreate);

        LocalDateTime dtCreate = LocalDateTime.now();
        LocalDateTime dtUpdate = dtCreate;
        UserCreateEntity userCreateEntity = new UserCreateEntity(UUID.randomUUID(),
                userCreate.getFio(),
                dtCreate,
                dtUpdate,
                userCreate.getMail(),
                UserRole.USER,
                userCreate.getStatus(),
                userCreate.getPassword());
        repository.save(userCreateEntity);
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
    public Page <User> getPageUsers(int page, int size) {

        org.springframework.data.domain.Page<UserCreateEntity> userCreateEntityPage = repository.findAll(PageRequest.of(page, size));

        List<UserCreateEntity> userCreateEntity = userCreateEntityPage.toList();
        List <User> users = new ArrayList<>();

        for (UserCreateEntity createEntity : userCreateEntity) {
            users.add(new User(createEntity.getUuid(), createEntity.getDtCreate(), createEntity.getDtUpdate(),
                    createEntity.getMail(), createEntity.getFio(), createEntity.getRole(), createEntity.getStatus()));
        }

        Page<User> pageOfUser = new Page<>(userCreateEntityPage.getNumber(),
                userCreateEntityPage.getSize(),
                userCreateEntityPage.getTotalPages(),
                userCreateEntityPage.getTotalElements(),
                userCreateEntityPage.isFirst(),
                userCreateEntityPage.getNumberOfElements(),
                userCreateEntityPage.isLast(),
                users);
        return pageOfUser;
    }

    @Override
    public void updateUser(UUID uuid, long dtUpdate, UserCreate userCreate){
        if(uuid == null || userCreate == null){
            throw new SingleErrorResponse("error","Не переданы параметры для обновления");
        }
        validation(userCreate);

        if(repository.findById(uuid).isEmpty()){
            throw new SingleErrorResponse("error", "Такого пользователя для обновления не существует");
        }

        UserCreateEntity userCreateEntity = repository.findById(uuid).get();

        if(userCreateEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != (dtUpdate)){
            throw new SingleErrorResponse("error", "У вас не актуальная версия");
        }

        if(repository.findByMail(userCreate.getMail()).isPresent()
                && !repository.findByMail(userCreate.getMail()).get().getUuid().equals(uuid)){
            throw new SingleErrorResponse("error", "Указанный email уже используется другим пользователем.");
        }
        userCreateEntity.setMail(userCreate.getMail());
        userCreateEntity.setFio(userCreate.getFio());
        userCreateEntity.setRole(userCreate.getRole());
        userCreateEntity.setRole(userCreate.getRole());
        userCreateEntity.setStatus(userCreate.getStatus());
        userCreateEntity.setPassword(userCreate.getPassword());
        repository.save(userCreateEntity);
    }

    @Override
    public void validation(UserCreate userCreate) {
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(userCreate.getMail() == null || userCreate.getMail().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "mail"));
        }
        if(userCreate.getPassword() == null || userCreate.getPassword().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "password"));
        }
        if(userCreate.getFio() == null || userCreate.getFio().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "fio"));
        }
        if(userCreate.getRole() == null){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "role"));
        }
        if(userCreate.getStatus() == null){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "role"));
        }
     //  if(repository.findByMail(userCreate.getMail()).isPresent() && repository.findByMail(userCreate.getMail()).get().getUuid() != null){
     //      if(multipleError.getLogref() == null){
     //          multipleError.setLogref("Проверьте введенные данные!");
     //      }
     //      multipleError.setErrors(new MyError("Пользователь с таким email уже существует.", "mail"));
     //  }
       if(!EMAIL_PATTERN.matcher(userCreate.getMail()).matches()){
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
