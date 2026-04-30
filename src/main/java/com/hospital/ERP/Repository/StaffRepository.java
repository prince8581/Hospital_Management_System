package com.hospital.ERP.Repository;

import com.hospital.ERP.DTO.StaffResDTO;
import com.hospital.ERP.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    List<Staff> findByDepartment(String dept);
}
