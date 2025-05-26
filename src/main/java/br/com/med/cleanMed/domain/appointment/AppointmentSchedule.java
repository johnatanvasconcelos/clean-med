package br.com.med.cleanMed.domain.appointment;

import br.com.med.cleanMed.domain.appointment.validations.ScheduleAppointmentValidator;
import br.com.med.cleanMed.domain.doctor.Doctor;
import br.com.med.cleanMed.domain.doctor.DoctorRepository;
import br.com.med.cleanMed.domain.patient.PatientRepository;
import br.com.med.cleanMed.infra.exception.AppointmentValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentSchedule {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ScheduleAppointmentValidator> validators;

    public void toSchedule(DataScheduleAppointmentDTO dataSchedule){
        if(!patientRepository.existsById(dataSchedule.patientId())){
            throw new AppointmentValidationException("Id do paciente informado não existe");
        }

        if(dataSchedule.doctorId() != null && !doctorRepository.existsById(dataSchedule.doctorId())){
            throw new AppointmentValidationException("Id do médico informado não existe");
        }

        validators.forEach(v -> v.toValidate(dataSchedule));

        var patient = patientRepository.getReferenceById(dataSchedule.patientId());
        var doctor = choiceDoctor(dataSchedule);
        var appointment = new Appointment(null, doctor, patient, dataSchedule.dateTime());

        appointmentRepository.save(appointment);
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

}
