package br.com.med.cleanMed.domain.appointment.validations;

import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;


public interface ScheduleAppointmentValidator {

    void toValidate(DataScheduleAppointmentDTO dataDTO);
}
