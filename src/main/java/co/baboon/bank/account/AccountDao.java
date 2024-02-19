package co.baboon.bank.account;

import co.baboon.bank.jooq.tables.records.AccountsRecord;
import jakarta.annotation.Nonnull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static co.baboon.bank.jooq.Tables.ACCOUNTS;

public class AccountDao {
    private final DSLContext context;
    private final List<TableField<AccountsRecord, ?>> ACCOUNT_FIELDS = List.of(
            ACCOUNTS.ID,
            ACCOUNTS.MONEY,
            ACCOUNTS.CURRENCY
    );

    public AccountDao(DSLContext context) {
        this.context = context;
    }

    public Optional<Account> tryGetAccountById(Integer id) {
        return context
                .select(ACCOUNT_FIELDS)
                .from(ACCOUNTS)
                .where(ACCOUNTS.ID.eq(id))
                .fetchOptional(AccountDao::buildAccount);
    }
    
    public Optional<Account> createAccount(Integer ownerId, String currency) {
        return context
                .insertInto(ACCOUNTS)
                .set(ACCOUNTS.OWNER_ID, ownerId)
                .set(ACCOUNTS.CURRENCY, currency)
                .set(ACCOUNTS.MONEY, BigDecimal.ZERO)
                .returning()
                .fetchOptional(AccountDao::buildAccount);
    }

    private static Account buildAccount(@Nonnull Record record) {
        var id = record.get(ACCOUNTS.ID);
        var currency = CurrencyUnit.of(record.get(ACCOUNTS.CURRENCY));
        var money = Money.of(currency, record.get(ACCOUNTS.MONEY));
        var ownerId = record.get(ACCOUNTS.OWNER_ID);
        
        return Account.builder()
                .withId(id)
                .withMoney(money)
                .withOwnerId(ownerId)
                .build();
    }
}
