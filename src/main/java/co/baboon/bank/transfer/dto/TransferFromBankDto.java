package co.baboon.bank.transfer.dto;

import java.math.BigDecimal;

public record TransferFromBankDto(
        Integer toAccountId,
        Integer fromAccountId,
        BigDecimal sum,
        String currency
) {}
