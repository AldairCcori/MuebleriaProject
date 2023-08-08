package com.ecommerce.muebleria.backend.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    Long id;
    String nombre;

    Float precio;

    String descripcion;


    String imagen;

    Integer cantidad;

    CategoriaDTO categoria;

}
