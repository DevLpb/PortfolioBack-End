
package com.miportfolio.lp.Interface;

import com.miportfolio.lp.Entity.Persona;
import java.util.List;

public interface IPersonaService {
    //Traer una lista de personas
    public List<Persona> getPersona();
    
    //Guardar un objeto de tipo Persona
    public void savePersona(Persona persona);
    
    //Eliminar un objeto (por ID)
    public void deletePersona(Long id);
    
    //Buscar una persona (por ID)
    public Persona findPersona(Long id);
}
