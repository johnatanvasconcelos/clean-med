package br.com.med.cleanMed.domain.patient;

import br.com.med.cleanMed.domain.address.DataAddressDTO;
import jakarta.validation.constraints.NotNull;

public record PatientUpdateDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        DataAddressDTO address) {
}
