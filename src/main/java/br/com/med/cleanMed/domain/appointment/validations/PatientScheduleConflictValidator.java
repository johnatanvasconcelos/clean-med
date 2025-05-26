package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.AppointmentRepository;
import br.com.med.cleanMed.domain.appointment.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientScheduleConflictValidator implements ScheduleAppointmentValidator {

    @Autowired
    private AppointmentRepository repository;

    public void toValidate(DataScheduleAppointmentDTO dataDTO){
        var firstSchedule = dataDTO.dateTime().withHour(7);
        var lastSchedule = dataDTO.dateTime().withHour(18);
        var patientHaveAnotherAppointmentInThisDay = repository.existsByPatientIdAndDateTimeBetween(dataDTO.patientId(), firstSchedule, lastSchedule);
        if(patientHaveAnotherAppointmentInThisDay){
            throw new AppointmentValidationException("Paciente já possui outro agendamento nesse horário");
        }

    }
}
