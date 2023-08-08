package com.ecommerce.muebleria.backend.controllers;

import com.ecommerce.muebleria.backend.models.DetalleFactura;
import com.ecommerce.muebleria.backend.models.Factura;
import com.ecommerce.muebleria.backend.models.Producto;
import com.ecommerce.muebleria.backend.models.dto.FacturaDTO;
import com.ecommerce.muebleria.backend.models.dto.VentaDTO;
import com.ecommerce.muebleria.backend.security.entity.Usuario;
import com.ecommerce.muebleria.backend.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.jsf.FacesContextUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    VentaService ventaService;


    @PostMapping("/grabarVenta")
    public Factura paginas (@RequestBody VentaDTO ventaDTO){
        System.out.println("recibido" + buildFactura(ventaDTO));
        System.out.println("RECIBIDO DTO " + ventaDTO);



        Factura factura=  ventaService.saveFactura(buildFactura(ventaDTO));



        return factura;

    }
    private Factura buildFactura(VentaDTO ventaDTO){

        Factura factura=new Factura();
        factura.setDetalleFacturas(buildDetalleFactura(ventaDTO));
        factura.setDireccion(ventaDTO.getUsuario().getDireccion());
        factura.setNombre(ventaDTO.getUsuario().getNombre());
        factura.setTelefono(ventaDTO.getUsuario().getTelefono());
        factura.setApellidoCliente(ventaDTO.getUsuario().getApellidoMaterno() + ventaDTO.getUsuario().getApellidoPaterno());
        factura.setRunUsuario(ventaDTO.getUsuario().getNombreUsuario());
        factura.setUsuario(buildUsuario(ventaDTO));


      return factura;

    }

    private List<DetalleFactura> buildDetalleFactura(VentaDTO ventaDT){
        System.out.println("ventaDTO : " + ventaDT.getProductos());
       return  ventaDT.getProductos().stream()
                .map(producto->{
                    DetalleFactura detalleFactura = new DetalleFactura();
                    detalleFactura.setCantidad(producto.getCantidad());
                    detalleFactura.setPrecio(producto.getPrecio());
                    detalleFactura.setNombre(producto.getNombre());
                    Float total = producto.getCantidad()*producto.getPrecio();
                    detalleFactura.setTotal(total);
                    Producto pro= new Producto();
                    pro.setId(producto.getId());
                    detalleFactura.setProducto(pro);
                    return detalleFactura;
                }).collect(Collectors.toList());

    }
    private Usuario buildUsuario(VentaDTO ventaDTO){
        Usuario usuario=new Usuario();
        usuario.setId(ventaDTO.getUsuario().getId());
        usuario.setEmail(ventaDTO.getUsuario().getEmail());

        return usuario;
    }
}
