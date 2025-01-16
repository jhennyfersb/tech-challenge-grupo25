package com.br.arraydesabores.rede.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_address")
public class Address extends BaseModel implements Serializable {
    private String street;
    private Integer number;
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
