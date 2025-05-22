package br.com.med.cleanMed.domain.patient;

import br.com.med.cleanMed.domain.address.Address;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Patient")
@Getter
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    @Embedded
    private Address address;

    private Boolean active;

    public Patient(PatientRegisterDTO patientRegisterDTO){
        this.active = true;
        this.name = patientRegisterDTO.name();
        this.email = patientRegisterDTO.email();
        this.phone = patientRegisterDTO.phone();
        this.cpf = patientRegisterDTO.cpf();
        this.address = new Address(patientRegisterDTO.address());
    }

    public void updateData(@Valid PatientUpdateDTO patientUpdateDTO) {
        if (patientUpdateDTO.name() != null){
            this.name = patientUpdateDTO.name();
        }
        if (patientUpdateDTO.phone() != null){
            this.phone = patientUpdateDTO.phone();
        }
        if (patientUpdateDTO.address() != null){
            this.address.updateAddress(patientUpdateDTO.address());
        }

    }

    public void delete() {
        this.active = false;
    }

}
