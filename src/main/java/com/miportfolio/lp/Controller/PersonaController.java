package com.miportfolio.lp.Controller;

import com.miportfolio.lp.Dto.DtoPersona;
import com.miportfolio.lp.Entity.Persona;
import com.miportfolio.lp.Security.Controller.Mensaje;
import com.miportfolio.lp.Service.PersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = {"https://miportfolioapfrontend.web.app", "http://localhost:4200"})
public class PersonaController{
    
    @Autowired
    PersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list() {
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        }
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }

    //Borrar y Crear nuevas personas. Actualmente sin uso.
    
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") int id) {
//        
//        //Validación. Comprueba la existencia del ID.
//        if (!personaService.existsById(id)) {
//            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.NOT_FOUND);
//        }
//
//        personaService.delete(id);
//
//        return new ResponseEntity(new Mensaje("Experiencia eliminada."), HttpStatus.OK);
//    }

//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestBody DtoPersona dtoPersona) {
//        
//        //Validación. Comprueba si el campo está en vacío.
//        if (StringUtils.isBlank(dtoPersona.getNombre())) {
//            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
//        }
//
//        //Validación. Comprueba si ese nombre ya ha sido usado en otro campo.
//        if (personaService.existsByNombre(dtoPersona.getNombre())) {
//            return new ResponseEntity(new Mensaje("Esa persona ya existe."), HttpStatus.BAD_REQUEST);
//        }
//
//        Persona persona = new Persona(dtoPersona.getNombre(), dtoPersona.getApellido(),
//                dtoPersona.getDescripcion(), dtoPersona.getImg());
//        personaService.save(persona);
//
//        return new ResponseEntity(new Mensaje("Persona agregada."), HttpStatus.OK);
//    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoPersona dtoPersona) {

        //Validación. Comprueba la existencia del ID.
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El id no existe."), HttpStatus.NOT_FOUND);
        }

        //Validación. Comparación de nombre de personas.    
        if (personaService.existsByNombre(dtoPersona.getNombre()) && personaService.getByNombre(dtoPersona.getNombre())
                .get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa persona ya existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si el campo está vacío.
        if (StringUtils.isBlank(dtoPersona.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }

        Persona persona = personaService.getOne(id).get();
        persona.setNombre(dtoPersona.getNombre());
        persona.setApellido(dtoPersona.getApellido());
        persona.setDescripcion(dtoPersona.getDescripcion());
        persona.setImg(dtoPersona.getImg());

        personaService.save(persona);

        return new ResponseEntity(new Mensaje("Persona actualizada."), HttpStatus.OK);
    }
    
}
