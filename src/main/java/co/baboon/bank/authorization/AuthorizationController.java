package co.baboon.bank.authorization;

import co.baboon.bank.authorization.dto.LoginRequestDto;
import co.baboon.bank.authorization.dto.RegistrationRequestDto;
import co.baboon.bank.authorization.handlers.LoginHandler;
import co.baboon.bank.authorization.handlers.RegisterHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("auth")
@RestController
public class AuthorizationController {
    private final RegisterHandler registerHandler;
    private final LoginHandler loginHandler;

    public AuthorizationController(RegisterHandler registerHandler, LoginHandler loginHandler) {
        this.registerHandler = registerHandler;
        this.loginHandler = loginHandler;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto requestDto) {
        return registerHandler.handle(requestDto);
    }
    
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        return loginHandler.handle(requestDto);
    }
}
