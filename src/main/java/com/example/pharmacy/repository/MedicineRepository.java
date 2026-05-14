package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("SELECT m FROM Medicine m WHERE m.quantity < 5")
    List<Medicine> getLowStockMedicines();

}
