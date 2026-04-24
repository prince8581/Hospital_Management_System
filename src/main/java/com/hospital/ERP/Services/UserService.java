package com.hospital.ERP.Services;


import com.hospital.ERP.DTO.UserDto;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Repository.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public Users created(UserDto dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNo(dto.getPhoneNo());
        user.setUpdatedAT(LocalDateTime.now());
        user.setCreatedAT(LocalDateTime.now());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());

        return userRepo.save(user);
    }


}
