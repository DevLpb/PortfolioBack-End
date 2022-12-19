package com.miportfolio.lp.Service;

import com.miportfolio.lp.Entity.Educacion;
import com.miportfolio.lp.Repository.RepoEducacion;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EducacionService {

    @Autowired
    RepoEducacion repoEducacion;

    public List<Educacion> list(){
        return repoEducacion.findAll();
    }
    
    public Optional<Educacion> getOne(int id){
        return repoEducacion.findById(id);
    }
    
    public Optional<Educacion> getByNombreE(String nombreE){
        return repoEducacion.findByNombreE(nombreE);
    }
    
    public void save(Educacion educacion){
        repoEducacion.save(educacion);
    }
    
    public void delete(int id){
        repoEducacion.deleteById(id);
    }
    
    public boolean existsById(int id){
        return repoEducacion.existsById(id);
    }
    
    public boolean existsByNombreE(String nombreE){
        return repoEducacion.existsByNombreE(nombreE);
    }

}
