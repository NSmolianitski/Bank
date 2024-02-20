package co.baboon.bank.transfer.dto;

import java.math.BigDecimal;

public record DepositDto(Integer accountId, BigDecimal money, String currency) {}
