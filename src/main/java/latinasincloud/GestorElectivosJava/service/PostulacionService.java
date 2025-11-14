package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException; // Necesario para validar IDs
import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Estado;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.model.Postulacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

// Para usar un logger en lugar de System.out.println
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostulacionService {

    // Uso de Logger: Mejor práctica que System.out.println
    private static final Logger logger = LoggerFactory.getLogger(PostulacionService.class);

    // MEJORA: Se usa Map para búsquedas O(1) en lugar de List para búsquedas O(n)
    private final Map<Integer, Postulacion> postulacionesMap = new HashMap<>();
    private static int contadorId = 1;

    private final EstudianteService estudianteService;
    private final ElectivoService electivoService;


    // Constructor con Inyección de Dependencias (final es buena práctica)
    public PostulacionService(EstudianteService estudianteService, ElectivoService electivoService) {
        this.estudianteService = estudianteService;
        this.electivoService = electivoService;
    }

    // 1. Crear Postulación (POST)
    public Postulacion crearPostulacion(int estudianteId, int electivoId) {
        // MEJORA: Validación de existencia y manejo de excepciones (RecursoNoEncontradoException)
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
        if (estudiante == null) {
            throw new RecursoNoEncontradoException("Estudiante no encontrada con ID: " + estudianteId);
        }

        Electivo electivo = electivoService.obtenerElectivoPorId(electivoId);
        if (electivo == null) {
            throw new RecursoNoEncontradoException("Electivo no encontrado con ID: " + electivoId);
        }

        Postulacion nueva = new Postulacion(
                contadorId++,
                estudiante,
                electivo,
                LocalDateTime.now(),
                Estado.PENDIENTE
        );

        // Uso de Map para almacenamiento
        postulacionesMap.put(nueva.getId(), nueva);

        // Vinculación bidireccional (asumiendo que las listas en los modelos están inicializadas)
        estudiante.getPostulaciones().add(nueva);
        electivo.getPostulaciones().add(nueva);

        logger.info("Postulación creada exitosamente con ID: {}", nueva.getId()); // Uso de Logger

        return nueva;
    }

    // 2. Obtener Postulación por ID (GET)
    // MEJORA: Acceso O(1) usando Map.get()
    public Postulacion obtenerPostulacionPorId(int id) {
        // Se puede añadir un Optional.ofNullable(postulacionesMap.get(id)) para mayor fluidez.
        return postulacionesMap.get(id);
    }

    // 3. Listar Postulaciones (GET)
    public List<Postulacion> listaPostulaciones (){
        // Devuelve una nueva List con los valores del Map (o simplemente Collections.unmodifiableList(new ArrayList<>(postulacionesMap.values())))
        return new ArrayList<>(postulacionesMap.values());
    }

    // 4. Eliminar Postulación por ID (DELETE)
    public boolean eliminarPostulacionPorId(int postulacionId) {
        // Map.remove(key) devuelve el valor eliminado (Postulacion) o null si no existe.
        Postulacion eliminada = postulacionesMap.remove(postulacionId);

        if (eliminada != null) {
            // Se asume que también se debería eliminar la postulación de las listas de Estudiante y Electivo
            eliminada.getEstudiante().getPostulaciones().remove(eliminada);
            eliminada.getElectivo().getPostulaciones().remove(eliminada);

            logger.info("La postulación con ID {} ha sido eliminada del registro exitosamente.", postulacionId); // Uso de Logger
            return true;
        }
        return false;
    }
}