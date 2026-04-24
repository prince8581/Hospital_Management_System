package com.hospital.ERP.Repository;

import com.hospital.ERP.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    List<Appointment> findByDoctorId(int doctorId);

    List<Appointment> findByDoctorIdAndDate(int doctorId, String today);
}
