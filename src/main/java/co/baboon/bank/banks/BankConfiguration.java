package co.baboon.bank.banks;

import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {
    @Bean
    public BankDao bankDao(DSLContext context) { return new BankDao(context); }
}
