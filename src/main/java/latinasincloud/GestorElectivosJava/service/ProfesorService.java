package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException; // Importar
import latinasincloud.GestorElectivosJava.model.Profesor;
import latinasincloud.GestorElectivosJava.repository.IProfesorRepository; // Importar Repositorio
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service // Asegúrate de incluir la anotación @Service para que Spring lo detecte
public class ProfesorService {

    // 1. Reemplazamos List<Profesor> y contadorId por el Repositorio
    private final IProfesorRepository profesorRepository;

    // Inyección de dependencias por constructor
    public ProfesorService(IProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }


    // ---------------------------------------------------
    // MÉTODOS CRUD (Usando JPA Repository)
    // ---------------------------------------------------

    // 1. Crear profesor (POST)
    public Profesor crearProfesor(Profesor profesor){
        // JPA asigna el ID automáticamente
        return profesorRepository.save(profesor);
    }

    // 2. Listar profesor. (GET)
    public List<Profesor> getProfesores(){
        return profesorRepository.findAll();
    }

    // 3. Obtener profesor por ID. (GET)
    public Profesor obtenerProfesorPorId(int id){
        // Usamos findById y lanzamos excepción si no existe
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesor no encontrado con ID: " + id));
    }

    // 4. Actualizar profesor por ID. (PUT)
    @Transactional
    public Profesor actualizarProfesor(int id, Profesor profesorActualizado) {
        // Obtener el profesor existente (lanza 404 si no existe)
        Profesor profesorExistente = obtenerProfesorPorId(id);

        // Actualiza solo los campos permitidos.
        profesorExistente.setNombre(profesorActualizado.getNombre() != null && !profesorActualizado.getNombre().isEmpty() ? profesorActualizado.getNombre() : profesorExistente.getNombre());
        profesorExistente.setEspecialidad(profesorActualizado.getEspecialidad() != null && !profesorActualizado.getEspecialidad().isEmpty() ? profesorActualizado.getEspecialidad() : profesorExistente.getEspecialidad());
        //profesorExistente.setRut(profesorActualizado.getRut() != null && !profesorActualizado.getRut().isEmpty() ? profesorActualizado.getRut() : profesorExistente.getRut());

        // Persistir el cambio
        return profesorRepository.save(profesorExistente);
    }


    // 5. Eliminar profesor por ID. (DELETE)
    public boolean eliminarProfesorPorId(int id){
        // Verificar existencia y eliminar
        Profesor profesor = obtenerProfesorPorId(id);
        profesorRepository.delete(profesor);
        return true;
    }
}