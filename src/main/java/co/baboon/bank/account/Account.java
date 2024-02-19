package co.baboon.bank.account;

import jakarta.annotation.Nonnull;
import org.joda.money.Money;

public class Account {
    private final Integer id;
    @Nonnull
    private final Money money;
    @Nonnull
    private final Integer ownerId;

    public Account(Integer id, @Nonnull Money money, @Nonnull Integer ownerId) {
        this.id = id;
        this.money = money;
        this.ownerId = ownerId;
    }

    public Integer getId() { return id; }
    @Nonnull
    public Money getMoney() { return money; }
    @Nonnull
    public Integer getOwnerId() { return ownerId; }
    
    public static Builder builder() { return new Builder(); }
    
    public static class Builder {
        private Integer id;
        private Money money;
        private Integer ownerId;
        
        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        
        public Builder withMoney(Money money) {
            this.money = money;
            return this;
        }
        
        public Builder withOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
            return this;
        }
        
        public Builder copyFrom(Account account) {
            this.id = account.id;
            this.money = account.money;
            return this;
        }
        
        public Account build() { return new Account(id, money, ownerId); }
    }
}
