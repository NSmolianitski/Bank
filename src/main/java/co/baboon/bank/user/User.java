package co.baboon.bank.user;

import co.baboon.bank.account.Account;
import jakarta.annotation.Nonnull;

import java.util.List;

public class User {
    private final Integer id;
    @Nonnull
    private final String name;
    @Nonnull
    private final String login;
    @Nonnull
    private final String password;
    @Nonnull
    private final List<Account> accounts; 

    private User(Integer id, @Nonnull String name, @Nonnull String login, @Nonnull String password, @Nonnull List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.accounts = accounts;
    }
    
    public Integer getId() { return id; }
    @Nonnull
    public String getName() { return name; }
    @Nonnull
    public String getLogin() { return login; }
    @Nonnull
    public String getPassword() { return password; }

    public static Builder builder() { return new Builder(); }
    
    public static class Builder {
        private Integer id;
        private String name;
        private String login;
        private String password;
        private List<Account> accounts;
        
        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }
        
        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }
        
        public Builder withAccounts(List<Account> accounts) {
            this.accounts = accounts;
            return this;
        }
        
        public Builder copyFrom(User user) {
            this.id = user.id;
            this.name = user.name;
            this.login = user.login;
            this.password = user.password;
            return this;
        }
        
        public User build() { return new User(id, name, login, password, accounts); }
    }
}
