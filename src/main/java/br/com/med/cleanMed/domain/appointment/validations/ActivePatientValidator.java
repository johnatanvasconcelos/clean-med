package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.domain.patient.PatientRepository;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientValidator implements ScheduleAppointmentValidator{

    @Autowired
    private PatientRepository repository;

    public void toValidate(DataScheduleAppointmentDTO dataDTO){
        var patientIsActive = repository.findActiveById(dataDTO.patientId());

        if(!patientIsActive){
            throw new AppointmentValidationException("Consulta não pode ser agendada com um paciente excluído");
        }
    }
}
