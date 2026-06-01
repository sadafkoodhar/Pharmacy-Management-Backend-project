package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    @Query("SELECT SUM(rec.totalAmount) FROM Receipt rec WHERE CAST(rec.saleDate AS date) = CURRENT_DATE")
    Double getTodayRevenue();
    @Query("SELECT COUNT(rec.id) FROM Receipt rec WHERE CAST(rec.saleDate AS date) = CURRENT_DATE")
    Long getTodayBillCount();

    @Query(value = "SELECT SUM(1 * (m.sale_price - m.cost_price)) " +
            "FROM receipt_items ri " +
            "JOIN medicine m ON ri.medicine_id = m.id", nativeQuery = true)
    Double calculateTotalProfit();
}