package co.baboon.bank.transfer;

import co.baboon.bank.transfer.dto.*;
import co.baboon.bank.transfer.handlers.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("transfers")
@RestController
public class TransferController {
    private final DepositHandler depositHandler;
    private final WithdrawHandler withdrawHandler;
    private final GetTransferHandler getTransferHandler;
    private final InnerTransferHandler innerTransferHandler;
    private final TransferToBankHandler transferToBankHandler;
    private final TransferFromBankHandler transferFromBankHandler;

    public TransferController(DepositHandler depositHandler,
                              WithdrawHandler withdrawHandler,
                              GetTransferHandler getTransferHandler,
                              InnerTransferHandler innerTransferHandler,
                              TransferToBankHandler transferToBankHandler,
                              TransferFromBankHandler transferFromBankHandler) {
        this.depositHandler = depositHandler;
        this.withdrawHandler = withdrawHandler;
        this.getTransferHandler = getTransferHandler;
        this.innerTransferHandler = innerTransferHandler;
        this.transferToBankHandler = transferToBankHandler;
        this.transferFromBankHandler = transferFromBankHandler;
    }

    @PostMapping("deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositDto depositDto) {
        return depositHandler.handle(depositDto);
    }
    
    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto) {
        return withdrawHandler.handle(withdrawDto);
    }
    
    @PostMapping("inner")
    public ResponseEntity<?> inner(@RequestBody InnerTransferDto innerTransferDto) {
        return innerTransferHandler.handle(innerTransferDto);
    }

    @PostMapping("outer-to-bank")
    public ResponseEntity<?> outerToBank(@RequestBody TransferToBankDto transferToBankDto) {
        return transferToBankHandler.handle(transferToBankDto);
    }

    @PostMapping("outer-from-bank")
    public ResponseEntity<?> outerFromBank(@RequestBody TransferFromBankDto transferFromBankDto) {
        return transferFromBankHandler.handle(transferFromBankDto);
    }
    
    @GetMapping("{operationId}")
    public ResponseEntity<?> getTransfer(@PathVariable UUID operationId) {
        return getTransferHandler.handle(operationId);
    }
}
