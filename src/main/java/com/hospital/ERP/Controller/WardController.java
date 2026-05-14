package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.WardDTO;
import com.hospital.ERP.DTO.WardResDTO;
import com.hospital.ERP.Services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ward")
public class WardController {

    @Autowired
    private WardService wardService;

    //add Ward
    @PostMapping("/addWard")
    public WardResDTO addWard(@RequestBody WardDTO dto){
        return wardService.addWard(dto);
    }

    //view All wards
    @GetMapping("/ViewWard")
    public List<WardResDTO> getAll(){
        return wardService.getAllWards();
    }

    //Update Ward
    @PutMapping("/{id}/updateWard")
     public WardResDTO updateWard(@PathVariable int id, @RequestBody WardDTO dto){
               return wardService.updateWard(id,dto);
     }

     //delete ward
    @DeleteMapping("/{id}/deleteWard")
    public String deleteWard(@PathVariable int id){

        return  wardService.deleteWard(id);
    }

}


