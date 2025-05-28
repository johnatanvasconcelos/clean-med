package br.com.med.cleanMed.domain.appointment.cancellation;

import br.com.med.cleanMed.domain.appointment.AppointmentRepository;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceCancellationTimeValidator implements AppointmentCancellationValidator {

    @Autowired
    private AppointmentRepository repository;


    @Override
    public void toValidate(DataCancellationAppointmentDTO data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getDateTime()).toHours();

        if (differenceInHours < 24){
            throw new AppointmentValidationException("Consulta somente pode ser cancelada com antecedência de 24 horas!");
        }
    }
}
