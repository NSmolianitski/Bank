package co.baboon.bank.transfer;

import co.baboon.bank.transfer.dto.DepositDto;
import co.baboon.bank.transfer.dto.WithdrawDto;
import co.baboon.bank.transfer.handlers.DepositHandler;
import co.baboon.bank.transfer.handlers.GetTransferHandler;
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

    public TransferController(DepositHandler depositHandler, WithdrawHandler withdrawHandler, GetTransferHandler getTransferHandler) {
        this.depositHandler = depositHandler;
        this.withdrawHandler = withdrawHandler;
        this.getTransferHandler = getTransferHandler;
    }

    @PostMapping("deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositDto depositDto) {
        return depositHandler.handle(depositDto);
    }
    
    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto) {
        return withdrawHandler.handle(withdrawDto);
    }
    
    @GetMapping("{operationId}")
    public ResponseEntity<?> getTransfer(@PathVariable UUID operationId) {
        return getTransferHandler.handle(operationId);
    }
}
