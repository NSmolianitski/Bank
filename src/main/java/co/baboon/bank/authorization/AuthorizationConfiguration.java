package co.baboon.bank.authorization;

import co.baboon.bank.authorization.handlers.LoginHandler;
import co.baboon.bank.authorization.handlers.RegisterHandler;
import co.baboon.bank.user.UserDao;
import co.baboon.bank.utilities.JwtUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AuthorizationConfiguration {
    @Bean
    public JwtRequestFilter jwtRequestFilter(JwtUtility jwtUtility) { return new JwtRequestFilter(jwtUtility); }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtRequestFilter jwtRequestFilter) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling((handler) -> handler
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .authorizeHttpRequests((request) -> {
//                    request.requestMatchers("auth/register").permitAll();
//                    request.requestMatchers("auth/login").permitAll();
//                    request.anyRequest().authenticated();
//                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    
    @Bean
    public RegisterHandler registerHandler(
            UserDao userDao,
            PasswordEncoder passwordEncoder,
            JwtUtility jwtUtility) { 
        return new RegisterHandler(userDao, passwordEncoder, jwtUtility); 
    }
    
    @Bean
    public LoginHandler loginHandler(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtility jwtUtility) { 
        return new LoginHandler(userDao, passwordEncoder, jwtUtility);
    }
}
