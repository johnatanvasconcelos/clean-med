package br.com.med.cleanMed.domain.appointment.updating;

import br.com.med.cleanMed.domain.appointment.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentUpdateStatusDTO(
        @NotNull
        Long id,
        @NotNull
        AppointmentStatus newStatus
) {
}
