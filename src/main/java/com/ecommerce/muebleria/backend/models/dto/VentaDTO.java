package com.ecommerce.muebleria.backend.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class VentaDTO {

    UsuarioDTO usuario;
    List<ProductoDTO> productos;

    public VentaDTO(UsuarioDTO usuario, List<ProductoDTO> productos) {
        this.usuario = usuario;
        this.productos = productos;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "VentaDTO{" +
                "usuario=" + usuario +
                ", productos=" + productos +
                '}';
    }
}
