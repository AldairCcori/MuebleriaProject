package com.ecommerce.muebleria.backend.services.impl;

import com.ecommerce.muebleria.backend.models.Producto;
import com.ecommerce.muebleria.backend.repository.ProductoRepository;
import com.ecommerce.muebleria.backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto saveProduct(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productoRepository.deleteById(id);
    }

    public Page<Producto> paginas(Pageable pageable){
        return productoRepository.findAll(pageable);
    }
}
