package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.InventoryDTO;
import com.hospital.ERP.DTO.InventoryResDTO;
import com.hospital.ERP.Entity.Inventory;
import com.hospital.ERP.Entity.PharmacyMedicine;
import com.hospital.ERP.Repository.InventoryRepository;
import com.hospital.ERP.Repository.PharmacyMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepo;

    @Autowired
    private PharmacyMedicineRepository medicineRepo;


    //Add Stock
    public InventoryResDTO addStock(InventoryDTO dto){

        PharmacyMedicine pm = medicineRepo.findById(dto.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        Inventory inv = new Inventory();
        inv.setPharmacyMedicine(pm);
        //inv.setMedicine(medicine);
        inv.setQuantity(dto.getQuantity());
        inv.setBatchNo(dto.getBatchNo());
        inv.setAddedDate(java.time.LocalDate.now());

        return mapToDTO(inventoryRepo.save(inv));

    }

    // Mapping
    private  InventoryResDTO mapToDTO(Inventory inv){

        InventoryResDTO dto = new InventoryResDTO();

        dto.setId(inv.getId());
        dto.setMedicineName(inv.getPharmacyMedicine().getName());
        dto.setQuantity(inv.getQuantity());
        dto.setBatchNo(inv.getBatchNo());
        dto.setExpiryDate(inv.getPharmacyMedicine().getExpiryDate().toString());
        return dto;
    }


    //update Stock
    public InventoryResDTO updateStock(int id,int quantity){
        Inventory inv = inventoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Stock not found"));

        inv.setQuantity(quantity);

        return mapToDTO(inventoryRepo.save(inv));
    }

    //low Stock
    public List<InventoryResDTO> getLowStock(int threshold){
        return inventoryRepo.findByQuantityLessThan(threshold)
                .stream()
                .map(this::mapToDTO)
                .toList();

    }

    //expiry check
    public List<InventoryResDTO> getExpired(){

        return inventoryRepo.findAll().stream()
                .filter(i -> i.getPharmacyMedicine().getExpiryDate()
                        .isBefore(LocalDate.now()))
                .map(this::mapToDTO)
                .toList();
    }




}
