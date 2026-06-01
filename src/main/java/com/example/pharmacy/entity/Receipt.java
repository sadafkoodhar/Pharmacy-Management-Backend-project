package com.example.pharmacy.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private LocalDateTime saleDate;
    private Double totalAmount;

    @ManyToMany
    @JoinTable(
            name = "receiptItems",
            joinColumns = @JoinColumn(name = "receiptId"),
            inverseJoinColumns = @JoinColumn(name = "medicineId"))
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Medicine> items;
}