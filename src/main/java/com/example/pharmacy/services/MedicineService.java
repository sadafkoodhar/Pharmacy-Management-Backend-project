package com.example.pharmacy.services;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public void addMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public List<Medicine> searchByName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name);
    }

    public Medicine updateMedicine(Long id, Medicine newData) {
        return medicineRepository.findById(id).map(medicine -> {
            medicine.setName(newData.getName());
            medicine.setQuantity(newData.getQuantity());
            medicine.setSalePrice(newData.getSalePrice());
            medicine.setExpiryDate(newData.getExpiryDate());
            return medicineRepository.save(medicine);
        }).orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}