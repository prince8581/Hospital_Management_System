package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.SuperAdminDTO;
import com.hospital.ERP.DTO.UserResDTO;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Services.SuperAdminService;
import com.hospital.ERP.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @Autowired
    private UserService userService;



    //create Superadmin
    @PostMapping("/createSuperAdmin")
    public Users createSuperAdmin(@RequestBody SuperAdminDTO dto){
        return superAdminService.createSuperAdmin(dto);

    }

    @GetMapping("/users")
    public List<UserResDTO> getAllUsers(){
        return superAdminService.getAllusers();
    }

    //delete SuperAdmin
    @DeleteMapping("/{id}/superadmin")
    public String deleteSuperAdmin(@PathVariable int id){
        return superAdminService.deleteSuperAdmin(id);
    }


    // delete Any Users
    @DeleteMapping("/user/{id}/role")
    public String deleteUser(@PathVariable int id){
        return superAdminService.deleteAnyUser(id);
    }


    //dashboard
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard(){
        return superAdminService.getDashboard();
    }


    //

}
