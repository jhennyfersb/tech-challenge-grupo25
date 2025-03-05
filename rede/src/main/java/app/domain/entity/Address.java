package app.domain.entity;

import com.br.arraydesabores.rede.dto.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_address")
public class Address extends DomainMain {

    private String street;

    private Integer number;

    private String city;

    private String state;

    private String complement;

    private String zipCode;

    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AddressDTO toDTO() {
        return AddressDTO.builder()
                .street(this.street)
                .number(this.number)
                .city(this.city)
                .state(this.state)
                .complement(this.complement)
                .zipcode(this.zipCode)
                .country(this.country)
                .build();
    }

}
