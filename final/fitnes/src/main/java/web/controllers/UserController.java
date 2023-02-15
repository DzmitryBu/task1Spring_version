package web.controllers;

import dto.UserLite;
import dto.UserLogDTO;
import org.springframework.web.bind.annotation.*;
import service.api.IUserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    public class GenresController {
        private final IUserService service;

        public GenresController(IUserService service) {
            this.service = service;
        }

        @RequestMapping(path = "/registration", method = RequestMethod.POST)
        public void addUser(@RequestBody UserLite user) {
            return service.add();
        }

        @RequestMapping(path = "/verification/{code}", method = RequestMethod.GET)
        public void getCard(@PathVariable("code") String code) {
            service.verification(code);
        }

        @RequestMapping(path = "/login", method = RequestMethod.POST)
        public void addUser(@RequestBody UserLogDTO userLog) {
            return service.loging();
        }

        @RequestMapping(path = "/me", method = RequestMethod.GET)
        public void getCard() {
            service.getCard();
        }
    }
}
