
package com.miportfolio.lp.Service;

import com.miportfolio.lp.Entity.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miportfolio.lp.Repository.RepoPersona;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Transactional
@Service
public class PersonaService{
    @Autowired RepoPersona repoPersona;
    
    public List<Persona> list() {
        return repoPersona.findAll();
    }
    
    public Optional<Persona> getOne(int id){
        return repoPersona.findById(id);
    }
    
    public Optional<Persona> getByNombre(String nombre){
        return repoPersona.findByNombre(nombre);
    }
    
    public void save(Persona persona){
        repoPersona.save(persona);
    }
    
    public void delete(int id){
        repoPersona.deleteById(id);
    }
    
    public boolean existsById(int id){
        return repoPersona.existsById(id);
    }
    
    public boolean existsByNombre(String nombre){
        return repoPersona.existsByNombre(nombre);
    }
}
