package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("SELECT m FROM Medicine m WHERE m.quantity < 5")
    List<Medicine> getLowStockMedicines();

    @Query("SELECT m FROM Medicine m WHERE m.expiryDate <= :targetDate AND m.expiryDate >= :today")
    List<Medicine> findExpiringSoon(@Param("today") LocalDate today, @Param("targetDate") LocalDate targetDate);
}
