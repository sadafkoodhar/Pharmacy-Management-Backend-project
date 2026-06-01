package com.example.pharmacy.services;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Receipt;
import com.example.pharmacy.exception.PharmacyException;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReceiptService {
    @Autowired
   private MedicineRepository medicineRepository;
    @Autowired
    private ReceiptRepository receiptRepo;

    public Receipt generateBill(Receipt receipt) {
        double tempTotal = 0.0;
        for (Medicine item : receipt.getItems()){
            Medicine data = medicineRepository.findById(item.getId()).orElseThrow(() -> new PharmacyException("CRITICAL: Medicine ID " + item.getId() + " not found."));
            if (data.getQuantity()< item.getQuantity()) {
                throw new PharmacyException("INSUFFICIENT STOCK: " + data.getName() + " only " + data.getQuantity() + " left.");
            }
            data.setQuantity(data.getQuantity()- item.getQuantity());
            medicineRepository.save(data);
            tempTotal += data.getSalePrice()* item.getQuantity();
            }
        receipt.setTotalAmount(tempTotal);
        receipt.setSaleDate(LocalDateTime.now());

        return receiptRepo.save(receipt);


        }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        Double revenue = receiptRepo.getTodayRevenue();
        Long billCount = receiptRepo.getTodayBillCount();
        List<Medicine> lowStock = medicineRepository.getLowStockMedicines();
        LocalDate today = LocalDate.now();

        Double totalLoss = medicineRepository.calculateTotalLoss(today);
        Double totalProfit = receiptRepo.calculateTotalProfit();

        // Agar database se null aaye (yaani koi data na ho), toh safely 0.0 set karein
        stats.put("totalLoss", (totalLoss != null) ? totalLoss : 0.0);
        stats.put("totalProfit", (totalProfit != null) ? totalProfit : 0.0);

        stats.put("todayRevenue", (revenue != null) ? revenue : 0.0);
        stats.put("todayBillCount", billCount);
        stats.put("lowStockMedicines", lowStock);
        stats.put("lowStockCount", lowStock.size());

        return stats;
    }
}

