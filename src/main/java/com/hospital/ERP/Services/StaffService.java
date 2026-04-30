package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.StaffDTO;
import com.hospital.ERP.DTO.StaffResDTO;
import com.hospital.ERP.Entity.Staff;
import com.hospital.ERP.Entity.Users;
import com.hospital.ERP.Repository.StaffRepository;
import com.hospital.ERP.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hospital.ERP.DTO.StaffRegistrationDTO;
import com.hospital.ERP.DTO.LabTestResponseDTO;
import com.hospital.ERP.Entity.LabTest;
import com.hospital.ERP.Repository.LabTestRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LabTestRepository labTestRepo;

    //Add Staff
    public StaffResDTO addStaff(StaffDTO dto){
        Users user = userRepo.findById(dto.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));

        user.setRole(Users.Role.STAFF);
        userRepo.save(user);

        Staff staff = new Staff();
        staff.setUser(user);
        staff.setDepartment(dto.getDepartment());
        staff.setDesignation(dto.getDesignation());
        staff.setSalary(dto.getSalary());
        staff.setShift(dto.getShift());

      //  return mapToDTO(staffRepo.save(staff));

        return  mapToDTO((Staff) staffRepo.save(staff));


    }

    // Single step registration for Staff (Creates User and Staff together)
    @Transactional
    public StaffResDTO registerStaff(StaffRegistrationDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email Already Exists");
        }

        // 1. Create User
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNo(dto.getPhoneNo());
        user.setRole(Users.Role.STAFF);
        user.setStatus(Users.Status.ACTIVE);
        user.setCreatedAT(java.time.LocalDateTime.now());
        user.setUpdatedAT(java.time.LocalDateTime.now());

        Users savedUser = userRepo.save(user);

        // 2. Create Staff
        Staff staff = new Staff();
        staff.setUser(savedUser);
        staff.setDepartment(dto.getDepartment());
        staff.setDesignation(dto.getDesignation());
        staff.setSalary(dto.getSalary());
        staff.setShift(dto.getShift());

        return mapToDTO((Staff) staffRepo.save(staff));
    }

    //Mapping
    private StaffResDTO mapToDTO(Staff staff){

        StaffResDTO dto = new StaffResDTO();

        dto.setId(staff.getId());
        dto.setName(staff.getUser().getName());
        dto.setDepartment(staff.getDepartment());
        dto.setDesignation(staff.getDesignation());
        dto.setSalary(staff.getSalary());
        dto.setShift(staff.getShift());

        return dto;
    }

    // view All Staff
    public List<StaffResDTO> getAllStaff(){
        return staffRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //update Staff
    public StaffResDTO updateStaff(int id, StaffDTO dto){
        Staff staff = staffRepo.findById(id).orElseThrow(()-> new RuntimeException("Staff not found"));

        staff.setDesignation(dto.getDesignation());
        staff.setDepartment(dto.getDepartment());
        staff.setSalary(dto.getSalary());
        staff.setShift(dto.getShift());

        return mapToDTO(staffRepo.save(staff));
    }

    //delete Staff
    public String deleteStaff(int id){
        Staff staff = staffRepo.findById(id).orElseThrow(()-> new RuntimeException("Staff not found"));
        staffRepo.delete(staff);
        return "Staff Deleted";
    }

    //filter by Department
    public List<StaffResDTO> getByDepartment(String dept){
        return staffRepo.findByDepartment(dept)
                .stream()
                .map(this::mapToDTO)
                 .toList();

    }

    // --- Lab Test Upload Feature for Staff ---
    
    private final String UPLOAD_DIR = "uploads/reports/";

    public LabTestResponseDTO uploadResult(int testId, String result, MultipartFile file) {
        LabTest test = labTestRepo.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));

        if (result != null && !result.isEmpty()) {
            test.setResult(result);
        }

        if (file != null && !file.isEmpty()) {
            try {
                // Ensure directory exists
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Generate unique file name
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                // Save file
                Files.write(filePath, file.getBytes());

                // Set file URL in database (relative path)
                test.setFileUrl("/reports/" + fileName);

            } catch (IOException e) {
                throw new RuntimeException("Could not store file. Error: " + e.getMessage());
            }
        }

        test.setStatus(LabTest.TestStatus.COMPLETED);
        return mapToLabTestDTO(labTestRepo.save(test));
    }

    private LabTestResponseDTO mapToLabTestDTO(LabTest t) {
        LabTestResponseDTO dto = new LabTestResponseDTO();
        dto.setId(t.getId());
        dto.setPatientName(t.getPatient().getName());
        dto.setDoctorName(t.getDoctor().getUser().getName());
        dto.setTestName(t.getTestname());
        dto.setStatus(t.getStatus().name());
        dto.setResult(t.getResult());
        dto.setFileUrl(t.getFileUrl());
        return dto;
    }

}
