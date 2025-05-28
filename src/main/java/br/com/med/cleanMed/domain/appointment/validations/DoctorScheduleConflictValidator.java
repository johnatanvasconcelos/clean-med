package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.AppointmentRepository;
import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorScheduleConflictValidator implements ScheduleAppointmentValidator{

    @Autowired
    private AppointmentRepository repository;

    public void toValidate(DataScheduleAppointmentDTO dataDTO){
        var doctorHaveAnotherAppointmentInThisTime = repository.existsByDoctorIdAndDateTime(dataDTO.doctorId(), dataDTO.dateTime());
        if(doctorHaveAnotherAppointmentInThisTime){
            throw new AppointmentValidationException("Médico já possui outro agendamento nesse horário");
        }

    }
}
