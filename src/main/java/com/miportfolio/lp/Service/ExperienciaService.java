package com.miportfolio.lp.Service;

import com.miportfolio.lp.Entity.Experiencia;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miportfolio.lp.Repository.RepoExperiencia;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExperienciaService {

    @Autowired
    RepoExperiencia repoExperiencia;

    public List<Experiencia> list() {
        return repoExperiencia.findAll();
    }
    
    public Optional<Experiencia> getOne(int id){
        return repoExperiencia.findById(id);
    }
    
    public Optional<Experiencia> getByNombreE(String nombreE){
        return repoExperiencia.findByNombreE(nombreE);
    }
    
    public void save(Experiencia exp){
        repoExperiencia.save(exp);
    }
    
    public void delete(int id){
        repoExperiencia.deleteById(id);
    }
    
    public boolean existsById(int id){
        return repoExperiencia.existsById(id);
    }
    
    public boolean existsByNombreE(String nombreE){
        return repoExperiencia.existsByNombreE(nombreE);
    }
}
