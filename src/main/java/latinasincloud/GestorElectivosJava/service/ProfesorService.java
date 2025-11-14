package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Profesor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Asegúrate de incluir la anotación @Service para que Spring lo detecte
public class ProfesorService {

    // Lista de profesores y contador Id incremental
    private final List<Profesor> profesores = new ArrayList<>();
    private static int contadorId = 1;


    // 1. Crear profesor (POST)
    public Profesor crearProfesor(Profesor profesor){
        profesor.setId(contadorId++);
        profesores.add(profesor);
        return profesor;
    }

    // 2. Listar profesor. (GET)
    public List<Profesor> getProfesores(){
        return profesores;
    }

    // 3. Obtener profesor por ID. (GET)
    public Profesor obtenerProfesorPorId(int id){
        for(Profesor p : profesores){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    // 4. Actualizar profesor por ID. (PUT)
    // Nuevo método implementado
    public Profesor actualizarProfesor(int id, Profesor profesorActualizado) {
        Profesor profesorExistente = obtenerProfesorPorId(id);

        if (profesorExistente != null) {
            // Actualiza solo los campos permitidos.
            // La ID no se actualiza (ya está en profesorExistente).
            profesorExistente.setNombre(profesorActualizado.getNombre());
            profesorExistente.setEspecialidad(profesorActualizado.getEspecialidad());

            // Retorna la referencia al profesor actualizado.
            return profesorExistente;
        }
        // Si no se encuentra, retorna null, lo que el Controller interpretará como 404.
        return null;
    }


    // 5. Eliminar profesor por ID. (DELETE)
    public boolean eliminarProfesorPorId(int id){
        Profesor eliminar = obtenerProfesorPorId(id);
        if (eliminar != null) {
            profesores.remove(eliminar);
            return true;
        }
        return false;
    }
}