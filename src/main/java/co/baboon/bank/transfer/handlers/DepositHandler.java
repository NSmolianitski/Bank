package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.DepositDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import co.baboon.bank.utilities.MoneyUtility;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.UUID;

public class DepositHandler {
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    public DepositHandler(AccountDao accountDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }
    
    public ResponseEntity<?> handle(DepositDto depositDto) {
        var optionalAccount = accountDao.tryGetAccountById(depositDto.accountId());
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found.");
        var account = optionalAccount.get();
        
        var money = MoneyUtility.createMoney(depositDto.money(), depositDto.currency());
        
        var currencyEquals = account.money().getCurrencyUnit().equals(money.getCurrencyUnit());
        if (!currencyEquals)
            return ResponseEntity.ok("The account currency is different from the deposit currency.");
        
        try {
            accountDao.addMoney(depositDto.accountId(), money);
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        
        var transfer = Transfer.builder()
                .withOperationId(UUID.randomUUID())
                .withDateNow()
                .withMoney(money)
                .withToAccountId(depositDto.accountId())
                .withOperationType(TransferOperationType.DEPOSIT)
                .build();
        
        transferDao.addTransfer(transfer);
        return ResponseEntity.ok("Success");
    }
}
