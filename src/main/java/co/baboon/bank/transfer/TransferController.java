package co.baboon.bank.transfer;

import co.baboon.bank.transfer.handlers.GetTransferHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("transfers")
@RestController
public class TransferController {
    private final GetTransferHandler getTransferHandler;

    public TransferController(GetTransferHandler getTransferHandler) {
        this.getTransferHandler = getTransferHandler;
    }

    @GetMapping("{operationId}")
    public ResponseEntity<?> getTransfer(@PathVariable UUID operationId) {
        return getTransferHandler.handle(operationId);
    }
}
