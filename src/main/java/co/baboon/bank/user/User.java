package co.baboon.bank.user;

import jakarta.annotation.Nonnull;

public record User(Integer id, @Nonnull String name, @Nonnull String login, @Nonnull String password) {

    public static Builder builder() { return new Builder(); }
    
    public static class Builder {
        private Integer id;
        private String name;
        private String login;
        private String password;
        
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
        
        public Builder copyFrom(User user) {
            this.id = user.id;
            this.name = user.name;
            this.login = user.login;
            this.password = user.password;
            return this;
        }
        
        public User build() { return new User(id, name, login, password); }
    }
}
