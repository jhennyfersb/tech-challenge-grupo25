package com.br.arraydesabores.rede.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_address")
public class Address extends DomainMain implements Serializable {

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

}
