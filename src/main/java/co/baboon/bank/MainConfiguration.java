package co.baboon.bank;

import co.baboon.bank.account.AccountController;
import co.baboon.bank.authorization.AuthorizationConfiguration;
import co.baboon.bank.user.UserConfiguration;
import co.baboon.bank.utilities.UtilitiesConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Import({ 
        AuthorizationConfiguration.class,
        UtilitiesConfiguration.class,
        UserConfiguration.class,
        AccountController.class
})
@Configuration
public class MainConfiguration {
    @Bean
    public HikariConfig hikariConfig() {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/BankDb");
        config.setUsername("admin");
        config.setPassword("admin");
        
        return config;
    }
    
    @Bean
    public DataSource dataSource(HikariConfig config) { return new HikariDataSource(config); }
    
    @Bean
    public DataSourceConnectionProvider dataSourceConnectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }
    
    @Bean
    public DefaultConfiguration defaultConfiguration(DataSourceConnectionProvider dataSourceConnectionProvider) {
        var configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.POSTGRES);
        configuration.setConnectionProvider(dataSourceConnectionProvider);
        
        return configuration;
    }
    
    @Bean
    public DSLContext dslContext(DefaultConfiguration configuration) { return new DefaultDSLContext(configuration); }
}
