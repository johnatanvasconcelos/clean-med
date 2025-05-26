package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceTimeValidator implements ScheduleAppointmentValidator{

    public void toValidate(DataScheduleAppointmentDTO dataDTO) {
        var dateAppointment = dataDTO.dateTime();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, dateAppointment).toMinutes();

        if (differenceInMinutes < 30) {
            throw new AppointmentValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }


}
