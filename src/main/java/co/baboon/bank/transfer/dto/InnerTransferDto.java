package co.baboon.bank.transfer.dto;

import java.math.BigDecimal;

public record InnerTransferDto(Integer fromAccountId, Integer toAccountId, BigDecimal money, String currency) {}
