package com.example.ReceiptProcessor.Controller;

import com.example.ReceiptProcessor.Model.Receipt;
import com.example.ReceiptProcessor.Service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // Processes the receipt and returns a JSON object with an "id" field.
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", id));
    }

    // Retrieves the points for the given receipt id.
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        return ResponseEntity.ok(Map.of("points", receiptService.getPoints(id)));
    }

}
