package br.com.med.cleanMed.domain.address;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String district;
    private String zipCode;
    private String city;
    private String state;
    private String additionalInfo;
    private String number;

    public Address(DataAddressDTO address) {
        this.street = address.street();
        this.district = address.district();
        this.zipCode = address.zipCode();
        this.city = address.city();
        this.state = address.state();
        this.additionalInfo = address.additionalInfo();
        this.number = address.number();
    }

    public void updateAddress(DataAddressDTO data) {
        if (data.street() != null){
            this.street = data.street();
        }
        if (data.district() != null){
            this.district = data.district();
        }
        if (data.zipCode() != null){
            this.zipCode = data.zipCode();
        }
        if (data.city() != null){
            this.city = data.city();
        }
        if (data.state() != null){
            this.state = data.state();
        }
        if (data.additionalInfo() != null){
            this.additionalInfo = data.additionalInfo();
        }
        if (data.number() != null){
            this.number = data.number();
        }
    }
}
