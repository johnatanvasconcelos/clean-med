package br.com.med.cleanMed.domain.appointment.scheduling;

import br.com.med.cleanMed.domain.appointment.*;
import br.com.med.cleanMed.domain.appointment.cancellation.AppointmentCancellationValidator;
import br.com.med.cleanMed.domain.appointment.cancellation.DataCancellationAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.updating.AppointmentUpdateStatusDTO;
import br.com.med.cleanMed.domain.appointment.validations.ScheduleAppointmentValidator;
import br.com.med.cleanMed.domain.doctor.Doctor;
import br.com.med.cleanMed.domain.doctor.DoctorRepository;
import br.com.med.cleanMed.domain.patient.PatientRepository;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentScheduling {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ScheduleAppointmentValidator> validators;

    @Autowired
    private List<AppointmentCancellationValidator> cancellationValidators;

    public DataDetailsAppointmentDTO toSchedule(DataScheduleAppointmentDTO dataSchedule){
        if(!patientRepository.existsById(dataSchedule.patientId())){
            throw new AppointmentValidationException("Id do paciente informado não existe");
        }

        if(dataSchedule.doctorId() != null && !doctorRepository.existsById(dataSchedule.doctorId())){
            throw new AppointmentValidationException("Id do médico informado não existe");
        }

        validators.forEach(v -> v.toValidate(dataSchedule));

        var patient = patientRepository.getReferenceById(dataSchedule.patientId());
        var doctor = choiceDoctor(dataSchedule);

        if (doctor == null) {
            throw new AppointmentValidationException("Não existe médico disponível nessa data");
        }

        var appointment = new Appointment(null, doctor, patient, dataSchedule.dateTime(), AppointmentStatus.SCHEDULED, null);

        appointmentRepository.save(appointment);

        return new DataDetailsAppointmentDTO(appointment);
    }

    private Doctor choiceDoctor(DataScheduleAppointmentDTO data){
        if(data.doctorId() != null){
            return doctorRepository.getReferenceById(data.doctorId());
        }
        if(data.specialty() == null){
            throw new AppointmentValidationException("Especialidade do médico não informada");

        }

        return doctorRepository.choiceRandomAvailableDoctorOnTheDate(data.specialty(), data.dateTime());
    }

    public void toCancel(DataCancellationAppointmentDTO dataDTO) {
        if (!appointmentRepository.existsById(dataDTO.idAppointment())) {
            throw new AppointmentValidationException("Id da consulta informado não existe!");
        }

        cancellationValidators.forEach(v -> v.toValidate(dataDTO));

        var appointment = appointmentRepository.getReferenceById(dataDTO.idAppointment());
        appointment.toCancel(dataDTO.reason());
    }

    public DataResponseAppointmentDTO updateStatus(AppointmentUpdateStatusDTO dto){
        var appointment = appointmentRepository.findById(dto.id())
                .orElseThrow(() -> new AppointmentValidationException("Consula não encontrada"));

        if (appointment.getDateTime().isAfter(LocalDateTime.now())) {
            throw new AppointmentValidationException("Não é possível atualizar o status de uma consulta que ainda não ocorreu.");
        }

        appointment.updateStatus(dto.newStatus());
        return new DataResponseAppointmentDTO(appointment);
    }

}
