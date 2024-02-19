package co.baboon.bank.account;

import co.baboon.bank.account.handlers.CreateAccountHandler;
import co.baboon.bank.account.handlers.GetAccountHandler;
import co.baboon.bank.utilities.JwtUtility;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {
    @Bean
    public AccountDao accountDao(DSLContext context) { return new AccountDao(context); }
    
    @Bean
    public GetAccountHandler getAccountHandler(AccountDao accountDao) { return new GetAccountHandler(accountDao); }
    
    @Bean
    public CreateAccountHandler createAccountHandler(AccountDao accountDao, JwtUtility jwtUtility) {
        return new CreateAccountHandler(accountDao, jwtUtility);
    }
}
