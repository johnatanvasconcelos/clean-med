package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.appointment.DataDetailsAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.DataScheduleAppointmentDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid DataScheduleAppointmentDTO dataDTO) {
        System.out.println(dataDTO);
        return ResponseEntity.ok(new DataDetailsAppointmentDTO(null, null, null, null));
    }
}
