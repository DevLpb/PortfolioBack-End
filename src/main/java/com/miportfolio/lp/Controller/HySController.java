
package com.miportfolio.lp.Controller;

import com.miportfolio.lp.Dto.DtoHyS;
import com.miportfolio.lp.Entity.HyS;
import com.miportfolio.lp.Security.Controller.Mensaje;
import com.miportfolio.lp.Service.HySService;
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
@CrossOrigin(origins = {"https://miportfolioapfrontend.web.app", "http://localhost:4200"})
@RequestMapping("/hys")
public class HySController {
    @Autowired
    HySService hysService;
    
   @GetMapping("/lista")
    public ResponseEntity<List<HyS>> list() {
        List<HyS> list = hysService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<HyS> getById(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!hysService.existsById(id)) {
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        }
        HyS hys = hysService.getOne(id).get();
        return new ResponseEntity(hys, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        
        //Validación. Comprueba la existencia del ID.
        if (!hysService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.NOT_FOUND);
        }

        hysService.delete(id);

        return new ResponseEntity(new Mensaje("Habilidad eliminada."), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoHyS dtoHyS) {
        
        //Validación. Comprueba si el campo está en vacío.
        if (StringUtils.isBlank(dtoHyS.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si ese nombre ya ha sido usado en otro campo.
        if (hysService.existsByNombre(dtoHyS.getNombre())) {
            return new ResponseEntity(new Mensaje("Esa habilidad ya existe."), HttpStatus.BAD_REQUEST);
        }

        HyS hys = new HyS(dtoHyS.getNombre(), dtoHyS.getPorcentaje());
        hysService.save(hys);

        return new ResponseEntity(new Mensaje("Habilidad agregada."), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoHyS dtoHyS) {

        //Validación. Comprueba la existencia del ID.
        if (!hysService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El id no existe."), HttpStatus.NOT_FOUND);
        }

        //Validación. Comparación de nombre de habilidades.    
        if (hysService.existsByNombre(dtoHyS.getNombre()) && hysService.getByNombre(dtoHyS.getNombre())
                .get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa habilidad ya existe."), HttpStatus.BAD_REQUEST);
        }

        //Validación. Comprueba si el campo está vacío.
        if (StringUtils.isBlank(dtoHyS.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio."), HttpStatus.BAD_REQUEST);
        }
        
        HyS hys = hysService.getOne(id).get();
        hys.setNombre(dtoHyS.getNombre());
        hys.setPorcentaje(dtoHyS.getPorcentaje());

        hysService.save(hys);

        return new ResponseEntity(new Mensaje("Habilidad actualizada."), HttpStatus.OK);
    }
}
