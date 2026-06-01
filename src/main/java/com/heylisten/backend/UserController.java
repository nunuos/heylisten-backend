package com.heylisten.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        user.setCredits(10); // Créditos de bienvenida
        return userRepository.save(user);
    }

    // 🌟 NUEVO ENDPOINT: Inicio de sesión real contra la Base de Datos
    @PostMapping("/login")
    public String loginUser(@RequestBody User loginData) {
        // Buscamos en la lista si existe algún usuario con ese username
        List<User> users = userRepository.findAll();
        
        Optional<User> encontrado = users.stream()
            .filter(u -> u.getUsername().equals(loginData.getUsername()) 
                      && u.getPassword().equals(loginData.getPassword()))
            .findFirst();

        if (encontrado.isPresent()) {
            return "OK"; // Si coincide todo, damos luz verde
        } else {
            throw new RuntimeException("Usuario o contraseña incorrectos ❌");
        }
    }
}