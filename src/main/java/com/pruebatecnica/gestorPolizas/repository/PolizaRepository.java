package com.pruebatecnica.gestorPolizas.repository;

import com.pruebatecnica.gestorPolizas.Model.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza,Long> {
    List<Poliza> findByTipoAndEstado(String tipo, String estado);
}
