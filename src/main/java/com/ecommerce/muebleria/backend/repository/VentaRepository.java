package com.ecommerce.muebleria.backend.repository;

import com.ecommerce.muebleria.backend.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Factura, Long> {
}
