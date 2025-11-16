package latinasincloud.GestorElectivosJava.service;



import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException; // Importar
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.repository.IEstudianteRepository; // Importar Repositorio
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Esta anotación es fundamental para que Spring lo detecte.
public class EstudianteService {

    // 1. Reemplazamos List<Estudiante> y contadorId por el Repositorio
    private final IEstudianteRepository estudianteRepository;

    // Inyección de dependencias por constructor
    public EstudianteService(IEstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // ---------------------------------------------------
    // MÉTODOS CRUD (Usando JPA Repository)
    // ---------------------------------------------------

    // Lógica para crear el estudiante
    public Estudiante crearEstudiante(Estudiante estudiante) {
        estudiante.setRol("Estudiante"); // Asigna el rol al crearlo
        // JPA asigna el ID automáticamente
        return estudianteRepository.save(estudiante);
    }

    // Lógica para listar los estudiantes
    public List<Estudiante> listaEstudiantes(){
        return estudianteRepository.findAll();
    }

    // Lógica para obtener por ID
    public Estudiante obtenerEstudiantePorId(int id){
        // Usamos findById y lanzamos excepción si no existe
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
    }

    // Lógica para eliminar por ID
    public boolean eliminarEstudiantePorId(int id){
        // Verificar existencia y eliminar
        Estudiante estudianteEliminar = obtenerEstudiantePorId(id);
        estudianteRepository.delete(estudianteEliminar);
        return true;
    }

}