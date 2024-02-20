package co.baboon.bank.transfer;

import co.baboon.bank.transfer.dto.DepositDto;
import co.baboon.bank.transfer.handlers.DepositHandler;
import co.baboon.bank.transfer.handlers.GetTransferHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("transfers")
@RestController
public class TransferController {
    private final DepositHandler depositHandler;
    private final GetTransferHandler getTransferHandler;

    public TransferController(DepositHandler depositHandler, GetTransferHandler getTransferHandler) {
        this.depositHandler = depositHandler;
        this.getTransferHandler = getTransferHandler;
    }

    @PostMapping("deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositDto depositDto) {
        return depositHandler.handle(depositDto);
    }
    
    @GetMapping("{operationId}")
    public ResponseEntity<?> getTransfer(@PathVariable UUID operationId) {
        return getTransferHandler.handle(operationId);
    }
}
