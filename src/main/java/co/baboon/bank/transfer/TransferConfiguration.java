package co.baboon.bank.transfer;

import co.baboon.bank.transfer.handlers.DepositHandler;
import co.baboon.bank.transfer.handlers.GetTransferHandler;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferConfiguration {
    @Bean
    public DepositHandler depositHandler(TransferDao transferDao) { return new DepositHandler(transferDao); }
    @Bean
    public GetTransferHandler getTransferHandler(TransferDao transferDao) { return new GetTransferHandler(transferDao); }
    @Bean
    public TransferDao transferDao(DSLContext context) { return new TransferDao(context); }
}
