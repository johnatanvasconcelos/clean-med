package br.com.med.cleanMed.domain.patient;

import br.com.med.cleanMed.domain.address.Address;

public record PatientDetailsDTO(Long id,
                                String name,
                                String email,
                                String cpf,
                                String phone,
                                Address address) {

    public PatientDetailsDTO(Patient patient) {
        this(patient.getId(),
               patient.getName(),
               patient.getEmail(),
               patient.getCpf(),
               patient.getPhone(),
               patient.getAddress());
    }
}
