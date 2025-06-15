package com.example.Practica2.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Practica2.config.AESE;
import com.example.Practica2.config.KeyInitializer;
import com.example.Practica2.models.UserModel;
import com.example.Practica2.repositories.IUserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServices {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private KeyInitializer keyInitializer;

    SecretKey key;

    @PostConstruct
    public void init() {
        this.key = keyInitializer.getSecretKey();
    }

    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }

    public UserModel saveUser(UserModel user) throws Exception{
        if(user.getRol() == null)
            user.setRol(0);
        if(user.getImagen() == null){
            Path path = Paths.get("src/main/resources/usuario.jpg");
            user.setImagen(Files.readAllBytes(path));
        }
            
        user.setContraseña(AESE.encrypt(user.getContraseña(), key));
        return userRepository.save(user);
    }

    public Optional<UserModel> findByCorreo(String Correo) {
        return userRepository.findByCorreo(Correo);
    }

    public UserModel updateByCorreo(UserModel request, int modo, String ncorreo, int mod) throws Exception {
        Optional<UserModel> existingUser = findByCorreo(request.getCorreo());
        
        if (existingUser.isPresent()) {
            UserModel userToUpdate = existingUser.get();
            
            userToUpdate.setNombre(request.getNombre());
            userToUpdate.setApellido(request.getApellido());
            userToUpdate.setContraseña(AESE.encrypt(request.getContraseña(), key));
            if(modo == 1){
                if(mod == 1){
                    userToUpdate.setContraseña(AESE.encrypt(request.getContraseña(), key));
                }else{
                    userToUpdate.setContraseña(request.getContraseña());
                }
                userToUpdate.setCorreo(ncorreo);
            }else{
                userToUpdate.setContraseña(request.getContraseña());
            }
            userToUpdate.setRol(request.getRol());
            userToUpdate.setImagen(request.getImagen());
            return userRepository.save(userToUpdate);
        } else {
            throw new RuntimeException("Usuario no encontrado con el correo: " + request.getCorreo());
        }
    }
}
