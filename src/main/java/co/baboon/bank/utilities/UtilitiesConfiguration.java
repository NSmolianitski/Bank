package co.baboon.bank.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilitiesConfiguration {
    @Bean
    public JwtUtility jwtUtility() { return new JwtUtility(); }
}
