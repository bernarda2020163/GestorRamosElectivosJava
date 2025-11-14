package latinasincloud.GestorElectivosJava.controller;

import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;
import latinasincloud.GestorElectivosJava.model.Profesor;
import latinasincloud.GestorElectivosJava.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired // Inyección de dependencia por campo
    private ProfesorService profesorService;

    /**
     * POST /api/profesores : Crea un nuevo profesor.
     * Retorna 201 Created.
     */
    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody Profesor profesor) {
        Profesor nuevoProfesor = profesorService.crearProfesor(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProfesor);
    }

    /**
     * GET /api/profesores : Lista todos los profesores.
     * Retorna 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Profesor>> listaProfesores(){
        // Se asume que getProfesores() en el service es listaProfesores()
        return ResponseEntity.ok(profesorService.getProfesores());
    }

    /**
     * GET /api/profesores/{id} : Obtiene un profesor por su ID.
     * Retorna 200 OK o lanza RecursoNoEncontradoException (404).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerProfesorPorId(@PathVariable int id) {
        Profesor profesor = profesorService.obtenerProfesorPorId(id);
        if (profesor != null) {
            return ResponseEntity.ok(profesor);
        }
        else{
            // Lanza la excepción si el recurso no existe
            throw new RecursoNoEncontradoException("Profesor no encontrado con ID: " + id);
        }
    }

    /**
     * PUT /api/profesores/{id} : Actualiza los datos de un profesor existente.
     * Retorna 200 OK o lanza RecursoNoEncontradoException (404).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable int id, @RequestBody Profesor profesor) {
        // Se asume que el service tiene un método actualizarProfesor(id, profesor)
        Profesor profesorActualizado = profesorService.actualizarProfesor(id, profesor);
        if (profesorActualizado != null) {
            return ResponseEntity.ok(profesorActualizado);
        } else {
            throw new RecursoNoEncontradoException("Profesor no encontrado con ID: " + id);
        }
    }


    /**
     * DELETE /api/profesores/{id} : Elimina un profesor por su ID.
     * Retorna 204 No Content o lanza RecursoNoEncontradoException (404).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesorPorId(@PathVariable int id) {
        boolean eliminada = profesorService.eliminarProfesorPorId(id);

        if (eliminada){
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        else{
            // Lanza la excepción si el service indica que no se pudo eliminar (generalmente porque no existe)
            throw new RecursoNoEncontradoException("No se puede eliminar. Profesor no encontrado con ID: " + id);
        }
    }
}