package com.heylisten.backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    private String password;
    private int credits;
    private boolean isListener; // true = Navi (oyente), false = Link (usuario)
    private String tags; 
    private String estadoEmocional;
}