package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.domain.doctor.DoctorRepository;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctorValidator implements ScheduleAppointmentValidator {

    @Autowired
    private DoctorRepository repository;

    public void toValidate(DataScheduleAppointmentDTO dataDTO){
        if (dataDTO.doctorId() == null){
            return;
        }

        var doctorIsActive = repository.findActiveById(dataDTO.doctorId());
        if(!doctorIsActive){
            throw new AppointmentValidationException("Consulta não pode ser agendada com um médico inativo ou inexistente");
        }
    }

}

