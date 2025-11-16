package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Estudiante;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Esta anotación es fundamental para que Spring lo detecte.
public class EstudianteService {

    private final List<Estudiante> estudiantes = new ArrayList<>();
    private static int contadorId = 1;

    // Lógica para crear el estudiante
    public Estudiante crearEstudiante(Estudiante estudiante) {
        estudiante.setId(contadorId++);
        estudiantes.add(estudiante);
        estudiante.setRol("Estudiante"); // Asigna el rol al crearlo
        return estudiante;
    }

    // Lógica para listar los estudiantes
    public List<Estudiante> listaEstudiantes(){
        return estudiantes;
    }

    // Lógica para obtener por ID
    public Estudiante obtenerEstudiantePorId(int id){
        for (Estudiante estudiante : estudiantes){
            if(estudiante.getId() == id){
                return estudiante; // Devuelve el estudiante si lo encuentra
            }
        }
        return null; // Devuelve null si no lo encuentra
    }

    // Lógica para eliminar por ID
    public boolean eliminarEstudiantePorId(int id){
        Estudiante estudianteEliminar = obtenerEstudiantePorId(id);
        if (estudianteEliminar != null){
            estudiantes.remove(estudianteEliminar);
            // (Mejora opcional: reemplazar esto por un Logger)
            System.out.println("¡El estudiante ha sido eliminado del registro exitosamente!");
            return true;
        }
        else{
        return false;}

    }

}
