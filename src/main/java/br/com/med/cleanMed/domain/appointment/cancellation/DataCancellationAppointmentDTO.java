package br.com.med.cleanMed.domain.appointment.cancellation;

import jakarta.validation.constraints.NotNull;


public record DataCancellationAppointmentDTO(
        @NotNull
        Long idAppointment,

        @NotNull
        CancellationReason reason) {
}
