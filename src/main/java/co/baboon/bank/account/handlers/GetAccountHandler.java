package co.baboon.bank.account.handlers;

import co.baboon.bank.account.AccountDao;
import org.springframework.http.ResponseEntity;

public class GetAccountHandler {
    private final AccountDao accountDao;

    public GetAccountHandler(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ResponseEntity<?> handle(Integer id) {
        var optionalAccount = accountDao.tryGetAccountById(id);
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found.");
        
        var account = optionalAccount.get();
        return ResponseEntity.ok(account);
    }
}
