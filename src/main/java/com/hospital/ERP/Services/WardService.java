package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.WardDTO;
import com.hospital.ERP.DTO.WardResDTO;
import com.hospital.ERP.Entity.Ward;
import com.hospital.ERP.Repository.AdmissionRepository;
import com.hospital.ERP.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService {

    @Autowired
    private WardRepository wardRepo;

    @Autowired
    private AdmissionRepository admissionRepository;

    //add Ward
    public WardResDTO addWard(WardDTO dto){
        Ward ward = new Ward();

        ward.setWardName(dto.getWardName());
        ward.setWardType(dto.getWardType());
        ward.setTotalBeds(dto.getTotalBeds());

        //initially all beds are available
        ward.setAvailableBeds(dto.getTotalBeds());
        return mapToDTO(wardRepo.save(ward));
    }

    //View All Beds
    public List<WardResDTO> getAllWards(){
        return wardRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //DTO Mapping
    private WardResDTO mapToDTO(Ward ward){
        WardResDTO dto  = new WardResDTO();
        dto.setId(ward.getId());
        dto.setWardName(ward.getWardName());
        dto.setWardType(ward.getWardType().name());
        dto.setTotalBeds(ward.getTotalBeds());
        dto.setAvailableBeds(ward.getAvailableBeds());
        return  dto;


    }

    //update wards
    public WardResDTO updateWard(int id, WardDTO dto){
     Ward ward = wardRepo.findById(id).orElseThrow(()-> new RuntimeException("Ward not found"));

     ward.setWardName(dto.getWardName());
     ward.setWardType(dto.getWardType());
     ward.setTotalBeds(dto.getTotalBeds());
     ward.setAvailableBeds(dto.getAvailableBeds());

     Ward updatedWard = wardRepo.save(ward);
     return mapToDTO(updatedWard);

    }

    //delete ward
    public String deleteWard(int id){
        Ward ward = wardRepo.findById(id).orElseThrow(()-> new RuntimeException("Ward not found"));

        if (admissionRepository.existsByWardId(id)) {
            throw new RuntimeException("Cannot delete ward: Patients are currently admitted in this ward.");
        }

        wardRepo.deleteById(id);

        return "Ward Deleted Successfully!";
    }


    //Delete Ward

}
