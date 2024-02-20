package co.baboon.bank.transfer;

import co.baboon.bank.account.AccountDao;
import co.baboon.bank.transfer.handlers.DepositHandler;
import co.baboon.bank.transfer.handlers.GetTransferHandler;
import co.baboon.bank.transfer.handlers.WithdrawHandler;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferConfiguration {
    @Bean
    public WithdrawHandler withdrawHandler(AccountDao accountDao, TransferDao transferDao) {
        return new WithdrawHandler(accountDao, transferDao);
    }
    @Bean
    public DepositHandler depositHandler(AccountDao accountDao, TransferDao transferDao) {
        return new DepositHandler(accountDao, transferDao);
    }
    @Bean
    public GetTransferHandler getTransferHandler(TransferDao transferDao) { return new GetTransferHandler(transferDao); }
    @Bean
    public TransferDao transferDao(DSLContext context) { return new TransferDao(context); }
}
