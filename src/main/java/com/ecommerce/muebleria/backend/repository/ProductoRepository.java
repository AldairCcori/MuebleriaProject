package com.ecommerce.muebleria.backend.repository;


import com.ecommerce.muebleria.backend.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
