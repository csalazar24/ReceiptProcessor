package com.example.ReceiptProcessor.Controller;

import com.example.ReceiptProcessor.Model.Receipt;
import com.example.ReceiptProcessor.Service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReceiptController.class)
class ReceiptControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReceiptService receiptService;

    @Test
    public void processReceipt_ValidReceipt_ReturnsReceiptId() throws Exception {
        String expectedId = "test-id-123";
        when(receiptService.processReceipt(any(Receipt.class))).thenReturn(expectedId);

        String receiptJson = """
                {
                  "retailer": "Target",
                  "purchaseDate": "2022-01-01",
                  "purchaseTime": "13:01",
                  "items": [
                    {"shortDescription": "Mountain Dew 12PK", "price": "6.49"},
                    {"shortDescription": "Emils Cheese Pizza", "price": "12.25"}
                  ],
                  "total": "35.35"
                }
                """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedId));
    }

    @Test
    public void processReceipt_InvalidInput_ReturnsBadRequest() throws Exception {
        String invalidJson = """
                {
                  "retailer": "Target",
                  "purchaseDate": "2022-01-01",
                  "total": "35.00"
                }
                """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPoints_ValidReceiptId_ReturnsPoints() throws Exception {
        String id = "test-id-123";
        int expectedPoints = 28;
        when(receiptService.getPoints(id)).thenReturn(expectedPoints);

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(expectedPoints));
    }

    @Test
    public void getPoints_NonExistentReceipt_ReturnsNotFound() throws Exception {
        String id = "non-existent-id";
        when(receiptService.getPoints(id))
                .thenThrow(new IllegalArgumentException("No receipt found for that ID."));

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isNotFound());
    }
  
}