package co.baboon.bank.transfer;

import co.baboon.bank.jooq.tables.records.TransfersRecord;
import co.baboon.bank.transfer.enums.TransferOperationType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static co.baboon.bank.jooq.Tables.TRANSFERS;

public class TransferDao {
    private final DSLContext context;
    
    private final List<TableField<TransfersRecord, ?>> TRANSFER_FIELDS = List.of(
            TRANSFERS.ID,
            TRANSFERS.OPERATION_ID,
            TRANSFERS.FROM_ACCOUNT_ID,
            TRANSFERS.TO_ACCOUNT_ID,
            TRANSFERS.MONEY,
            TRANSFERS.CURRENCY,
            TRANSFERS.OPERATION_TYPE
    );

    public TransferDao(DSLContext context) {
        this.context = context;
    }
    
    public Optional<Transfer> tryGetTransferByOperationId(UUID operationId) {
        return context
                .select(TRANSFER_FIELDS)
                .from(TRANSFERS)
                .where(TRANSFERS.OPERATION_ID.eq(operationId))
                .fetchOptional(TransferDao::buildTransfer);
    }
    
    private static Transfer buildTransfer(Record record) {
        var currency = CurrencyUnit.of(record.get(TRANSFERS.CURRENCY));
        var money = Money.of(currency, record.get(TRANSFERS.MONEY));
        var operationType = TransferOperationType.valueOf(record.get(TRANSFERS.OPERATION_TYPE));
        return Transfer.builder()
                .withId(record.get(TRANSFERS.ID))
                .withOperationId(record.get(TRANSFERS.OPERATION_ID))
                .withFromAccountId(record.get(TRANSFERS.FROM_ACCOUNT_ID))
                .withToAccountId(record.get(TRANSFERS.TO_ACCOUNT_ID))
                .withMoney(money)
                .withOperationType(operationType)
                .build();
    }
}
