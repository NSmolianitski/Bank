package co.baboon.bank.transfer.dto;

import java.math.BigDecimal;

public record WithdrawDto(Integer accountId, BigDecimal money, String currency) {}
