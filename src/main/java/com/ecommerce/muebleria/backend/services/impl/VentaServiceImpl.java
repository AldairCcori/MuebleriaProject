package com.ecommerce.muebleria.backend.services.impl;


import com.ecommerce.muebleria.backend.models.Factura;
import com.ecommerce.muebleria.backend.repository.VentaRepository;
import com.ecommerce.muebleria.backend.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl implements VentaService {
    @Autowired
    VentaRepository ventaRepository;
    @Override
    public Factura saveFactura(Factura factura) {
        return ventaRepository.save(factura);
    }
}
