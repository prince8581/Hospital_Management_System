package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.AdmissionDTO;
import com.hospital.ERP.DTO.AdmissionResDTO;
import com.hospital.ERP.Entity.Admission;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Entity.Ward;
import com.hospital.ERP.Repository.AdmissionRepository;
import com.hospital.ERP.Repository.UserRepo;
import com.hospital.ERP.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WardRepository wardRepo;

    //Admit Patient
    public AdmissionResDTO admitPatient(AdmissionDTO dto){
        Users patient = userRepo.findById(dto.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found"));
        Ward ward = wardRepo.findById(dto.getWardId()).orElseThrow(()-> new RuntimeException("Ward not found."));


        //bed check
        if(ward.getAvailableBeds() <= 0){
            throw new RuntimeException("No Beds available");
        }

        //reduce bed
        ward.setAvailableBeds(ward.getAvailableBeds() - 1);
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setWard(ward);
        admission.setAdmitDate(LocalDate.now());
        admission.setStatus(Admission.Status.ADMITTED);
        
        admissionRepo.save(admission);
        wardRepo.save(ward);
        
        return mapToDTO(admission);
    }

    //DTO Mapping
    private AdmissionResDTO mapToDTO(Admission admission){
        AdmissionResDTO dto = new AdmissionResDTO();
        dto.setId(admission.getId());
        dto.setPatientName(admission.getPatient().getName());
        dto.setWardName(admission.getWard().getWardName());
        dto.setWardType(admission.getWard().getWardType().name());
        dto.setAdmitDate(admission.getAdmitDate().toString());
        dto.setStatus(admission.getStatus().name());
        if(admission.getDischargeDate()!=null){
            dto.setDischargeDate(
                    admission.getDischargeDate().toString()
            );
        }
        return dto;
    }

}
