package br.com.med.cleanMed.domain.appointment;

import br.com.med.cleanMed.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record DataResponseAppointmentDTO(
        Long id,
        String patientName,
        String patientDoctor,
        Specialty specialty,
        AppointmentStatus status,
        LocalDateTime dateTime) {

    public DataResponseAppointmentDTO(Appointment appointment) {
        this(appointment.getId(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialty(),
                appointment.getAppointmentStatus(),
                appointment.getDateTime());
    }
}
