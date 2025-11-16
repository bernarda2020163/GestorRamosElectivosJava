package latinasincloud.GestorElectivosJava.controller;

import latinasincloud.GestorElectivosJava.model.Postulacion;
import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;
import latinasincloud.GestorElectivosJava.model.Administrador;
import latinasincloud.GestorElectivosJava.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Este controlador permitirá a un administrador ejecutar la asignación masiva.

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // -------------------------------------------------------------------------
    // MÉTODOS CRUD PARA LA ENTIDAD ADMINISTRADOR (Estructura estándar)
    // -------------------------------------------------------------------------

    // POST: Crear nuevo Administrador
    // Se asume que AdministradorService tiene un método crearAdministrador(Administrador)
    @PostMapping
    public ResponseEntity<Administrador> crearAdministrador(@RequestBody Administrador administrador) {
        Administrador nuevoAdmin = administradorService.crearAdministrador(administrador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdmin);
    }

    // GET: Listar todos los Administradores
    // Se asume que AdministradorService tiene un método listaAdministradores()
    @GetMapping
    public ResponseEntity<List<Administrador>> listaAdministradores(){
        return ResponseEntity.ok(administradorService.listaAdministradores());
    }

    // GET: Obtener Administrador por ID
    // Se asume que AdministradorService tiene un método obtenerAdministradorPorId(int)
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerAdministradorPorId(@PathVariable int id) {
        Administrador administrador = administradorService.obtenerAdministradorPorId(id);
        if (administrador != null) {
            return ResponseEntity.ok(administrador);
        }
        else{
            throw new RecursoNoEncontradoException("Administrador no encontrado con ID: " + id);
        }
    }

    // PUT: Actualizar Administrador por ID
    // Se asume que AdministradorService tiene un método actualizarAdministrador(int, Administrador)
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizarAdministrador(@PathVariable int id, @RequestBody Administrador administrador) {
        Administrador adminActualizado = administradorService.actualizarAdministrador(id, administrador);
        if (adminActualizado != null) {
            return ResponseEntity.ok(adminActualizado);
        } else {
            throw new RecursoNoEncontradoException("Administrador no encontrado con ID: " + id);
        }
    }

    // DELETE: Eliminar Administrador por ID
    // Se asume que AdministradorService tiene un método eliminarAdministradorPorId(int)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAdministradorPorId(@PathVariable int id) {
        boolean eliminada = administradorService.eliminarAdministradorPorId(id);

        if (eliminada){
            return ResponseEntity.noContent().build();
        }
        else{
            throw new RecursoNoEncontradoException("No se puede eliminar. Administrador no encontrado con ID: " + id);
        }
    }

    // Endpoint para activar el proceso de asignación masiva.
    // Esto lo debe hacer un administrador al final del periodo de postulación.
    @PostMapping("/asignacion-masiva")
    public ResponseEntity<List<Postulacion>> realizarAsignacionMasiva() {
        // Llama al servicio para ejecutar la lógica de asignación por prioridad
        List<Postulacion> asignacionesAceptadas = administradorService.realizarAsignacionMasiva();

        // Retorna las postulaciones que fueron exitosamente ACEPTADAS
        return ResponseEntity.ok(asignacionesAceptadas);
    }


}