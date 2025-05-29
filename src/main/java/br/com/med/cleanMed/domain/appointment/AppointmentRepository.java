package br.com.med.cleanMed.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateTimeAndCancellationReasonIsNull(Long doctorId, LocalDateTime dateTime);

    boolean existsByPatientIdAndDateTimeBetween(Long patientId, LocalDateTime firstSchedule, LocalDateTime lastSchedule);

    Page<Appointment> findByAppointmentStatusAndDateTimeBefore(AppointmentStatus appointmentStatus, LocalDateTime dateTime, Pageable pageable);
}
