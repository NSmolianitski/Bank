package co.baboon.bank.transfer;

import co.baboon.bank.transfer.enums.TransferOperationType;
import jakarta.annotation.Nonnull;
import org.joda.money.Money;

import java.util.UUID;

public class Transfer {
    private final Integer id;
    @Nonnull
    private final UUID operationId;
    private final Integer fromAccountId;
    private final Integer toAccountId;
    @Nonnull
    private final Money money;
    @Nonnull
    private final TransferOperationType operationType;

    public Transfer(Integer id, @Nonnull UUID operationId, @Nonnull Integer fromAccountId, @Nonnull Integer toAccountId, @Nonnull Money money, @Nonnull TransferOperationType operationType) {
        this.id = id;
        this.operationId = operationId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.money = money;
        this.operationType = operationType;
    }

    public Integer getId() { return id; }
    @Nonnull
    public UUID getOperationId() { return operationId; }
    @Nonnull
    public Money getMoney() { return money; }
    @Nonnull
    public Integer getToAccountId() { return toAccountId; }
    @Nonnull
    public Integer getFromAccountId() { return fromAccountId; }
    @Nonnull
    public TransferOperationType getOperationType() { return operationType; }
    
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Integer id;
        private UUID operationId;
        private Integer fromAccountId;
        private Integer toAccountId;
        private Money money;
        private TransferOperationType operationType;
        
        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        
        public Builder withOperationId(UUID operationId) {
            this.operationId = operationId;
            return this;
        }
        
        public Builder withFromAccountId(Integer fromAccountId) {
            this.fromAccountId = fromAccountId;
            return this;
        }
        
        public Builder withToAccountId(Integer toAccountId) {
            this.toAccountId = toAccountId;
            return this;
        }
        
        public Builder withMoney(Money money) {
            this.money = money;
            return this;
        }
        
        public Builder withOperationType(TransferOperationType operationType) {
            this.operationType = operationType;
            return this;
        }
        
        public Builder copyFrom(Transfer transfer) {
            this.id = transfer.id;
            this.operationId = transfer.operationId;
            this.fromAccountId = transfer.fromAccountId;
            this.toAccountId = transfer.toAccountId;
            this.money = transfer.money;
            this.operationType = transfer.operationType;
            return this;
        }
        
        public Transfer build() { return new Transfer(id, operationId, fromAccountId, toAccountId, money, operationType); }
    }
}
