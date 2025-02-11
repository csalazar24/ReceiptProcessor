package com.example.ReceiptProcessor.Controller;

import com.example.ReceiptProcessor.Model.Receipt;
import com.example.ReceiptProcessor.Service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptController.class)
class ReceiptControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReceiptService receiptService;

    @Test
    public void testProcessReceipt_Valid_ReturnsId() throws Exception {

        String expectedId = "adb6b560-0eef-42bc-9d16-df48f30e89b2";
        when(receiptService.processReceipt(any(Receipt.class))).thenReturn(expectedId);

        String validReceiptJson = """
                {
                  "retailer": "M&M Corner Market",
                  "purchaseDate": "2022-01-01",
                  "purchaseTime": "13:01",
                  "items": [
                      {
                        "shortDescription": "Mountain Dew 12PK",
                        "price": "6.49"
                      }
                  ],
                  "total": "6.49"
                }
                """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validReceiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    public void testProcessReceipt_InvalidInput_ReturnsBadRequest() throws Exception {

        String invalidReceiptJson = """
                {
                  "retailer": "M&M Corner Market",
                  "purchaseDate": "2022-01-01",
                  "total": "6.49"
                }
                """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidReceiptJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testGetPoints_ValidId_ReturnsPoints() throws Exception {

        String id = "adb6b560-0eef-42bc-9d16-df48f30e89b2";
        int expectedPoints = 100;
        when(receiptService.getPoints(id)).thenReturn(expectedPoints);

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists())
                .andExpect(jsonPath("$.points").value(expectedPoints));

    }

    @Test
    public void testGetPoints_NonExistentId_ReturnsNotFound() throws Exception {

        String id = "nonexistent-id";
        when(receiptService.getPoints(id))
                .thenThrow(new IllegalArgumentException("No receipt found for that ID."));

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isNotFound());

    }

}