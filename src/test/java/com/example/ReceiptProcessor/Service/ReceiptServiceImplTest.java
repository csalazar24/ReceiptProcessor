package com.example.ReceiptProcessor.Service;

import com.example.ReceiptProcessor.Model.Item;
import com.example.ReceiptProcessor.Model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceImplTest {
    private ReceiptServiceImpl receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptServiceImpl();
    }

    @Test
    void processReceipt_ValidReceipt_ReturnsExpectedPoints() {

        Receipt receipt = new Receipt(
                "Target",
                "2022-01-01",
                "15:00",
                List.of(
                        new Item("Pizza", "8.00"),
                        new Item("Burger", "10.00"),
                        new Item("Soda", "2.00")
                ),
                "35.00"
        );

        String id = receiptService.processReceipt(receipt);
        assertNotNull(id, "processReceipt should return a non-null ID");

        int points = receiptService.getPoints(id);
        assertEquals(104, points, "Points should be 104 for the valid receipt.");
    }

    @Test
    void processReceipt_TotalNotRoundDollars_ReturnsExpectedPoints() {

        Receipt receipt = new Receipt(
                "Target",
                "2022-01-01",
                "15:00",
                List.of(
                        new Item("Burger", "10.00")
                ),
                "35.35"
        );

        String id = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(id);
        assertEquals(24, points, "For total 35.35, points should be 24.");
    }

    @Test
    void processReceipt_NoItems_ReturnsExpectedPoints() {

        Receipt receipt = new Receipt(
                "Target",
                "2022-01-01",
                "15:00",
                List.of(),  // No items provided
                "35.00"
        );

        String id = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(id);
        assertEquals(97, points, "For a receipt with no items, the computed points should be 97.");
    }

    @Test
    void getPoints_NonExistentReceipt_ThrowsException() {

        String nonExistentId = "non-existent-id";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> receiptService.getPoints(nonExistentId)
        );

        assertEquals("No receipt found for that ID.", exception.getMessage());
    }

}