package br.com.med.cleanMed.domain.doctor;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        Specialty specialty) {

    public DoctorResponseDTO(Doctor doctor) {
        this(doctor.getId() ,doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}
