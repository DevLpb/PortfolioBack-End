package com.miportfolio.lp.Controller;

import com.miportfolio.lp.Dto.DtoExperiencia;
import com.miportfolio.lp.Entity.Experiencia;
import com.miportfolio.lp.Security.Controller.Mensaje;
import com.miportfolio.lp.Service.ExperienciaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/explab")
@CrossOrigin(origins = "https://miportfolioapfrontend.web.app")
/*@CrossOrigin(origins = "http://localhost:4200")*/
public class ExperienciaController {

    @Autowired
    ExperienciaService expService;

    @GetMapping("/lista")
    public ResponseEntity<List<Experiencia>> list() {
        List<Experiencia> list = expService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Experiencia> getById(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!expService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        }
        Experiencia experiencia = expService.getOne(id).get();
        return new ResponseEntity(experiencia, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!expService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.NOT_FOUND);
        }

        expService.delete(id);

        return new ResponseEntity(new Mensaje("Experiencia eliminada."), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoExperiencia dtoExp) {
        
        //Validación. Comprueba si el campo está en vacío.
        if (StringUtils.isBlank(dtoExp.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si ese nombre ya ha sido usado en otro campo.
        if (expService.existsByNombreE(dtoExp.getNombreE())) {
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe."), HttpStatus.BAD_REQUEST);
        }

        Experiencia experiencia = new Experiencia(dtoExp.getNombreE(), dtoExp.getDescripcionE());
        expService.save(experiencia);

        return new ResponseEntity(new Mensaje("Experiencia agregada."), HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoExperiencia dtoExp) {

        //Validación. Comprueba la existencia del ID.
        if (!expService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El id no existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comparación de nombre de experiencias.    
        if (expService.existsByNombreE(dtoExp.getNombreE()) && expService.getByNombreE(dtoExp.getNombreE())
                .get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si el campo está vacío.
        if (StringUtils.isBlank(dtoExp.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre obligatorio."), HttpStatus.BAD_REQUEST);
        }

        Experiencia experiencia = expService.getOne(id).get();
        experiencia.setNombreE(dtoExp.getNombreE());
        experiencia.setDescripcionE(dtoExp.getDescripcionE());

        expService.save(experiencia);

        return new ResponseEntity(new Mensaje("Experiencia actualizada."), HttpStatus.OK);
    }
}
