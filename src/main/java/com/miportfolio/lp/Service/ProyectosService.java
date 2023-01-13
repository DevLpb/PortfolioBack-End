
package com.miportfolio.lp.Service;

import com.miportfolio.lp.Entity.Proyectos;
import com.miportfolio.lp.Repository.RepoProyectos;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional  
@Service
public class ProyectosService {
    @Autowired RepoProyectos repoProyectos;
    
    public List<Proyectos> list() {
        return repoProyectos.findAll();
    }
    
    public Optional<Proyectos> getOne(int id){
        return repoProyectos.findById(id);
    }
    
    public Optional<Proyectos> getByNombre(String nombre){
        return repoProyectos.findByNombre(nombre);
    }
    
    public void save(Proyectos proyectos){
        repoProyectos.save(proyectos);
    }
    
    public void delete(int id){
        repoProyectos.deleteById(id);
    }
    
    public boolean existsById(int id){
        return repoProyectos.existsById(id);
    }
    
    public boolean existsByNombre(String nombre){
        return repoProyectos.existsByNombre(nombre);
    }
}
