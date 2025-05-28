package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.appointment.AppointmentRepository;

import br.com.med.cleanMed.domain.appointment.AppointmentStatus;
import br.com.med.cleanMed.domain.appointment.DataResponseAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.cancellation.DataCancellationAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.scheduling.AppointmentScheduling;
import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;

import br.com.med.cleanMed.domain.appointment.updating.AppointmentUpdateStatusDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentScheduling schedule;

    @Autowired
    private AppointmentRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid DataScheduleAppointmentDTO dataDTO) {
        var dto = schedule.toSchedule(dataDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity toCancel(@RequestBody @Valid DataCancellationAppointmentDTO dataDTO) {
        schedule.toCancel(dataDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DataResponseAppointmentDTO>> listSchedule(@PageableDefault(size = 10, sort = {"patientName"}) Pageable pageable){
        var page = repository.findAll(pageable).map(DataResponseAppointmentDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pending-confirmation")
    public ResponseEntity<Page<DataResponseAppointmentDTO>> listPendingConfirmation(@PageableDefault(size = 10, sort = {"patientName"}) Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        var page = repository.findByAppointmentStatusAndDateTimeBefore(AppointmentStatus.SCHEDULED, now, pageable);
        return ResponseEntity.ok(page.map(DataResponseAppointmentDTO::new));
    }

    @PatchMapping("/update-status")
    @Transactional
    public ResponseEntity<DataResponseAppointmentDTO> updateStatusAppointment(@RequestBody @Valid AppointmentUpdateStatusDTO dto){
        var response = schedule.updateStatus(dto);
        return ResponseEntity.ok(response);
    }

}
