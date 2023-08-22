package com.pruebas.banco.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.pruebas.banco.entidades.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    @Query("SELECT c FROM Cuenta c WHERE c.persona=?1")
    public Optional<Cuenta> findByPersona(String persona);
    
}
