package com.hospital.ERP.Services;

import com.hospital.ERP.DTO.*;
import com.hospital.ERP.Entity.*;
import com.hospital.ERP.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepo;

    @Autowired
    private LabTestRepository labTestRepo;

    public Doctor addDoctor(DoctorDTO dto){

        Optional<Users> userOpt =  userRepo.findById(dto.getUserId());

        if(userOpt.isEmpty()){
            throw  new RuntimeException("User not found");
        }
        Users user = userOpt.get();

        if(user.getRole() != Users.Role.DOCTOR){
            throw new RuntimeException("User is not a Doctor");
        }

        if(doctorRepo.existsByUserId(user.getId())){
            throw new RuntimeException("Doctor Already exists for this user");
        }



        Doctor doctor = new Doctor();
        doctor.setUser(user);

        doctor.setSpecialization(dto.getSpecialization());
        doctor.setExperience(dto.getExperience());
        doctor.setQualification(dto.getQualification());
        doctor.setConsultationFee(dto.getConsultationFee());

        return doctorRepo.save(doctor);

    }

    //Set Availability
    public AvailabilityDTO setAvailability(AvailabilityDTO dto){
        Doctor doctor = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()->  new RuntimeException("Doctor not found"));

        DoctorAvailability doctorAvailability = new DoctorAvailability();
        doctorAvailability.setDoctor(doctor);
        doctorAvailability.setDay(dto.getDay());
        doctorAvailability.setStartTime(dto.getStartTime());
        doctorAvailability.setEndTime(dto.getEndTime());

        DoctorAvailability saved = doctorAvailabilityRepo.save(doctorAvailability);

        // Entity → DTO mapping
        AvailabilityDTO response = new AvailabilityDTO();
        response.setDoctorId(saved.getDoctor().getId());
        response.setDay(saved.getDay());
        response.setStartTime(saved.getStartTime());
        response.setEndTime(saved.getEndTime());

        return response;
       // return doctorAvailabilityRepo.save(doctorAvailability);

    }


    //get All Doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }


    //manage specailization (update doctor)
    public Doctor manageSpecialization(SpecailizationDTO dto){
        Doctor doctor = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));


        doctor.setSpecialization(dto.getSpecialization());
        return doctorRepo.save(doctor);


    }


    //View Assign  Patients
    public List<Users> getPatientsByDoctor(int doctorId){
        List<Appointment> appointments = appointmentRepo.findByDoctorId(doctorId);

        return appointments.stream()
                .map(Appointment :: getPatient)
                .distinct()
                .toList();
    }


    //prescription and medicine
    public Prescription createPrescription(PrescriptionDTO dto){

        Doctor doctor  = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));

        Users patient = userRepo.findById(dto.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found"));

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setNotes(dto.getNotes());

        Prescription saved = prescriptionRepo.save(prescription);

        for (MedicineDTO med : dto.getMedicines()){
            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(saved);
            item.setMedicine(med.getMedicine());
            item.setDosage(med.getDosage());
            item.setDuration(med.getDuration());

            prescriptionItemRepo.save(item);

        }
        return saved;


    }

    //book Appointment
    public Appointment bookAppointment(AppointmentDTO dto){
        Doctor doctor  = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));

        Users patient = userRepo.findById(dto.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(dto.getDate());
        appointment.setTime(dto.getTime());

        appointment.setStatus(Appointment.AppointmentStatus.BOOK);

        return appointmentRepo.save(appointment);

    }


  //View all Appointment
  public List<AppointmentDTO> getAppointmentByDoctor(int doctorId){

      List<Appointment> list = appointmentRepo.findByDoctorId(doctorId);

      return list.stream().map(a -> {

          AppointmentDTO dto = new AppointmentDTO();

          dto.setDoctorId(a.getDoctor().getId());
          dto.setPatientId(a.getPatient().getId());
          dto.setDate(a.getDate());
          dto.setTime(a.getTime());
          dto.setStatus(a.getStatus());

          return dto;

      }).toList();
  }


    //Today Appointment
    public List<AppointmentDTO> getTodayAppointments(int doctorId){

        String today = java.time.LocalDate.now().toString();

        List<Appointment> list =
                appointmentRepo.findByDoctorIdAndDate(doctorId, today);

        return list.stream().map(a -> {

            AppointmentDTO dto = new AppointmentDTO();

            dto.setPatientId(a.getPatient().getId());
            dto.setDoctorId(a.getDoctor().getId());
            dto.setDate(a.getDate());
            dto.setTime(a.getTime());
            dto.setStatus(a.getStatus());

            return dto;

        }).toList();
    }



    //mark as completed appointment
    public AppointmentDTO markAsCompleted(int id){

        // 1. DB se fetch karo
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // 2. Status update
        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);

        // 3. Save karo
        Appointment saved = appointmentRepo.save(appointment);

        // 4. DTO convert karo
        AppointmentDTO dto = new AppointmentDTO();

        dto.setDoctorId(saved.getDoctor().getId());
        dto.setPatientId(saved.getPatient().getId());
        dto.setDate(saved.getDate());
        dto.setTime(saved.getTime());
        dto.setStatus(saved.getStatus());

        return dto;
    }


    //Cancel Appointment
    public AppointmentDTO cancelAppointment(int id){

        // 1. DB se fetch karo
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // 2. Status update
        appointment.setStatus(Appointment.AppointmentStatus.CANCEL);

        // 3. Save karo
        Appointment saved = appointmentRepo.save(appointment);

        // 4. DTO convert karo
        AppointmentDTO dto = new AppointmentDTO();

        dto.setDoctorId(saved.getDoctor().getId());
        dto.setPatientId(saved.getPatient().getId());
        dto.setDate(saved.getDate());
        dto.setTime(saved.getTime());
        dto.setStatus(saved.getStatus());

        return dto;
    }


    //lab test
    public LabTestResponseDTO assignTest(LabTestDTO dto){
        Users patient = userRepo.findById(dto.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));

        LabTest test = new LabTest();
        test.setPatient(patient);
        test.setDoctor(doctor);
        test.setTestname(dto.getTestName());
        test.setStatus(LabTest.TestStatus.PENDING);

        LabTest saved = labTestRepo.save(test);


        return mapToDTO(saved);

    }

    //DTO mapping
    public LabTestResponseDTO mapToDTO(LabTest t) {
        LabTestResponseDTO dto = new LabTestResponseDTO();
        dto.setId(t.getId());
        dto.setPatientName(t.getPatient().getName());
        dto.setDoctorName(t.getDoctor().getUser().getName());
        dto.setTestName(t.getTestname());
        dto.setStatus(t.getStatus().name());
        dto.setResult(t.getResult());

        return dto;

    }

    //upload result
    public LabTestResponseDTO uploadResult(int testId, String result){
        LabTest test = labTestRepo.findById(testId).orElseThrow(() ->   new RuntimeException("Test not found"));

        test.setResult(result);
        test.setStatus(LabTest.TestStatus.COMPLETED);

        return mapToDTO(labTestRepo.save(test));
    }


    //View Patient Reports
    public List<LabTestResponseDTO> getPatientTests(int patientId){

        List<LabTest> list = labTestRepo.findByPatientId(patientId);

        return  list.stream().map(this::mapToDTO).toList();
    }







}
