package com.miportfolio.lp.Controller;

import com.miportfolio.lp.Dto.DtoEducacion;
import com.miportfolio.lp.Entity.Educacion;
import com.miportfolio.lp.Security.Controller.Mensaje;
import com.miportfolio.lp.Service.EducacionService;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/educacion")

public class EducacionController {
    @Autowired
    EducacionService educService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Educacion>> list() {
        List<Educacion> list = educService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Educacion> getById(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!educService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        }
        Educacion educacion = educService.getOne(id).get();
        return new ResponseEntity(educacion, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!educService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.NOT_FOUND);
        }

        educService.delete(id);

        return new ResponseEntity(new Mensaje("Educación eliminada."), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoEducacion dtoEduc) {
        
        //Validación. Comprueba si el campo está en vacío.
        if (StringUtils.isBlank(dtoEduc.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }
        
        //Validación. Comprueba si ese nombre ya ha sido usado en otro campo.
        if (educService.existsByNombreE(dtoEduc.getNombreE())) {
            return new ResponseEntity(new Mensaje("Ese nombre ya existe."), HttpStatus.BAD_REQUEST);
        }
        
        Educacion educacion = new Educacion
                (dtoEduc.getNombreE(), dtoEduc.getDescripcionE());
        educService.save(educacion);
        return new ResponseEntity(new Mensaje("Educación creada."), HttpStatus.OK);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoEducacion dtoEduc) {

        //Validación. Comprueba la existencia del ID.
        if (!educService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comparación de nombre de experiencias.    
        if (educService.existsByNombreE(dtoEduc.getNombreE()) && educService.getByNombreE(dtoEduc.getNombreE())
                .get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa educación ya existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si el campo está vacío.
        if (StringUtils.isBlank(dtoEduc.getNombreE())) {
            return new ResponseEntity(new Mensaje("El nombre obligatorio."), HttpStatus.BAD_REQUEST);
        }

        Educacion educacion = educService.getOne(id).get();
        educacion.setNombreE(dtoEduc.getNombreE());
        educacion.setDescripcionE(dtoEduc.getDescripcionE());

        educService.save(educacion);

        return new ResponseEntity(new Mensaje("Educación actualizada."), HttpStatus.OK);
    }
}