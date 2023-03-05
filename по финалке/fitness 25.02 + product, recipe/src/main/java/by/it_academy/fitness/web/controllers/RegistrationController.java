package by.it_academy.fitness.web.controllers;


import by.it_academy.fitness.core.dto.User;
import by.it_academy.fitness.core.dto.UserLogin;
import by.it_academy.fitness.core.dto.UserRegistration;
import by.it_academy.fitness.service.api.IRegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class RegistrationController {
    private final IRegistrationService service;

    public RegistrationController(IRegistrationService service) {
            this.service = service;
    }

     @RequestMapping(path = "/registration", method = RequestMethod.POST)
     public ResponseEntity<?> addUser(@RequestBody UserRegistration user) {
         service.addUser(user);
         return ResponseEntity.status(HttpStatus.CREATED).build();
     }


     // поменять передачу параметра на query
     @RequestMapping(path = "/verification", method = RequestMethod.GET)
     public ResponseEntity<?> verification(@RequestParam("mail") String mail, @RequestParam("code") String code) {
         service.verification(mail, code);
         return ResponseEntity.status(HttpStatus.OK).build();
     }

     @RequestMapping(path = "/login", method = RequestMethod.POST)
     public ResponseEntity<?> loging(@RequestBody UserLogin userLog) {
        service.loging(userLog);
        return ResponseEntity.status(HttpStatus.OK).build();
     }

     @RequestMapping(path = "/me", method = RequestMethod.GET)
     public ResponseEntity<User> getCard(HttpSession session) {
         UUID uuid = (UUID) session.getAttribute("uuid");
         return ResponseEntity.status(HttpStatus.OK).body(service.getCard(uuid));
     }
}
