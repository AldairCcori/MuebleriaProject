package com.ecommerce.muebleria.backend.models.dto;

import com.ecommerce.muebleria.backend.models.DetalleFactura;
import com.ecommerce.muebleria.backend.security.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
public class FacturaDTO {

    private Long id;

    private Date createAt;

    private String nombre;
    private String runUsuario;
    private String apellidoCliente;
    private String direccion;
    private String telefono;

    private Usuario usuario;

    private List<DetalleFactura>detalleFacturas;

}
