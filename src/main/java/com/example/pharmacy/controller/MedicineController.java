package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.PharmacyException;
import com.example.pharmacy.repository.MedicineRepository;
import com.example.pharmacy.services.MedicineService;
import com.example.pharmacy.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private MedicineRepository medicineRepository;

    @PostMapping("/add")
    public String addMedicine(@RequestBody Medicine medicine) {
        medicineService.addMedicine(medicine);
        return "Medicine added successfully!";
    }
    @PostMapping("/bulk-add")
    public GenericResponse<List<Medicine>> bulkAddMedicines(@RequestBody List<Medicine> medicines) {
        try {
            List<Medicine> savedList = medicineService.addMedicines(medicines);
            return GenericResponse.success(savedList);
        } catch (Exception e) {
            return GenericResponse.failed(e.getMessage());}
    }

    @GetMapping("/all")
    public GenericResponse<List<Medicine>> getAllMedicines() {
        List<Medicine> medicineList = medicineService.getAllMedicines();
        return GenericResponse.success(medicineList);
    }

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
    @GetMapping("/low-stock")
    public GenericResponse<Map<String, Object>> getDashboardData() {
        Map<String, Object> stats = medicineService.getDashboardStats();
        return GenericResponse.success(stats);
    }
}