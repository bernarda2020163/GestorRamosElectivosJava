package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Estudiante;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteService {

    private final List<Estudiante> estudiantes = new ArrayList<>();
    private static int contadorId = 1;

    public Estudiante crearEstudiante(Estudiante estudiante) {
        estudiante.setId(contadorId++);
        estudiantes.add(estudiante);
        estudiante.setRol("Estudiante");
        return estudiante;
    }

    public List<Estudiante> listaEstudiantes(){
        return estudiantes;
    }

    public Estudiante obtenerEstudiantePorId(int id){
        for (Estudiante estudiante : estudiantes){
            if(estudiante.getId() == id){
                return estudiante;
            }
        }
        return null;
    }

    public boolean eliminarEstudiantePorId(int id){
        Estudiante estudianteEliminar = obtenerEstudiantePorId(id);
        if (estudianteEliminar != null){
            estudiantes.remove(estudianteEliminar);
            System.out.println("¡El estudiante ha sido eliminado del registro exitosamente!");
            return true;
        }
        else{
        return false;}

    }

}
