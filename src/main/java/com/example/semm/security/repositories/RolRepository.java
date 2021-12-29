package com.example.semm.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.semm.security.enums.RolNombre;
import com.example.semm.security.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer>{
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
