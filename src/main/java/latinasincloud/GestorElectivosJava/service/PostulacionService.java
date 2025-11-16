package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.dto.ElectivoPreferenciaDTO;
import latinasincloud.GestorElectivosJava.dto.PostulacionRequestDTO;
import latinasincloud.GestorElectivosJava.exception.EstadoInvalidoException;
import latinasincloud.GestorElectivosJava.exception.RecursoNoEncontradoException;

import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Estado;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.model.Postulacion;


import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service // Anotación para que Spring lo reconozca
public class PostulacionService {

    private final List<Postulacion> postulaciones = new ArrayList<>();
    private static int contadorId = 1;

    private EstudianteService estudianteService;
    private ElectivoService electivoService;

    // Inyección de dependencias por constructor (buena práctica)
    public PostulacionService(EstudianteService estudianteService, ElectivoService electivoService) {
        this.estudianteService = estudianteService;
        this.electivoService = electivoService;
    }


    // MÉTODO EXISTENTE (Actualizado para incluir prioridad por defecto)
    public Postulacion crearPostulacion(int estudianteId, int electivoId) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
        Electivo electivo = electivoService.obtenerElectivoPorId(electivoId);

        Postulacion nueva = new Postulacion(contadorId++,estudiante,
                electivo, LocalDateTime.now(), Estado.PENDIENTE,
                1 // Prioridad por defecto 1 si se usa el método simple
        );

        postulaciones.add(nueva);
        estudiante.getPostulaciones().add(nueva);
        electivo.getPostulaciones().add(nueva);

        return nueva;
    }

    // NUEVO MÉTODO: Crea 3 postulaciones a partir del DTO
    public List<Postulacion> crearPostulacionesConPrioridad(PostulacionRequestDTO dto) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(dto.getEstudianteId());
        if (estudiante == null) {
            throw new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + dto.getEstudianteId());
        }

        if (dto.getPreferencias() == null || dto.getPreferencias().size() != 3) {
            throw new EstadoInvalidoException("El estudiante debe postular a exactamente 3 electivos.");
        }

        // Validación de unicidad de prioridades y electivos
        long prioridadesDistintas = dto.getPreferencias().stream().map(ElectivoPreferenciaDTO::getPrioridad).distinct().count();
        long electivosDistintos = dto.getPreferencias().stream().map(ElectivoPreferenciaDTO::getElectivoId).distinct().count();
        if (prioridadesDistintas != 3 || electivosDistintos != 3) {
            throw new EstadoInvalidoException("Las postulaciones deben ser para 3 electivos distintos y con prioridades únicas (1, 2, 3).");
        }

        List<Postulacion> nuevasPostulaciones = new ArrayList<>();
        for (ElectivoPreferenciaDTO pref : dto.getPreferencias()) {
            Electivo electivo = electivoService.obtenerElectivoPorId(pref.getElectivoId());
            if (electivo == null) {
                throw new RecursoNoEncontradoException("Electivo no encontrado con ID: " + pref.getElectivoId());
            }

            Postulacion nueva = new Postulacion(contadorId++,
                    estudiante,electivo,
                    LocalDateTime.now(), Estado.PENDIENTE,
                    pref.getPrioridad() // Asigna la prioridad del DTO
            );

            postulaciones.add(nueva);
            estudiante.getPostulaciones().add(nueva);
            nuevasPostulaciones.add(nueva);
        }
        return nuevasPostulaciones;
    }

    // NUEVO MÉTODO: LÓGICA DE ASIGNACIÓN MASIVA POR PRIORIDAD Y CUPOS
    public List<Postulacion> procesarAsignaciones() {
        // 1. Obtener postulaciones PENDIENTES
        List<Postulacion> postulacionesPendientes = postulaciones.stream()
                .filter(p -> p.getEstado() == Estado.PENDIENTE)
                .collect(Collectors.toList());

        List<Integer> estudiantesAceptadosId = new ArrayList<>();
        List<Postulacion> asignacionesFinales = new ArrayList<>();

        // 2. Iterar por Prioridad (1 es la más alta)
        for (int prioridad = 1; prioridad <= 3; prioridad++) {

            // CORRECCIÓN: Crear una copia efectivamente final de 'prioridad'
            final int prioridadActual = prioridad;

            // 3. Obtener postulaciones para la prioridad actual, ordenadas por fecha
            List<Postulacion> postulacionesPorPrioridad = postulacionesPendientes.stream()
                    // Usar la copia 'prioridadActual' en la lambda
                    .filter(p -> p.getPrioridad() == prioridadActual)
                    .sorted(Comparator.comparing(Postulacion::getFecha))
                    .collect(Collectors.toList());

            // 4. Bucle 'for-each' estándar para manejar las variables mutables
            for (Postulacion p : postulacionesPorPrioridad) {

                int estudianteId = p.getEstudiante().getId();
                Electivo electivo = p.getElectivo();

                // 4a. Chequear si el estudiante ya fue aceptado en una prioridad mayor
                if (estudiantesAceptadosId.contains(estudianteId)) {
                    p.setEstado(Estado.RECHAZADA);
                    continue;
                }

                // 5. Asignar si hay cupo
                if (electivo.getCupos() > 0) {
                    p.setEstado(Estado.ACEPTADA);
                    electivo.setCupos(electivo.getCupos() - 1); // Disminuir cupo
                    estudiantesAceptadosId.add(estudianteId); // Marcar estudiante como asignado
                    asignacionesFinales.add(p);
                } else {
                    // Rechazar por falta de cupos en esa prioridad
                    p.setEstado(Estado.RECHAZADA);
                }
            }
        }

        // 6. Limpieza final: Rechazar PENDIENTES que no fueron procesadas
        postulacionesPendientes.stream()
                .filter(p -> p.getEstado() == Estado.PENDIENTE)
                .forEach(p -> p.setEstado(Estado.RECHAZADA));

        return asignacionesFinales;
    }

    // --- MÉTODOS CRUD BÁSICOS ---

    public Postulacion obtenerPostulacionPorId(int id) {
        for (Postulacion postulacion : postulaciones) {
            if (postulacion.getId() == id) {
                return postulacion;
            }
        }
        return null;
    }

    public List<Postulacion> listaPostulaciones (){
        return postulaciones;
    }

    public boolean eliminarPostulacionPorId(int postulacionId) {
        Postulacion postulacion = obtenerPostulacionPorId(postulacionId);
        if  (postulacion != null) {
            postulaciones.remove(postulacion);
            System.out.println("¡La postulacion ha sido eliminada del registro exitosamente!");
            return true;
        }
        else{
            return false;}

    }
}