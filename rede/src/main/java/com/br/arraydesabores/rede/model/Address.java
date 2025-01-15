package com.br.arraydesabores.rede.model;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
