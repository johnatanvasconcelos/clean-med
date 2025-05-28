package br.com.med.cleanMed.domain.appointment;

import br.com.med.cleanMed.domain.appointment.cancellation.CancellationReason;
import br.com.med.cleanMed.domain.doctor.Specialty;
import br.com.med.cleanMed.domain.patient.Patient;
import br.com.med.cleanMed.domain.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Getter
@Entity(name = "Appointment")
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dateTime;

    @Column(name = "appointment_status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @Column(name = "cancellation_reason")
    @Enumerated(EnumType.STRING)
    private CancellationReason cancellationReason;

    public void toCancel(CancellationReason reason) {
        this.cancellationReason = reason;
        this.appointmentStatus = AppointmentStatus.CANCELED;

    }
}
