package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.WithdrawDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import co.baboon.bank.utilities.MoneyUtility;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class WithdrawHandler {
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    public WithdrawHandler(AccountDao accountDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    public ResponseEntity<?> handle(WithdrawDto withdrawDto) {
        var optionalAccount = accountDao.tryGetAccountById(withdrawDto.accountId());
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found.");
        
        var account = optionalAccount.get();
        if (account.money().getAmount().compareTo(withdrawDto.money()) < 0)
            return ResponseEntity.ok("Not enough money.");
        
        var money = MoneyUtility.createMoney(withdrawDto.money(), withdrawDto.currency());
        accountDao.withdrawMoney(withdrawDto.accountId(), money);
        
        var transfer = Transfer.builder()
                .withOperationId(UUID.randomUUID())
                .withFromAccountId(withdrawDto.accountId())
                .withMoney(money)
                .withDateNow()
                .withOperationType(TransferOperationType.WITHDRAW)
                .build();
        
        transferDao.addTransfer(transfer);
        
        return ResponseEntity.ok("Success");
    }
}
