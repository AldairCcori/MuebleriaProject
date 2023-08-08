package com.ecommerce.muebleria.backend.services.impl;

import com.ecommerce.muebleria.backend.models.CategoriaProducto;
import com.ecommerce.muebleria.backend.repository.CategoriaRepository;
import com.ecommerce.muebleria.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> findAll() {
        return (List<CategoriaProducto>) categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public CategoriaProducto saveCategoria(CategoriaProducto categoriaProducto) {
        return categoriaRepository.save(categoriaProducto);
    }

    @Override
    @Transactional
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaProducto findById(Long id) {

        return categoriaRepository.findById(id).get();
    }
}
