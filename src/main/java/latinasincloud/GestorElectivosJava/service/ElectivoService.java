package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Estado;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.model.Postulacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostulacionService {
    //agregar lista de postulaciones y contador Id incremental
    private final List<Postulacion> postulaciones = new ArrayList<>();
    private static int contadorId = 1;

    private EstudianteService estudianteService;
    private ElectivoService electivoService;


    public PostulacionService(EstudianteService estudianteService, ElectivoService electivoService) {
        this.estudianteService = estudianteService;
        this.electivoService = electivoService;
    }

    // 1. Crear profesor (POST) <-- COMENTARIO INCORRECTO
    public Postulacion crearPostulacion(int estudianteId, int electivoId) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
        Electivo electivo = electivoService.obtenerElectivoPorId(electivoId);

        // RIESGO DE NullPointerException si estudiante o electivo es null
        // (Aunque EstudianteController.java maneja RecursoNoEncontradoException, el Service debería hacer la validación)

        Postulacion nueva = new Postulacion(
                contadorId++,
                estudiante,
                electivo,
                LocalDateTime.now(),
                Estado.PENDIENTE
        );

        postulaciones.add(nueva);
        estudiante.getPostulaciones().add(nueva); // RIESGO DE NullPointerException
        electivo.getPostulaciones().add(nueva); // RIESGO DE NullPointerException

        return nueva;
    }

    public Postulacion obtenerPostulacionPorId(int id) {
        for (Postulacion postulacion : postulaciones) { // BÚSQUEDA LINEAL (O(n))
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
            System.out.println("¡La postulacion ha sido eliminada del registro exitosamente!"); // USANDO System.out.println
            return true;
        }
        else{
            return false;}
    }
}