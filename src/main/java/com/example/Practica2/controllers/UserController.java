package com.example.Practica2.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Practica2.config.AESE;
import com.example.Practica2.config.KeyInitializer;
import com.example.Practica2.models.UserModel;
import com.example.Practica2.models.dto.LoginDTO;
import com.example.Practica2.models.dto.ModifyUserDTO;
import com.example.Practica2.models.dto.UserResponseDTO;
import com.example.Practica2.models.dto.ObtenerUserDTO;
import com.example.Practica2.repositories.IUserRepository;
import com.example.Practica2.services.UserServices;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/usuario")
public class UserController {   
    
    @Autowired
    private UserServices userService;

    @Autowired
    private KeyInitializer keyInitializer;

    @Autowired
    IUserRepository userRepository;

    SecretKey key;

    @PostConstruct
    public void init() {
        this.key = keyInitializer.getSecretKey();
    }

    @PostMapping("/saveUser")
    public ResponseEntity<UserResponseDTO> saveuser(@RequestBody UserModel user) throws Exception {
        if (user.getRol() == null)
            user.setRol(0);
        if (user.getImagen() == null) {
            Path path = Paths.get("src/main/resources/usuario.jpg");
            user.setImagen(Files.readAllBytes(path));
        }

        UserModel saved = userService.saveUser(user);

        UserResponseDTO dto = new UserResponseDTO();
        dto.setNombre(saved.getNombre());
        dto.setApellido(saved.getApellido());
        dto.setCorreo(saved.getCorreo());
        dto.setRol(saved.getRol());
        user.setContraseña(saved.getContraseña());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/comprobarCorreo")
    public ResponseEntity<Integer> comprobarCorreo(@RequestParam String correo) {
        return ResponseEntity.ok(userService.findByCorreo(correo).isPresent() ? 1 : 0);
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody LoginDTO user) throws Exception {
        Optional<UserModel> usuario = userService.findByCorreo(user.getCorreo());
    
        if (usuario.isPresent()) {
            String contraseñaOriginal = AESE.decrypt(usuario.get().getContraseña(), key);
            if (user.getContraseña().equals(contraseñaOriginal)) {
                return ResponseEntity.ok(1);
            }
        }
        return ResponseEntity.ok(0);
    }

    @GetMapping("/desencriptar/{correo}")
    public ResponseEntity<String> desencriptarContraseña(@PathVariable String correo) throws Exception {
        Optional<UserModel> user = userRepository.findByCorreo(correo);
        if (user.isPresent()) {
            String contraCifrada = user.get().getContraseña();
            String contraOriginal = AESE.decrypt(contraCifrada, key);
            return ResponseEntity.ok(contraOriginal);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping("/usuario/{correo}")
    public ResponseEntity<UserModel> obtenerUsuario(@PathVariable String correo) {
        Optional<UserModel> usuario = userRepository.findByCorreo(correo);
        return usuario.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/modificar")
    public ResponseEntity<Integer> actualizarUsuario(@RequestBody ModifyUserDTO user) throws Exception {
        Optional<UserModel> existente = userRepository.findByCorreo(user.getCorreoActual());

        if (existente.isPresent()) {
            UserModel usuarioActual = existente.get();

            if(user.getNombre() != null && !user.getNombre().isBlank())
                usuarioActual.setNombre(user.getNombre());
            if(user.getApellido() != null && !user.getApellido().isBlank())
                usuarioActual.setApellido(user.getApellido());
            if(user.getContraseña() != null && !user.getContraseña().isBlank())
                usuarioActual.setContraseña(AESE.encrypt(user.getContraseña(), key));
            if(user.getNuevoCorreo() != null && !user.getNuevoCorreo().isBlank())
                usuarioActual.setCorreo(user.getNuevoCorreo());
            if(user.getRol() != null)
                usuarioActual.setRol(user.getRol());
            if (user.getImagen() != null)
                usuarioActual.setImagen(user.getImagen());

            userRepository.save(usuarioActual);
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarUsuarios() {
        List<UserModel> usuarios = userRepository.findAll();
        List<ObtenerUserDTO> lista = usuarios.stream()
            .map(u -> new ObtenerUserDTO(u.getNombre(), u.getApellido(), u.getCorreo(), u.getRol(), u.getImagen()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/eliminar/{correo}")
    public ResponseEntity<?> eliminarUsuarioPorCorreo(@PathVariable String correo) {
        Optional<UserModel> usuario = userRepository.findByCorreo(correo);

        if (usuario.isPresent()) {
            userRepository.delete(usuario.get());
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró ningún usuario con el correo: " + correo);
        }
    }

}
