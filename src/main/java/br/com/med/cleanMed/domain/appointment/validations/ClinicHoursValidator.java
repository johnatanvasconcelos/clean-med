package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicHoursValidator implements ScheduleAppointmentValidator{

    public void toValidate(DataScheduleAppointmentDTO dataDTO) {
        var dateAppointment = dataDTO.dateTime();

        var sunday = dateAppointment.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpenClinic = dateAppointment.getHour() < 7;
        var afterCloseClinic = dateAppointment.getHour() > 18;

        if (sunday || beforeOpenClinic || afterCloseClinic) {
            throw new AppointmentValidationException("Consulta fora do horário de atendimento da clínica.");
        }
    }
}
