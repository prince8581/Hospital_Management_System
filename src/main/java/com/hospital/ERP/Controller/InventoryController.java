package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.InventoryDTO;
import com.hospital.ERP.DTO.InventoryResDTO;
import com.hospital.ERP.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    //  Add stock
    @PostMapping("/addStock")
    public InventoryResDTO add(@RequestBody InventoryDTO dto){
        return inventoryService.addStock(dto);
    }

    // update Stock
    @PutMapping("/{id}/updateStock")
    public InventoryResDTO update(@PathVariable int id, @RequestParam int quantity){
        return inventoryService.updateStock(id,quantity);

    }

    //low stock
    @GetMapping("/lowStock")
    public List<InventoryResDTO> lowStock(@RequestParam int threshold){
        return inventoryService.getLowStock(threshold);

    }

    //check expiry
    @GetMapping("/expired")
    public List<InventoryResDTO> expired(){
        return inventoryService.getExpired();
    }

}
