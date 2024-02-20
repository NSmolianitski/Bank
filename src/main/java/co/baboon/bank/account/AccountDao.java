package co.baboon.bank.account;

import co.baboon.bank.account.exceptions.NotEnoughMoneyException;
import co.baboon.bank.jooq.tables.records.AccountsRecord;
import co.baboon.bank.utilities.MoneyUtility;
import jakarta.annotation.Nonnull;
import org.joda.money.Money;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static co.baboon.bank.jooq.Tables.ACCOUNTS;

public class AccountDao {
    private final DSLContext context;
    private final List<TableField<AccountsRecord, ?>> ACCOUNT_FIELDS = List.of(
            ACCOUNTS.ID,
            ACCOUNTS.MONEY,
            ACCOUNTS.CURRENCY,
            ACCOUNTS.OWNER_ID
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
    
    public Boolean isOwnerOfAccount(Integer userId, Integer accountId) {
        return context
                .select(ACCOUNT_FIELDS)
                .from(ACCOUNTS)
                .where(ACCOUNTS.OWNER_ID.eq(userId).and(ACCOUNTS.ID.eq(accountId)))
                .fetch().isNotEmpty();
    }
    
    public void addMoney(Integer accountId, Money money) {
        var optionalMoney = getMoney(accountId);
        if (optionalMoney.isEmpty())
            throw new NoSuchElementException("Account not found: " + accountId);
        
        var newMoney = optionalMoney.get().getAmount().add(money.getAmount());
        context.update(ACCOUNTS)
                .set(ACCOUNTS.CURRENCY, money.getCurrencyUnit().toString())
                .set(ACCOUNTS.MONEY, newMoney)
                .where(ACCOUNTS.ID.eq(accountId))
                .execute();
    }
    
    public void withdrawMoney(Integer accountId, Money withdrawMoney) {
        var optionalMoney = getMoney(accountId);
        if (optionalMoney.isEmpty())
            throw new NoSuchElementException("Account not found: " + accountId);

        var moneyFromDb = optionalMoney.get();
        if (!hasEnoughMoney(moneyFromDb, withdrawMoney))
            throw new NotEnoughMoneyException("Not enough money on account: " + accountId);

        var newMoney = moneyFromDb.getAmount().subtract(withdrawMoney.getAmount());
        context.update(ACCOUNTS)
                .set(ACCOUNTS.CURRENCY, withdrawMoney.getCurrencyUnit().toString())
                .set(ACCOUNTS.MONEY, newMoney)
                .where(ACCOUNTS.ID.eq(accountId))
                .execute();
    }
    
    public Boolean hasEnoughMoney(Money accountMoney, Money withdrawMoney) {
        return accountMoney.getAmount().compareTo(withdrawMoney.getAmount()) >= 0;
    }
    
    public Optional<Money> getMoney(Integer accountId) {
        var optionalAccount = tryGetAccountById(accountId);
        return optionalAccount.map(Account::money);
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
        var money = MoneyUtility.createMoney(record.get(ACCOUNTS.MONEY), record.get(ACCOUNTS.CURRENCY));
        var ownerId = record.get(ACCOUNTS.OWNER_ID);
        
        return Account.builder()
                .withId(id)
                .withMoney(money)
                .withOwnerId(ownerId)
                .build();
    }
}
