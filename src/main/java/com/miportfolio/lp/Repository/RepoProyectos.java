
package com.miportfolio.lp.Repository;

import com.miportfolio.lp.Entity.Proyectos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoProyectos extends JpaRepository<Proyectos, Integer> {
    public Optional<Proyectos> findByNombre (String nombre);
    public boolean existsByNombre(String nombre);
}
