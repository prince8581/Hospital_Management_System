package com.hospital.ERP.Repository;

import com.hospital.ERP.Entity.PharmacyMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyMedicineRepository extends JpaRepository<PharmacyMedicine,Integer> {
}
