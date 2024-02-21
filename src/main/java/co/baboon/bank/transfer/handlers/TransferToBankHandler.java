package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.banks.BankDao;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.TransferToBankDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import co.baboon.bank.utilities.BankUtility;
import co.baboon.bank.utilities.MoneyUtility;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class TransferToBankHandler {
    private final AccountDao accountDao;
    private final TransferDao transferDao;
    private final BankDao bankDao;
    
    public TransferToBankHandler(AccountDao accountDao, TransferDao transferDao, BankDao bankDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.bankDao = bankDao;
    }

    public ResponseEntity<?> handle(TransferToBankDto transferToBankDto) {
        var optionalAccount = accountDao.tryGetAccountById(transferToBankDto.fromAccountId());
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found: " + transferToBankDto.fromAccountId());
        var account = optionalAccount.get();

        var bankCode = BankUtility.getBankCodeFromAccountId(transferToBankDto.toAccountId());
        var optionalBank = bankDao.tryGetBankByCode(bankCode);
        if (optionalBank.isEmpty())
            return ResponseEntity.ok("Bank not found.");

        var transferMoney = MoneyUtility.createMoney(transferToBankDto.sum(), transferToBankDto.currency());

        if (!accountDao.hasEnoughMoney(account.money(), transferMoney))
            return ResponseEntity.ok("Not enough money.");
        
        accountDao.withdrawMoney(account.id(), transferMoney);

        var transfer = Transfer.builder()
                .withOperationId(UUID.randomUUID())
                .withDateNow()
                .withToAccountId(transferToBankDto.toAccountId())
                .withFromAccountId(transferToBankDto.fromAccountId())
                .withMoney(transferMoney)
                .withOperationType(TransferOperationType.OUTER_TRANSFER_TO_BANK)
                .build();

        transferDao.addTransfer(transfer);

        return ResponseEntity.ok("Success");
    }
}
