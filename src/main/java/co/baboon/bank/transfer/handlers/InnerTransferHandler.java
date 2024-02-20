package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.account.exceptions.NotEnoughMoneyException;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.InnerTransferDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import co.baboon.bank.utilities.MoneyUtility;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.UUID;

public class InnerTransferHandler {
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    public InnerTransferHandler(AccountDao accountDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    public ResponseEntity<?> handle(InnerTransferDto transferDto) {
        var fromOptionalAccount = accountDao.tryGetAccountById(transferDto.fromAccountId());
        if (fromOptionalAccount.isEmpty())
            return ResponseEntity.ok("FromAccount not found");
        var fromAccount = fromOptionalAccount.get();
        
        var toOptionalAccount = accountDao.tryGetAccountById(transferDto.toAccountId());
        if (toOptionalAccount.isEmpty())
            return ResponseEntity.ok("ToAccount not found.");
        var toAccount = toOptionalAccount.get();
        
        var currencyEquals = fromAccount.money().getCurrencyUnit()
                .equals(toAccount.money().getCurrencyUnit()); 
        if (!currencyEquals)
            return ResponseEntity.ok("The FromAccount currency is different from the ToAccount currency.");
        
        var transferMoney = MoneyUtility.createMoney(transferDto.money(), transferDto.currency());
        if (!accountDao.hasEnoughMoney(fromAccount.money(), transferMoney))
            return ResponseEntity.ok("Not enough money");
        
        accountDao.withdrawMoney(fromAccount.id(), transferMoney);
        accountDao.addMoney(toAccount.id(), transferMoney);
        
        var transfer = Transfer.builder()
                .withOperationId(UUID.randomUUID())
                .withDateNow()
                .withFromAccountId(fromAccount.id())
                .withToAccountId(toAccount.id())
                .withMoney(transferMoney)
                .withOperationType(TransferOperationType.INNER_TRANSFER)
                .build();
        transferDao.addTransfer(transfer);

        return ResponseEntity.ok("Success");
    }
}
