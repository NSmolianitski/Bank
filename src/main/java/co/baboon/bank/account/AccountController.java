package co.baboon.bank.account;

import co.baboon.bank.account.dto.AccountCreationDto;
import co.baboon.bank.account.handlers.CreateAccountHandler;
import co.baboon.bank.account.handlers.GetAccountHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("accounts")
@RestController
public class AccountController {
    private final GetAccountHandler getAccountHandler;
    private final CreateAccountHandler createAccountHandler;

    public AccountController(GetAccountHandler getAccountHandler, CreateAccountHandler createAccountHandler) {
        this.getAccountHandler = getAccountHandler;
        this.createAccountHandler = createAccountHandler;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                        @PathVariable Integer id) {
        return getAccountHandler.handle(authorization, id);
    }
    
    @PostMapping("create")
    public ResponseEntity<?> createAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                           @RequestBody AccountCreationDto accountCreationDto) {
        return createAccountHandler.handle(authorization, accountCreationDto);
    }
}
