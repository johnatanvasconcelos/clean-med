package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.appointment.cancellation.DataCancellationAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.scheduling.AppointmentScheduling;
import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentScheduling schedule;

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

}
