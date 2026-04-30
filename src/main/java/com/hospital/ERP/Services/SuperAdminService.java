package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.SuperAdminDTO;
import com.hospital.ERP.DTO.UserResDTO;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SuperAdminService {

    @Autowired
    private UserRepo userRepo;


    //create Super Admin
    public Users createSuperAdmin(SuperAdminDTO dto){
        if(userRepo.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email Already Exists");
        }

        if(userRepo.countByRole(Users.Role.SUPERADMIN) > 0){
            throw  new RuntimeException("Super Admin Already exists");
        }

        Users sa = new Users();
        sa.setName(dto.getName());
        sa.setEmail(dto.getEmail());
        sa.setPassword(dto.getPassword());

        sa.setRole(Users.Role.SUPERADMIN);
        sa.setStatus(Users.Status.ACTIVE);

        return userRepo.save(sa);
    }


    //delete superadmin
    public String deleteSuperAdmin(int id){
        Users sa = userRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(sa.getRole() != Users.Role.SUPERADMIN){
            throw  new RuntimeException("Not a Super Admin");
        }
        userRepo.delete(sa);
        return "Super Admin Deleted Successfully";
    }

    //mapping method
    private UserResDTO mapToDTO(Users user){
        UserResDTO dto = new UserResDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNo(user.getPhoneNo());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    //view All Users
    public List<UserResDTO> getAllusers(){
        return userRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //delete any user
    public String deleteAnyUser(int id){
        Users  user = userRepo.findById(id).orElseThrow(()-> new RuntimeException("user not found!"));
        userRepo.delete(user);
        return "User Deleted Successfully!";
    }

    //dashboard
    public Map<String, Object> getDashboard(){
        Map<String, Object> data = new HashMap<>();
        List<Users>  users = userRepo.findAll();
        data.put("totalUsers",users.size());
        data.put("totalAdmins", users.stream().filter(u-> u.getRole() == Users.Role.ADMIN).count());
        data.put("totalDoctors",users.stream().filter(u-> u.getRole()== Users.Role.DOCTOR).count());
        data.put("totalPatients", users.stream().filter(u-> u.getRole() == Users.Role.PATIENT).count());
        data.put("totalStaffs", users.stream().filter(u-> u.getRole() == Users.Role.STAFF).count());
        return data;


    }


}
