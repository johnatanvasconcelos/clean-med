package br.com.med.cleanMed.domain.patient;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf) {

    public PatientResponseDTO(Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
