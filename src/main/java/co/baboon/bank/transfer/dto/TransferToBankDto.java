package co.baboon.bank.transfer.dto;

import java.math.BigDecimal;

public record TransferToBankDto(
        Integer fromAccountId,
        Integer toAccountId,
        BigDecimal sum,
        String currency
) {}
