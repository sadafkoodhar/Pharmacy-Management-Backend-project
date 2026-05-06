package com.example.pharmacy.controller;

import com.example.pharmacy.services.ReceiptService;
import com.example.pharmacy.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/dashboard")
    public GenericResponse<Map<String, Object>> getDashboardData() {
        Map<String, Object> stats = receiptService.getDashboardStats();
        return GenericResponse.success(stats);
    }
}