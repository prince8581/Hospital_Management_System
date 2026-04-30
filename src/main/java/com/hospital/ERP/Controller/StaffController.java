package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.LabTestResponseDTO;
import com.hospital.ERP.DTO.StaffDTO;
import com.hospital.ERP.DTO.StaffResDTO;
import com.hospital.ERP.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService  staffService;

    //add Stock (Old 2-step process)
    @PostMapping("/addStaff")
    public StaffResDTO addStaff(@RequestBody StaffDTO dto){
        return staffService.addStaff(dto);
    }

    // Register Staff (New 1-step process)
    @PostMapping("/register-staff")
    public StaffResDTO registerStaff(@RequestBody com.hospital.ERP.DTO.StaffRegistrationDTO dto){
        return staffService.registerStaff(dto);
    }

    //view All Staff
    @GetMapping("/allStaff")
    public List<StaffResDTO> getAll(){
        return staffService.getAllStaff();

    }

    //update Staff
    @PutMapping("/{id}/updateStaff")
    public  StaffResDTO updateStaff(@PathVariable int id, @RequestBody StaffDTO dto){

        return staffService.updateStaff(id,dto);
    }

    //delete Staff
    @DeleteMapping("/{id}/deleteStaff")
    public  String DeleteStaff(@PathVariable int id){
        return staffService.deleteStaff(id);
    }

    //filter department
    @GetMapping("/department")
    public List<StaffResDTO> filter(@RequestParam String dept){
        return staffService.getByDepartment(dept);
    }

    // upload lab test result
    @PutMapping(value = "/test/{id}/result", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LabTestResponseDTO upload(
            @PathVariable int id, 
            @RequestParam(required = false) String result,
            @RequestParam(value = "file", required = false) org.springframework.web.multipart.MultipartFile file) {
        return staffService.uploadResult(id, result, file);
    }
}
