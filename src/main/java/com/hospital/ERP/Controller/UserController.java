package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.UserDto;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Repository.UserRepo;
import com.hospital.ERP.Services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public Users create(@RequestBody UserDto dto) {
         return userService.created(dto);
    }

}

