package com.example.ReceiptProcessor.Controller;

import com.example.ReceiptProcessor.Model.Receipt;
import com.example.ReceiptProcessor.Service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
    }

    @Test
    public void processReceipt_ValidReceipt_ReturnsReceiptId() throws Exception {
        //Arrange
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
        when(receiptService.getPoints(id)).thenReturn(null);

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void processReceipt_MalformedInput_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"retailer\": \"Target\"}";

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }


}