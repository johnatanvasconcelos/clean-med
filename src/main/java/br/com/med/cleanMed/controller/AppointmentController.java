package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.appointment.AppointmentSchedule;
import br.com.med.cleanMed.domain.appointment.DataDetailsAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.DataScheduleAppointmentDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentSchedule schedule;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid DataScheduleAppointmentDTO dataDTO) {
        schedule.toSchedule(dataDTO);
        return ResponseEntity.ok(new DataDetailsAppointmentDTO(null, null, null, null));
    }
}
