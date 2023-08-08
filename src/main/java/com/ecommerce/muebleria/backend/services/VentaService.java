package com.ecommerce.muebleria.backend.services;

import com.ecommerce.muebleria.backend.models.Factura;
import com.ecommerce.muebleria.backend.models.Producto;

public interface VentaService {
    public Factura saveFactura (Factura factura);
}
