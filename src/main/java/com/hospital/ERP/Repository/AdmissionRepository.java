package com.hospital.ERP.Repository;

import com.hospital.ERP.Entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission,Integer> {
    
    boolean existsByWardId(int wardId);

}
