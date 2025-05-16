package br.com.med.cleanMed.domain.doctor;

import br.com.med.cleanMed.domain.address.Address;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;


@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    private Boolean active;

    public Doctor(DoctorRegisterDTO doctorRegisterDTO) {
        this.active = true;
        this.name = doctorRegisterDTO.name();
        this.email = doctorRegisterDTO.email();
        this.phone = doctorRegisterDTO.phone();
        this.crm = doctorRegisterDTO.crm();
        this.specialty = doctorRegisterDTO.specialty();
        this.address = new Address(doctorRegisterDTO.address());
    }

    public void updateData(@Valid DoctorUpdateDTO doctorUpdateDTO) {
        if (doctorUpdateDTO.name() != null){
            this.name = doctorUpdateDTO.name();
        }
        if (doctorUpdateDTO.phone() != null){
            this.phone = doctorUpdateDTO.phone();
        }
        if (doctorUpdateDTO.address() != null){
            this.address.updateAddress(doctorUpdateDTO.address());
        }

    }

    public void delete() {
        this.active = false;
    }
}
