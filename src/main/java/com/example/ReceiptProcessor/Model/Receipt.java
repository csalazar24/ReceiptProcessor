package com.example.ReceiptProcessor.Model;

import java.util.List;

public record Receipt(String retailer,
                      String purchaseDate,   // Format: "YYYY-MM-DD"
                      String purchaseTime,   // Format: "HH:MM" (24-hour)
                      List<Item> items,
                      String total
) {}
