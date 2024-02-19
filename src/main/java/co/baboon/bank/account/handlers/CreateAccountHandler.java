package co.baboon.bank.account.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.account.dto.AccountCreationDto;
import co.baboon.bank.utilities.JwtUtility;
import org.springframework.http.ResponseEntity;

public class CreateAccountHandler {
    private final AccountDao accountDao;
    private final JwtUtility jwtUtility;

    public CreateAccountHandler(AccountDao accountDao, JwtUtility jwtUtility) {
        this.accountDao = accountDao;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<?> handle(String authorization, AccountCreationDto accountCreationDto) {
        var jwt = jwtUtility.getJwtFromAuthorizationHeader(authorization);
        var userId = jwtUtility.getUserIdFromJwt(jwt);
        
        var account = accountDao.createAccount(userId, accountCreationDto.currency());
        return ResponseEntity.ok(account);
    }
}
