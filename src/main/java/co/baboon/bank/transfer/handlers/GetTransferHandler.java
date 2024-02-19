package co.baboon.bank.transfer.handlers;

import co.baboon.bank.transfer.TransferDao;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class GetTransferHandler {
    private final TransferDao transferDao;

    public GetTransferHandler(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public ResponseEntity<?> handle(UUID operationId) {
        var transfer = transferDao.tryGetTransferByOperationId(operationId);
        return ResponseEntity.ok(transfer);
    }
}
