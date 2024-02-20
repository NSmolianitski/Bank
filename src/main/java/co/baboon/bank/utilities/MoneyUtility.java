package co.baboon.bank.utilities;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

public class MoneyUtility {
    public static Money createMoney(BigDecimal sum, String currency) {
        return Money.of(CurrencyUnit.of(currency), sum);
    }
}
