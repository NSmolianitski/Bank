package co.baboon.bank.transfer;

import co.baboon.bank.transfer.enums.TransferOperationType;
import jakarta.annotation.Nonnull;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public record Transfer(
        Integer id,
        @Nonnull UUID operationId,
        @Nonnull LocalDateTime date,
        Integer fromAccountId,
        Integer toAccountId,
        @Nonnull Money money,
        @Nonnull TransferOperationType operationType
) {
    public Transfer(Integer id,
                    @Nonnull UUID operationId,
                    @Nonnull LocalDateTime date,
                    @Nonnull Integer fromAccountId,
                    @Nonnull Integer toAccountId,
                    @Nonnull Money money,
                    @Nonnull TransferOperationType operationType) {
        this.id = id;
        this.date = date;
        this.operationId = operationId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.money = money;
        this.operationType = operationType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private UUID operationId;
        private LocalDateTime date;
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

        public Builder withDateNow() {
            this.date = LocalDateTime.now();
            return this;
        }
        
        public Builder withDate(LocalDateTime date) {
            this.date = date;
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
            this.date = transfer.date;
            this.operationId = transfer.operationId;
            this.fromAccountId = transfer.fromAccountId;
            this.toAccountId = transfer.toAccountId;
            this.money = transfer.money;
            this.operationType = transfer.operationType;
            return this;
        }

        public Transfer build() {
            return new Transfer(id, operationId, date, fromAccountId, toAccountId, money, operationType);
        }
    }
}
