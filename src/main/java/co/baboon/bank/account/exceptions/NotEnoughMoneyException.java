package co.baboon.bank.account.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}
