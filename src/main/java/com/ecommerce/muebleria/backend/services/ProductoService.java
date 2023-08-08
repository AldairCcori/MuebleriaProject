package com.ecommerce.muebleria.backend.services;

import com.ecommerce.muebleria.backend.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    public List<Producto> findAll();
    public Page<Producto> findAll(Pageable pageable);

    public Producto findById(Long id);

    public Producto saveProduct (Producto producto);

    public void deleteProduct(Long id);
}
