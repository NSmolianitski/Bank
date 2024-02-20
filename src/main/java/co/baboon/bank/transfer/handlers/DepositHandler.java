package co.baboon.bank.transfer.handlers;

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
    private final TransferDao transferDao;

    public DepositHandler(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
    
    public ResponseEntity<?> handle(DepositDto depositDto) {
        var money = Money.of(CurrencyUnit.of(depositDto.currency()), depositDto.money());
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
