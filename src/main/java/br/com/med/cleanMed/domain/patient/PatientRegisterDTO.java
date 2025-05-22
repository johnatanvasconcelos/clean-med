package br.com.med.cleanMed.domain.patient;

import br.com.med.cleanMed.domain.address.Address;
import br.com.med.cleanMed.domain.address.DataAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientRegisterDTO(
        @NotBlank(message = "{name.required}")
        String name,

        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email,

        @NotBlank(message = "{phone.required}")
        @Pattern(regexp = "^\\d{2}\\d{9}$", message = "{phone.invalid}")
        String phone,

        @NotBlank(message = "{crm.required}")
        //irei trocar a lógica de validação do CPF para uma classe
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotNull(message = "{address.required}")
        @Valid
        DataAddressDTO address
) {
}
