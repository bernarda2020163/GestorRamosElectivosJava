package latinasincloud.GestorElectivosJava.controller;


import latinasincloud.GestorElectivosJava.dto.PostulacionRequestDTO; // <-- IMPORTAR DTO
import latinasincloud.GestorElectivosJava.exception.EstadoInvalidoException;
import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.model.Postulacion; // <-- IMPORTAR
import latinasincloud.GestorElectivosJava.service.EstudianteService;
import latinasincloud.GestorElectivosJava.service.PostulacionService; // <-- IMPORTAR SERVICE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* Se añade un nuevo endpoint para que el
estudiante pueda enviar su solicitud de 3 electivos con prioridad.
 */

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired // <-- MODIFICACIÓN: Inyectar PostulacionService
    private PostulacionService postulacionService;

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevaEstudiante = estudianteService.crearEstudiante(estudiante);
        return ResponseEntity.status(201).body(nuevaEstudiante);
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listaEstudiantes(){
        return ResponseEntity.ok(estudianteService.listaEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorId(@PathVariable int id) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(id);
        if (estudiante != null) {
            return ResponseEntity.ok(estudiante);
        }
        else{
            throw new RecursoNoEncontradoException("Estudiante no encontrada con ID: " + id);
        }
    }

    // NUEVO ENDPOINT AÑADIDO: Postulación de 3 electivos con prioridad
    @PostMapping("/postular")
    public ResponseEntity<List<Postulacion>> postularElectivos(@RequestBody PostulacionRequestDTO postulacionRequest) {
        // La validación detallada se hace en el Service, aquí solo se llama al proceso
        List<Postulacion> nuevasPostulaciones = postulacionService.crearPostulacionesConPrioridad(postulacionRequest);

        // Retorna las 3 postulaciones creadas (aún en estado PENDIENTE)
        return ResponseEntity.status(201).body(nuevasPostulaciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiantePorId(@PathVariable int id) {
        boolean eliminada = estudianteService.eliminarEstudiantePorId(id);

        if (eliminada){
            return ResponseEntity.noContent().build();
        }
        else{
            Estudiante estudiante = estudianteService.obtenerEstudiantePorId(id);

            throw new RecursoNoEncontradoException("No se puede eliminar. Estudiante no encontrada con ID: " + id);
        }
    }

}
