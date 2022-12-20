package com.miportfolio.lp.Service;

import com.miportfolio.lp.Entity.HyS;
import com.miportfolio.lp.Repository.RepoHyS;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class HySService {
    @Autowired
    RepoHyS repoHyS;
    
    public List<HyS> list() {
        return repoHyS.findAll();        
    }
    
    public Optional<HyS> getOne(int id) {
        return repoHyS.findById(id);
    }
    
    public Optional<HyS> getByNombre(String nombre) {
        return repoHyS.findByNombre(nombre);
    }
    
    public void save(HyS skill) {
        repoHyS.save(skill);
    }
    
    public void delete(int id) {
        repoHyS.deleteById(id);
    }
    
    public boolean existsById(int id) {
        return repoHyS.existsById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return repoHyS.existsByNombre(nombre);
    }
}
