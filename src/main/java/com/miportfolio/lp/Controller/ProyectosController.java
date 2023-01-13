
package com.miportfolio.lp.Controller;

import com.miportfolio.lp.Dto.DtoProyectos;
import com.miportfolio.lp.Entity.Proyectos;
import com.miportfolio.lp.Security.Controller.Mensaje;
import com.miportfolio.lp.Service.ProyectosService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/proyectos")
@CrossOrigin(origins = {"https://miportfolioapfrontend.web.app", "http://localhost:4200"})
public class ProyectosController {
    
    @Autowired
    ProyectosService proyectosService;
    
     @GetMapping("/lista")
    public ResponseEntity<List<Proyectos>> list() {
        List<Proyectos> list = proyectosService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Proyectos> getById(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!proyectosService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        }
        Proyectos proyectos = proyectosService.getOne(id).get();
        return new ResponseEntity(proyectos, HttpStatus.OK);
    }

    //Borrar y Crear nuevos proyectos.
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!proyectosService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.NOT_FOUND);
        }

        proyectosService.delete(id);

        return new ResponseEntity(new Mensaje("Proyecto eliminado."), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoProyectos dtoProyectos) {
        
        //Validación. Comprueba si el campo está en vacío.
        if (StringUtils.isBlank(dtoProyectos.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si ese nombre ya ha sido usado en otro campo.
        if (proyectosService.existsByNombre(dtoProyectos.getNombre())) {
            return new ResponseEntity(new Mensaje("Esa nombre de proyecto ya existe."), HttpStatus.BAD_REQUEST);
        }

        Proyectos proyectos = new Proyectos(dtoProyectos.getNombre(),
                dtoProyectos.getDescripcion(), dtoProyectos.getImg());
        proyectosService.save(proyectos);

        return new ResponseEntity(new Mensaje("Proyecto agregado."), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoProyectos dtoProyectos) {

        //Validación. Comprueba la existencia del ID.
        if (!proyectosService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El id no existe."), HttpStatus.NOT_FOUND);
        }

        //Validación. Comparación de nombre de proyectos.    
        if (proyectosService.existsByNombre(dtoProyectos.getNombre()) && proyectosService
                .getByNombre(dtoProyectos.getNombre())
                .get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa nombre de proyecto ya existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si el campo está vacío.
        if (StringUtils.isBlank(dtoProyectos.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }

        Proyectos proyectos = proyectosService.getOne(id).get();
        proyectos.setNombre(dtoProyectos.getNombre());
        proyectos.setDescripcion(dtoProyectos.getDescripcion());
        proyectos.setImg(dtoProyectos.getImg());

        proyectosService.save(proyectos);

        return new ResponseEntity(new Mensaje("Proyecto actualizado."), HttpStatus.OK);
    }
    
}
