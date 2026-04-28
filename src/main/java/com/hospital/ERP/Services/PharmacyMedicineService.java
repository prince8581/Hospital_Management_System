package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.PharmacyMedicineDTO;
import com.hospital.ERP.DTO.PharmacyMedicineResDTO;
import com.hospital.ERP.Entity.PharmacyMedicine;
import com.hospital.ERP.Repository.PharmacyMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyMedicineService {

    @Autowired
    private PharmacyMedicineRepository pharmacyMedicineRepo;

    //AddMedicine
    public PharmacyMedicineResDTO addMedicine(PharmacyMedicineDTO dto){
        PharmacyMedicine pm = new PharmacyMedicine();
        pm.setName(dto.getName());
        pm.setCategory(dto.getCategory());
        pm.setPrice(dto.getPrice());
        pm.setManufacturer(dto.getManufacturer());
        pm.setExpiryDate(dto.getExpiryDate());

        return  mapToDTO(pharmacyMedicineRepo.save(pm));


    }

    //mapping
    private PharmacyMedicineResDTO mapToDTO(PharmacyMedicine pm){
        PharmacyMedicineResDTO dto = new PharmacyMedicineResDTO();
        dto.setId(pm.getId());
        dto.setName(pm.getName());
        dto.setCategory(pm.getCategory());
        dto.setPrice(pm.getPrice());
        dto.setManufacturer(pm.getManufacturer());
        dto.setExpiryDate(pm.getExpiryDate());
        return dto;
    }

    //View All Medicine
    public List<PharmacyMedicineResDTO> getAllMedicines(){
        return pharmacyMedicineRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //update Medicine
    public PharmacyMedicineResDTO updateMedicine(int id, PharmacyMedicineDTO dto){
        PharmacyMedicine pm = pharmacyMedicineRepo.findById(id).orElseThrow(()-> new RuntimeException("Medicine not found"));

        pm.setName(dto.getName());
        pm.setCategory(dto.getCategory());
        pm.setPrice(dto.getPrice());
        pm.setManufacturer(dto.getManufacturer());
        pm.setExpiryDate(dto.getExpiryDate());

        return mapToDTO(pharmacyMedicineRepo.save(pm));
    }

    //Delete Medicine
    public String deleteMedicine(int id){
        pharmacyMedicineRepo.deleteById(id);
        return "Medicine Deleted Successfully";
    }
}
