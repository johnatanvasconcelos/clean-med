package br.com.med.cleanMed.domain.doctor;

import br.com.med.cleanMed.domain.address.DataAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRegisterDTO(
        @NotBlank(message = "{name.required}")
        String name,

        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email,

        @NotBlank(message = "{phone.required}")
        @Pattern(regexp = "^\\d{2}\\d{9}$", message = "{phone.invalid}")
        String phone,

        @NotBlank(message = "{crm.required}")
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull(message = "{specialty.required}")
        Specialty specialty,

        @NotNull(message = "{address.required}")
        @Valid
        DataAddressDTO address) {
}
