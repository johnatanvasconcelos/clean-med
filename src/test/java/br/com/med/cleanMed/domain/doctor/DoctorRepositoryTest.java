package br.com.med.cleanMed.domain.doctor;

import br.com.med.cleanMed.domain.address.DataAddressDTO;
import br.com.med.cleanMed.domain.appointment.Appointment;
import br.com.med.cleanMed.domain.appointment.AppointmentStatus;
import br.com.med.cleanMed.domain.patient.Patient;
import br.com.med.cleanMed.domain.patient.PatientRegisterDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria devolver null quando único medico cadastrado não está disponível na data")
    void choiceRandomAvailableDoctorOnTheDateCase1() {
        // given
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = registerDoctor("Doctor", "doctor@clean.med", "123456", Specialty.CARDIOLOGY);
        var patient = registerPatient("Patient", "patient@clean.med", "00000000000");
        registerAppointment(doctor, patient, nextMondayAt10);

        // when
        var doctorFree = repository.choiceRandomAvailableDoctorOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10);

        // then
        assertThat(doctorFree).isNull();

    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void choiceRandomAvailableDoctorOnTheDateCase2() {
        // given
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Doctor", "doctor@clean.med", "123456", Specialty.CARDIOLOGY);

        // when
        var doctorFree = repository.choiceRandomAvailableDoctorOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10);

        // then
        assertThat(doctorFree).isEqualTo(doctor);

    }

    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime) {
        entityManager.persist(new Appointment(null, doctor, patient, dateTime, AppointmentStatus.SCHEDULED, null));
    }

    private Doctor registerDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(dataDoctor(name, email, crm, specialty));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf) {
        var patient = new Patient(dataPatient(name, email, cpf));
        entityManager.persist(patient);
        return patient;
    }

    private DoctorRegisterDTO dataDoctor(String name, String email, String crm, Specialty specialty) {
        return new DoctorRegisterDTO(
                name,
                email,
                "61999999999",
                crm,
                specialty,
                dataAddress()
        );
    }

    private PatientRegisterDTO dataPatient(String nome, String email, String cpf) {
        return new PatientRegisterDTO(
                nome,
                email,
                "61999999999",
                cpf,
                dataAddress()
        );
    }

    private DataAddressDTO dataAddress() {
        return new DataAddressDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}