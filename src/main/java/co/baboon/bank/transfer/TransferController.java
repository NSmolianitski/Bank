package co.baboon.bank.transfer;

import co.baboon.bank.transfer.dto.DepositDto;
import co.baboon.bank.transfer.dto.InnerTransferDto;
import co.baboon.bank.transfer.dto.WithdrawDto;
import co.baboon.bank.transfer.handlers.DepositHandler;
import co.baboon.bank.transfer.handlers.GetTransferHandler;
import co.baboon.bank.transfer.handlers.InnerTransferHandler;
import co.baboon.bank.transfer.handlers.WithdrawHandler;
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

    public TransferController(DepositHandler depositHandler,
                              WithdrawHandler withdrawHandler,
                              GetTransferHandler getTransferHandler,
                              InnerTransferHandler innerTransferHandler) {
        this.depositHandler = depositHandler;
        this.withdrawHandler = withdrawHandler;
        this.getTransferHandler = getTransferHandler;
        this.innerTransferHandler = innerTransferHandler;
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
    
    @GetMapping("{operationId}")
    public ResponseEntity<?> getTransfer(@PathVariable UUID operationId) {
        return getTransferHandler.handle(operationId);
    }
}
