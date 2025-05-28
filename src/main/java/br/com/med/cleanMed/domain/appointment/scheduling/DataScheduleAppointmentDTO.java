package br.com.med.cleanMed.domain.appointment.scheduling;

import br.com.med.cleanMed.domain.doctor.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataScheduleAppointmentDTO(
        Long doctorId,

        @NotNull
        Long patientId,

        @NotNull
        @Future
        LocalDateTime dateTime,

        Specialty specialty) {

}
