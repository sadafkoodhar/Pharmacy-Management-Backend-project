package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.services.MedicineService;
import com.example.pharmacy.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/add")
    public String addMedicine(@RequestBody Medicine medicine) {
        medicineService.addMedicine(medicine);
        return "Medicine added successfully!";
    }

    @GetMapping("/all")
    public GenericResponse<List<Medicine>> getAllMedicines() {
        List<Medicine> medicineList = medicineService.getAllMedicines();
        return GenericResponse.success(medicineList);
    }

    // Search API: localhost:8080/api/medicines/search?name=Panadol
    @GetMapping("/search")
    public List<Medicine> search(@RequestParam String name) {
        return medicineService.searchByName(name);
    }
    @PutMapping("/update/{id}")
    public Medicine update(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "Deleted successfully!";
    }
}