package com.example.pharmacy.controller;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.services.ReceiptService;
import com.example.pharmacy.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "*")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/generate")
    public GenericResponse<Receipt> createBill(@RequestBody Receipt receipt) {
        try {
            Receipt savedReceipt = receiptService.generateBill(receipt);
            return GenericResponse.success(savedReceipt);
        } catch (Exception e) {
            return GenericResponse.failed(e.getMessage());
        }
    }
}
