package co.baboon.bank.transfer.handlers;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.banks.BankDao;
import co.baboon.bank.transfer.Transfer;
import co.baboon.bank.transfer.TransferDao;
import co.baboon.bank.transfer.dto.TransferFromBankDto;
import co.baboon.bank.transfer.enums.TransferOperationType;
import co.baboon.bank.utilities.BankUtility;
import co.baboon.bank.utilities.MoneyUtility;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class TransferFromBankHandler {
    private final AccountDao accountDao;
    private final TransferDao transferDao;
    private final BankDao bankDao;
    
    public TransferFromBankHandler(AccountDao accountDao, TransferDao transferDao, BankDao bankDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.bankDao = bankDao;
    }

    public ResponseEntity<?> handle(TransferFromBankDto transferFromBankDto) {
        var optionalAccount = accountDao.tryGetAccountById(transferFromBankDto.toAccountId());
        if (optionalAccount.isEmpty())
            return ResponseEntity.ok("Account not found: " + transferFromBankDto.toAccountId());
        var account = optionalAccount.get();

        var bankCode = BankUtility.getBankCodeFromAccountId(transferFromBankDto.fromAccountId());
        var optionalBank = bankDao.tryGetBankByCode(bankCode);
        if (optionalBank.isEmpty())
            return ResponseEntity.ok("Bank not found.");
        
        var money = MoneyUtility.createMoney(transferFromBankDto.sum(), transferFromBankDto.currency());
        
        accountDao.addMoney(account.id(), money);
                
        var transfer = Transfer.builder()
                        .withOperationId(UUID.randomUUID())
                        .withDateNow()
                        .withToAccountId(transferFromBankDto.toAccountId())
                        .withFromAccountId(transferFromBankDto.fromAccountId())
                        .withMoney(money)
                        .withOperationType(TransferOperationType.OUTER_TRANSFER_FROM_BANK)
                        .build();
        
        transferDao.addTransfer(transfer);
        
        return ResponseEntity.ok("Success");
    }
}
