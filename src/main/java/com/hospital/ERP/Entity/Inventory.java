package com.hospital.ERP.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private PharmacyMedicine pharmacyMedicine;

    private int quantity;
    private String batchNo;

    private LocalDate addedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PharmacyMedicine getPharmacyMedicine() {
        return pharmacyMedicine;
    }

    public void setPharmacyMedicine(PharmacyMedicine pharmacyMedicine) {
        this.pharmacyMedicine = pharmacyMedicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }
}
