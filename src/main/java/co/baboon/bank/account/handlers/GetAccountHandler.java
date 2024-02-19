package co.baboon.bank.account.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.utilities.JwtUtility;
import org.springframework.http.ResponseEntity;

public class GetAccountHandler {
    private final AccountDao accountDao;
    private final JwtUtility jwtUtility;

    public GetAccountHandler(AccountDao accountDao, JwtUtility jwtUtility) {
        this.accountDao = accountDao;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<?> handle(String authorization, Integer accountId) {
        var jwt = jwtUtility.getJwtFromAuthorizationHeader(authorization);
        var userId = jwtUtility.getUserIdFromJwt(jwt);
        
        if (!accountDao.isOwnerOfAccount(userId, accountId))
            return ResponseEntity.ok("Account not found.");
        
        var optionalAccount = accountDao.tryGetAccountById(accountId);
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found.");
        
        var account = optionalAccount.get();
        return ResponseEntity.ok(account);
    }
}
