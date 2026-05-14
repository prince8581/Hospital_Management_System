package com.hospital.ERP.Controller;


import com.hospital.ERP.DTO.AdmissionDTO;
import com.hospital.ERP.DTO.AdmissionResDTO;
import com.hospital.ERP.Repository.AdmissionRepository;
import com.hospital.ERP.Services.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

    @Autowired
    private AdmissionRepository admissionRepo;

    @Autowired
    private AdmissionService admissionService;

    @PostMapping("/admitPatient")
    public AdmissionResDTO admit(@RequestBody AdmissionDTO dto){
        return  admissionService.admitPatient(dto);

    }
}
