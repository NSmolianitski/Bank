package co.baboon.bank.banks;

import jakarta.annotation.Nonnull;

public record Bank(Integer id, @Nonnull Integer code, String name) {
    public static Builder builder() { return new Builder(); }
    
    public static class Builder {
        private Integer id;
        private Integer code;
        private String name;
        
        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        
        public Builder withCode(Integer code) {
            this.code = code;
            return this;
        }
        
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder copyFrom(Bank bank) {
            this.id = bank.id;
            this.code = bank.code;
            this.name = bank.name;
            return this;
        }
        
        public Bank build() {
            return new Bank(id, code, name);
        }
    }
}
