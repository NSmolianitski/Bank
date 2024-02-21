package co.baboon.bank.banks;

import co.baboon.bank.jooq.tables.records.BanksRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;
import java.util.Optional;

import static co.baboon.bank.jooq.Tables.BANKS;

public class BankDao {
    private final DSLContext context;
    
    private final List<TableField<BanksRecord, ?>> BANK_FIELDS = List.of(
            BANKS.ID,
            BANKS.CODE,
            BANKS.NAME
    );

    public BankDao(DSLContext context) {
        this.context = context;
    }
    
    public Optional<Bank> tryGetBankByCode(Integer code) {
        return context
                .select(BANK_FIELDS)
                .from(BANKS)
                .where(BANKS.CODE.eq(code))
                .fetchOptional(BankDao::buildBank);
    }
    
    private static Bank buildBank(Record record) {
        return Bank.builder()
                .withId(record.get(BANKS.ID))
                .withCode(record.get(BANKS.CODE))
                .withName(record.get(BANKS.NAME))
                .build();
    }
}
