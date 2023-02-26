package by.it_academy.fitness.web.controllers;


import by.it_academy.fitness.core.dto.Page;
import by.it_academy.fitness.core.dto.User;
import by.it_academy.fitness.core.dto.UserCreate;
import by.it_academy.fitness.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
            this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody UserCreate userCreate){
        service.add(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page> getPageUsers(@RequestParam (name = "page", defaultValue = "0") Integer page,
                                             @RequestParam (name = "size", defaultValue = "20") Integer size){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPageUsers(page, size));
    }
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<User> getCard(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCard(uuid));
    }

    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable ("uuid") UUID uuid,
                                   @PathVariable ("dt_update") Long dt_update,
                                   @RequestBody UserCreate userCreate){
        service.updateUser(uuid, dt_update, userCreate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
