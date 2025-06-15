package com.example.Practica2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Practica2.models.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, Long>{
    Optional<UserModel> findByCorreo(String correo);    
}
