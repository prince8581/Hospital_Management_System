package com.hospital.ERP.Repository;

import com.hospital.ERP.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    boolean existsByUserId(int id);
}
