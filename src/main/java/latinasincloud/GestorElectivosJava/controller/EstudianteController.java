package latinasincloud.GestorElectivosJava.controller;

import latinasincloud.GestorElectivosJava.exception.EstadoInvalidoException;
import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

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
