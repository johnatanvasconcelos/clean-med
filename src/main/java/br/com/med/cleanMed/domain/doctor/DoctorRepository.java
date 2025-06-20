package br.com.med.cleanMed.domain.doctor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query("""
            select d from Doctor d
            where
                d.active = true
                and
                d.specialty = :specialty
                and
                d.id not in(
                    select a.doctor.id from Appointment a
                    where
                    a.dateTime = :dateTime
            and
                    a.cancellationReason is null
            )
            order by function('RANDOM')
            limit 1
            """)
    Doctor choiceRandomAvailableDoctorOnTheDate(Specialty specialty, @NotNull @Future LocalDateTime dateTime);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :doctorId
            """)
    Boolean findActiveById(Long doctorId);
}
