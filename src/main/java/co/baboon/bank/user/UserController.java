package co.baboon.bank.user;

import co.baboon.bank.user.handlers.GetUserHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController
public class UserController {
    private final GetUserHandler getUserHandler;

    public UserController(GetUserHandler getUserHandler) {
        this.getUserHandler = getUserHandler;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        return getUserHandler.handle(id);
    }
}
