package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Administrador;
import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Estado;
import latinasincloud.GestorElectivosJava.model.Postulacion;
import org.springframework.stereotype.Service; // Importar la anotación @Service

import java.util.ArrayList;
import java.util.List;

@Service // Añadida la anotación para que Spring lo reconozca
public class AdministradorService {

    // CRUD: Lista en memoria y contador de ID para Administrador
    private final List<Administrador> administradores = new ArrayList<>();
    private static int contadorId = 1;

    // Servicios inyectados para el método de negocio (existente)
    private final PostulacionService postulacionService;
    private final ElectivoService electivoService;

    // Constructor para inyección de dependencias
    public AdministradorService(PostulacionService postulacionService, ElectivoService electivoService) {
        this.postulacionService = postulacionService;
        this.electivoService = electivoService;
    }

    // ---------------------------------------------------
    // 1. Crear Administrador (POST)
    public Administrador crearAdministrador(Administrador administrador) {
        administrador.setId(contadorId++);
        administradores.add(administrador);
        return administrador;
    }

    // 2. Listar Administradores (GET)
    public List<Administrador> listaAdministradores() {
        return administradores;
    }

    // 3. Obtener Administrador por ID (GET)
    public Administrador obtenerAdministradorPorId(int id) {
        for (Administrador a : administradores) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // 4. Actualizar Administrador por ID (PUT)
    public Administrador actualizarAdministrador(int id, Administrador adminActualizado) {
        Administrador adminExistente = obtenerAdministradorPorId(id);

        if (adminExistente != null) {
            // Actualizar campos heredados de Usuario
            adminExistente.setNombre(adminActualizado.getNombre());
            adminExistente.setEmail(adminActualizado.getEmail());
            adminExistente.setPassword(adminActualizado.getPassword());
            adminExistente.setRol(adminActualizado.getRol());

            // Actualizar campo propio de Administrador
            adminExistente.setCargo(adminActualizado.getCargo());

            return adminExistente;
        }
        return null;
    }

    // 5. Eliminar Administrador por ID (DELETE)
    public boolean eliminarAdministradorPorId(int id) {
        Administrador eliminar = obtenerAdministradorPorId(id);
        if (eliminar != null) {
            administradores.remove(eliminar);
            return true;
        }
        return false;
    }

    // ---------------------------------------------------
    // MÉTODO DE NEGOCIO (Existente, con verificación de nulidad)
    // ---------------------------------------------------

    public void revisarPostulacion (int postulacionId){
        // Obtener la postulación
        Postulacion postulacionRevisar = postulacionService.obtenerPostulacionPorId(postulacionId);

        // Verificar que la postulación exista antes de intentar acceder a sus propiedades
        if (postulacionRevisar != null) {
            Electivo electivo = postulacionRevisar.getElectivo();

            // Lógica de cupos
            if(electivo.getCupos() > 0){
                postulacionRevisar.setEstado(Estado.ACEPTADA);
                electivo.setCupos(electivo.getCupos() - 1);
                System.out.println("Postulación aceptada. Cupos restantes: " + electivo.getCupos());
            }
            else{
                postulacionRevisar.setEstado(Estado.RECHAZADA);
                System.out.println("Postulación rechazada por falta de cupos.");
            }
        } else {
            // Manejo simple para indicar que no se encontró el recurso
            System.out.println("Error: Postulación no encontrada con ID: " + postulacionId);
            // NOTA: En una aplicación Spring Boot real con manejo de excepciones,
            // PostulacionService debería lanzar RecursoNoEncontradoException.
        }
    }
}