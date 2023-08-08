package com.ecommerce.muebleria.backend.controllers;

import com.ecommerce.muebleria.backend.models.CategoriaProducto;
import com.ecommerce.muebleria.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis")
@CrossOrigin(origins = "*")
public class CategoriaControllers {

    @Autowired
    private CategoriaService categoriaService;



    @GetMapping("/categoria")
    public List<CategoriaProducto> index(){
        return categoriaService.findAll();
    }


    @GetMapping("/categoria/{id}")
    public CategoriaProducto show(@PathVariable Long id){
        return categoriaService.findById(id);
    }


    @PostMapping("/categoria")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaProducto create(@RequestBody CategoriaProducto categoriaProducto){

        System.out.println("informacion recibida categroaio" + categoriaProducto);
        return categoriaService.saveCategoria(categoriaProducto);
    }

    @PutMapping ("/categoria/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaProducto update(@RequestBody CategoriaProducto categoriaProducto, @PathVariable Long id){

        System.out.println("DATO INGRESO UP" + categoriaProducto +" " +id);
        CategoriaProducto categoriaProductoActual = categoriaService.findById(id);
        categoriaProductoActual.setDescripcion(categoriaProducto.getDescripcion());

        System.out.println("EDITAR * " + categoriaProducto + " "+id);
        return categoriaService.saveCategoria(categoriaProductoActual);
    }

    @DeleteMapping("/categoria/{id}")
    public void delete(@PathVariable Long id){
        categoriaService.deleteCategoria(id);
    }

}
