package com.example.ReceiptProcessor.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Receipt(String retailer,
                      @NotBlank String purchaseDate,
                      @NotBlank String purchaseTime,
                      @NotBlank @NotNull @Size(min = 1) List<Item> items,
                      @NotBlank String total
) {}
