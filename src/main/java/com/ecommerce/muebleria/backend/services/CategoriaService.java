package com.ecommerce.muebleria.backend.services;

import com.ecommerce.muebleria.backend.models.CategoriaProducto;
import com.ecommerce.muebleria.backend.models.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoriaService {

    public List<CategoriaProducto> findAll();
    public CategoriaProducto saveCategoria (CategoriaProducto categoriaProducto);

    public void deleteCategoria(Long id);
    public CategoriaProducto findById(Long id);
}
