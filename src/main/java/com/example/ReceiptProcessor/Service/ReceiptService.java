package com.example.ReceiptProcessor.Service;

import com.example.ReceiptProcessor.Model.Receipt;

public interface ReceiptService {

    String processReceipt(Receipt receipt);
    int getPoints(String id);
}
