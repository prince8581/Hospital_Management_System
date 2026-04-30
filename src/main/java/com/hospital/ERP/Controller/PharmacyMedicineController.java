package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.PharmacyMedicineDTO;
import com.hospital.ERP.DTO.PharmacyMedicineResDTO;
import com.hospital.ERP.Services.PharmacyMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicine")
public class PharmacyMedicineController {

    @Autowired
    private PharmacyMedicineService pharmacyMedicineService;


    //add medicine
    @PostMapping("/addMedicine")
    public PharmacyMedicineResDTO addMedicine(@RequestBody PharmacyMedicineDTO dto){
        return pharmacyMedicineService.addMedicine(dto);
    }

    //View All Medicine
    @GetMapping("/AllMedicine")
    public List<PharmacyMedicineResDTO> getAll(){
        return pharmacyMedicineService.getAllMedicines();
    }

    //Update medicine
    @PutMapping("/{id}/updatemedicine")
    public PharmacyMedicineResDTO updateMedicine(@PathVariable int id, @RequestBody PharmacyMedicineDTO dto){
        return pharmacyMedicineService.updateMedicine(id,dto);
    }


    //Delete medicine
    @DeleteMapping("/{id}/deletemedicine")
    public String deleteMedicine(@PathVariable int id){
        return pharmacyMedicineService.deleteMedicine(id);
    }
}
