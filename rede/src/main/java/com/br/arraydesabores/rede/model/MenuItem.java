package com.br.arraydesabores.rede.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_menu_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description;

    private BigDecimal price;

    // Indica se o item só pode ser consumido no restaurante.
    @Column(name = "apenas_no_local")
    private boolean apenasNoLocal;

    // Caminho onde a foto do prato estaria armazenada.
    private String fotoPath;
}
