package com.ecommerce.muebleria.backend.controllers;

import com.ecommerce.muebleria.backend.models.Producto;
import com.ecommerce.muebleria.backend.services.ProductoService;
import com.ecommerce.muebleria.backend.services.impl.ProductoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductoControllers {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoServiceImpl productoServiceImpl;

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productoLista")
    public ResponseEntity<Page<Producto>> paginas (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "nombre")String order,
                                                   @RequestParam(defaultValue = "true") boolean asc){
        Page<Producto> producto = productoServiceImpl.paginas(
                PageRequest.of(page,size, Sort.by(order)));
        if (!asc)
            producto = productoServiceImpl.paginas(
                    PageRequest.of(page,size, Sort.by(order).descending()));
        return new ResponseEntity<Page<Producto>>(producto, HttpStatus.OK);

    }

    @GetMapping("/productos")
    public List<Producto> index(){
        return productoService.findAll();
    }

    @GetMapping("/productos/page/{page}")
    public Page<Producto> index(@PathVariable Integer page){
        return productoService.findAll(PageRequest.of(page, 5));
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Producto producto = null;
        Map<String, String> response = new HashMap<>();
        try {
            producto = productoService.findById(id);
        }catch (DataAccessException exception){
            response.put("mensaje", "Error al hacer la consulta a la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (producto == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("No existe en la base de datos")));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Producto>(producto, HttpStatus.OK);
    }


    @PostMapping("/productos")
    public ResponseEntity<?> createProduct(@RequestBody Producto producto){

        Producto productoNew = null;
        Map<String, String> response = new HashMap<>();
        try {
            productoNew = productoService.saveProduct(producto);
        }catch(DataAccessException exception){
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El producto ha sido creado con exito");
        response.put("Producto", String.valueOf(productoNew));
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }


    @PutMapping("/productos/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Producto producto, @PathVariable Long id){
        Producto productoActual = productoService.findById(id);
        Producto productoUpdate = null;

        Map<String, String> response = new HashMap<>();

        if (productoActual == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("No existe en la base de datos")));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            productoActual.setNombre(producto.getNombre());
            productoActual.setDescripcion((producto.getDescripcion()));
            productoActual.setCantidad(producto.getCantidad());
            productoActual.setPrecio(producto.getPrecio());
            productoActual.setCategoriaProducto(producto.getCategoriaProducto());

            productoUpdate = productoService.saveProduct(productoActual);
        }catch(DataAccessException exception){
            response.put("mensaje", "Error al realizar la actualizar a la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.put("mensaje", "El producto ha sido actualizado con exito");
        response.put("Producto", String.valueOf(productoUpdate));
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }


    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){

        Map<String, String> response = new HashMap<>();
        try{

            Producto producto = productoService.findById(id);

            String nombreFotoAnterior = producto.getImagen();

            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }

            productoService.deleteProduct(id);
        }catch(DataAccessException exception){
            response.put("mensaje", "Error al realizar la eliminacion a la base de datos");
            response.put("error", exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El producto ha sido eliminado con exito");
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }


    @PostMapping("/productos/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id") Long id){

        Map<String, String> response = new HashMap<>();

        Producto producto = productoService.findById(id);

        if (!archivo.isEmpty()){

            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");

            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

            log.info(rutaArchivo.toString());

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la iamgen a la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = producto.getImagen();

            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }

            producto.setImagen(nombreArchivo);

            productoService.saveProduct(producto);

            response.put("Producto", String.valueOf(producto));
            response.put("mensaje", "has subido correctamente la imagen: " + nombreArchivo);

        }

        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }


    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        log.info(rutaArchivo.toString());

        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!recurso.exists() && !recurso.isReadable()){
            throw new RuntimeException("Error no se pudo cargar la imagen");
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);

    }
}
