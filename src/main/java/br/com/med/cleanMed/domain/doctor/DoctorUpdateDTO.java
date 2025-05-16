package br.com.med.cleanMed.domain.doctor;

import br.com.med.cleanMed.domain.address.DataAddressDTO;
import jakarta.validation.constraints.NotNull;

public record DoctorUpdateDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        DataAddressDTO address) {
}
