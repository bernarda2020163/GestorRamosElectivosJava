package latinasincloud.GestorElectivosJava.controller;

import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;
import latinasincloud.GestorElectivosJava.model.Administrador;
import latinasincloud.GestorElectivosJava.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

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

    // -------------------------------------------------------------------------
    // MÉTODO DE NEGOCIO (Basado en el AdministradorService)
    // -------------------------------------------------------------------------

    /**
     * PUT /api/administradores/revisar-postulacion/{postulacionId}
     * Realiza la revisión de una postulación, cambiando su estado a ACEPTADA o RECHAZADA.
     */
    @PutMapping("/revisar-postulacion/{postulacionId}")
    public ResponseEntity<Void> revisarPostulacion(@PathVariable int postulacionId) {

        // Llama al service para aplicar la lógica de revisión de cupos
        administradorService.revisarPostulacion(postulacionId);

        // Se asume que la verificación de existencia de la Postulación y el manejo
        // de excepciones por recurso no encontrado (RecursoNoEncontradoException)
        // se manejan dentro del AdministradorService o en la capa de PostulacionService.

        // Retorna 200 OK para indicar que la acción se completó con éxito
        return ResponseEntity.ok().build();
    }
}