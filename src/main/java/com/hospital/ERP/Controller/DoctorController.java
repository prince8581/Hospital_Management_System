package com.hospital.ERP.Controller;

import com.hospital.ERP.DTO.*;
import com.hospital.ERP.Entity.*;
import com.hospital.ERP.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;



     //Add Doctor
    @PostMapping("/add")
    public Doctor AddDoctor(@RequestBody DoctorDTO dto){
        return doctorService.addDoctor(dto);

    }

    //Set Availability
    @PostMapping("/availability")
    public AvailabilityDTO  setAvailability(@RequestBody AvailabilityDTO dto){
        return doctorService.setAvailability(dto);

    }


    //get All Doctors
    @GetMapping("/alldoctor")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }


    //manage Specailization(update Docotor)
    @PutMapping("/specailization")
    public Doctor updateSpecailization(@RequestBody SpecailizationDTO dto){
        return doctorService.manageSpecialization(dto);
    }

    //view Assign patients
    @GetMapping("/{doctorId}/patients")
    public List<Users> getPatients(@PathVariable int doctorId){

        return doctorService.getPatientsByDoctor(doctorId);
    }


    //Prescription and medicine
    @PostMapping("/prescription")
    public Prescription createPrescription(@RequestBody PrescriptionDTO dto){
        return  doctorService.createPrescription(dto);

    }

    //Book Appointment
    @PostMapping("/appointment/book")
    public Appointment book(@RequestBody AppointmentDTO dto){
        return doctorService.bookAppointment(dto);
    }


    //View  Doctor All Appointment
   @GetMapping("/{doctorId}/appointment")
    public List<AppointmentDTO> getAppointments(@PathVariable int doctorId){
        return doctorService.getAppointmentByDoctor(doctorId);

    }

    //today Appointment
    @GetMapping("/{doctorId}/appointment/today")
    public List<AppointmentDTO> today(@PathVariable int doctorId){
        return doctorService.getTodayAppointments(doctorId);
    }


    //mark completed
    @PutMapping("/appointment/{id}/complete")
    public AppointmentDTO completeAppointment(@PathVariable int id){
        return doctorService.markAsCompleted(id);
    }


    //cancel Appointment
    @PutMapping("/appointment/{id}/cancel")
    public AppointmentDTO cancelAppointment(@PathVariable int id){
        return  doctorService.cancelAppointment(id);
    }

    //Lab Test (Assign Test)  result me null ja raha hai
    @PostMapping("/assign")
    public LabTestResponseDTO assign(@RequestBody LabTestDTO dto){
        return doctorService.assignTest(dto);
    }

    //upload result
    @PutMapping("/{id}/result")
    public LabTestResponseDTO upload(@PathVariable int id, @RequestParam String result){
        return doctorService.uploadResult(id,result);
    }

    //view patient reports
    @GetMapping("/patient/{patientId}")
    public List<LabTestResponseDTO> getReports(@PathVariable int patientId){
        return doctorService.getPatientTests(patientId);
    }


}
