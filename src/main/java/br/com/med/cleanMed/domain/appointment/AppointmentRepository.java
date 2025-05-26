package br.com.med.cleanMed.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    boolean existsByPatientIdAndDateTimeBetween(Long patientId, LocalDateTime firstSchedule, LocalDateTime lastSchedule);
}
