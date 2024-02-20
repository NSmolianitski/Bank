package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.DepositDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
        
        var currency = CurrencyUnit.of(depositDto.currency());
        var money = Money.of(currency, depositDto.money());
        
        var currencyEquals = account.money().getCurrencyUnit().equals(currency);
        if (!currencyEquals)
            return ResponseEntity.ok("The account currency is different from the deposit currency.");
        
        if (!accountDao.addMoney(depositDto.accountId(), money))
            return ResponseEntity.ok("Operation failed.");
        
        var transfer = Transfer.builder()
                .withOperationId(UUID.randomUUID())
                .withDate(LocalDateTime.now())
                .withMoney(money)
                .withToAccountId(depositDto.accountId())
                .withOperationType(TransferOperationType.DEPOSIT)
                .build();
        
        transferDao.addTransfer(transfer);
        return ResponseEntity.ok("Success");
    }
}
