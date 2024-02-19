package co.baboon.bank.user;

import co.baboon.bank.user.handlers.GetUserHandler;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    @Bean
    public UserDao userDao(DSLContext context) { return new UserDao(context); }
    
    @Bean
    public GetUserHandler getUserHandler(UserDao userDao) { return new GetUserHandler(userDao); }
}
