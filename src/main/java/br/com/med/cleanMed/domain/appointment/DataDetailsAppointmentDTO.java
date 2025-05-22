package br.com.med.cleanMed.domain.appointment;

import java.time.LocalDateTime;

public record DataDetailsAppointmentDTO(
        Long id,
        Long idDoctor,
        Long idPatient,
        LocalDateTime dateTime
) {
}
