package com.heylisten.backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "surveys")
@Data
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // Quién hizo la encuesta
    private int estrellas;   // 1 a 5
    private String comentario;
}