package com.ecommerce.muebleria.backend.security.repository;


import com.ecommerce.muebleria.backend.models.CategoriaProducto;
import com.ecommerce.muebleria.backend.models.Producto;
import com.ecommerce.muebleria.backend.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

    public List<Usuario> findAll();

}
